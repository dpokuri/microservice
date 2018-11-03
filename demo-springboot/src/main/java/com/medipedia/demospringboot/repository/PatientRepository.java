package com.medipedia.demospringboot.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.medipedia.demospringboot.response.PatientResponse;

@Repository
public class PatientRepository {
	
	public PatientResponse getPatientDetailsByIdStub(String patientId) {
		
		PatientResponse patientResponse = new PatientResponse();
		patientResponse.setPatientId("PAT100");
		patientResponse.setPatientName("XXX");
		patientResponse.setPatientAddr("USA");
		
		List<String> diseases = new ArrayList<String>();
		diseases.add("DIS100");
		diseases.add("DIS200");
		diseases.add("DIS300");
		
		patientResponse.setDiseases(diseases);
		
		return patientResponse;
		
	}

}
