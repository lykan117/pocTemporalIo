package com.poc.tcs.temporal;

import com.poc.tcs.temporal.activities.Activities;

import java.util.concurrent.atomic.AtomicInteger;

public class MockActivities implements Activities {
    private static String modo = "EXITO";
    private static final AtomicInteger intentos = new AtomicInteger(0);

    public static void setModo(String nuevoModo) {
        modo = nuevoModo;
        intentos.set(0);
    }

    @Override
    public void validarSolicitud(String solicitud) {
        System.out.println("✔ Validación exitosa: " + solicitud);
    }

    @Override
    public String solicitarFolio(String solicitud) {
        return "FOLIO-" + System.currentTimeMillis();
    }

    @Override
    public void procesarPago(String folio) {
        int intento = intentos.incrementAndGet();
        System.out.println("🔄 Intento #" + intento + " | modo=" + modo);

        switch (modo) {
            case "FALLO_TRANSITORIO":
                if (intento == 1) throw new RuntimeException("❌ Fallo temporal");
                break;
            case "FALLO_FATAL":
                throw new RuntimeException("💥 Fallo permanente");
        }

        System.out.println("✅ Pago exitoso para folio: " + folio);
    }

    @Override
    public void rollback(String motivo) {
        System.out.println("🔁 Rollback ejecutado: " + motivo);
    }

    @Override
    public void notificarResultado(String resultado) {
        System.out.println("📣 Resultado notificado: " + resultado);
    }

}
