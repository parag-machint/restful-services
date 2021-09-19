/**
 * 
 */
package com.machint.service.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.machint.service.filereader.dto.UniversalFileReaderRequestDTO;
import com.machint.service.filereader.dto.UniversalFileReaderResponseDTO;
import com.machint.service.filereader.service.UniversalFileReaderService;
import com.machint.service.sendemail.dto.SendEmailComponentsDTO;
import com.machint.service.sendemail.dto.SendEmailConfigDTO;
import com.machint.service.sendemail.dto.SendEmailRequestDTO;
import com.machint.service.sendemail.dto.SendEmailResponseDTO;
import com.machint.service.sendemail.exception.SendEmailException;
import com.machint.service.sendemail.service.SendEmailService;

/**
 * @author Parag Ranjan
 *
 */
@Controller
public class MachintServiceController 
{
	@Autowired
	SendEmailService sendEmailService;
	
	@Autowired
	UniversalFileReaderService universalFileReaderService;
	
	@RequestMapping(path = "sendEmail", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public ResponseEntity<SendEmailResponseDTO> captureEmailContents(@RequestHeader Map<String, String> headers, @RequestParam(value="attachments", required=false) List<MultipartFile> attachments, @RequestParam("requestJson") String requestJson) throws Exception
	{
		SendEmailResponseDTO responseDto;
		ObjectMapper mapper;
		SendEmailRequestDTO requestDto;
		SendEmailComponentsDTO emailComponentsDto;
		SendEmailConfigDTO emailConfigDto;
		String host=null;
		String port=null;
		String username=null;
		String password=null;
		String protocol=null;
		
		mapper= new ObjectMapper();
		
		requestDto = mapper.readValue(requestJson, SendEmailRequestDTO.class);
		
		emailComponentsDto=requestDto.getRequestData().getEmailComponents();
		
		emailConfigDto=requestDto.getRequestData().getEmailConfig();
		
		host=headers.get("email-host");
		port=headers.get("email-port");
		username=headers.get("email-username");
		password=headers.get("email-password");
		protocol=headers.get("email-protocol");
		
		try
		{			
			if(host==null||port==null||username==null||password==null||protocol==null||emailComponentsDto==null||emailConfigDto==null||emailComponentsDto.getBody()==null||emailComponentsDto.getSubject()==null||emailComponentsDto.getRecipientList()==null||emailConfigDto.getApplicationName()==null||emailConfigDto.getUsername()==null||emailConfigDto.getDisplayName()==null)
			{
				responseDto=new SendEmailResponseDTO();
				responseDto.setStatusCode("400");
				responseDto.setMessage("One or more mandatory parameters are missing from request header or request body.");
				
				return new ResponseEntity<SendEmailResponseDTO>(responseDto, HttpStatus.BAD_REQUEST);
			}
			else
			{
				if(attachments==null)
				{
					sendEmailService.sendEmail(emailComponentsDto, emailConfigDto, host, port, username, password, protocol);
				}
				else
				{
					sendEmailService.sendEmail(emailComponentsDto, attachments, emailConfigDto, host, port, username, password, protocol);
				}
				
				responseDto=new SendEmailResponseDTO();
				responseDto.setStatusCode("200");
				responseDto.setMessage("Success");
				
				return new ResponseEntity<SendEmailResponseDTO>(responseDto, HttpStatus.OK);
			}
		}
		catch (SendEmailException|IOException|MessagingException e)
		{
			responseDto=new SendEmailResponseDTO();
			responseDto.setStatusCode("500");
			responseDto.setMessage(e.getMessage());
			
			return new ResponseEntity<SendEmailResponseDTO>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(path = "fileReader", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public ResponseEntity<UniversalFileReaderResponseDTO> captureFileData(@RequestParam("file") MultipartFile multipartFile, @RequestParam("requestJson") String requestJson) throws Exception
	{
		UniversalFileReaderResponseDTO responseDto;
		ObjectMapper mapper;
		UniversalFileReaderRequestDTO requestDto;
		
		mapper= new ObjectMapper();
		
		requestDto = mapper.readValue(requestJson, UniversalFileReaderRequestDTO.class);
		
		try 
		{
			if(multipartFile==null||requestDto.getRequestData().getFileExt()==null||requestDto.getRequestData().getFileExt()=="")
			{
				responseDto=new UniversalFileReaderResponseDTO();
				responseDto.setStatusCode("400");
				responseDto.setMessage("One or more mandatory parameters are missing");
				return new ResponseEntity<UniversalFileReaderResponseDTO>(responseDto, HttpStatus.BAD_REQUEST);
			}
			else
			{
				responseDto=new UniversalFileReaderResponseDTO();
				responseDto.setStatusCode("200");
				responseDto.setMessage("Success");
				responseDto.setFileName(multipartFile.getOriginalFilename());
				responseDto.setTextList(universalFileReaderService.readFile(multipartFile, requestDto.getRequestData().getFileExt()));
				return new ResponseEntity<UniversalFileReaderResponseDTO>(responseDto, HttpStatus.OK);
			}
		} 
		catch (Exception e) 
		{
			responseDto=new UniversalFileReaderResponseDTO();
			responseDto.setStatusCode("500");
			responseDto.setMessage(e.getMessage());
			return new ResponseEntity<UniversalFileReaderResponseDTO>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
