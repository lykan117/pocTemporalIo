package com.poc.tcs.temporal.activities;




public class ActivitiesImpl implements Activities {
    private static int intentos = 0;

    @Override
    public void validarSolicitud(String solicitud) {
        System.out.println("Validación exitosa para solicitud: " + solicitud);
    }

    @Override
    public String solicitarFolio(String solicitud) {
        String folio = "FOLIO-" + System.currentTimeMillis();
        System.out.println("Folio generado: " + folio);
        return folio;
    }

    @Override
    public void procesarPago(String folio) {
        intentos++;
        System.out.println("[Pago] Intento " + intentos + " para folio " + folio);
        if (intentos == 1) {
            throw new RuntimeException("Simulación de error en el procesamiento de pago");
        }
        boolean fallo = true;
        if (fallo) {
            throw new RuntimeException("Simulación de error");
        }

        System.out.println("✔ Pago exitoso con folio: " + folio);
    }

    @Override
    public void rollback(String motivo) {
        System.out.println("Rollback ejecutado. Motivo: " + motivo);
    }

    @Override
    public void notificarResultado(String resultado) {
        System.out.println("Resultado notificado: " + resultado);
    }

}