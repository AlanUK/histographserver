package com.histograph.server.utils;

import java.util.Calendar;

public class DateUtil {
	
	public static int getCurrentYear(){
		
		Calendar now = Calendar.getInstance();   // This gets the current date and time.
		return(now.get(Calendar.YEAR));                 // This returns the year as an int.
	}

}
