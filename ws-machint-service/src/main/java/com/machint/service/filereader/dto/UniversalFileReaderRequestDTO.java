/**
 * 
 */
package com.machint.service.filereader.dto;

import lombok.Data;

/**
 * @author Parag Ranjan
 *
 */
@Data
public class UniversalFileReaderRequestDTO 
{
	String requestorId;
	String source;
	UniversalFileReaderRequestDataDTO requestData;
}
