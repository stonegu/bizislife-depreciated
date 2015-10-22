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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.bizislife.core.appservice.ActionLogService;
import com.bizislife.core.configuration.ApplicationConfiguration;

public class EmailMessageListener implements MessageListener {
	private static final Logger logger = LoggerFactory.getLogger(EmailMessageListener.class);
	
    public static enum EmailType {
    	signup
    }
    
    @Autowired
    ActionLogService actionLogService;
    
    @Autowired
    private ApplicationConfiguration applicationConfiguration;
    
    private VelocityEngine velocityEngine;
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	@Override
	public void onMessage(Message message) {
		
		String from = null;
		List<String> to = new ArrayList<>();
		String body = null;
		String subject = null;

		
		final ActiveMQMapMessage mapMessage = (ActiveMQMapMessage) message;
		
		try {
			EmailType emailtype = EmailType.valueOf(mapMessage.getString("type"));
			if (emailtype==EmailType.signup) {
				String sendto = mapMessage.getString("sendTo");
				String token = mapMessage.getString("token");
				String username = mapMessage.getString("username");
				from = "info@bizislife.com";
				
				
				if (sendto!=null && token!=null) {
					to.add(sendto);
					
					String signupConfirmLink = generateSignupConfirmLink(token);
					
					Map<String, Object> model = new HashMap<String, Object>();
					model.put("username", username);
					model.put("signupConfirmLink", signupConfirmLink);
					
					String vmFileLocation = "/email/emailSignup.vm";
					
					// get body from velocity template:
					body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, vmFileLocation, "UTF-8", model);
					subject = "Welcome to Bizislife";

			        // Construct an object to contain the recipient address.
			        Destination theDestination = new Destination().withToAddresses(to.toArray(new String[0]));

			        // Create the subject and body of the message.
			        Content theSubject = new Content().withData(subject);
			        Content theTextBody = new Content().withData(body);
			        Body theBody = new Body().withHtml(theTextBody);

			        // Create a message with the specified subject and body.
			        com.amazonaws.services.simpleemail.model.Message theMessage = new com.amazonaws.services.simpleemail.model.Message().withSubject(theSubject).withBody(theBody);

			        // Assemble the email.
			        SendEmailRequest request = new SendEmailRequest().withSource(from).withDestination(theDestination).withMessage(theMessage);

			        try {
			            AWSCredentials credentials = new ClasspathPropertiesFileCredentialsProvider().getCredentials();
			            // Instantiate an Amazon SES client, which will make the service call with the supplied AWS credentials.
			            AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(credentials);
			            Region REGION = Region.getRegion(Regions.US_EAST_1);
			            client.setRegion(REGION);

			            // Send the email.
			            SendEmailResult sendEmailResult = client.sendEmail(request);
			            System.out.println("Email sent!");
			            
			            // create a log
			            actionLogService.createEmailLog(username, EmailType.signup, sendto, null, vmFileLocation);

			        } catch (Exception ex) {
			            System.out.println("The email was not sent.");
			            System.out.println("Error message: " + ex.getMessage());
			        }
			        
				} else {
					logger.error("sendto & token can't be null");
				}
			}
			
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String generateSignupConfirmLink(String token) {
		StringBuilder link = new StringBuilder();
		link.append(applicationConfiguration.getHostName());
		link.append("/sign/regitrationConfirm/").append(token);
		return link.toString();
	}
	
}
