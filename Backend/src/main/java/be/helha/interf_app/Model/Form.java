package be.helha.interf_app.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
/**
 * Represents a form containing a set of questions.
 *
 * Attributes:
 * - id: Unique identifier for the form.
 * - title: Title of the form.
 * - questions: List of questions associated with the form.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Forms")
public class Form {
    /**
     * Unique identifier for the form.
     */
    @Id
    private String id;
    /**
     * identifier for the group of the form.
     */
    private String idGroup;
    /**
     * Title of the form.
     */
    private String title;
    /**
     * List of questions associated with the form.
     */
    private List<Question> questions;
}
