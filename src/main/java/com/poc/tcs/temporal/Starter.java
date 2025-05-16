package com.poc.tcs.temporal;

import com.poc.tcs.temporal.workflows.PagoTDCWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;

public class Starter {
    public static void main(String[] args) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);

        PagoTDCWorkflow workflow = client.newWorkflowStub(
                PagoTDCWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setTaskQueue("PAGO_TDC_TASK_QUEUE")
                        .build());

        workflow.ejecutarPago("Datos simulados");
    }
}
