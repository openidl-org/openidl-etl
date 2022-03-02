package org.openidl.etl.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.openidl.etl.AutoHelper;
import org.openidl.etl.DataValidationFact;
import org.openidl.etl.DataValidationRule;
import org.openidl.etl.LineHelper;
import org.openidl.etl.ValidationOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.google.gson.Gson;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AWSSQSProcessor implements RequestHandler<SQSEvent, Void> {

	private static final Logger logger = LoggerFactory.getLogger(AWSSQSProcessor.class);

	private static KieSession kieSession;
	private static LineHelper lineHelper = new AutoHelper();
	private static Gson gson = new Gson();
	private static AmazonSQS sqs;
	private static Properties configuration;

	static {

		logger.info("Loading Properties");

		try (InputStream input = AWSSQSProcessor.class.getClassLoader().getResourceAsStream("config.properties")) {

            configuration = new Properties();

            if (input == null) {
                throw new RuntimeException("Unable to find config.properties");
            }

            //load a properties file from class path, inside static method
            configuration.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
        }


		logger.info("Intializing Rules Processor");
		
		//get a handle of the main KIE Services
		KieServices kieServices = KieServices.Factory.get();

		//Get a handle to the rule file
		Resource dt = ResourceFactory.newClassPathResource(lineHelper.getRuleFilePath(), AWSSQSProcessor.class);

		//write it to the KIE virtual file system
		KieFileSystem kieFileSystem = kieServices.newKieFileSystem().write(dt);

		//Build everything
		KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
		kieBuilder.buildAll();

		//get the repository
		KieRepository kieRepository = kieServices.getRepository();

		//Get a release ID
		ReleaseId krDefaultReleaseId = kieRepository.getDefaultReleaseId();
		KieContainer kieContainer = kieServices.newKieContainer(krDefaultReleaseId);

		//get a new session from the container
		kieSession = kieContainer.newKieSession();
		
		logger.info("Done setting up KIE services");

		sqs = AmazonSQSClientBuilder.defaultClient();

	}

	@Override
	public Void handleRequest(SQSEvent event, Context context) {
		
		logger.info("Received messaged from SQS " + event.getRecords().size());
		
		//keep a list of all records to get the validation results later
		List<DataValidationRule> records = new ArrayList<>();

		//iterate over each of the records in the SQS message
		for (SQSMessage msg : event.getRecords()) {
			logger.info(new String(msg.getBody()));
			
			// OpenIdlAuto openIdlAutoRecord = gson.fromJson(msg.getBody(), OpenIdlAuto.class);
			Map<String,String> data = lineHelper.mapFromOpenidlRecord(msg.getBody());
			
			//setup the data in the expected format for that particular line
			DataValidationFact fact = new DataValidationFact(lineHelper.getFields(), data);

			//create the rule
			DataValidationRule record = new DataValidationRule(fact);
			record.setOriginalRecord(gson.toJson(msg.getBody()));

			//add the record to the 
			kieSession.insert(record);
			
			//add the record to keep track of results later
			records.add(record);
		}

		//Run all the rules
		kieSession.fireAllRules();

		//get the list of records to see if there are any validations errors
		for (DataValidationRule record : records) {
			List<ValidationOutput> outputs = record.getOutput().getValidations();

			logger.info("Found Errors: " + outputs.size());
			if (outputs.size() > 0 ) {
				sendFailure("Failed", record.getOriginalRecord());
			} else {
				sendSuccess("Success", record.getOriginalRecord());
			}

			for (ValidationOutput o : outputs) {
				logger.info(o.toString());
			}
		}
		return null;
	}
	
	public void sendSuccess(String message, String record) {
		logger.info("Success: " + message);
		SendMessageRequest sendMessageRequest = new SendMessageRequest()
			.withQueueUrl(configuration.getProperty("SUCCESS_QUEUE_URL"))
			.withMessageBody(record);
		sqs.sendMessage(sendMessageRequest);
	}

	public void sendFailure(String message, String record) {
		logger.info("Failure: " + message);
		SendMessageRequest sendMessageRequest = new SendMessageRequest()
			.withQueueUrl(configuration.getProperty("FAILURE_QUEUE_URL"))
			.withMessageBody(record);
		sqs.sendMessage(sendMessageRequest);
	}

	public static void main(String[] args) {
		AWSSQSProcessor p = new AWSSQSProcessor();
		SQSEvent event = new SQSEvent();
		List<SQSMessage> messages = new ArrayList<>();
		SQSMessage message = new SQSMessage();
		
		String sampleBody = "{\r\n"
				+ "  \"_id\": \"ID0000001\",\r\n"
				+ "  \"Policy\": {\r\n"
				+ "  \"PolicyNumber\": \"polno123\"\r\n"
				+ "  }\r\n"
				+ "}\r\n"
				+ "";
		message.setBody(sampleBody);
		messages.add(message);
		event.setRecords(messages);
		p.handleRequest(event, null);
	}
}
