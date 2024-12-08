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
 * This controller defines endpoints for:
 * - Creating a new answer
 * - Retrieving all answers
 * - Retrieving an answer by its ID
 * - Deleting an answer by its ID
 */
@RestController
@RequestMapping("/api/answers")
@CrossOrigin(origins = "*") // Allow Angular to access endpoints
public class AnswerController {

    /**
     * Service layer for answer-related operations.
     */
    @Autowired
    private AnswerService answerService;

    /**
     * Endpoint to create a new answer.
     *
     * @param answer the answer object to be created
     * @return the created answer wrapped in a ResponseEntity
     */
    @PostMapping
    public ResponseEntity<Answer> createReponse(@RequestBody Answer answer) {
        Answer savedAnswer = answerService.saveAnswer(answer);
        return ResponseEntity.ok(savedAnswer);
    }

    /**
     * Endpoint to retrieve all answers.
     *
     * @return a list of all answers wrapped in a ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<Answer>> getAllReponses() {
        List<Answer> repons = answerService.getAllAnswers();
        return ResponseEntity.ok(repons);
    }

    /**
     * Endpoint to retrieve an answer by its ID.
     *
     * @param id the unique identifier of the answer
     * @return the answer object wrapped in a ResponseEntity, or a 404 status if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Answer> getReponseById(@PathVariable String id) {
        Optional<Answer> reponse = answerService.getAnswerById(id);
        return reponse.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to delete an answer by its ID.
     *
     * @param id the unique identifier of the answer to delete
     * @return a ResponseEntity with no content upon successful deletion or a 404 status if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReponse(@PathVariable String id) {
        Optional<Answer> reponse = answerService.getAnswerById(id);
        if (reponse.isPresent()) {
            answerService.deleteAnswer(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

