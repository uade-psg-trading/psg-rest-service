package integracionapp.psgtrading.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;

public class EmailService {

    private final AmazonSimpleEmailService client;
    private final String fromAddress = "brea.emanuel@gmail.com"; // TODO use another verified email in AWS SES

    public EmailService() {
        AWSCredentialsProvider credentialsProvider = new EnvironmentVariableCredentialsProvider();
        this.client = AmazonSimpleEmailServiceClientBuilder.standard().withCredentials(credentialsProvider).build();
    }

    public void sendEmail(String to, String subject, String body) {
        try{
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(new Destination().withToAddresses(to))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content().withCharset("UTF-8").withData(body)))
                            .withSubject(new Content().withCharset("UTF-8").withData(subject)))
                    .withSource(this.fromAddress);

            SendEmailResult result = client.sendEmail(request);
            System.out.println("Email sent: " + result.getMessageId());
        }
        catch (AmazonClientException e){
            System.out.println("Error sending email: " + e);
        }

    }
}
