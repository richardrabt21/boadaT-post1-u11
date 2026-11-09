package com.tienda.pedidos.dto;

public class PedidoResponseDTO {

    private Long id;
    private String clienteEmail;
    private String tipoCliente;
    private Double subtotal;
    private Double descuento;
    private Double iva;
    private Double total;
    private String estado;
    // NUNCA incluye notasInternas

    public PedidoResponseDTO() {
    }

    public PedidoResponseDTO(Long id, String clienteEmail, String tipoCliente, Double subtotal,
                             Double descuento, Double iva, Double total, String estado) {
        this.id = id;
        this.clienteEmail = clienteEmail;
        this.tipoCliente = tipoCliente;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.iva = iva;
        this.total = total;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
