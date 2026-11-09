package com.tienda.pedidos.service;

import org.springframework.stereotype.Service;

@Service
public class NotificacionEmailServiceImpl implements NotificacionService {

    @Override
    public void enviarConfirmacion(String clienteEmail, double total) {
        // simulacion de envio de correo (fuera del alcance de este laboratorio)
        System.out.println("Correo enviado a " + clienteEmail);
    }
}
