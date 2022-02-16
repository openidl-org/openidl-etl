package org.openidl.etl;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import org.openidl.etl.model.OpenidlAutoPolicyRecord;

/**
 * Specifically made for the Auto line
 * @author findlayc
 *
 */
public class AutoHelper implements LineHelper{

	private static Gson gson = new Gson();

	@Override
	public Map<String, DataFactProperty> getFields(){
		
		Map<String, DataFactProperty> fields = new HashMap<String, DataFactProperty>();
		
		fields.put("LOB", new DataFactProperty("LOB", "LOB", 10, "STRING", "STRING", ""));
		fields.put("state", new DataFactProperty("state", "state", 10, "STRING", "STRING", ""));
		fields.put("coverageCode", new DataFactProperty("coverageCode", "coverageCode", 10, "STRING", "STRING", ""));
		fields.put("programCode", new DataFactProperty("programCode", "programCode", 10, "STRING", "STRING", ""));
		fields.put("causeOfLoss", new DataFactProperty("causeOfLoss", "causeOfLoss", 10, "STRING", "STRING", ""));
		fields.put("transactionCode", new DataFactProperty("programCode", "programCode", 10, "STRING", "STRING", ""));
		fields.put("limitLossCD", new DataFactProperty("limitLossCD", "limitLossCD", 10, "STRING", "STRING", ""));
		fields.put("sublineCode", new DataFactProperty("sublineCode", "sublineCode", 10, "STRING", "STRING", ""));
		fields.put("driverTraining", new DataFactProperty("driverTraining", "driverTraining", 10, "STRING", "STRING", ""));
		fields.put("operatorAge", new DataFactProperty("operatorAge", "operatorAge", 10, "STRING", "STRING", ""));
		fields.put("maritalStatus", new DataFactProperty("maritalStatus", "maritalStatus", 10, "STRING", "STRING", ""));
		fields.put("vehicleUse", new DataFactProperty("vehicleUse", "vehicleUse", 10, "STRING", "STRING", ""));
		fields.put("vehiclePerformance", new DataFactProperty("vehiclePerformance", "vehiclePerformance", 10, "STRING", "STRING", ""));
		fields.put("liabilityLimit", new DataFactProperty("liabilityLimit", "liabilityLimit", 10, "STRING", "STRING", ""));
		fields.put("deductible", new DataFactProperty("deductible", "deductible", 10, "STRING", "STRING", ""));
		fields.put("bodyStyle", new DataFactProperty("bodyStyle", "bodyStyle", 10, "STRING", "STRING", ""));
		fields.put("commercialClass", new DataFactProperty("commercialClass", "commercialClass", 10, "STRING", "STRING", ""));
		fields.put("poolCode", new DataFactProperty("poolCode", "poolCode", 10, "STRING", "STRING", ""));
		fields.put("bodySize", new DataFactProperty("bodySize", "bodySize", 10, "STRING", "STRING", ""));
		fields.put("modelYear", new DataFactProperty("modelYear", "modelYear", 10, "STRING", "STRING", ""));
		fields.put("uninsuredMotorist", new DataFactProperty("uninsuredMotorist", "uninsuredMotorist", 10, "STRING", "STRING", ""));
		fields.put("companyNo", new DataFactProperty("companyNo", "companyNo", 10, "STRING", "STRING", ""));
		fields.put("terrorismIndicator", new DataFactProperty("terrorismIndicator", "terrorismIndicator", 10, "STRING", "STRING", ""));
		fields.put("packageCode", new DataFactProperty("packageCode", "packageCode", 10, "STRING", "STRING", ""));
		fields.put("optZipCdInd", new DataFactProperty("optZipCdInd", "optZipCdInd", 10, "STRING", "STRING", ""));
		fields.put("vehicleClass", new DataFactProperty("vehicleClass", "vehicleClass", 10, "STRING", "STRING", ""));
		fields.put("zipCode", new DataFactProperty("zipCode", "vehicleClass", 10, "STRING", "STRING", ""));
		fields.put("UMStackingIndicator", new DataFactProperty("UMStackingIndicator", "UMStackingIndicator", 10, "STRING", "STRING", ""));
		fields.put("symbolCode", new DataFactProperty("symbolCode", "symbolCode", 10, "STRING", "STRING", ""));
		fields.put("passiveRestraintDiscountCode", new DataFactProperty("passiveRestraintDiscountCode", "passiveRestraintDiscountCode", 10, "STRING", "STRING", ""));
		fields.put("antiLockDiscountCode", new DataFactProperty("antiLockDiscountCode", "antiLockDiscountCode", 10, "STRING", "STRING", ""));
		fields.put("antiTheftDiscountCode", new DataFactProperty("antiTheftDiscountCode", "antiTheftDiscountCode", 10, "STRING", "STRING", ""));
		fields.put("defensiveDriverDiscountCode", new DataFactProperty("defensiveDriverDiscountCode", "defensiveDriverDiscountCode", 10, "STRING", "STRING", ""));
		fields.put("PIPLimitsDeductibleCode", new DataFactProperty("PIPLimitsDeductibleCode", "defensiveDriverDiscountCode", 10, "STRING", "STRING", ""));
		fields.put("assignedRiskRateClassCode", new DataFactProperty("assignedRiskRateClassCode", "assignedRiskRateClassCode", 10, "STRING", "STRING", ""));
		fields.put("assignedRiskPenaltyPoints", new DataFactProperty("assignedRiskPenaltyPoints", "assignedRiskPenaltyPoints", 10, "STRING", "STRING", ""));
		fields.put("exceptionCodeA", new DataFactProperty("exceptionCodeA", "exceptionCodeA", 10, "STRING", "STRING", ""));
		fields.put("exceptionCodeB", new DataFactProperty("exceptionCodeB", "exceptionCodeB", 10, "STRING", "STRING", ""));
		fields.put("exceptionCodeC", new DataFactProperty("exceptionCodeC", "exceptionCodeC", 10, "STRING", "STRING", ""));
		fields.put("exceptionCodeD", new DataFactProperty("exceptionCodeD", "exceptionCodeD", 10, "STRING", "STRING", ""));
	
		return fields;
	}

	public Map<String,String> mapFromOpenidlRecord(String record) {

		OpenidlAutoPolicyRecord openidlAutoPolicyRecord = gson.fromJson(record, OpenidlAutoPolicyRecord.class);

		//transform the data from the SQS message to format expected to run the rule
		Map<String, String> data = new HashMap<String, String>();
		data.put("LOB", openidlAutoPolicyRecord.getPolicy().getRecordLOB());
		data.put("state", openidlAutoPolicyRecord.getPolicy().getPolicyState());
		data.put("coverageCode", openidlAutoPolicyRecord.getCoverage() == null ? null : (openidlAutoPolicyRecord.getCoverage().getCoverageType() == null ? null : openidlAutoPolicyRecord.getCoverage().getCoverageType().value()));
		data.put("programCode", openidlAutoPolicyRecord.getPolicy().getAnnualStatementLine());
		data.put("sublineCode", openidlAutoPolicyRecord.getPolicy() == null ? null : (openidlAutoPolicyRecord.getPolicy().getSubline() == null ?  null : openidlAutoPolicyRecord.getPolicy().getSubline().value()));

		return data;
	}

	@Override
	public String getRuleFilePath() {
		
		return "org/openidl/rules/DataValidationRules-AU_Test.xls";
	}
}
