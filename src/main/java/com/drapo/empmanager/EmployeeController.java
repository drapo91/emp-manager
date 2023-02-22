package com.drapo.empmanager;

import com.drapo.empmanager.dto.EmployeeDto;
import com.drapo.empmanager.exceptions.CsvUploadException;
import com.drapo.empmanager.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("api/employees")
class EmployeeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("upload")
    ResponseEntity<String> readEmployeesFromCsvAndSaveToDatabase(@RequestParam("employees") MultipartFile file) {
        try {
            employeeService.saveCsvFile(file);
        } catch (CsvUploadException e) {
            LOGGER.error("Error when parsing uploaded csv file. {}", e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping
    ResponseEntity<List<EmployeeDto>> getEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();

        return ResponseEntity.ok(employees);
    }
}
