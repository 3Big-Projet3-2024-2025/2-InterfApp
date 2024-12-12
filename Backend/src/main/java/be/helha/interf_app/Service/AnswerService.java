package be.helha.interf_app.Service;

import be.helha.interf_app.Model.Answer;
import be.helha.interf_app.Repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing and performing operations related to {@link Answer} entities.
 * This class provides methods to save, retrieve, update, and delete answers from the repository.
 */
@Service
public class AnswerService {

    /**
     * The repository for accessing and performing CRUD operations on {@link Answer} data.
     */
    @Autowired
    private AnswerRepository answerRepository;

    /**
     * Saves a new answer or updates an existing answer in the repository.
     * If the ID of the answer is already present in the repository, the method does not save it to prevent unintended updates.
     *
     * @param answer The answer to be saved.
     * @return The saved {@link Answer}, or {@code null} if the answer already exists.
     */
    public Answer saveAnswer(Answer answer) {
        if (getAnswerById(answer.getId()).isEmpty()) {  // Ensures no accidental updates occur when the ID already exists.
            return answerRepository.save(answer);
        }
        return null;
    }

    /**
     * Retrieves all answers stored in the repository.
     *
     * @return A {@link List} of all {@link Answer} objects.
     */
    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    /**
     * Retrieves an answer by its ID from the repository.
     *
     * @param id The ID of the answer to retrieve.
     * @return An {@link Optional} containing the {@link Answer} if found, or an empty {@link Optional} if not.
     */
    public Optional<Answer> getAnswerById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return answerRepository.findById(id);
    }

    /**
     * Retrieves answers associated with a specific form ID.
     *
     * @param id_Form The ID of the form for which answers are retrieved.
     * @return A {@link List} of {@link Answer} objects associated with the given form ID.
     */
    public List<Answer> getAnswerById_Form(String id_Form) {
        return answerRepository.getAnswerByIdForm(id_Form);
    }

    /**
     * Deletes an answer by its ID from the repository.
     *
     * @param id The ID of the answer to be deleted.
     */
    public void deleteAnswer(String id) {
        answerRepository.deleteById(id);
    }

    /**
     * Updates an existing answer in the repository.
     * If the answer does not already exist, the method does not save it.
     *
     * @param answer The answer to be updated.
     * @return The updated {@link Answer}, or {@code null} if the answer does not exist.
     */
    public Answer updateAnswer(Answer answer) {
        if (getAnswerById(answer.getId()).isPresent()) {
            return answerRepository.save(answer);
        }
        return null;
    }
}
