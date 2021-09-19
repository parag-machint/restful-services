/**
 * 
 */
package com.machint.service.sendemail.dto;

import java.util.List;

import lombok.Data;

/**
 * @author Parag Ranjan
 *
 */
@Data
public class SendEmailComponentsDTO
{
	List<String> recipientList;
	List<String> carbonCopyList;
	List<String> blindCarbonCopyList;
	String subject;
	String body;
}
