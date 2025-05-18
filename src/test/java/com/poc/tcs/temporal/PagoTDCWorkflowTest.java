package com.poc.tcs.temporal;

import com.poc.tcs.temporal.activities.Activities;
import com.poc.tcs.temporal.workflows.PagoTDCWorkflow;
import com.poc.tcs.temporal.workflows.PagoTDCWorkflowImpl;
import io.temporal.testing.TestWorkflowEnvironment;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
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
    public void testWorkflowConRollbackSimulado() {
        PagoTDCWorkflow workflow = client.newWorkflowStub(
                PagoTDCWorkflow.class,
                WorkflowOptions.newBuilder().setTaskQueue("PAGO_TDC_TASK_QUEUE").build()
        );

        workflow.ejecutarPago("Prueba Unitaria");
    }

    public static class MockActivities implements Activities {
        private AtomicInteger intentos = new AtomicInteger(0);

        @Override
        public void validarSolicitud(String solicitud) {
            System.out.println("Validación exitosa para " + solicitud);
        }

        @Override
        public String solicitarFolio(String solicitud) {
            return "TEST-FOLIO";
        }

        @Override
        public void procesarPago(String folio) {
            if (intentos.incrementAndGet() < 2) {
                throw new RuntimeException("Fallo simulado");
            }
            System.out.println("Pago exitoso en test para folio " + folio);
        }

        @Override
        public void rollback(String motivo) {
            System.out.println("Rollback ejecutado en test: " + motivo);
        }

        @Override
        public void notificarResultado(String resultado) {
            System.out.println("Notificado: " + resultado);
        }
    }
}
