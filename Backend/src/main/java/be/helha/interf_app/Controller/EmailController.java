package be.helha.interf_app.Controller;

import be.helha.interf_app.Model.EmailDetails;
import be.helha.interf_app.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/mail")
@CrossOrigin(origins = "*")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMail(@RequestBody EmailDetails emailDetails) {
        String response = emailService.sendSimpleMail(emailDetails);
        if (response.startsWith("Erreur")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", response));
        }
        return ResponseEntity.ok(Map.of("message", response));
    }
}
