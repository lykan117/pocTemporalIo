package com.poc.tcs.temporal.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface Activities {
    @ActivityMethod
    void validarSolicitud(String solicitud);

    @ActivityMethod
    String solicitarFolio(String solicitud);

    @ActivityMethod
    void procesarPago(String folio);

    @ActivityMethod
    void rollback(String motivo);

    @ActivityMethod
    void notificarResultado(String resultado);

}
