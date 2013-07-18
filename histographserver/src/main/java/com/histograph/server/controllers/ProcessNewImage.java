package com.histograph.server.controllers;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.histograph.server.utils.LogUtils;

/**
 * 
 * @author alandonohoe
 * Handles a new img sent from user's phone.
 * Crops img into a square, resizes it to 300 x 300 px
 * and persists it in the GAE blobstore.
 */
public class ProcessNewImage extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(ProcessNewImage.class.getName());
	
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}



	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		log.info("In doPost(...)");
		
		log.info("URL Parameters: " + LogUtils.listParams(req));
		
		
		//get image
		
		
		// get longest side
		
		
		// crop
		
		
		// shrink to 300 px to 300 px

	}
	
	

}
