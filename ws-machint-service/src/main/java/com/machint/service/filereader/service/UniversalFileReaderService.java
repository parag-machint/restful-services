/**
 * 
 */
package com.machint.service.filereader.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Parag Ranjan
 *
 */
@Service
public interface UniversalFileReaderService 
{
	public String[] readFile(MultipartFile file, String fileType)throws Exception;
}
