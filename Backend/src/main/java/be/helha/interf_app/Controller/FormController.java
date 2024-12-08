package be.helha.interf_app.Controller;
import be.helha.interf_app.Model.Form;
import be.helha.interf_app.Service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controller class for handling HTTP requests related to forms.
 *
 * This controller defines endpoints for:
 * - Creating a new form
 * - Retrieving all forms
 * - Retrieving a form by its ID
 * - Deleting a form by its ID
 */
@RestController
@RequestMapping("/api/forms")
@CrossOrigin(origins = "*") // Allow Angular to access endpoints
public class FormController {
    /**
     * Service layer for form-related operations.
     */
    @Autowired
    private FormService formService;

    /**
     * Endpoint to create a new form.
     *
     * @param form the form object to be created
     * @return the created form wrapped in a ResponseEntity
     */
    @PostMapping
    public ResponseEntity<Form> createForm(@RequestBody Form form) {
        return ResponseEntity.ok(formService.saveForm(form));
    }

    /**
     * Endpoint to retrieve all forms.
     *
     * @return a list of all forms wrapped in a ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<Form>> getAllForms() {
        return ResponseEntity.ok(formService.getAllForms());
    }

    /**
     * Endpoint to retrieve a form by its ID.
     *
     * @param id the unique identifier of the form
     * @return the form object wrapped in a ResponseEntity, or a 404 status if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Form> getFormById(@PathVariable String id) {
        return formService.getFormById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to delete a form by its ID.
     *
     * @param id the unique identifier of the form to delete
     * @return a ResponseEntity with no content upon successful deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForm(@PathVariable String id) {
        formService.deleteForm(id);
        return ResponseEntity.noContent().build();
    }
}