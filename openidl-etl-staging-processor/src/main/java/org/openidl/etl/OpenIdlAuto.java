package org.openidl.etl;

public class OpenIdlAuto {

	private String ID;
	private String EtlID;
	private String LineOfInsurance;
	private String Coverage;
	private String StateCode;
	private String Program;
	private String Subline;
	private String PrivatePassengerDriversTraining;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getEtlID() {
		return EtlID;
	}

	public void setEtlID(String etlID) {
		EtlID = etlID;
	}

	public String getLineOfInsurance() {
		return LineOfInsurance;
	}

	public void setLineOfInsurance(String lineOfInsurance) {
		LineOfInsurance = lineOfInsurance;
	}

	public String getCoverage() {
		return Coverage;
	}

	public void setCoverage(String coverage) {
		Coverage = coverage;
	}

	public String getStateCode() {
		return StateCode;
	}

	public void setStateCode(String stateCode) {
		StateCode = stateCode;
	}

	public String getProgram() {
		return Program;
	}

	public void setProgram(String Program) {
		this.Program = Program;
	}

	public String getSubline() {
		return Subline;
	}

	public void setSubline(String subline) {
		Subline = subline;
	}

	public String getPrivatePassengerDriversTraining() {
		return PrivatePassengerDriversTraining;
	}

	public void setPrivatePassengerDriversTraining(String privatePassengerDriversTraining) {
		PrivatePassengerDriversTraining = privatePassengerDriversTraining;
	}

	@Override
	public String toString() {
		return "OpenIdlAuto [ID=" + ID + ", EtlID=" + EtlID + ", LineOfInsurance=" + LineOfInsurance + ", Coverage=" + Coverage + ", StateCode=" + StateCode + "]";
	}

}
