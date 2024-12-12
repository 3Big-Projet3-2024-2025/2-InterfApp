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
     * @param form the form object to be created, provided in the request body
     * @return the created form wrapped in a ResponseEntity with HTTP 200 status
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
     * @param id the unique identifier of the form to retrieve, extracted from the URL path
     * @return a ResponseEntity containing the requested form with HTTP 200 status, or HTTP 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Form> getFormById(@PathVariable String id) {
        Optional<Form> form = formService.getFormById(id);
        return form.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to delete a specific form by its unique ID.
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
