package com.histograph.server.controllers;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.histograph.server.entities.Image;
import com.histograph.server.persistence.ImagePersistence;
import com.histograph.server.user.Location;

/**
 * 
 * @author alandonohoe
 * Called async. when user turns on phone app
 * Request passes location and direction of mobile device
 * Response passes images that correspond to that location.
 * Initially, count is made of images in 0.2 mile radius of current location.
 * If there is no images, then none are returned.
 * If there are more than x ?? images, then a smaller radius (0.1m) and direction (NESW) 
 * are used to filter the images returned.
 * 
 * 
 * refs:
 * 
 * [1] http://jeremyblythe.blogspot.co.uk/2010/07/google-app-engine-image-storage.html
 * 
 */
public class GetHistographImagesServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(GetHistographImagesServlet.class.getName());

	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}



	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// See: http://www.knowledgetip.com/index.php/home/software-development/1-java/16-ajaxservlet
		// 	for ajax servlet...
		
		resp.setContentType("text/html");
		resp.setHeader("Access-Control-Allow-Origin","*");
		 //resp.setContentType("image/jpeg");
		
		//TODO: some error-handling...
		log.info("path = " + req.getQueryString());
		
		String longitude =  req.getParameter("long");
		String latitude = req.getParameter("lat");
		String dir = req.getParameter("dir");
		String accuracy =  req.getParameter("accuracy");
		
		log.info("longitude =" +longitude );
		log.info("latitude = " + latitude);
		log.info("dir = " + dir);
		log.info("accuracy = " + accuracy);
		
		Double longDouble = Double.parseDouble(longitude);
		Double latDouble  = Double.parseDouble(latitude);
		Integer dirInteger = Integer.parseInt(dir);
		
		//String 
		Location location = new Location(longDouble, latDouble, dirInteger);
		

	      resp.getWriter().println("Hello World ");
	      
	      ImagePersistence imgPersistence = new ImagePersistence();
	      List<Image> images;
	      
	      //TODO: for now getting ALL images... but really just want those that are local...
	      images = imgPersistence.getAllImages();
	      //TODO: replace with 
	      // imgPersistence.getLocalImagesInChronOrder();
	      
	      
	      
	      
	      //from [1]:
//	      String picId = req.getParameter("id");
//	      if (picId != null) {
//	          Pic p = dao.getPic(Long.parseLong(picId));
//	          if (p != null) {
//	              res.setContentType("image/jpeg");
//	              res.setHeader("Cache-Control", "max-age=1209600"); //Cache for two weeks
//	              res.getOutputStream().write(p.getImage().getBytes());                
//	          }
//	      }
	}
	
	

	
}
