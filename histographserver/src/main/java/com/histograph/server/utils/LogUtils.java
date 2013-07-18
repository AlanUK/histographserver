package com.histograph.server.utils;

import java.util.Enumeration;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

public class LogUtils {
	
	private static Logger log = Logger.getLogger(LogUtils.class
			.getName());

	/**
	 * 
	 * @param req - HttpServletRequest object with 0-* params & values.
	 * @return string of params and their values
	 */
	public static String listParams(HttpServletRequest req) {
		
		log.info("In listParams");
		
		String paramName, paramValue, urlParameters = "";

		// //////////////
		// Log params & values
		Enumeration<String> paramNames = req.getParameterNames();
		while (paramNames.hasMoreElements()) {

			// check that we're not at the first param, if not - then need to
			// add & to the string
			if (!urlParameters.isEmpty())
				urlParameters += "&";

			paramName = (String) paramNames.nextElement();
			urlParameters += paramName;
			urlParameters += "=";

			paramValue = req.getParameter(paramName);
			urlParameters += paramValue;
		}

		return urlParameters;
	}

}
