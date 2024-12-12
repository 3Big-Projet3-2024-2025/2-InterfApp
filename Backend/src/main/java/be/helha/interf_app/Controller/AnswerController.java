package be.helha.interf_app.Controller;

import be.helha.interf_app.Model.Answer;
import be.helha.interf_app.Service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class for handling HTTP requests related to answers.
 *
 * This controller defines endpoints for performing CRUD operations on answers:
 * - Creating a new answer
 * - Retrieving all answers
 * - Retrieving a specific answer by its ID
 * - Updating an existing answer
 * - Deleting an answer by its ID
 *
 * All endpoints are cross-origin enabled to allow interaction with frontend applications (e.g., Angular).
 */
@RestController
@RequestMapping("/api/answers")
@CrossOrigin(origins = "*") // Allow all origins to access endpoints
public class AnswerController {

    /**
     * Service layer for handling answer-related business logic.
     */
    @Autowired
    private AnswerService answerService;

    /**
     * Endpoint to create a new answer.
     *
     * @param answer the answer object to be created, provided in the request body
     * @return the created answer wrapped in a ResponseEntity with HTTP 200 status, or HTTP 404 if creation fails
     */
    @PostMapping
    public ResponseEntity<Answer> createAnswer(@RequestBody Answer answer) {
        Answer savedAnswer = answerService.saveAnswer(answer);
        if (savedAnswer != null) {
            return ResponseEntity.ok(savedAnswer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to retrieve all answers.
     *
     * @return a ResponseEntity containing a list of all answers with HTTP 200 status
     */
    @GetMapping
    public ResponseEntity<List<Answer>> getAllAnswers() {
        List<Answer> answers = answerService.getAllAnswers();
        return ResponseEntity.ok(answers);
    }

    /**
     * Endpoint to retrieve a specific answer by its unique ID.
     *
     * @param id the unique identifier of the answer to retrieve, extracted from the URL path
     * @return a ResponseEntity containing the requested answer with HTTP 200 status, or HTTP 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Answer> getAnswerById(@PathVariable String id) {
        Optional<Answer> answer = answerService.getAnswerById(id);
        return answer.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to delete a specific answer by its unique ID.
     *
     * @param id the unique identifier of the answer to delete, extracted from the URL path
     * @return a ResponseEntity with HTTP 204 status upon successful deletion, or HTTP 404 if the answer is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable String id) {
        Optional<Answer> answer = answerService.getAnswerById(id);
        if (answer.isPresent()) {
            answerService.deleteAnswer(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to update an existing answer.
     *
     * @param answer the updated answer object, provided in the request body
     * @return a ResponseEntity containing the updated answer with HTTP 200 status, or HTTP 404 if the update fails
     */
    @PutMapping
    public ResponseEntity<Answer> updateAnswer(@RequestBody Answer answer) {
        Answer updatedAnswer = answerService.updateAnswer(answer);
        if (updatedAnswer != null) {
            return ResponseEntity.ok(updatedAnswer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
