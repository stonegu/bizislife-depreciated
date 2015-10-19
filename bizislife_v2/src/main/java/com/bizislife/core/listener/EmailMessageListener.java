package com.bizislife.core.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;

public class EmailMessageListener  implements MessageListener {
	
    public static enum EmailType {
    	signup
    }
    
    private VelocityEngine velocityEngine;
    
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	private String from = null;
	private List<String> to = new ArrayList<>();
	private String body = null;
	private String subject = null;

	@Override
	public void onMessage(Message message) {
		final ActiveMQMapMessage mapMessage = (ActiveMQMapMessage) message;
		
		try {
			EmailType emailtype = EmailType.valueOf(mapMessage.getString("type"));
			if (emailtype==EmailType.signup) {
				from = "info@bizislife.com";
				String sendto = mapMessage.getString("sendTo");
				String token = mapMessage.getString("token");
				
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("firstName", "stone");
				model.put("lastName", "gu");
				
				if (sendto!=null && token!=null) {
					to.add(sendto);
					// get body from velocity template:
					body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/email/emailtest.vm", "UTF-8", model);
					subject = "this is test subject";
					
					SendEmailResult sendEmailResult = null;
					
			        // Construct an object to contain the recipient address.
			        Destination destination = new Destination().withToAddresses(to.toArray(new String[0]));

			        // Create the subject and body of the message.
			        Content theSubject = new Content().withData(subject);
			        Content textBody = new Content().withData(body);
			        Body body = new Body().withHtml(textBody);

			        // Create a message with the specified subject and body.
			        com.amazonaws.services.simpleemail.model.Message theMessage = new com.amazonaws.services.simpleemail.model.Message().withSubject(theSubject).withBody(body);

			        // Assemble the email.
			        SendEmailRequest request = new SendEmailRequest().withSource(from).withDestination(destination).withMessage(theMessage);

			        try {
			            System.out.println("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");

			            /*
			             * The ProfileCredentialsProvider will return your [default]
			             * credential profile by reading from the credentials file located at
			             * (~/.aws/credentials).
			             *
			             * TransferManager manages a pool of threads, so we create a
			             * single instance and share it throughout our application.
			             */
//			            AWSCredentials credentials = null;
//			            try {
//			                credentials = new ProfileCredentialsProvider().getCredentials();
//			            } catch (Exception e) {
//			                throw new AmazonClientException(
//			                        "Cannot load the credentials from the credential profiles file. " +
//			                        "Please make sure that your credentials file is at the correct " +
//			                        "location (~/.aws/credentials), and is in valid format.",
//			                        e);
//			            }
			            
			            AWSCredentials credentials = new ClasspathPropertiesFileCredentialsProvider().getCredentials();

			            // Instantiate an Amazon SES client, which will make the service call with the supplied AWS credentials.
			            AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(credentials);

			            // Choose the AWS region of the Amazon SES endpoint you want to connect to. Note that your production
			            // access status, sending limits, and Amazon SES identity-related settings are specific to a given
			            // AWS region, so be sure to select an AWS region in which you set up Amazon SES. Here, we are using
			            // the US East (N. Virginia) region. Examples of other regions that Amazon SES supports are US_WEST_2
			            // and EU_WEST_1. For a complete list, see http://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html
			            Region REGION = Region.getRegion(Regions.US_EAST_1);
			            client.setRegion(REGION);

			            // Send the email.
			            sendEmailResult = client.sendEmail(request);
			            System.out.println("Email sent!");

			        } catch (Exception ex) {
			            System.out.println("The email was not sent.");
			            System.out.println("Error message: " + ex.getMessage());
			        }		
			        					
					
					
				}
			}
			
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
