package com.drapo.empmanager.dto;

import com.drapo.empmanager.model.Employee;
import com.opencsv.bean.CsvBindByName;

public class EmployeeDto {

    private int id;
    @CsvBindByName(column = "First Name")
    private String firstName;
    @CsvBindByName(column = "Last Name")
    private String lastName;
    @CsvBindByName(column = "Email")
    private String email;
    @CsvBindByName(column = "Phone Number")
    private String phoneNumber;

    public Employee toEmployee() {
        Employee employee = new Employee();
        employee.setFirstName(this.firstName);
        employee.setLastName(this.lastName);
        employee.setEmail(this.email);
        employee.setPhoneNumber(this.phoneNumber);

        return employee;
    }

    public static EmployeeDto from(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        dto.id = employee.getId();
        dto.firstName = employee.getFirstName();
        dto.lastName = employee.getLastName();
        dto.email = employee.getEmail();
        dto.phoneNumber = employee.getPhoneNumber();

        return dto;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
