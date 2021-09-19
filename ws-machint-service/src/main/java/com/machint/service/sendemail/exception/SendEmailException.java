/**
 * 
 */
package com.machint.service.sendemail.exception;

/**
 * @author Parag Ranjan
 *
 */
@SuppressWarnings("serial")
public class SendEmailException extends RuntimeException
{
	public SendEmailException(String errorMsg, Throwable err)
	{
		super(errorMsg, err);
	}
}
