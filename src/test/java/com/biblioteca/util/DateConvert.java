package com.biblioteca.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.biblioteca.services.exceptions.BadRequestException;



public class DateConvert {

	public static Date convertData(String text)  {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	    Date date = new Date();
		try {
			date = sdf.parse(text);
		} catch (ParseException e) {
			throw new BadRequestException(e.getMessage());
		}
		
		return date;
	}
	
}
