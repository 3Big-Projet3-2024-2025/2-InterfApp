package be.helha.interf_app.Controller;

import be.helha.interf_app.Model.EmailDetails;
import be.helha.interf_app.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
/**
 * Controller class for handling email-related API requests.
 * This class provides an endpoint to send emails via the EmailService.
 *
 * Endpoints:
 * - POST /api/mail/send: Sends an email using the provided EmailDetails.
 */
@RestController
@RequestMapping("/api/mail")
@CrossOrigin(origins = "*")
public class EmailController {
    /**
     * The service used to handle email sending operations.
     */
    @Autowired
    private EmailService emailService;
    /**
     * Endpoint to send an email with the details provided in the request body.
     *
     * This method receives an EmailDetails object from the request body,
     * and uses the EmailService to send the email. It returns a response with
     * the result of the operation.
     *
     * @param emailDetails The details of the email to be sent, including recipient, subject, and message.
     * @return A ResponseEntity containing the status and a message:
     *         - If email sending is successful, a 200 OK response with the success message.
     *         - If there is an error, a 500 Internal Server Error response with the error message.
     */
    @PostMapping("/send")
    public ResponseEntity<?> sendMail(@RequestBody EmailDetails emailDetails) {
        String response = emailService.sendSimpleMail(emailDetails);
        if (response.startsWith("Erreur")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", response));
        }
        return ResponseEntity.ok(Map.of("message", response));
    }
}
