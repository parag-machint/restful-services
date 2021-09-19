/**
 * 
 */
package com.machint.service.sendemail.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.machint.service.sendemail.config.SendEmailConfig;
import com.machint.service.sendemail.dto.SendEmailComponentsDTO;
import com.machint.service.sendemail.dto.SendEmailConfigDTO;
import com.machint.service.sendemail.exception.SendEmailException;

/**
 * @author Parag Ranjan
 *
 */
@Service
public class SendEmailServiceImpl implements SendEmailService
{
	
	@Override
	public void sendEmail(SendEmailComponentsDTO emailComponentDto, List<MultipartFile> emailAttachments, SendEmailConfigDTO emailConfigDto, String host, String port, String username, String password, String protocol)throws MessagingException, IOException, SendEmailException
	{
		MimeMessageHelper helper;
		MimeMessage msg;
		JavaMailSender javaMailSender;
		
		javaMailSender=new SendEmailConfig().getJavaMailSender(host, port, username, password, protocol);
		msg = javaMailSender.createMimeMessage();

		// true = multipart message
		helper = new MimeMessageHelper(msg, true);
		helper.setFrom(emailConfigDto.getUsername(), emailConfigDto.getDisplayName());
		helper.setTo(Arrays.copyOf(emailComponentDto.getRecipientList().toArray(), emailComponentDto.getRecipientList().toArray().length, String[].class));
		if(emailComponentDto.getCarbonCopyList()!=null)
		{
			helper.setCc(Arrays.copyOf(emailComponentDto.getCarbonCopyList().toArray(), emailComponentDto.getCarbonCopyList().toArray().length, String[].class));
		}
		if(emailComponentDto.getBlindCarbonCopyList()!=null)
		{
			helper.setBcc(Arrays.copyOf(emailComponentDto.getBlindCarbonCopyList().toArray(), emailComponentDto.getBlindCarbonCopyList().toArray().length, String[].class));
		}
		helper.setSubject(emailComponentDto.getSubject());
		// true = text/html
		helper.setText(emailComponentDto.getBody(), true);
		emailAttachments.stream().forEachOrdered(emailAttachment->
		{
			try 
			{
				helper.addAttachment(emailAttachment.getOriginalFilename(), new InputStreamSource()//Inner Class Implementation
						{	
					@Override
					public InputStream getInputStream() throws IOException 
					{
						return emailAttachment.getInputStream();
					}
						});
			} 
			catch (MessagingException me) 
			{
				//Because checked exception cant be rethrown from inside stream()
				throw new SendEmailException("Send Email service failed!", me);
			}
		});

		javaMailSender.send(msg);
	}
	
	@Override
	public void sendEmail(SendEmailComponentsDTO emailComponentDto, SendEmailConfigDTO emailConfigDto, String host, String port, String username, String password, String protocol)throws MessagingException, IOException, SendEmailException
	{
		MimeMessageHelper helper;
		MimeMessage msg;
		JavaMailSender javaMailSender;
		
		javaMailSender=new SendEmailConfig().getJavaMailSender(host, port, username, password, protocol);

		msg = javaMailSender.createMimeMessage();

		// true = multipart message
		helper = new MimeMessageHelper(msg, true);
		helper.setFrom(emailConfigDto.getUsername(), emailConfigDto.getDisplayName());
		helper.setTo(Arrays.copyOf(emailComponentDto.getRecipientList().toArray(), emailComponentDto.getRecipientList().toArray().length, String[].class));
		if(emailComponentDto.getCarbonCopyList()!=null)
		{
			helper.setCc(Arrays.copyOf(emailComponentDto.getCarbonCopyList().toArray(), emailComponentDto.getCarbonCopyList().toArray().length, String[].class));
		}
		if(emailComponentDto.getBlindCarbonCopyList()!=null)
		{
			helper.setBcc(Arrays.copyOf(emailComponentDto.getBlindCarbonCopyList().toArray(), emailComponentDto.getBlindCarbonCopyList().toArray().length, String[].class));
		}
		helper.setSubject(emailComponentDto.getSubject());
		// true = text/html
		helper.setText(emailComponentDto.getBody(), true);
		
		javaMailSender.send(msg);
	}
}
