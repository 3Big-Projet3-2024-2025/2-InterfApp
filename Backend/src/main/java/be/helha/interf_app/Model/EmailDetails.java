package be.helha.interf_app.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Model class representing the details of an email.
 * This class contains the recipient's email address, the subject of the email,
 * and the message content to be sent.
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetails {
    /**
     * The email address of the recipient.
     */
    private String recipient;
    /**
     * The subject of the email.
     */
    private String subject;
    /**
     * The content of the email message.
     */
    private String message;
}
