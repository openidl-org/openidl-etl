package org.openidl.etl.processors;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.openidl.etl.OpenIdlAuto;
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
			
			OpenIdlAuto openIdlAutoRecord = gson.fromJson(msg.getBody(), OpenIdlAuto.class);
			
			logger.info("Printing openIDLRecord " + openIdlAutoRecord);

			//transform the data from the SQS message to format expected to run the rule
			Map<String, String> data = new HashMap<String, String>();
			data.put("LOB", openIdlAutoRecord.getLineOfInsurance());
			data.put("state", openIdlAutoRecord.getStateCode());
			data.put("coverageCode", openIdlAutoRecord.getCoverage());
			data.put("programCode", openIdlAutoRecord.getProgram());
			data.put("sublineCode", openIdlAutoRecord.getSubline());
			data.put("driverTraining", openIdlAutoRecord.getPrivatePassengerDriversTraining());

			//setup the data in the expected format for that particular line
			DataValidationFact fact = new DataValidationFact(lineHelper.getFields(), data);

			//create the rule
			DataValidationRule record = new DataValidationRule(fact);
			record.setOriginalRecord(gson.toJson(openIdlAutoRecord));

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
				+ "  \"$schema\": \"./au-schema.json\",\r\n"
				+ "  \"ID\": \"Guid-123456789\",\r\n"
				+ "  \"EtlID\": \"loadId123\",\r\n"
				+ "  \"LineOfInsurance\": \"56\",\r\n"
				+ "  \"Coverage\": \"4\",\r\n"
				+ "  \"StateCode\": \"37\",\r\n"
				+ "  \"AccountingDate\": {\r\n"
				+ "    \"Month\": \"String\",\r\n"
				+ "    \"Year\": 2020\r\n"
				+ "  },\r\n"
				+ "  \"CompanyCode\": \"1234\",\r\n"
				+ "  \"Territory\": \"01\",\r\n"
				+ "  \"OptionalZipCodeIndicator\": \"String\",\r\n"
				+ "  \"TransactionCode\": \"Ref\",\r\n"
				+ "  \"PremiumLossAmounts\": {\r\n"
				+ "    \"Premium\": \"Number\",\r\n"
				+ "    \"Loss\": \"Number\"\r\n"
				+ "  },\r\n"
				+ "  \"Program\": \"3\",\r\n"
				+ "  \"Subline\": \"1\",\r\n"
				+ "  \"OperateAge\": \"INT\",\r\n"
				+ "  \"SexMaterialStatus\": \"Ref\",\r\n"
				+ "  \"VehicleUse\": \"Ref\",\r\n"
				+ "  \"VehicleProformance\": \"Ref\",\r\n"
				+ "  \"PrivatePassengerDriversTraining\": \"1\",\r\n"
				+ "  \"CommercialAutomobileClassification\": \"Ref\",\r\n"
				+ "  \"LiabilityLimitsAmount\": \"Ref\",\r\n"
				+ "  \"LiabilityLimitsAmountNJ\": \"Ref\",\r\n"
				+ "  \"LiabilityLimitsAmountPA\": \"Ref\",\r\n"
				+ "  \"DeductibleAmount\": \"Ref\",\r\n"
				+ "  \"VehicleClassBodyStyle\": \"Ref\",\r\n"
				+ "  \"VehicleClassBodySize\": \"Ref\",\r\n"
				+ "  \"ModelYear\": \"INT\",\r\n"
				+ "  \"UnisuredUnderinsuredMotorist\": \"Ref\",\r\n"
				+ "  \"Exposure\": \"Ref\",\r\n"
				+ "  \"ClaimCount\": \"?INT?\",\r\n"
				+ "  \"MonthsCovered\": \"Ref\",\r\n"
				+ "  \"CasueOfLoss\": \"Ref\",\r\n"
				+ "  \"TerrorismIndicator\": \"Ref\",\r\n"
				+ "  \"SinlgeMultiCar\": \"Ref\",\r\n"
				+ "  \"AccidentDate\": {\r\n"
				+ "    \"Month\": \"String\",\r\n"
				+ "    \"Year\": \"INT\"\r\n"
				+ "  },\r\n"
				+ "  \"PackageCode\": \"Ref\",\r\n"
				+ "  \"PoolAffiliation\": \"Ref\",\r\n"
				+ "  \"OccurenceOrPolicyIdentification\": {\r\n"
				+ "    \"PolicyNumber\": \"Ref\",\r\n"
				+ "    \"ClaimNumber\": \"Ref\",\r\n"
				+ "    \"ClaimIdentifier\": \"Ref\"\r\n"
				+ "  },\r\n"
				+ "  \"NCProgramEnhancemntIndicator\": \"Ref\",\r\n"
				+ "  \"ZipCode\": \"?String?\",\r\n"
				+ "  \"ZipeCodeSuffix(Optional)\": \"?String\",\r\n"
				+ "  \"UM/UIMStackingIndicator\": \"Ref\",\r\n"
				+ "  \"SymbolCode\": \"Ref\",\r\n"
				+ "  \"PassiveRestraintDiscountCode\": \"Ref\",\r\n"
				+ "  \"Anti-LockBrakesDiscountCode\": \"Ref\",\r\n"
				+ "  \"Anti-TheftDeviceDiscountCode\": \"Ref\",\r\n"
				+ "  \"DefensiveDriverDiscountCode\": \"Ref\",\r\n"
				+ "  \"PIPLimitsDeductibleCode\": \"Ref\",\r\n"
				+ "  \"NewJerseyPIPLimit\": \"Ref\",\r\n"
				+ "  \"NewJerseyDeductible\": \"Ref\",\r\n"
				+ "  \"RatingTerminalZoneCode\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(PrivatePassengerNon-FleetOnly)RateClassCode\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(PrivatePassengerNon-FleetOnly)PenaltyPoints/PercentOfSurchargeCode\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(PrivatePassengerMotorcycles,MotorScooters,Etc.LiabilityRecords)Class\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(PrivatePassengerMotorcycles,MotorScooters,Etc.LiabilityRecords)MiscellaneousSurchargeCode\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(PrivatePassengerMotorcycles,MotorScooters,Etc.PhysicalDamageRecords)Class\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(ExcessIndemnityPolicies(PrivatePassenger-NewYorkOnly(31)))\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(CommercialTrucks,Trucktractors,AndTrailersLiabilityRecords)TypeOfVehicle/BusinessUse/RadiusOfOperations\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(CommercialTrucks,Trucktractors,AndTrailersLiabilityRecords)SecondaryClassificationCode\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(CommercialTrucks,Trucktractors,AndTrailersPhysicalDamageRecords)Class\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(CommercialTrucks,Trucktractors,AndTrailersPhysicalDamageRecords)CommercialSurchargeCode\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(PublicsLiabilityRecords)Classification\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(PublicsLiabilityRecords)SeatingCapacityCode\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(PublicsLiabilityRecords)CommeericialSurchargeCode\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(PublicsPhysicalDamageRecords)Classification\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(PublicsPhysicalDamageRecords)CommercialSurchargeCode\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(GaragesLiability)Class\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(GaragesLiability)CommercialSurchargeCode\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(PrivatePassengerTypes)Class\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(PrivatePassengerTypes)CommercialSurchargeCode\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(TransportationOfMigrantWorkers)Class\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(TransportationOfMigrantWorkers)CommercialSurchargeCode\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(Non-OwnedAutomobiles)Class\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(Non-OwnedAutomobiles)CommercialSurchargeCode\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(CompositeRatingPlan)Class\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(CompositeRatingPlan)CommercialSurchargeCode\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(ExcessIndemnityPolicies:OtherThanPrivatePassengerNon-Fleet-NewYorkOnly)Class\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(ExcessIndemnityPolicies:OtherThanPrivatePassengerNon-Fleet-NewYorkOnly)CommercialSurchargeCode\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(AllOtherClassifications)Class\": \"Ref\",\r\n"
				+ "  \"ClassificationCodesAssignedRisks(AllOtherClassification)CommercialSurchargeCode\": \"Ref\",\r\n"
				+ "  \"ExperienceRatingModificationFactor\": \"Ref\",\r\n"
				+ "  \"LimitedCode-LossTransactionCode\": \"Ref\",\r\n"
				+ "  \"ExceptionCodeA(TortLimitation)(Kentucky)\": \"Ref\",\r\n"
				+ "  \"ExceptionCodeA(Intrastate/InterstateCode)(Michigan)\": \"Ref\",\r\n"
				+ "  \"ExceptionCodeA(Threshold/TortLimitation)(NewJersey)\": \"Ref\",\r\n"
				+ "  \"ExceptionCodeA(AccidentPreventionCredit)(NewYork)\": \"Ref\",\r\n"
				+ "  \"ExceptionCodeA(TortLimitation)(Pennesylvania)\": \"Ref\",\r\n"
				+ "  \"ExceptionCodeA(S.C.ReinsuranceFacility(S.C.R.F))(SouthCarolina)\": \"Ref\",\r\n"
				+ "  \"ExceptionCodeB(Multi-CarRisks)(NewJersey)\": \"Ref\",\r\n"
				+ "  \"ExceptionCodeB(PrimaryNo-FaultHealthPlan)(NewYork)\": \"Ref\",\r\n"
				+ "  \"ExceptionCodeB(55&Overdiscount)(Pennsylvania)\": \"Ref\",\r\n"
				+ "  \"ExceptionCodeC(EngineSize)(NewJersey)\": \"Ref\",\r\n"
				+ "  \"ExceptionCodeC(CombinedFirstPartyBenefits(IndivisablePremiumPolicies))\": \"Ref\",\r\n"
				+ "  \"ExceptionCodeD(NewYorkTaxicabs&PublicLivery)(NewYork)\": \"Ref\",\r\n"
				+ "  \"CompanyUse\": \"Ref\",\r\n"
				+ "  \"VehicleIdentification\": \"Ref\"\r\n"
				+ "}\r\n"
				+ "";
		message.setBody(sampleBody);
		messages.add(message);
		event.setRecords(messages);
		p.handleRequest(event, null);
	}
}
