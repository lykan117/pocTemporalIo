package com.poc.tcs.temporal.activities;

import java.util.Random;
import java.util.UUID;

public class ActivitiesImpl implements Activities {
    @Override
    public void validarSolicitud(String solicitud) {
        System.out.println("[ValidarSolicitud] Validación exitosa.");
    }

    @Override
    public String solicitarFolio(String solicitud) {
        System.out.println("[SolicitarFolio] Generando folio...");
        return UUID.randomUUID().toString();
    }

    @Override
    public void procesarPago(String folio) {
        System.out.println("[ProcesarPago] Procesando pago con folio: " + folio);
        if (new Random().nextBoolean()) { // Simula falla aleatoria
            throw new RuntimeException("Fallo simulado en procesamiento de pago");
        }
    }

    @Override
    public void rollback(String motivo) {
        System.out.println("[Rollback] Ejecutando rollback: " + motivo);
    }

    @Override
    public void notificarResultado(String resultado) {
        System.out.println("[NotificarResultado] " + resultado);
    }
}
