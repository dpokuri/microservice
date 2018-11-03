package com.medipedia.demospringboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medipedia.demospringboot.repository.PatientRepository;
import com.medipedia.demospringboot.response.PatientResponse;

@Service
public class PatientService {
	
	@Autowired
	PatientRepository patientRepository;

	public PatientResponse getPatientDetailsById(String patientId) {
		
		return patientRepository.getPatientDetailsByIdStub(patientId);

	}

}
