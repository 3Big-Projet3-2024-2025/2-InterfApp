package be.helha.interf_app.Service;


import be.helha.interf_app.Model.Answer;
import be.helha.interf_app.Repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Service class for managing and performing operations related to Answer entities.
 * This class provides methods to save, retrieve, and delete answers from the repository.
 *
 * Attributes:
 * - answerRepository: The repository used for accessing Answer data in the database.
 */
@Service
public class AnswerService {
    /**
     * The repository for accessing and performing CRUD operations on the Answer data.
     */
    @Autowired
    private AnswerRepository answerRepository;
    /**
     * Saves a new answer or updates an existing answer in the repository.
     *
     * @param answer The answer to be saved.
     * @return The saved answer.
     */
    public Answer saveAnswer(Answer answer) {
        if(getAnswerById(answer.getId()).isEmpty()) {  // The methode post is accessible by any User and if he knows the id he can use it like an update methode
            return answerRepository.save(answer);
        }
        return null;
    }
    /**
     * Retrieves all the answers stored in the repository.
     *
     * @return A list of all answers.
     */
    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }
    /**
     * Retrieves an answer by its ID from the repository.
     *
     * @param id The ID of the answer to be retrieved.
     * @return An Optional containing the answer if found, or an empty Optional if not.
     */
    public Optional<Answer> getAnswerById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return answerRepository.findById(id);
    }
    /**
     * Deletes an answer by its ID from the repository.
     *
     * @param id The ID of the answer to be deleted.
     */
    public void deleteAnswer(String id) {
        answerRepository.deleteById(id);
    }

    public Answer updateAnswer(Answer answer) {
        if(getAnswerById(answer.getId()).isPresent()) {
            return answerRepository.save(answer);
        }
        return null;
    }
}
