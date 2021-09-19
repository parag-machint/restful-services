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
public class SendEmailRequestDataDTO 
{
	SendEmailConfigDTO emailConfig;
	SendEmailComponentsDTO emailComponents;
}
