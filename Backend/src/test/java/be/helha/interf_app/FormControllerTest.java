package be.helha.interf_app;
import be.helha.interf_app.Controller.FormController;
import be.helha.interf_app.Model.Form;
import be.helha.interf_app.Model.Question;
import be.helha.interf_app.Service.FormService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the {@link FormController} class.
 *
 * This class contains unit tests for the FormController, which is responsible for handling HTTP requests
 * related to form operations. The tests include creating, retrieving, and deleting forms with questions.
 */
public class FormControllerTest {
    private MockMvc mockMvc;

    @Mock
    private FormService formService;

    @InjectMocks
    private FormController formController;

    private ObjectMapper objectMapper;

    private Form form;

    /**
     * Sets up the test environment by initializing mocks and preparing a sample form with a question.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(formController).build();
        objectMapper = new ObjectMapper();

        // Create a sample question for the form
        Question question = new Question(
                "What is your favorite fruit?",
                "multiple-choice",
                List.of("Apple", "Banana", "Orange"),
                true,
                true
        );

        // Create a form with one question
        form = new Form("1", "Form with question", List.of(question));
    }

    /**
     * Tests the {@link FormController#createForm} method.
     *
     * This test validates the POST request to create a new form and ensures that the form is created successfully.
     * The response is checked to confirm that the form has the correct attributes, including the question.
     *
     * @throws Exception if the test fails
     */
    @Test
    void createForm() throws Exception {
        // Mock the service call
        when(formService.saveForm(any(Form.class))).thenReturn(form);

        // Perform the POST request and validate the response
        mockMvc.perform(post("/api/forms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Form with question"))
                .andExpect(jsonPath("$.questions[0].inputQuestion").value("What is your favorite fruit?"))
                .andExpect(jsonPath("$.questions[0].inputChoices[0]").value("Apple"))
                .andExpect(jsonPath("$.questions[0].inputChoices[1]").value("Banana"))
                .andExpect(jsonPath("$.questions[0].inputChoices[2]").value("Orange"));

        // Verify that the service method was called once
        verify(formService, times(1)).saveForm(any(Form.class));
    }

    /**
     * Tests the {@link FormController#getAllForms} method.
     *
     * This test validates the GET request to retrieve all forms. The response is checked to ensure it contains
     * the expected form with a question.
     *
     * @throws Exception if the test fails
     */
    @Test
    void getAllForms() throws Exception {
        // Mock the service call
        when(formService.getAllForms()).thenReturn(Collections.singletonList(form));

        // Perform the GET request and validate the response
        mockMvc.perform(get("/api/forms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].title").value("Form with question"))
                .andExpect(jsonPath("$[0].questions[0].inputQuestion").value("What is your favorite fruit?"))
                .andExpect(jsonPath("$[0].questions[0].inputChoices[0]").value("Apple"))
                .andExpect(jsonPath("$[0].questions[0].inputChoices[1]").value("Banana"))
                .andExpect(jsonPath("$[0].questions[0].inputChoices[2]").value("Orange"));

        // Verify that the service method was called once
        verify(formService, times(1)).getAllForms();
    }

    /**
     * Tests the {@link FormController#getFormById} method.
     *
     * This test validates the GET request to retrieve a form by its ID. The response is checked to ensure that
     * the correct form is returned with the expected attributes, including questions.
     * The JSON response is also printed to the console for inspection.
     *
     * @throws Exception if the test fails
     */
    @Test
    void getFormById() throws Exception {
        // Mock the service call
        when(formService.getFormById("1")).thenReturn(Optional.of(form));

        // Perform the GET request
        MvcResult result = mockMvc.perform(get("/api/forms/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Form with question"))
                .andExpect(jsonPath("$.questions[0].inputQuestion").value("What is your favorite fruit?"))
                .andExpect(jsonPath("$.questions[0].inputChoices[0]").value("Apple"))
                .andExpect(jsonPath("$.questions[0].inputChoices[1]").value("Banana"))
                .andExpect(jsonPath("$.questions[0].inputChoices[2]").value("Orange"))
                .andReturn();

        // Print the JSON response content to the console
        String content = result.getResponse().getContentAsString();
        System.out.println("Response JSON: " + content);

        // Verify that the service method was called once
        verify(formService, times(1)).getFormById("1");
    }

    /**
     * Tests the {@link FormController#getFormById} method when the form is not found.
     *
     * This test validates the GET request to retrieve a form by its ID when the form is not found in the database.
     * The response is expected to return a 404 (Not Found) status.
     *
     * @throws Exception if the test fails
     */
    @Test
    void getFormById_NotFound() throws Exception {
        // Mock the service call to return an empty Optional
        when(formService.getFormById("1")).thenReturn(Optional.empty());

        // Perform the GET request and validate the response
        mockMvc.perform(get("/api/forms/1"))
                .andExpect(status().isNotFound());

        // Verify that the service method was called once
        verify(formService, times(1)).getFormById("1");
    }

    /**
     * Tests the {@link FormController#deleteForm} method.
     *
     * This test validates the DELETE request to delete a form by its ID. The response is expected to return a 204
     * (No Content) status to indicate successful deletion.
     *
     * @throws Exception if the test fails
     */
    @Test
    void deleteForm() throws Exception {
        // Perform the DELETE request and validate the response
        mockMvc.perform(delete("/api/forms/1"))
                .andExpect(status().isNoContent());

        // Verify that the service method was called once
        verify(formService, times(1)).deleteForm("1");
    }
}
