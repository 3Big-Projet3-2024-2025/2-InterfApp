package be.helha.interf_app.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
/**
 * Represents an answer provided by a user for a specific form.
 *
 * Attributes:
 * - id_answer: Unique identifier for the answer.
 * - id_Form: Identifier of the associated form.
 * - id_User: Identifier of the user who submitted the answer.
 * - answer: Map containing the answers, where keys are question IDs and values are the corresponding answers.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Answers")
public class Answer {
    /**
     * Unique identifier for the answer.
     */
    @Id
    private String id;
    /**
     * Identifier of the associated form.
     */
    private String idForm;
    /**
     * Identifier of the user who submitted the answer.
     */
    private String idUser;
    /**
     * Map containing the answers, where keys are question IDs and values are the corresponding answers.
     */
    private Map<String,Object> answer;
}
