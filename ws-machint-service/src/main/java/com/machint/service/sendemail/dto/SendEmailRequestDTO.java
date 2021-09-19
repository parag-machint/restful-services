/**
 * 
 */
package com.machint.service.sendemail.dto;

import lombok.Data;

/**
 * @author Parag Ranjan
 *
 */
@Data
public class SendEmailRequestDTO 
{
	String requestorId;
	String source;
	SendEmailRequestDataDTO requestData;
}
