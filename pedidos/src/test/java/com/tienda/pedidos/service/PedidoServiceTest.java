package com.tienda.pedidos.service;

import com.tienda.pedidos.dto.PedidoRequestDTO;
import com.tienda.pedidos.dto.PedidoResponseDTO;
import com.tienda.pedidos.entity.Pedido;
import com.tienda.pedidos.exception.RecursoNoEncontradoException;
import com.tienda.pedidos.repository.PedidoRepository;
import com.tienda.pedidos.strategy.DescuentoMayorista;
import com.tienda.pedidos.strategy.DescuentoRegular;
import com.tienda.pedidos.strategy.DescuentoVip;
import com.tienda.pedidos.strategy.EstrategiaDescuento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository repo;

    @Mock
    private NotificacionService notificador;

    private PedidoService service;

    @BeforeEach
    void setUp() {
        Map<String, EstrategiaDescuento> estrategias = new HashMap<>();
        estrategias.put("REGULAR", new DescuentoRegular());
        estrategias.put("VIP", new DescuentoVip());
        estrategias.put("MAYORISTA", new DescuentoMayorista());

        service = new PedidoServiceImpl(repo, notificador, estrategias);
    }

    @Test
    @DisplayName("Debe crear pedido para cliente REGULAR con 0% de descuento")
    void crearPedidoRegular() {
        PedidoRequestDTO dto = new PedidoRequestDTO("juan@correo.com", "REGULAR", 100000.0);

        when(repo.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido p = invocation.getArgument(0);
            p.setId(1L);
            return p;
        });

        PedidoResponseDTO response = service.crear(dto);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("juan@correo.com", response.getClienteEmail());
        assertEquals("REGULAR", response.getTipoCliente());
        assertEquals(100000.0, response.getSubtotal());
        assertEquals(0.0, response.getDescuento());
        assertEquals(19000.0, response.getIva());
        assertEquals(119000.0, response.getTotal());
        assertEquals("CREADO", response.getEstado());

        verify(repo).save(any(Pedido.class));
        verify(notificador).enviarConfirmacion("juan@correo.com", 119000.0);
    }

    @Test
    @DisplayName("Debe crear pedido para cliente VIP con 15% de descuento")
    void crearPedidoVip() {
        PedidoRequestDTO dto = new PedidoRequestDTO("ana@correo.com", "VIP", 100000.0);

        when(repo.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido p = invocation.getArgument(0);
            p.setId(2L);
            return p;
        });

        PedidoResponseDTO response = service.crear(dto);

        assertNotNull(response);
        assertEquals(2L, response.getId());
        assertEquals("VIP", response.getTipoCliente());
        assertEquals(15000.0, response.getDescuento());
        assertEquals(16150.0, response.getIva());
        assertEquals(101150.0, response.getTotal());

        verify(repo).save(any(Pedido.class));
        verify(notificador).enviarConfirmacion("ana@correo.com", 101150.0);
    }

    @Test
    @DisplayName("Debe crear pedido para cliente MAYORISTA con 25% de descuento")
    void crearPedidoMayorista() {
        PedidoRequestDTO dto = new PedidoRequestDTO("distribuidor@correo.com", "MAYORISTA", 100000.0);

        when(repo.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido p = invocation.getArgument(0);
            p.setId(3L);
            return p;
        });

        PedidoResponseDTO response = service.crear(dto);

        assertNotNull(response);
        assertEquals(3L, response.getId());
        assertEquals("MAYORISTA", response.getTipoCliente());
        assertEquals(25000.0, response.getDescuento());
        assertEquals(14250.0, response.getIva());
        assertEquals(89250.0, response.getTotal());

        verify(repo).save(any(Pedido.class));
        verify(notificador).enviarConfirmacion("distribuidor@correo.com", 89250.0);
    }

    @Test
    @DisplayName("Debe buscar pedido por ID exitosamente")
    void buscarPorIdExitoso() {
        Pedido p = new Pedido();
        p.setId(10L);
        p.setClienteEmail("cliente@correo.com");
        p.setTipoCliente("VIP");
        p.setSubtotal(50000.0);
        p.setDescuento(7500.0);
        p.setIva(8075.0);
        p.setTotal(50575.0);
        p.setEstado("CREADO");

        when(repo.findById(10L)).thenReturn(Optional.of(p));

        PedidoResponseDTO response = service.buscarPorId(10L);

        assertNotNull(response);
        assertEquals(10L, response.getId());
        assertEquals("cliente@correo.com", response.getClienteEmail());
    }

    @Test
    @DisplayName("Debe lanzar RecursoNoEncontradoException cuando pedido no existe")
    void buscarPorIdNoEncontrado() {
        when(repo.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> service.buscarPorId(999L));
    }
}
