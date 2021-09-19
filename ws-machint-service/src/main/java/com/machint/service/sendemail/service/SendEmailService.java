/**
 * 
 */
package com.machint.service.sendemail.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.machint.service.sendemail.dto.SendEmailComponentsDTO;
import com.machint.service.sendemail.dto.SendEmailConfigDTO;
import com.machint.service.sendemail.exception.SendEmailException;

/**
 * @author Parag Ranjan
 *
 */
@Service
public interface SendEmailService
{
	public void sendEmail(SendEmailComponentsDTO emailComponentDto, List<MultipartFile> emailAttachments, SendEmailConfigDTO emailConfigDto, String host, String port, String username, String password, String protocol)throws MessagingException, IOException, SendEmailException;
	
	public void sendEmail(SendEmailComponentsDTO emailComponentDto, SendEmailConfigDTO emailConfigDto, String host, String port, String username, String password, String protocol)throws MessagingException, IOException, SendEmailException;
}
