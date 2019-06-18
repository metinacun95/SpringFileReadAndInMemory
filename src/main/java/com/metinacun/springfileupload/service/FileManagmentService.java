package com.metinacun.springfileupload.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metinacun.springfileupload.model.Employee;
import com.metinacun.springfileupload.repository.EmployeeRepository;

@Service
public class FileManagmentService {
	
	private static final Logger log = LoggerFactory.getLogger(FileManagmentService.class);

	private Map<String, Employee> listEmployee;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	public FileManagmentService(EmployeeRepository employeeRepository) {
		super();
		this.listEmployee = new ConcurrentHashMap<>();
		this.employeeRepository = employeeRepository;
	}
	
	@PostConstruct
	public void loadEmployees() {
		log.info("Employees loaded from database.");
		List<Employee> employees = employeeRepository.findAll();
		listEmployee.clear();
		for(Employee employee : employees) {
			listEmployee.put(employee.getId(), employee);
		}
		log.info("Employees : {}", listEmployee);
	}

	public Map<String, Employee> getListEmployee() {
		return listEmployee;
	}

	public void setListEmployee(Map<String, Employee> listEmployee) {
		this.listEmployee = listEmployee;
	}
	
}
