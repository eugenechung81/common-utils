package com.common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils
{

	private static final String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";
	public static final DateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);

	public static Date formatToDate(Serializable dateValue)
	{
		try
		{
			if (dateValue.toString().split(" ").length <= 1)
			{
				dateValue = dateValue.toString() + " 00:00:00";
			}
			return dateFormatter.parse((String) dateValue);
		}
		catch (Exception ex)
		{
			return null;
		}
	}

}
