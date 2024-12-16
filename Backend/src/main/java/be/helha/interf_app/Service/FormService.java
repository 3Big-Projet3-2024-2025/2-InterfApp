package be.helha.interf_app.Service;

import be.helha.interf_app.Model.Form;
import be.helha.interf_app.Repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing and performing operations related to {@link Form} entities.
 * This class provides methods to save, retrieve, update, and delete forms from the repository.
 */
@Service
public class FormService {

    /**
     * The repository for accessing and performing CRUD operations on {@link Form} data.
     */
    @Autowired
    private FormRepository formRepository;

    /**
     * Service for managing group-related operations, used to validate form associations.
     */
    @Autowired
    private GroupService groupService;

    /**
     * Saves a new form in the repository.
     * Ensures the form ID is unique and the associated group exists before saving.
     *
     * @param form The form to be saved.
     * @return The saved {@link Form}, or {@code null} if the form already exists or the group is invalid.
     */
    public Form saveForm(Form form) {
        if (getFormById(form.getId()).isEmpty() && groupService.getGroupById(form.getId()).isPresent()) {
            return formRepository.save(form);
        }
        return null;
    }

    /**
     * Retrieves all forms stored in the repository.
     *
     * @return A {@link List} of all {@link Form} objects.
     */
    public List<Form> getAllForms() {
        return formRepository.findAll();
    }

    /**
     * Retrieves a form by its ID from the repository.
     *
     * @param id The ID of the form to retrieve.
     * @return An {@link Optional} containing the {@link Form} if found, or an empty {@link Optional} if not.
     */
    public Optional<Form> getFormById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return formRepository.findById(id);
    }

    /**
     * Retrieves forms associated with a specific group ID.
     *
     * @param idGroup The ID of the group for which forms are retrieved.
     * @return A {@link List} of {@link Form} objects associated with the given group ID.
     */
    public List<Form> getFormByIdGroup(String idGroup) {
        return formRepository.findByIdGroup(idGroup);
    }

    /**
     * Deletes a form by its ID from the repository.
     *
     * @param id The ID of the form to delete.
     */
    public void deleteForm(String id) {
        formRepository.deleteById(id);
    }

    /**
     * Updates an existing form in the repository.
     * Ensures the form exists and the group ID is unchanged before updating.
     *
     * @param form The form to be updated.
     * @return The updated {@link Form}, or {@code null} if the form does not exist or the group ID does not match.
     */
    public Form updateForm(Form form) {
        if (getFormById(form.getId()).isPresent() &&
                getFormById(form.getId()).get().getIdGroup().equals(form.getIdGroup())) {
            return formRepository.save(form);
        }
        return null;
    }
}
