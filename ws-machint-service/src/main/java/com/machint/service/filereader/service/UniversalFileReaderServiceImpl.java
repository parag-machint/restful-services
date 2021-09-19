/**
 * 
 */
package com.machint.service.filereader.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.machint.service.filereader.utility.CharsetGuess;
import com.machint.service.filereader.utility.TextFileReader;

/**
 * @author Parag Ranjan
 *
 */
@Service
public class UniversalFileReaderServiceImpl implements UniversalFileReaderService 
{
	@Override
	public String[] readFile(MultipartFile file, String fileType) throws Exception 
	{
		if(fileType.equalsIgnoreCase("txt"))
		{
			return TextFileReader.readTextFile(file, CharsetGuess.checkCharset(file));
		}
		else
		{
			throw new Exception("File type not supported yet.");
		}
	}

}
