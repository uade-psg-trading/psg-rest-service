package integracionapp.psgtrading.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private AmazonSimpleEmailService client;

    @Value("${sender.email}")
    private String fromAddress;


    public EmailService(Environment env) {
        if (env.acceptsProfiles(Profiles.of("aws"))) {
            try {
                this.client = AmazonSimpleEmailServiceClientBuilder.standard()
                        .withCredentials(new InstanceProfileCredentialsProvider(false))
                        .build();
            } catch (AmazonClientException e) {
                this.client = null;
            }
        } else {
            this.client = null;
        }
    }

    public void sendEmail(String to, String subject, String body) {
        if (this.client != null) {
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
}
