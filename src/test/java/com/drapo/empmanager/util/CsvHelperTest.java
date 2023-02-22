package com.drapo.empmanager.util;

import com.drapo.empmanager.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvHelperTest {

    @Test
    void parse_csv_file() throws IOException {
        var csvData = """
                First Name, Last Name, Email, Phone Number
                Jose, Aldo, aldo@gmail.com, 555888999
                Conor, Mcgregor, cmgregor@gmail.com, 999666333
                """;
        MockMultipartFile csvFile = new MockMultipartFile("test", "employees.csv", "text/csv", csvData.getBytes());

        List<Employee> employees = CsvHelper.csvToEmployees(csvFile);

        assertEquals(2, employees.size());
        //compare Employee entities data with the one provided in csv file
    }

    @Test
    void csv_file_could_not_be_empty() {

    }

    @Test
    void csv_file_must_contains_required_headers() {

    }

    @Test
    void data_in_csv_file_must_be_separated_by_comma() {

    }

    @Test
    void data_in_csv_file_must_have_all_required_employee_parameter() {

    }
}