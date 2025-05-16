package com.poc.tcs.temporal.workflows;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface PagoTDCWorkflow {
    @WorkflowMethod
    void ejecutarPago(String datosSolicitud);
}
