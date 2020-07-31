package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.Repository.CompanyRepository;
import com.thoughtworks.springbootemployee.entity.Company;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompangIntergratorTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;

    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
    }

    @Test
    void should_return_ok_when_finAll_company() throws Exception {
        mockMvc.perform(get("/companies")).andExpect(status().isOk());
    }

    @Test
    void should_return_ok_when_find_employee() throws Exception {
        String resultActions = mockMvc.perform(get("/companies/1/employees")).andReturn().getResponse().getContentAsString();
        System.out.println(resultActions);
        String s = "name: dong";
    }

    @Test
    void should_add_1_company_when_add_company_given_1_company() throws Exception {
        String companyJson = "{\n" +
                "    \"name\": \"tw\"\n" +
                "}";
        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(companyJson)).andExpect(status().isOk());
        List<Company> companies = companyRepository.findAll();
        System.out.println(companies.size());
        String resultActions = mockMvc.perform(get("/companies")).andReturn().getResponse().getContentAsString();
        System.out.println(resultActions);
        String s = "tw";

        Assertions.assertTrue(resultActions.contains(s));
    }

}
