package com.histograph.server.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class GetBlobStoreURLServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(GetBlobStoreURLServlet.class
			.getName());


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String blobstoreURL = "";
		
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		
		try{
			// creates new blobstore url with  callback servlet -  uploadimages - to be called when asynx upload of img is complete
			blobstoreURL = blobstoreService.createUploadUrl("/uploadimages"); 
		
		}catch(Exception e){
			log.warning(e.getMessage());
			resp.sendError(resp.SC_GONE);
		}

		log.info("blobstoreURL = " + blobstoreURL);

		resp.getWriter().append(blobstoreURL);
		
		
	}
	
	

}
