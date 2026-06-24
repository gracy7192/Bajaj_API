package com.bajaj.api.controller;

import com.bajaj.api.dto.BfhlRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BfhlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetEndpointSuccess() throws Exception {
        mockMvc.perform(get("/bfhl"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation_code", is(1)));
    }

    @Test
    public void testPostExampleA() throws Exception {
        BfhlRequestDto request = new BfhlRequestDto();
        request.setData(Arrays.asList("a", "1", "334", "4", "R", "$"));

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.user_id", is("gracy_mahajan_09022005")))
                .andExpect(jsonPath("$.email", is("gracymahajan09022005@gmail.com")))
                .andExpect(jsonPath("$.roll_number", is("2310991827")))
                .andExpect(jsonPath("$.odd_numbers", contains("1")))
                .andExpect(jsonPath("$.even_numbers", contains("334", "4")))
                .andExpect(jsonPath("$.alphabets", contains("A", "R")))
                .andExpect(jsonPath("$.special_characters", contains("$")))
                .andExpect(jsonPath("$.sepcial_characters", contains("$")))
                .andExpect(jsonPath("$.sum", is("339")))
                .andExpect(jsonPath("$.concat_string", is("Ra")));
    }

    @Test
    public void testPostExampleB() throws Exception {
        BfhlRequestDto request = new BfhlRequestDto();
        request.setData(Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b"));

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.user_id", is("gracy_mahajan_09022005")))
                .andExpect(jsonPath("$.email", is("gracymahajan09022005@gmail.com")))
                .andExpect(jsonPath("$.roll_number", is("2310991827")))
                .andExpect(jsonPath("$.odd_numbers", contains("5")))
                .andExpect(jsonPath("$.even_numbers", contains("2", "4", "92")))
                .andExpect(jsonPath("$.alphabets", contains("A", "Y", "B")))
                .andExpect(jsonPath("$.special_characters", contains("&", "-", "*")))
                .andExpect(jsonPath("$.sepcial_characters", contains("&", "-", "*")))
                .andExpect(jsonPath("$.sum", is("103")))
                .andExpect(jsonPath("$.concat_string", is("ByA")));
    }

    @Test
    public void testPostExampleC() throws Exception {
        BfhlRequestDto request = new BfhlRequestDto();
        request.setData(Arrays.asList("A", "ABCD", "DOE"));

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.user_id", is("gracy_mahajan_09022005")))
                .andExpect(jsonPath("$.email", is("gracymahajan09022005@gmail.com")))
                .andExpect(jsonPath("$.roll_number", is("2310991827")))
                .andExpect(jsonPath("$.odd_numbers", hasSize(0)))
                .andExpect(jsonPath("$.even_numbers", hasSize(0)))
                .andExpect(jsonPath("$.alphabets", contains("A", "ABCD", "DOE")))
                .andExpect(jsonPath("$.special_characters", hasSize(0)))
                .andExpect(jsonPath("$.sepcial_characters", hasSize(0)))
                .andExpect(jsonPath("$.sum", is("0")))
                .andExpect(jsonPath("$.concat_string", is("EoDdCbAa")));
    }

    @Test
    public void testPostEmptyData() throws Exception {
        BfhlRequestDto request = new BfhlRequestDto();
        request.setData(Collections.emptyList());

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.odd_numbers", hasSize(0)))
                .andExpect(jsonPath("$.even_numbers", hasSize(0)))
                .andExpect(jsonPath("$.alphabets", hasSize(0)))
                .andExpect(jsonPath("$.special_characters", hasSize(0)))
                .andExpect(jsonPath("$.sum", is("0")))
                .andExpect(jsonPath("$.concat_string", is("")));
    }
}
