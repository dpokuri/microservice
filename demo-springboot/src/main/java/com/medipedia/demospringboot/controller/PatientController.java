package com.medipedia.demospringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medipedia.demospringboot.response.PatientResponse;
import com.medipedia.demospringboot.service.PatientService;

@RestController
@RequestMapping("/api/v1")
public class PatientController {
	
	@Autowired
	PatientService patientService;
	
	@GetMapping("/patients/{patientId}")
	public ResponseEntity<PatientResponse> getPatientDetailsById(String patientId){
		
		PatientResponse patientResponse = patientService.getPatientDetailsById(patientId);
		
		return ResponseEntity.ok(patientResponse);
		
	}

}
