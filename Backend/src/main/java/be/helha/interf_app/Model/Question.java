package be.helha.interf_app.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private String inputQuestion; // The actual question
    private String inputTypeQuestion; // The type
    private List<String> inputChoices; // For questions with choices
    private boolean inputAnswerMultiple;
    private boolean inputRequired; // If the question is mandatory
}
