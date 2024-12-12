package be.helha.interf_app.Service;

import be.helha.interf_app.Model.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public String sendSimpleMail(EmailDetails emailDetails) {
        try {
            // Créer le message
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setSubject(emailDetails.getSubject());
            mailMessage.setText(emailDetails.getMessage());

            // Envoyer le mail
            mailSender.send(mailMessage);
            return "Mail envoyé avec succès !";
        } catch (Exception e) {
            return "Erreur lors de l'envoi du mail : " + e.getMessage();
        }
    }
}
