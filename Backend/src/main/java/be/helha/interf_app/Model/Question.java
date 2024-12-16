package be.helha.interf_app.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * Represents a question in a form with the following attributes:
 *
 * Attributes:
 * - inputQuestion: The text of the question.
 * - inputTypeQuestion: The type of the question (e.g., text, multiple-choice).
 * - inputChoices: A list of choices for questions that have predefined answers.
 * - inputAnswerMultiple: Indicates whether multiple answers are allowed.
 * - inputRequired: Specifies if answering the question is mandatory.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    /**
     * The text of the question.
     */
    private String inputQuestion;

    /**
     * The type of the question (e.g., text, multiple-choice).
     */
    private String inputTypeQuestion;

    /**
     * A list of choices for questions that have predefined answers.
     */
    private List<String> inputChoices;

    /**
     * Indicates whether multiple answers are allowed.
     */
    private boolean inputAnswerMultiple;

    /**
     * Specifies if answering the question is mandatory.
     */
    private boolean inputRequired;
}
