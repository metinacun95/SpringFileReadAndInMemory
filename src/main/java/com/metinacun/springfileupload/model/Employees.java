package com.metinacun.springfileupload.model;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "employees")
public class Employees {
	
    @JacksonXmlElementWrapper(localName = "employee", useWrapping = false)
    private List<Employee> employee;
    
    public Employees() {
    }

	public List<Employee> getEmployee() {
		return employee;
	}

	public void setEmployee(List<Employee> employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "Employees [employee=" + employee + "]";
	}
    
   
}
