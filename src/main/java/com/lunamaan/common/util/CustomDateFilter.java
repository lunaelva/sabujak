package com.lunamaan.common.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.mitchellbosecke.pebble.extension.Filter;

public class CustomDateFilter implements Filter {
	private final List<String> argumentNames = new ArrayList<String>();

	@Override
	public List<String> getArgumentNames() {
		// TODO Auto-generated method stub
		argumentNames.add("format");
		argumentNames.add("type");
		return argumentNames;
	}

	@Override
	public Object apply(Object input, Map<String, Object> args) {
		if(input ==null){
			return null;
		}
		
		String type = (String) args.get("type");
		String format = (String) args.get("format");
		String result ="";
		if(type.equals("date")){
			LocalDate localDate = LocalDate.parse(input.toString());
			result = localDate.format(DateTimeFormatter.ofPattern(format));
			
			if(type.equals("yyyy.MM.dd (E)")){
				DayOfWeek dayOfWeek = localDate.getDayOfWeek();
				String convertDayOfWeek = " (" + dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN) + ")";
				result = localDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + convertDayOfWeek;
			}	
		}else{
			LocalDateTime time = LocalDateTime.parse(input.toString());
			result = time.format(DateTimeFormatter.ofPattern(format));
		}
		return result;
	}
}
