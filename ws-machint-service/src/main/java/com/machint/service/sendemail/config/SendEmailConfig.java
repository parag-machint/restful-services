/**
 * 
 */
package com.machint.service.sendemail.config;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author Parag Ranjan
 *
 */
public class SendEmailConfig 
{	
    public JavaMailSender getJavaMailSender(String host, String port, String username, String password, String protocol) throws IOException
    {
    	String filename=null;
		InputStream is = null;
	    Properties prop = null;
	    JavaMailSenderImpl mailSender = null;
		
		filename="port"+port+".properties";
		
		is=new ClassPathResource(filename).getInputStream();
		
		prop=new Properties();
		prop.load(is);
		
		//Email Configs
		mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(Integer.parseInt(port));
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setProtocol(protocol);
        mailSender.setDefaultEncoding("UTF-8");
         
        Properties otherProps = mailSender.getJavaMailProperties();
        otherProps.put("mail.smtp.auth", prop.getProperty("auth"));
        if(mailSender.getPort()==465)
        {
        	otherProps.put("mail.smtp.ssl.enable", prop.getProperty("ssl-enable"));
        	otherProps.put("mail.smtp.ssl.trust", host);
        }
        else
        if(mailSender.getPort()==587)
        {
        	otherProps.put("mail.smtp.starttls.enable", prop.getProperty("starttls-enable"));
        }
        else
        {
        	return null;
        }
          
        return mailSender;
    }
}
