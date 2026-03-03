package com.emi.notification_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import com.emi.events.mail.MailEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final JavaMailSender javaMailSender;

	@KafkaListener(topics = "Order-topic")
	public void listen(MailEvent orderPlaced) {
		String body = String.format(
				"""
						<!DOCTYPE html>
						<html>
						<head>
						    <meta charset="UTF-8">
						</head>
						<body style="margin:0; padding:0; background-color:#f4f6f8; font-family:Arial, sans-serif;">

						<table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#f4f6f8; padding:20px 0;">
						    <tr>
						        <td align="center">

						            <table width="600" cellpadding="0" cellspacing="0"
						                   style="background:#ffffff; border-radius:8px; padding:30px; box-shadow:0 2px 8px rgba(0,0,0,0.05);">

						                <!-- Header -->
						                <tr>
						                    <td align="center" style="padding-bottom:20px;">
						                        <h2 style="margin:0; color:#2c3e50;">🎉 Payment Successful</h2>
						                    </td>
						                </tr>

						                <!-- Greeting -->
						                <tr>
						                    <td style="color:#555555; font-size:15px; line-height:1.6;">
						                        <p>Hi <strong>%s %s</strong>,</p>

						                        <p>
						                            Great news! Your purchase on <strong>Inkly</strong> has been successfully completed.
						                        </p>

						                        <!-- Order Details Box -->
						                        <div style="background:#f9fafb; padding:15px; border-radius:6px; margin:20px 0;">
						                            <p style="margin:5px 0;"><strong>Order Type:</strong> %s</p>
						                            <p style="margin:5px 0;"><strong>Amount Paid:</strong> ₹%s</p>
						                        </div>

						                        <p>
						                            You can now access your content directly from the
						                            <strong>Library</strong> section in the app.
						                        </p>

						                        <p>
						                            If you need any assistance, feel free to reach out to our support team.
						                        </p>

						                        <p style="margin-top:25px;">
						                            Happy Reading 📖<br/>
						                            <strong>Team EMI</strong>
						                        </p>
						                    </td>
						                </tr>

						            </table>

						        </td>
						    </tr>
						</table>

						</body>
						</html>
						""",
				orderPlaced.getFirstName(), orderPlaced.getLastName(), orderPlaced.getOrderType(),
				orderPlaced.getPrice());
		MimeMessagePreparator mimeMessagePresparator = mimeMessage -> {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setFrom("himanshumaurya447756@gmail.com");
			helper.setTo(orderPlaced.getEmail().toString());
			helper.setSubject(String.format("Order Placed successfully " + orderPlaced.getOrderType()));
			helper.setText(body, false);
		};

		try {
			javaMailSender.send(mimeMessagePresparator);
		} catch (MailException ex) {
			throw new RuntimeException("Mail couldnt be sent to the user");
		}
	}
}
