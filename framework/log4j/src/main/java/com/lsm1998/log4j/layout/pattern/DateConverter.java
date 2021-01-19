package com.lsm1998.log4j.layout.pattern;

import com.lsm1998.log4j.LoggingEvent;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateConverter extends KeywordConverter
{
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private SimpleDateFormat sdf = new SimpleDateFormat();

    @Override
    public String convert(LoggingEvent e)
    {
        return dateTimeFormatter.format(Instant.ofEpochMilli((e.getTimestamp())).atZone(ZoneId.systemDefault()).toLocalDateTime());
    }
}
