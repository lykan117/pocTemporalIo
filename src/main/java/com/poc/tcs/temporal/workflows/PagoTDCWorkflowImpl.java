package com.poc.tcs.temporal.workflows;

import com.poc.tcs.temporal.activities.Activities;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class PagoTDCWorkflowImpl implements PagoTDCWorkflow {

    private final Activities activities =
            Workflow.newActivityStub(Activities.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(30))
                            .setRetryOptions(RetryOptions.newBuilder()
                                    .setInitialInterval(Duration.ofSeconds(5))
                                    .setMaximumAttempts(3)
                                    .build())
                            .build());

    @Override
    public void ejecutarPago(String datosSolicitud) {
        try {
            activities.validarSolicitud(datosSolicitud);
            String folio = activities.solicitarFolio(datosSolicitud);
            activities.procesarPago(folio);
            activities.notificarResultado("Pago Exitoso");
        } catch (Exception e) {
            activities.rollback("Reversando pago debido a error: " + e.getMessage());
            activities.notificarResultado("Error en el pago: " + e.getMessage());
        }
    }
}
