package com.tienda.pedidos.strategy;

import org.springframework.stereotype.Component;

@Component("MAYORISTA")
public class DescuentoMayorista implements EstrategiaDescuento {

    @Override
    public double calcularDescuento(double subtotal) {
        return subtotal * 0.25;
    }
}
