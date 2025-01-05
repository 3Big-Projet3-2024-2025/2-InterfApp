package be.helha.interf_app.Controller;

import be.helha.interf_app.Model.Form;
import be.helha.interf_app.Service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class for handling HTTP requests related to forms.
 *
 * This controller defines endpoints for performing CRUD operations on forms:
 * - Creating a new form
 * - Retrieving all forms
 * - Retrieving a specific form by its ID
 * - Updating an existing form
 * - Deleting a form by its ID
 *
 * All endpoints are cross-origin enabled to allow interaction with frontend applications (e.g., Angular).
 */
@RestController
@RequestMapping("/api/forms")
@CrossOrigin(origins = "*") // Allow all origins to access endpoints
public class FormController {

    /**
     * Service layer for handling form-related business logic.
     */
    @Autowired
    private FormService formService;

    /**
     * Endpoint to create a new form.
     *
     * This method creates a new form based on the provided form object in the request body.
     * It returns the created form wrapped in a ResponseEntity with an HTTP 200 status if successful.
     * If creation fails, an HTTP 400 response is returned.
     *
     * @param form the form object to be created, provided in the request body
     * @return the created form wrapped in a ResponseEntity with HTTP 200 status, or HTTP 400 if creation fails
     */
    @PostMapping
    public ResponseEntity<Form> createForm(@RequestBody Form form) {
        Form savedForm = formService.saveForm(form);
        if (savedForm != null) {
            return ResponseEntity.ok(savedForm);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint to retrieve all forms.
     *
     * This method retrieves a list of all forms and returns them wrapped in a ResponseEntity
     * with HTTP 200 status.
     *
     * @return a ResponseEntity containing a list of all forms with HTTP 200 status
     */
    @GetMapping
    public ResponseEntity<List<Form>> getAllForms() {
        List<Form> forms = formService.getAllForms();
        return ResponseEntity.ok(forms);
    }

    /**
     * Endpoint to retrieve a specific form by its unique ID.
     *
     * This method retrieves a form by its unique ID. If the form is found, it returns
     * the form with HTTP 200 status. If the form is not found, it returns HTTP 404.
     *
     * @param id the unique identifier of the form to retrieve, extracted from the URL path
     * @return a ResponseEntity containing the requested form with HTTP 200 status, or HTTP 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Form> getFormById(@PathVariable String id) {
        Optional<Form> form = formService.getFormById(id);
        return form.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<List<Form>> getFormsByIdGroup(@PathVariable String id) {
        List<Form> forms = formService.getFormByIdGroup(id);
        return ResponseEntity.ok(forms);
    }

    /**
     * Endpoint to delete a specific form by its unique ID.
     *
     * This method deletes a form by its unique ID. If the form is found and deleted
     * successfully, it returns HTTP 204 status. If the form is not found, it returns HTTP 404.
     *
     * @param id the unique identifier of the form to delete, extracted from the URL path
     * @return a ResponseEntity with HTTP 204 status upon successful deletion, or HTTP 404 if the form is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForm(@PathVariable String id) {
        Optional<Form> form = formService.getFormById(id);
        if (form.isPresent()) {
            formService.deleteForm(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to update an existing form.
     *
     * This method updates an existing form based on the provided form object in the request body.
     * If the update is successful, it returns the updated form wrapped in a ResponseEntity with HTTP 200 status.
     * If the update fails or the form is not found, it returns HTTP 404.
     *
     * @param form the updated form object, provided in the request body
     * @return a ResponseEntity containing the updated form with HTTP 200 status, or HTTP 404 if the update fails
     */
    @PutMapping
    public ResponseEntity<Form> updateForm(@RequestBody Form form) {
        Form updatedForm = formService.updateForm(form);
        if (updatedForm != null) {
            return ResponseEntity.ok(updatedForm);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
