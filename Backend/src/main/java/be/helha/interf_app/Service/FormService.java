package be.helha.interf_app.Service;


import be.helha.interf_app.Model.Form;
import be.helha.interf_app.Repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
/**
 * Service class for managing and performing operations related to Form entities.
 * This class provides methods to save, retrieve, and delete forms from the repository.
 *
 * Attributes:
 * - formRepository: The repository used for accessing Form data in the database.
 */
@Service
public class FormService {
    /**
     * The repository for accessing and performing CRUD operations on the Form data.
     */
    @Autowired
    private FormRepository formRepository;
    @Autowired
    private GroupService groupService;
    /**
     * Saves a new form or updates an existing form in the repository.
     *
     * @param form The form to be saved.
     * @return The saved form.
     */
    public Form saveForm(Form form) {
        if(getFormById(form.getId()).isEmpty()&& groupService.getGroupById(form.getId()).isPresent()) {
            return formRepository.save(form);
        }
        return null;
    }
    /**
     * Retrieves all the forms stored in the repository.
     *
     * @return A list of all forms.
     */
    public List<Form> getAllForms() {
        return formRepository.findAll();
    }
    /**
     * Retrieves a form by its ID from the repository.
     *
     * @param id The ID of the form to be retrieved.
     * @return An Optional containing the form if found, or an empty Optional if not.
     */
    public Optional<Form> getFormById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return formRepository.findById(id);
    }

    public List<Form> getFormByIdGroup(String idGroup) {
        return formRepository.findByIdGroup(idGroup);
    }
    /**
     * Deletes a form by its ID from the repository.
     *
     * @param id The ID of the form to be deleted.
     */
    public void deleteForm(String id) {
        formRepository.deleteById(id);
    }

    public Form updateForm(Form form) {
        if(getFormById(form.getId()).isPresent() && getFormById(form.getId()).get().getIdGroup().equals(form.getIdGroup())) {
            return formRepository.save(form);
        }
        return null;
    }
}
