package com.poc.tcs.temporal;

import com.poc.tcs.temporal.activities.ActivitiesImpl;
import com.poc.tcs.temporal.workflows.PagoTDCWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TemporalApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemporalApplication.class, args);

		WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
		WorkflowClient client = WorkflowClient.newInstance(service);
		WorkerFactory factory = WorkerFactory.newInstance(client);
		Worker worker = factory.newWorker("PAGO_TDC_TASK_QUEUE");

		worker.registerWorkflowImplementationTypes(PagoTDCWorkflowImpl.class);
		worker.registerActivitiesImplementations(new ActivitiesImpl());

		factory.start();
	}

}
