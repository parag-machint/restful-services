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
public class UniversalFileReaderResponseDTO 
{
	private String statusCode;
	private String message;
	private String fileName;
	private String textList[];
}
