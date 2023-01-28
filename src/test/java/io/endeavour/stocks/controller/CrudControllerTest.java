package io.endeavour.stocks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.endeavour.stocks.UnitTestBase;
import io.endeavour.stocks.entity.PersonEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
class CrudControllerTest extends UnitTestBase {
    Logger logger = LoggerFactory.getLogger(CrudControllerTest.class);
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

    PersonEntity person;
//    ThreadLocal<PersonEntity> personEntityThreadLocal;

    @BeforeEach
    void setUp() throws Exception {
        String json = getJson("test-data/create-person.json");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/crud/person")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        person = objectMapper.readValue(content, PersonEntity.class);
        logger.info("Inside setup method");
    }

    @Test
    void test_404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/crud/person/770"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

//    @Test
//    void person_createPerson() throws Exception {
//        String json = getJson("test-data/create-person.json");
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/crud/person")
//                .content(json)
//                .contentType(MediaType.APPLICATION_JSON);
//        mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());
//
//    }

    @Test
    void test_updatePerson() throws Exception {
        String content = objectMapper.writeValueAsString(person);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/crud/person/" + person.getPersonID())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void test_deletePerson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/crud/person/" + person.getPersonID()))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    static String getJson(String filePath) throws Exception {
        Resource resource = new ClassPathResource(filePath);
        Path path = resource.getFile().toPath();
        return Files.readString(path);
    }


}