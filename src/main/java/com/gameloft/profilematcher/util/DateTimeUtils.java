package com.gameloft.profilematcher.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateTimeUtils.class);

    private static final String DATE_STRING_FORMAT = "yyyy-MM-dd HH:mm:ssX";

    public static Date convertDateStringToDate(String dateString) {
        try {
            if(StringUtils.isNotEmpty(dateString)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_STRING_FORMAT);
                return dateFormat.parse(dateString);
            }
        } catch (Exception e) {
            LOGGER.error("Could not convert date string {} to date", dateString, e);
        }
        return null;
    }
}
