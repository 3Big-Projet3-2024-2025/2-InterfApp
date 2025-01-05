package be.helha.interf_app;

import be.helha.interf_app.Controller.AnswerController;
import be.helha.interf_app.Model.Answer;
import be.helha.interf_app.Service.AnswerService;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the {@link AnswerController} class.
 *
 * This class contains unit tests for the AnswerController, which is responsible for handling HTTP requests
 * related to answer operations. The tests include creating, retrieving, and deleting answers.
 */
@SpringBootTest
public class AnswerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AnswerService answerService;

    @InjectMocks
    private AnswerController answerController;

    private ObjectMapper objectMapper;

    private Answer answer;
    private Answer answer1;
    private Answer answer2;

    /**
     * Sets up the test environment by initializing mocks and preparing a sample answer.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(answerController).build();
        objectMapper = new ObjectMapper();

        // Create a sample answer with a map of answers for a given form
        answer = new Answer(
                "1", // id
                "1", // idForm
                "user1", // idUser
                Map.of("q1", "Apple", "q2", "Banana") // answer map
        );

        // Create multiple answers for testing
        answer1 = new Answer(
                "1", // id
                "1", // idForm
                "user1", // idUser
                Map.of("q1", "Apple", "q2", "Banana")
        );

        answer2 = new Answer(
                "2", // id
                "1", // idForm
                "user2", // idUser
                Map.of("q1", "Orange", "q2", "Grapes")
        );
    }

    /**
     * Tests the {@link AnswerController#createAnswer} method.
     *
     * This test validates the POST request to create a new answer. The response is checked to ensure that
     * the answer is saved and returned correctly with the appropriate attributes.
     *
     * @throws Exception if the test fails
     */
    @Test
    void createAnswer() throws Exception {
        // Mock the service call
        when(answerService.saveAnswer(any(Answer.class))).thenReturn(answer);

        // Perform the POST request and validate the response
        mockMvc.perform(post("/api/answers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))  // id instead of id_answer
                .andExpect(jsonPath("$.idForm").value("1"))
                .andExpect(jsonPath("$.idUser").value("user1"))
                .andExpect(jsonPath("$.answer.q1").value("Apple"))
                .andExpect(jsonPath("$.answer.q2").value("Banana"));

        // Verify that the service method was called once
        verify(answerService, times(1)).saveAnswer(any(Answer.class));
    }

    /**
     * Tests the {@link AnswerController#getAllAnswers} method.
     *
     * This test validates the GET request to retrieve all answers. The response is checked to ensure it contains
     * the expected answer data, including the map of question IDs and corresponding answers.
     *
     * @throws Exception if the test fails
     */
    @Test
    void getAllAnswers() throws Exception {
        // Mock the service call
        when(answerService.getAllAnswers()).thenReturn(Arrays.asList(answer1, answer2));

        // Perform the GET request and validate the response
        mockMvc.perform(get("/api/answers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].idForm").value("1"))
                .andExpect(jsonPath("$[0].idUser").value("user1"))
                .andExpect(jsonPath("$[0].answer.q1").value("Apple"))
                .andExpect(jsonPath("$[0].answer.q2").value("Banana"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].idForm").value("1"))
                .andExpect(jsonPath("$[1].idUser").value("user2"))
                .andExpect(jsonPath("$[1].answer.q1").value("Orange"))
                .andExpect(jsonPath("$[1].answer.q2").value("Grapes"));

        // Verify that the service method was called once
        verify(answerService, times(1)).getAllAnswers();
    }

    /**
     * Tests the {@link AnswerController#getAnswerById} method.
     *
     * This test validates the GET request to retrieve an answer by its ID. The response is checked to ensure that
     * the correct answer is returned, including the associated form, user, and answer map.
     * The JSON response is also printed to the console for inspection.
     *
     * @throws Exception if the test fails
     */
    @Test
    void getAnswerById() throws Exception {
        // Mock the service call
        when(answerService.getAnswerById("1")).thenReturn(Optional.of(answer));

        // Perform the GET request
        MvcResult result = mockMvc.perform(get("/api/answers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.idForm").value("1"))
                .andExpect(jsonPath("$.idUser").value("user1"))
                .andExpect(jsonPath("$.answer.q1").value("Apple"))
                .andExpect(jsonPath("$.answer.q2").value("Banana"))
                .andReturn();

        // Print the JSON response content to the console
        String content = result.getResponse().getContentAsString();
        System.out.println("Response JSON: " + content);

        // Verify that the service method was called once
        verify(answerService, times(1)).getAnswerById("1");
    }

    /**
     * Tests the {@link AnswerController#getAnswerById} method when the answer is not found.
     *
     * This test validates the GET request to retrieve an answer by its ID when the answer is not found in the database.
     * The response is expected to return a 404 (Not Found) status.
     *
     * @throws Exception if the test fails
     */
    @Test
    void getAnswerById_NotFound() throws Exception {
        // Mock the service call to return an empty Optional
        when(answerService.getAnswerById("1")).thenReturn(Optional.empty());

        // Perform the GET request and validate the response
        mockMvc.perform(get("/api/answers/1"))
                .andExpect(status().isNotFound());

        // Verify that the service method was called once
        verify(answerService, times(1)).getAnswerById("1");
    }

    /**
     * Tests the {@link AnswerController#deleteAnswer} method.
     *
     * This test validates the DELETE request to delete an answer by its ID. The response is expected to return a 204
     * (No Content) status to indicate successful deletion.
     *
     * @throws Exception if the test fails
     */
    @Test
    void deleteAnswer() throws Exception {
        // Mock the service call to return a present answer
        when(answerService.getAnswerById("1")).thenReturn(Optional.of(answer));

        // Perform the DELETE request and validate the response
        mockMvc.perform(delete("/api/answers/1"))
                .andExpect(status().isNoContent());

        // Verify that the service method was called once
        verify(answerService, times(1)).deleteAnswer("1");
    }

    /**
     * Tests the {@link AnswerController#deleteAnswer} method when the answer is not found.
     *
     * This test validates the DELETE request when attempting to delete an answer that doesn't exist in the database.
     * The response is expected to return a 404 (Not Found) status.
     *
     * @throws Exception if the test fails
     */
    @Test
    void deleteAnswer_NotFound() throws Exception {
        // Mock the service call to return an empty Optional
        when(answerService.getAnswerById("1")).thenReturn(Optional.empty());

        // Perform the DELETE request and validate the response
        mockMvc.perform(delete("/api/answers/1"))
                .andExpect(status().isNotFound());

        // Verify that the service method was called once
        verify(answerService, times(1)).getAnswerById("1");
    }

    /**
     * Tests the {@link AnswerController#createAnswer} method with multiple answers.
     *
     * This test validates the POST request with multiple answers from different users.
     *
     * @throws Exception if the test fails
     */
    @Test
    void createMultipleAnswers() throws Exception {
        // Create answers with multiple responses
        Answer answerWithMultipleChoices = new Answer(
                "3", // id
                "1", // idForm
                "user3", // idUser
                Map.of("q1", "Apple, Orange", "q2", "Banana, Grapes") // Multiple answers for the question
        );

        // Mock the service call
        when(answerService.saveAnswer(any(Answer.class))).thenReturn(answerWithMultipleChoices);

        // Perform the POST request and validate the response
        mockMvc.perform(post("/api/answers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answerWithMultipleChoices)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.idForm").value("1"))
                .andExpect(jsonPath("$.idUser").value("user3"))
                .andExpect(jsonPath("$.answer.q1").value("Apple, Orange"))
                .andExpect(jsonPath("$.answer.q2").value("Banana, Grapes"));

        // Verify that the service method was called once
        verify(answerService, times(1)).saveAnswer(any(Answer.class));
    }
    /**
     * Tests the creation of an answer with multiple choices for each question.
     * This method simulates a POST request to create an answer that contains multiple choices for questions.
     * It verifies that the answer was correctly saved and returns the expected data.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    void createAnswer_multipleChoice() throws Exception {
        Answer answerWithMultipleChoices = new Answer(
                "3", // id
                "1", // idForm
                "user3", // idUser
                Map.of("q1", "Apple, Orange", "q2", "Banana, Grapes") // multiple answers for the question
        );

        when(answerService.saveAnswer(any(Answer.class))).thenReturn(answerWithMultipleChoices);

        mockMvc.perform(post("/api/answers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answerWithMultipleChoices)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.idForm").value("1"))
                .andExpect(jsonPath("$.idUser").value("user3"))
                .andExpect(jsonPath("$.answer.q1").value("Apple, Orange"))
                .andExpect(jsonPath("$.answer.q2").value("Banana, Grapes"));

        verify(answerService, times(1)).saveAnswer(any(Answer.class));
    }
    /**
     * Tests retrieving an answer with multiple choices by its ID.
     * This method simulates a GET request to retrieve a specific answer by its ID.
     * It verifies that the returned answer contains multiple choices for each question.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    void getAnswerById_multipleChoice() throws Exception {
        Answer answerWithMultipleChoices = new Answer(
                "3", // id
                "1", // idForm
                "user3", // idUser
                Map.of("q1", "Apple, Orange", "q2", "Banana, Grapes") // multiple answers for the question
        );

        when(answerService.getAnswerById("3")).thenReturn(Optional.of(answerWithMultipleChoices));

        mockMvc.perform(get("/api/answers/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.idForm").value("1"))
                .andExpect(jsonPath("$.idUser").value("user3"))
                .andExpect(jsonPath("$.answer.q1").value("Apple, Orange"))
                .andExpect(jsonPath("$.answer.q2").value("Banana, Grapes"));

        verify(answerService, times(1)).getAnswerById("3");
    }
    /**
     * Tests retrieving all answers with multiple responses for questions.
     * This method simulates a GET request to retrieve all answers and verifies that the answer contains
     * multiple choices for specific questions.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    void getAllAnswers_multipleResponses() throws Exception {
        Answer answerWithMultipleResponses = new Answer(
                "3", // id
                "1", // idForm
                "user3", // idUser
                Map.of("q1", "Apple, Orange", "q2", "Banana, Grapes") // multiple answers for the question
        );

        when(answerService.getAllAnswers()).thenReturn(Arrays.asList(answer1, answer2, answerWithMultipleResponses));

        mockMvc.perform(get("/api/answers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[2].id").value("3"))
                .andExpect(jsonPath("$[2].idForm").value("1"))
                .andExpect(jsonPath("$[2].idUser").value("user3"))
                .andExpect(jsonPath("$[2].answer.q1").value("Apple, Orange"))
                .andExpect(jsonPath("$[2].answer.q2").value("Banana, Grapes"));

        verify(answerService, times(1)).getAllAnswers();
    }
}
