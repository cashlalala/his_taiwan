package org.his.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import cc.johnwu.login.UserInfo;

public class CustomLogger {

	public static void log(Logger logger, Level level, String message,
			Object... args) {
		message = String.format("[%s][%s] %s", UserInfo.getUserID(),
				UserInfo.getUserName(), message);
		logger.log(level, message, args);
	}

	public static void debug(Logger logger, String message, Object... args) {
		log(logger, Level.DEBUG, message, args);
	}
	
	public static void info(Logger logger, String message, Object... args) {
		log(logger, Level.INFO, message, args);
	}
	
	public static void warn(Logger logger, String message, Object... args) {
		log(logger, Level.WARN, message, args);
	}
	
	public static void error(Logger logger, String message, Object... args) {
		log(logger, Level.ERROR, message, args);
	}
	
	public static void trace(Logger logger, String message, Object... args) {
		log(logger, Level.TRACE, message, args);
	}
}
