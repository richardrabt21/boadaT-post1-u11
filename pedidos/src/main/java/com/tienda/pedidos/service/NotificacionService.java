package com.tienda.pedidos.service;

public interface NotificacionService {
    void enviarConfirmacion(String clienteEmail, double total);
}
