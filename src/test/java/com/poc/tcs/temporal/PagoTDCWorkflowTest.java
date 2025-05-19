package com.poc.tcs.temporal;

import com.poc.tcs.temporal.activities.Activities;
import com.poc.tcs.temporal.workflows.PagoTDCWorkflow;
import com.poc.tcs.temporal.workflows.PagoTDCWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.testing.TestWorkflowEnvironment;
import io.temporal.worker.Worker;
import org.junit.jupiter.api.*;

import java.util.concurrent.atomic.AtomicInteger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PagoTDCWorkflowTest {

    private TestWorkflowEnvironment testEnv;
    private WorkflowClient client;

    @BeforeAll
    public void setup() {
        testEnv = TestWorkflowEnvironment.newInstance();
        Worker worker = testEnv.newWorker("PAGO_TDC_TASK_QUEUE");
        worker.registerWorkflowImplementationTypes(PagoTDCWorkflowImpl.class);
        worker.registerActivitiesImplementations(new MockActivities());
        testEnv.start();
        client = testEnv.getWorkflowClient();
    }

    @AfterAll
    public void teardown() {
        testEnv.close();
    }

    @Test
    public void testExitoDirecto() {
        System.out.println("TEST: Éxito sin error");
        MockActivities.setModo("EXITO");
        ejecutar("CASO_EXITO");
    }

    @Test
    public void testRetryYExito() {
        System.out.println("TEST: Fallo inicial, éxito en retry");
        MockActivities.setModo("FALLO_TRANSITORIO");
        ejecutar("CASO_REINTENTO");
    }

    @Test
    public void testFalloYRollback() {
        System.out.println("TEST: Fallo total, se ejecuta rollback");
        MockActivities.setModo("FALLO_FATAL");
        ejecutar("CASO_FALLO");
    }

    private void ejecutar(String solicitud) {
        PagoTDCWorkflow workflow = client.newWorkflowStub(
                PagoTDCWorkflow.class,
                WorkflowOptions.newBuilder().setTaskQueue("PAGO_TDC_TASK_QUEUE").build()
        );
        workflow.ejecutarPago(solicitud);
    }

}
