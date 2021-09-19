/**
 * 
 */
package com.machint.service.filereader.utility;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Parag Ranjan
 *
 */
public class TextFileReader 
{
	public static String[] readTextFile(MultipartFile file, String charset) throws IOException
	{
		ByteArrayInputStream stream = null;
		String myString = null;
		String lines[] = null;
		
		stream = new ByteArrayInputStream(file.getBytes());
		
		myString = IOUtils.toString(stream, charset);
		
		lines = myString.split(System.lineSeparator());
		
		return lines;
	}
}
