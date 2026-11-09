package com.tienda.pedidos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class PedidoRequestDTO {

    @Schema(description = "Correo del cliente", example = "ana@correo.com")
    @NotBlank(message = "El email del cliente es obligatorio")
    private String clienteEmail;

    @Schema(description = "Tipo de cliente, determina el descuento aplicado",
            allowableValues = {"REGULAR", "VIP", "MAYORISTA"}, example = "VIP")
    @NotBlank(message = "El tipo de cliente es obligatorio")
    private String tipoCliente; // REGULAR, VIP o MAYORISTA

    @Schema(description = "Subtotal del pedido antes de descuento e IVA", example = "100000.00")
    @Positive(message = "El subtotal debe ser mayor a cero")
    private Double subtotal;

    public PedidoRequestDTO() {
    }

    public PedidoRequestDTO(String clienteEmail, String tipoCliente, Double subtotal) {
        this.clienteEmail = clienteEmail;
        this.tipoCliente = tipoCliente;
        this.subtotal = subtotal;
    }

    public String getClienteEmail() {
        return clienteEmail;
    }

    public void setClienteEmail(String clienteEmail) {
        this.clienteEmail = clienteEmail;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}
