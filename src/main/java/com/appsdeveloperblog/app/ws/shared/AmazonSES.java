package com.appsdeveloperblog.app.ws.shared;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.appsdeveloperblog.app.ws.security.SecurityConstants;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;

public class AmazonSES {

	final String FROM = "hajiyev1996@gmail.com";

	final String SUBJECT = "One last step to complete your registration with BlogApp";

	final String HTMLBODY = "<h1>Please verify your email email address </h1>"
			+ "<a href='http://localhost:8080/verification-service/email-verification.html?token=$tokenValue'>Final step to  complete your registration. please click me</a>"
			+ "<br/>" + " Thank you !";

	final String TEXTBODY = "Please verify your email address " + "<br/>"
			+ " http://localhost:8080/verification-service/email-verification.html?token=$tokenValue";

	public void verifyEmail(UserDto userDto) {

		AWSCredentials credentials = new BasicAWSCredentials(SecurityConstants.ACCESS_KEY,
				SecurityConstants.SECRET_KEY);

		AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_1).build();

		String htmlBodyWithToken = HTMLBODY.replace("$tokenValue", userDto.getEmailVerificationToken());
		String textBodyWithToken = HTMLBODY.replace("$tokenValue", userDto.getEmailVerificationToken());

		SendEmailRequest request = new SendEmailRequest()
				.withDestination(new Destination().withToAddresses(userDto.getEmail()))
				.withMessage(new Message()
						.withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBodyWithToken))
								.withText(new Content().withCharset("UTF-8").withData(textBodyWithToken)))
						.withSubject(new Content().withCharset("UTF-8").withData(SUBJECT)))
				.withSource(FROM);

		client.sendEmail(request);

		System.out.println("Email sent !");

	}

}
