package com.drapo.empmanager.services;

import com.drapo.empmanager.dto.EmployeeDto;
import com.drapo.empmanager.exceptions.CsvParsingException;
import com.drapo.empmanager.exceptions.CsvUploadException;
import com.drapo.empmanager.model.Employee;
import com.drapo.empmanager.repositories.EmployeeRepository;
import com.drapo.empmanager.util.CsvHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class EmployeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeService(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void saveCsvFile(MultipartFile file) {
        if (!"text/csv".equals(file.getContentType())) {
            throw new CsvUploadException("Invalid file format!. Only csv allowed.");
        }

        try {
            List<Employee> employees = CsvHelper.csvToEmployees(file);
            LOGGER.debug("Saving employees from csv file to database");
            employeeRepository.saveAll(employees);
        } catch (IOException e) {
            LOGGER.error("Error when reading csv file");
            throw new CsvUploadException("Csv file could not be read.", e.getCause());
        } catch (CsvParsingException e) {
            LOGGER.error("Error when parsing csv file");
            throw new CsvUploadException(String.format("Error when parsing csv file. Reason: %s", e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Error when saving employee entities to database");
            throw new CsvUploadException("Error when saving data to database. Make sure that imported data is unique.");
        } catch (Exception e) {
            LOGGER.error("Error when parsing csv file");
            throw new CsvUploadException(String.format("Error when parsing csv file. Reason %s", e.getMessage()));
        }
    }

    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(EmployeeDto::from)
                .toList();
    }
}
