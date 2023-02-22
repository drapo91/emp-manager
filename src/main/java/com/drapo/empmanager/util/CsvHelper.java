package com.drapo.empmanager.util;

import com.drapo.empmanager.dto.EmployeeDto;
import com.drapo.empmanager.exceptions.CsvParsingException;
import com.drapo.empmanager.model.Employee;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CsvHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvHelper.class);
    private static final String[] REQUIRED_HEADERS = {"First Name", "Last Name", "Email", "Phone Number"};

    public static List<Employee> csvToEmployees(MultipartFile csv) throws IOException {
        validateFile(csv);

        //TODO consider checking email format validation

        List<EmployeeDto> employeeDtos = new CsvToBeanBuilder<EmployeeDto>(new InputStreamReader(csv.getInputStream()))
                .withType(EmployeeDto.class)
                .build()
                .parse();

        return employeeDtos.stream()
                .map(EmployeeDto::toEmployee)
                .collect(Collectors.toList());
    }

    private static void validateFile(final MultipartFile csv) throws IOException {
        LOGGER.debug("validating csv file");
        BufferedReader reader = new BufferedReader(new InputStreamReader(csv.getInputStream()));
        String headerLine = reader.readLine();
        if (Objects.isNull(headerLine)) {
            throw new CsvParsingException("File is empty");
        }

        if (!headerLine.contains(",")) {
            LOGGER.debug("Csv row header row does not have comma separated values");
            throw new CsvParsingException("Values should be separated by comma!");
        }

        List<String> csvHeaders = Arrays.stream(headerLine.split(","))
                .map(s -> s.stripTrailing().stripLeading())
                .toList();

        for (String requiredHeader : REQUIRED_HEADERS) {
            if (!csvHeaders.contains(requiredHeader)) {
                LOGGER.debug("Csv row does not have required header '{}'", requiredHeader);
                throw new CsvParsingException(String.format("There is no required header '%s' in the file.", requiredHeader));
            }
        }
    }
}
