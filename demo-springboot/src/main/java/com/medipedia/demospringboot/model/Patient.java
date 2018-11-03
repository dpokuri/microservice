package com.medipedia.demospringboot.model;

import java.util.List;

public class Patient {
	
	private String patientId;
	private String patientName;
	private String patientAddr;
	private List<String> diseases;
	
	private String doctorName;
	
	public Patient() {
	
	}
	
	public Patient(String patientId, String patientName, String patientAddr, List<String> diseases) {
		
		this.patientId = patientId;
		this.patientName = patientName;
		this.patientAddr = patientAddr;
		this.diseases = diseases;
	}
	
	public String getPatientId() {
		return patientId;
	}
	
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	
	public String getPatientName() {
		return patientName;
	}
	
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	
	public String getPatientAddr() {
		return patientAddr;
	}
	
	public void setPatientAddr(String patientAddr) {
		this.patientAddr = patientAddr;
	}
	
	public List<String> getDiseases() {
		return diseases;
	}
	
	public void setDiseases(List<String> diseases) {
		this.diseases = diseases;
	}
	
	@Override
	public String toString() {
		return "Patient [patientId=" + patientId + ", patientName=" + patientName + ", patientAddr=" + patientAddr
				+ ", diseases=" + diseases + "]";
	}
	
	
	

}
