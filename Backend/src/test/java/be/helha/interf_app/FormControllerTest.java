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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
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
@SpringBootTest
public class FormControllerTest {
    private MockMvc mockMvc;

    @Mock
    private FormService formService;

    @InjectMocks
    private FormController formController;

    private ObjectMapper objectMapper;

    private Form form;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(formController).build();
        objectMapper = new ObjectMapper();

        // Créer une question d'exemple pour le formulaire
        Question question = new Question(
                "What is your favorite fruit?",
                "multiple-choice",
                List.of("Apple", "Banana", "Orange"),
                true,
                true
        );

        // Créer un formulaire avec une question
        form = new Form("1", "Form with question", List.of(question));
    }

    /**
     * Tests the {@link FormController#createForm} method.
     * This test validates the POST request to create a new form.
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
     * Tests the  method.
     * This test validates creating a form with multiple types of questions.
     *
     * @throws Exception if the test fails
     */
    @Test
    void createFormWithAllQuestionTypes() throws Exception {
        // Create questions with different types
        List<Question> questions = new ArrayList<>();

        // Short Answer question
        questions.add(new Question("What is your name?", "short-answer", null, false, true));

        // Open Answer question
        questions.add(new Question("Describe your experience", "open-answer", null, false, true));

        // Checkbox question
        questions.add(new Question("Select your hobbies", "checkbox", List.of("Reading", "Traveling", "Gaming"), true, true));

        // Multiple choice question
        questions.add(new Question("Choose your favorite color", "multiple-choice", List.of("Red", "Blue", "Green"), false, true));

        // Date question
        questions.add(new Question("Select your birth date", "date", null, false, true));

        // Date and time question
        questions.add(new Question("When is your next appointment?", "datetime", null, false, true));

        // Email question
        questions.add(new Question("Provide your email address", "email", null, false, true));

        // Number question
        questions.add(new Question("How many siblings do you have?", "number", null, false, true));

        // Range question
        questions.add(new Question("Rate your satisfaction (1-10)", "range", null, false, true));

        // Month question
        questions.add(new Question("Select a month", "month", null, false, true));

        // Time question
        questions.add(new Question("What time do you wake up?", "time", null, false, true));

        // Phone number question
        questions.add(new Question("What is your phone number?", "phone", null, false, true));

        // Week question
        questions.add(new Question("Pick a week", "week", null, false, true));

        // Color question
        questions.add(new Question("Choose a color", "color", null, false, true));

        // Coordinates question
        questions.add(new Question("Provide your location", "coordinates", null, false, true));

        // Create the form with all these questions
        Form formWithAllQuestionTypes = new Form("2", "Form with all question types", questions);

        // Mock the service call to return the form with all questions
        when(formService.saveForm(any(Form.class))).thenReturn(formWithAllQuestionTypes);

        // Perform the POST request and validate the response
        mockMvc.perform(post("/api/forms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(formWithAllQuestionTypes)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.title").value("Form with all question types"))
                .andExpect(jsonPath("$.questions[0].inputQuestion").value("What is your name?"))
                .andExpect(jsonPath("$.questions[1].inputQuestion").value("Describe your experience"))
                .andExpect(jsonPath("$.questions[2].inputChoices[0]").value("Reading"))
                .andExpect(jsonPath("$.questions[2].inputChoices[1]").value("Traveling"))
                .andExpect(jsonPath("$.questions[2].inputChoices[2]").value("Gaming"))
                .andExpect(jsonPath("$.questions[3].inputChoices[0]").value("Red"))
                .andExpect(jsonPath("$.questions[3].inputChoices[1]").value("Blue"))
                .andExpect(jsonPath("$.questions[3].inputChoices[2]").value("Green"))
                .andExpect(jsonPath("$.questions[4].inputQuestion").value("Select your birth date"))
                .andExpect(jsonPath("$.questions[5].inputQuestion").value("When is your next appointment?"))
                .andExpect(jsonPath("$.questions[6].inputQuestion").value("Provide your email address"))
                .andExpect(jsonPath("$.questions[7].inputQuestion").value("How many siblings do you have?"))
                .andExpect(jsonPath("$.questions[8].inputQuestion").value("Rate your satisfaction (1-10)"))
                .andExpect(jsonPath("$.questions[9].inputQuestion").value("Select a month"))
                .andExpect(jsonPath("$.questions[10].inputQuestion").value("What time do you wake up?"))
                .andExpect(jsonPath("$.questions[11].inputQuestion").value("What is your phone number?"))
                .andExpect(jsonPath("$.questions[12].inputQuestion").value("Pick a week"))
                .andExpect(jsonPath("$.questions[13].inputQuestion").value("Choose a color"))
                .andExpect(jsonPath("$.questions[14].inputQuestion").value("Provide your location"));

        // Verify that the service method was called once
        verify(formService, times(1)).saveForm(any(Form.class));
    }

    /**
     * Tests the {@link FormController#getAllForms} method.
     * This test validates the GET request to retrieve all forms.
     *
     * @throws Exception if the test fails
     */
    @Test
    void getAllForms() throws Exception {
        // Mock the service call
        when(formService.getAllForms()).thenReturn(List.of(form));

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
     * This test validates the GET request to retrieve a form by its ID.
     *
     * @throws Exception if the test fails
     */
    @Test
    void getFormById() throws Exception {
        // Mock the service call
        when(formService.getFormById("1")).thenReturn(java.util.Optional.of(form));

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

        // Get the response content and print it to the console
        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response: " + responseContent);

        // Verify that the service method was called once
        verify(formService, times(1)).getFormById("1");
    }
    /**
     * Tests the {@link FormController#getFormById} method.
     * This test validates the GET request to retrieve a form by its ID.
     *
     * @throws Exception if the test fails
     */
    @Test
    void getFormById_2() throws Exception {
        // Mock the service call to return the form with all questions (id "2")
        List<Question> questions = new ArrayList<>();

        // Short Answer question
        questions.add(new Question("What is your name?", "short-answer", null, false, true));

        // Open Answer question
        questions.add(new Question("Describe your experience", "open-answer", null, false, true));

        // Checkbox question
        questions.add(new Question("Select your hobbies", "checkbox", List.of("Reading", "Traveling", "Gaming"), true, true));

        // Multiple choice question
        questions.add(new Question("Choose your favorite color", "multiple-choice", List.of("Red", "Blue", "Green"), false, true));

        // Date question
        questions.add(new Question("Select your birth date", "date", null, false, true));

        // Date and time question
        questions.add(new Question("When is your next appointment?", "datetime", null, false, true));

        // Email question
        questions.add(new Question("Provide your email address", "email", null, false, true));

        // Number question
        questions.add(new Question("How many siblings do you have?", "number", null, false, true));

        // Range question
        questions.add(new Question("Rate your satisfaction (1-10)", "range", null, false, true));

        // Month question
        questions.add(new Question("Select a month", "month", null, false, true));

        // Time question
        questions.add(new Question("What time do you wake up?", "time", null, false, true));

        // Phone number question
        questions.add(new Question("What is your phone number?", "phone", null, false, true));

        // Week question
        questions.add(new Question("Pick a week", "week", null, false, true));

        // Color question
        questions.add(new Question("Choose a color", "color", null, false, true));

        // Coordinates question
        questions.add(new Question("Provide your location", "coordinates", null, false, true));

        // Create the form with all these questions
        Form formWithAllQuestionTypes = new Form("2", "Form with all question types", questions);

        // Mock the service call
        when(formService.getFormById("2")).thenReturn(java.util.Optional.of(formWithAllQuestionTypes));

        // Perform the GET request and validate the response
        MvcResult result = mockMvc.perform(get("/api/forms/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.title").value("Form with all question types"))
                .andExpect(jsonPath("$.questions[0].inputQuestion").value("What is your name?"))
                .andExpect(jsonPath("$.questions[1].inputQuestion").value("Describe your experience"))
                .andExpect(jsonPath("$.questions[2].inputChoices[0]").value("Reading"))
                .andExpect(jsonPath("$.questions[2].inputChoices[1]").value("Traveling"))
                .andExpect(jsonPath("$.questions[2].inputChoices[2]").value("Gaming"))
                .andExpect(jsonPath("$.questions[3].inputChoices[0]").value("Red"))
                .andExpect(jsonPath("$.questions[3].inputChoices[1]").value("Blue"))
                .andExpect(jsonPath("$.questions[3].inputChoices[2]").value("Green"))
                .andExpect(jsonPath("$.questions[4].inputQuestion").value("Select your birth date"))
                .andExpect(jsonPath("$.questions[5].inputQuestion").value("When is your next appointment?"))
                .andExpect(jsonPath("$.questions[6].inputQuestion").value("Provide your email address"))
                .andExpect(jsonPath("$.questions[7].inputQuestion").value("How many siblings do you have?"))
                .andExpect(jsonPath("$.questions[8].inputQuestion").value("Rate your satisfaction (1-10)"))
                .andExpect(jsonPath("$.questions[9].inputQuestion").value("Select a month"))
                .andExpect(jsonPath("$.questions[10].inputQuestion").value("What time do you wake up?"))
                .andExpect(jsonPath("$.questions[11].inputQuestion").value("What is your phone number?"))
                .andExpect(jsonPath("$.questions[12].inputQuestion").value("Pick a week"))
                .andExpect(jsonPath("$.questions[13].inputQuestion").value("Choose a color"))
                .andExpect(jsonPath("$.questions[14].inputQuestion").value("Provide your location"))
                .andReturn();

        // Get the response content and print it to the console
        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response: " + responseContent);

        // Verify that the service method was called once
        verify(formService, times(1)).getFormById("2");
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
        // Mock the service call
        doNothing().when(formService).deleteForm("1");

        // Perform the DELETE request and validate the response
        mockMvc.perform(delete("/api/forms/1"))
                .andExpect(status().isNoContent());

        // Verify that the service method was called once
        verify(formService, times(1)).deleteForm("1");
    }

    @Test
    void deleteFormAllQuestions() throws Exception {
        // Mock the service call
        doNothing().when(formService).deleteForm("2");

        // Perform the DELETE request and validate the response
        mockMvc.perform(delete("/api/forms/2"))
                .andExpect(status().isNoContent());

        // Verify that the service method was called once
        verify(formService, times(1)).deleteForm("2");
    }
}
