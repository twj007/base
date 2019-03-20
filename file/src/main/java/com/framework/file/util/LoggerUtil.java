package com.framework.file.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {

    private static final Logger logger = LoggerFactory.getLogger(LoggerUtil.class);

    public static void log(LOGLEVEL level, String msg){

        switch (level){
            case INFO:
                logger.info(msg);
                break;
            case WARN:
                logger.warn(msg);
                break;
            case DEBUG:
                logger.debug(msg);
                break;
            case ERROR:
                logger.error(msg);
                break;
            case FATA:
                logger.error(msg);
                break;
            default:
                logger.info(msg);
                break;
        }
    }

    public static void log(LOGLEVEL level, String msg, Object... padding){

        switch (level){
            case INFO:
                logger.info(msg, padding);
                break;
            case WARN:
                logger.warn(msg, padding);
                break;
            case DEBUG:
                logger.debug(msg, padding);
                break;
            case ERROR:
                logger.error(msg, padding);
                break;
            case FATA:
                logger.error(msg, padding);
                break;
            default:
                logger.info(msg, padding);
                break;
        }

    }
}
