package be.helha.interf_app;
import be.helha.interf_app.Model.EmailDetails;
import be.helha.interf_app.Service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * Integration tests for the EmailService to verify real email sending functionality.
 */
@SpringBootTest
public class EmailTest {

    @Autowired
    private EmailService emailService;

    /**
     * Test sending a real email to ensure the EmailService works correctly with valid details.
     */
    @Test
    void testSendRealEmail() {
        // Arrange: Create an EmailDetails object with valid information
        EmailDetails emailDetails = new EmailDetails(
                "example@gmail.com", // Replace with a valid email address
                "Spring Boot Integration Test",
                "This is a test message sent from a Spring Boot integration test."
        );

        // Act: Call the method to send an email
        String response = emailService.sendSimpleMail(emailDetails);

        // Assert: Verify that the email was sent successfully
        assertEquals("Mail sent successfully!", response);
    }
    /**
     * Test sending a real email to ensure the EmailService works correctly with valid details.
     * Also verifies that a clickable link is included in the message.
     */
    @Test
    void testSendRealEmailWithLink() {
        // Arrange: Create an EmailDetails object with valid information
        EmailDetails emailDetails = new EmailDetails(
                "example@gmail.com", // Replace with a valid email address
                "Spring Boot Integration Test with Link",
                "This is a test message with a clickable link: <br><a href='https://www.youtube.com/watch?v=xvFZjo5PgG0'>Click here</a>"
        );

        // Act: Call the method to send an email
        String response = emailService.sendSimpleMail(emailDetails);

        // Assert: Verify that the email was sent successfully
        assertEquals("Mail sent successfully!", response);
    }

    /**
     * Test sending an email with an invalid recipient to verify error handling.
     */
    @Test
    void testSendEmailWithInvalidRecipient() {
        // Arrange: Create an EmailDetails object with an invalid recipient
        EmailDetails emailDetails = new EmailDetails(
                "invalid-email", // Invalid email address
                "Invalid Recipient Test",
                "This test should fail due to an invalid recipient."
        );

        // Act: Call the method to send an email
        String response = emailService.sendSimpleMail(emailDetails);

        // Assert: Verify that the email service returns an error message
        assertEquals("Error sending email: Invalid email address", response);
    }

    /**
     * Test sending an email with an empty message to verify validation.
     */
    @Test
    void testSendEmailWithEmptyMessage() {
        // Arrange: Create an EmailDetails object with an empty message
        EmailDetails emailDetails = new EmailDetails(
                "recipient@example.com", // Replace with a valid email address
                "Empty Message Test",
                "" // Empty message body
        );

        // Act: Call the method to send an email
        String response = emailService.sendSimpleMail(emailDetails);

        // Assert: Verify that the email service handles empty messages gracefully
        assertEquals("Error sending email: Message content is empty", response);
    }

    /**
     * Test sending an email with null values to ensure robust validation.
     */
    @Test
    void testSendEmailWithNullValues() {
        // Arrange: Create an EmailDetails object with null values
        EmailDetails emailDetails = new EmailDetails(null, null, null);

        // Act: Call the method to send an email
        String response = emailService.sendSimpleMail(emailDetails);

        // Assert: Verify that the email service returns an appropriate error message
        assertEquals("Error sending email: Invalid email details", response);
    }
}
