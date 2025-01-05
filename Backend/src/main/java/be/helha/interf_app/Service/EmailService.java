package be.helha.interf_app.Service;

import be.helha.interf_app.Model.EmailDetails;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
/**
 * Service class for sending simple email messages.
 * This class provides a method for sending emails with a recipient, subject, and message content.
 *
 * Attributes:
 * - mailSender: The JavaMailSender used to send the email.
 */
@Service
public class EmailService {
    /**
     * The JavaMailSender instance used to send email messages.
     */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Sends an email with the provided details.
     * Supports sending both plain text and HTML emails.
     *
     * @param emailDetails The details of the email to be sent.
     * @return A message indicating whether the email was sent successfully or if there was an error.
     */
    public String sendSimpleMail(EmailDetails emailDetails) {
        try {
            // Validate email details
            if (emailDetails.getRecipient() == null && emailDetails.getMessage() == null && emailDetails.getSubject() ==null){
                return "Error sending email: Invalid email details";
            }
            if (emailDetails.getRecipient() == null || !emailDetails.getRecipient().contains("@")) {
                return "Error sending email: Invalid email address";
            }
            if (emailDetails.getMessage() == null || emailDetails.getMessage().isEmpty()) {
                return "Error sending email: Message content is empty";
            }

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setTo(emailDetails.getRecipient());
            messageHelper.setSubject(emailDetails.getSubject());

            // The message will be sent as HTML (with a clickable link)
            String htmlMessage = "<html><body>" + emailDetails.getMessage() +
                    "<br></body></html>";
            messageHelper.setText(htmlMessage, true); // true indicates HTML content

            mailSender.send(mimeMessage);
            return "Mail sent successfully!";
        } catch (Exception e) {
            return "Error sending email: " + e.getMessage();
        }
    }
}
