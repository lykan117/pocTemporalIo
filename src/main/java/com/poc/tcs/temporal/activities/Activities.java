package com.poc.tcs.temporal.activities;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface Activities {
    void validarSolicitud(String solicitud);
    String solicitarFolio(String solicitud);
    void procesarPago(String folio);
    void rollback(String motivo);
    void notificarResultado(String resultado);
}
