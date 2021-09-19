/**
 * 
 */
package com.machint.service.filereader.utility;

import org.mozilla.universalchardet.UniversalDetector;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Parag Ranjan
 *
 */
public class CharsetGuess 
{
	public static String checkCharset (MultipartFile multipartFile) throws Exception 
	{
		String encoding = UniversalDetector.detectCharset(multipartFile.getInputStream());
		
		if (encoding != null) 
		{
			return encoding;
		} 
		else 
		{
			throw new Exception("File contains invalid charset");
		}
	}
}
