package com.metinacun.springfileupload.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.metinacun.springfileupload.model.Employee;
import com.metinacun.springfileupload.model.Employees;
import com.metinacun.springfileupload.model.FileInfo;
import com.metinacun.springfileupload.repository.EmployeeRepository;
import com.metinacun.springfileupload.repository.FileInfoRepository;
import com.metinacun.springfileupload.service.FileManagmentService;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RestController.class);

	@Autowired
	EmployeeRepository repository;
	
	@Autowired
	FileInfoRepository fileInfoRepository;
	
	@Autowired
	FileManagmentService fileManagmentService;
	
	private Map<String, Employee> listEmployee = new ConcurrentHashMap<>();
	
	@PostMapping("/save")
	public ResponseEntity<?> fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
		
		ByteArrayInputStream bis = new ByteArrayInputStream(file.getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(bis));
		
		FileInfo oldFileInfo = fileInfoRepository.findOne(0);
		if(oldFileInfo == null) {
			oldFileInfo = new FileInfo();
			oldFileInfo.setFileLinesCount(0);
			oldFileInfo.setFileSize(0);
		}
		
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileSize(file.getSize());
		fileInfo.setFileLinesCount(br.lines().count());
		Employees employees = null;
		
		//Önceki dosya'nın yüzde 10'luk dilimini öğreniyoruz.
		double tenPercentOfSize = oldFileInfo.getFileSize() * 0.1;
		double tenPercentOfLineCount = oldFileInfo.getFileLinesCount() * 0.1;
		//Yeni dosya ile eski dosya arasındaki farkın mutlağını alıyoruz.
		double sizeDifference = Math.abs(oldFileInfo.getFileSize() - fileInfo.getFileSize());
		double lineCountDifference = Math.abs(oldFileInfo.getFileLinesCount() - fileInfo.getFileLinesCount());
		
		//Farkın aşağı yukarı sapma oranı %10 dan fazla ise işlemi gerçekleştiriyoruz.
		if(sizeDifference < tenPercentOfSize && lineCountDifference < tenPercentOfLineCount) {
			
			fileInfoRepository.deleteAll();
			fileInfoRepository.save(fileInfo);
		    ObjectMapper mapper = new XmlMapper();
		    employees = mapper.readValue(file.getInputStream(), Employees.class);
		    LOGGER.info("File readed.");
		    repository.deleteAll();
		    repository.save(employees.getEmployee());
		    listEmployee.clear();
		    for(Employee employee : employees.getEmployee()) {
		    	listEmployee.put(employee.getId(), employee);
		    }
		    fileManagmentService.setListEmployee(listEmployee);
		    
		}else {
			LOGGER.error("The file was not read because the change rate is more than 10%.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The file was not read because the change rate is more than 10%.");
		}
		
	    return ResponseEntity.ok(employees);
	}
	
	@GetMapping("/read")
	public Map<String, Employee> read(){
		
		return fileManagmentService.getListEmployee();
	}
	
}
