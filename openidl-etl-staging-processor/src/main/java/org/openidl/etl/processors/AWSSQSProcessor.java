package org.openidl.etl.processors;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;

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

public class AWSSQSProcessor implements RequestHandler<SQSEvent, Void> {

	private static final Logger logger = LoggerFactory.getLogger(AWSSQSProcessor.class);

	private static KieSession kieSession;
	private static LineHelper lineHelper = new AutoHelper();
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
			record.setOriginalRecord(msg.getBody());

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
				String loadedRecord = lineHelper.loadErrors(record);
				sendFailure("Failed", loadedRecord);
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
		
		// to create a string from json use https://jsontostring.com/
		String sampleBody = "{\"RecordLOB\":\"Auto\",\"RecordType\":\"Policy\",\"Policy\":{\"CompanyID\":\"12345\",\"AnnualStatementLine\":\"19.1\",\"PolicyCategory\":\"Personal\",\"Subline\":\"PD\",\"PolicyIdentifier\":\"12345678\",\"PolicyEffectiveDate\":\"2022-01-01\",\"TransactionType\":\"CHG\",\"TransactionEffectiveDate\":\"2022-03-01\",\"TransactionExpirationDate\":\"2023-01-01\",\"Market\":\"??\",\"AccountingDate\":\"2022-03-01\",\"Program\":\"3\",\"MultiCarDiscountCode\":\"Yes\",\"PackageCode\":\"Standalone\",\"PoolAffiliation\":\"None\",\"NCProgramEnhancementIndicator\":\"No\",\"NCReinsuranceFacility\":\"No\",\"SCReinsuranceFacility\":\"No\",\"MultiCarRisks\":\"No\"},\"Driver\":{\"Gender\":\"Female\",\"DriverAge\":50,\"MaritalStatus\":\"Married\",\"SafeDrivingDefensiveDriverDiscount\":\"Yes\",\"GoodStudentDiscount\":\"Yes\",\"NumberOfPenaltyPoints\":0,\"DriversTrainingDiscount\":\"Yes\",\"55AndOverDiscount\":\"No\",\"AccidentPreventionCredit\":\"Yes\"},\"Vehicle\":{\"BodyStyle\":\"??\",\"BodySize\":\"??\",\"EngineSizeMotorcycle\":\"??\",\"VIN\":\"ABCDEFG12345678\",\"GarageState\":\"NJ\",\"GarageZIP5\":\"02002\",\"GarageZIP4\":\"0000\",\"VehicleYear\":\"2010\",\"VehicleUse\":\"Personal\",\"VechicleSymbol\":\"??\",\"VechiclePerformance\":\"??\",\"PerCommuteMiles\":20,\"AnnualVehicleMiles\":10000,\"AntilockBrakesDiscount\":\"Yes\",\"SafetyRestraintDiscount\":\"Yes\",\"AntiTheftDeviceDiscount\":\"No\"},\"Coverage\":{\"CoverageCode\":\"1\"}}"; //,\"PerPersonLimit\":100000,\"PerAccidentLimit\":300000,\"PhysicalDamageLimit\":100000,\"CombinedSingleLimit\":\"??\",\"Premium\":100,\"Deductible\":2500,\"UMUIMStackingIndicator\":\"No\",\"MedicalExpensesDeductibleAmount\":2000,\"NJThresholdTortLimitation\":\"No\",\"PrimaryNoFaultHealthPlan\":\"??\",\"CombinedFirstPartyBenefits\":\"??\"}},{\"system\":{\"ruleValidation\":{\"valid\":\"Yes\",\"validated\":\"Yes\"}},\"RecordLOB\":\"Auto\",\"RecordType\":\"Policy\",\"Policy\":{\"CompanyID\":\"12345\",\"AnnualStatementLine\":\"19.1\",\"PolicyCategory\":\"Personal\",\"Subline\":\"PD\",\"PolicyIdentifier\":\"12345678\",\"PolicyEffectiveDate\":\"2022-01-01\",\"TransactionType\":\"CHG\",\"TransactionEffectiveDate\":\"2022-03-01\",\"TransactionExpirationDate\":\"2023-01-01\",\"Market\":\"??\",\"AccountingDate\":\"2022-03-01\",\"Program\":\"??\",\"MultiCarDiscountCode\":\"Yes\",\"PackageCode\":\"Standalone\",\"PoolAffiliation\":\"None\",\"NCProgramEnhancementIndicator\":\"No\",\"NCReinsuranceFacility\":\"No\",\"SCReinsuranceFacility\":\"No\",\"MultiCarRisks\":\"No\"},\"Driver\":{\"Gender\":\"Female\",\"DriverAge\":50,\"MaritalStatus\":\"Married\",\"SafeDrivingDefensiveDriverDiscount\":\"Yes\",\"GoodStudentDiscount\":\"Yes\",\"NumberOfPenaltyPoints\":0,\"DriversTrainingDiscount\":\"Yes\",\"55AndOverDiscount\":\"No\",\"AccidentPreventionCredit\":\"Yes\"},\"Vehicle\":{\"BodyStyle\":\"??\",\"BodySize\":\"??\",\"EngineSizeMotorcycle\":\"??\",\"VIN\":\"ABCDEFG12345678\",\"GarageState\":\"NJ\",\"GarageZIP5\":\"02002\",\"GarageZIP4\":\"0000\",\"VehicleYear\":\"2010\",\"VehicleUse\":\"Personal\",\"VechicleSymbol\":\"??\",\"VechiclePerformance\":\"??\",\"PerCommuteMiles\":20,\"AnnualVehicleMiles\":10000,\"AntilockBrakesDiscount\":\"Yes\",\"SafetyRestraintDiscount\":\"Yes\",\"AntiTheftDeviceDiscount\":\"No\"},\"Coverage\":{\"CoverageCode\":\"??\",\"PerPersonLimit\":100000,\"PerAccidentLimit\":300000,\"PhysicalDamageLimit\":100000,\"CombinedSingleLimit\":\"??\",\"Premium\":100,\"Deductible\":2500,\"UMUIMStackingIndicator\":\"No\",\"MedicalExpensesDeductibleAmount\":2000,\"NJThresholdTortLimitation\":\"No\",\"PrimaryNoFaultHealthPlan\":\"??\",\"CombinedFirstPartyBenefits\":\"??\"}},{\"system\":{\"ruleValidation\":{\"valid\":\"Yes\",\"validated\":\"Yes\"}},\"RecordLOB\":\"Auto\",\"RecordType\":\"Policy\",\"Policy\":{\"CompanyID\":\"12345\",\"AnnualStatementLine\":\"19.1\",\"PolicyCategory\":\"Personal\",\"Subline\":\"COMP\",\"PolicyIdentifier\":\"12345678\",\"PolicyEffectiveDate\":\"2022-01-01\",\"TransactionType\":\"CHG\",\"TransactionEffectiveDate\":\"2022-03-01\",\"TransactionExpirationDate\":\"2023-01-01\",\"Market\":\"??\",\"AccountingDate\":\"2022-03-01\",\"Program\":\"??\",\"MultiCarDiscountCode\":\"Yes\",\"PackageCode\":\"Standalone\",\"PoolAffiliation\":\"None\",\"NCProgramEnhancementIndicator\":\"No\",\"NCReinsuranceFacility\":\"No\",\"SCReinsuranceFacility\":\"No\",\"MultiCarRisks\":\"No\"},\"Driver\":{\"Gender\":\"Female\",\"DriverAge\":50,\"MaritalStatus\":\"Married\",\"SafeDrivingDefensiveDriverDiscount\":\"Yes\",\"GoodStudentDiscount\":\"Yes\",\"NumberOfPenaltyPoints\":0,\"DriversTrainingDiscount\":\"Yes\",\"55AndOverDiscount\":\"No\",\"AccidentPreventionCredit\":\"Yes\"},\"Vehicle\":{\"BodyStyle\":\"??\",\"BodySize\":\"??\",\"EngineSizeMotorcycle\":\"??\",\"VIN\":\"ABCDEFG12345678\",\"GarageState\":\"NJ\",\"GarageZIP5\":\"02002\",\"GarageZIP4\":\"0000\",\"VehicleYear\":\"2010\",\"VehicleUse\":\"Personal\",\"VechicleSymbol\":\"??\",\"VechiclePerformance\":\"??\",\"PerCommuteMiles\":20,\"AnnualVehicleMiles\":10000,\"AntilockBrakesDiscount\":\"Yes\",\"SafetyRestraintDiscount\":\"Yes\",\"AntiTheftDeviceDiscount\":\"No\"},\"Coverage\":{\"CoverageCode\":\"??\",\"PerPersonLimit\":100000,\"PerAccidentLimit\":300000,\"PhysicalDamageLimit\":100000,\"CombinedSingleLimit\":\"??\",\"Premium\":100,\"Deductible\":2500,\"UMUIMStackingIndicator\":\"No\",\"MedicalExpensesDeductibleAmount\":2000,\"NJThresholdTortLimitation\":\"No\",\"PrimaryNoFaultHealthPlan\":\"??\",\"CombinedFirstPartyBenefits\":\"??\"}}";
		message.setBody(sampleBody);
		messages.add(message);
		event.setRecords(messages);
		p.handleRequest(event, null);
	}
}
