package integracionapp.psgtrading.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final AmazonSimpleEmailService client;

    @Value("${sender.email}")
    private String fromAddress;

    public EmailService() {
        AWSCredentialsProvider credentialsProvider = new EnvironmentVariableCredentialsProvider();
        this.client = AmazonSimpleEmailServiceClientBuilder.standard().withCredentials(credentialsProvider).build();
    }

    public void sendEmail(String to, String subject, String body) {
        try {
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(new Destination().withToAddresses(to))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content().withCharset("UTF-8").withData(body)))
                            .withSubject(new Content().withCharset("UTF-8").withData(subject)))
                    .withSource(this.fromAddress);

            SendEmailResult result = client.sendEmail(request);
            System.out.println("Email sent: " + result.getMessageId());
        } catch (AmazonClientException e) {
            System.out.println("Error sending email: " + e);
        }

    }
}
