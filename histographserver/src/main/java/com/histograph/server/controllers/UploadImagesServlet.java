package com.histograph.server.controllers;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.histograph.server.entities.Image;
import com.histograph.server.persistence.ImagePersistence;
//import com.google.cloud.demo.AppContext;
//import com.google.cloud.demo.ServletUtils;
//import com.google.cloud.demo.model.DemoUser;
//import com.google.cloud.demo.model.Photo;
//import com.google.cloud.demo.model.PhotoManager;

/**
 * 
 * @author alandonohoe
 * Request - Post with new image, taken by current user and list of ids of images in datastore, that user has
 * selected to be in histograph.
 * Response - URL of new composite histograph image, or... the image itself? - depending on mechanics
 * of subsequent use case: ie: how to share to FB/Twitter/ Sincerely API.
 *
 */
public class UploadImagesServlet extends HttpServlet{

	/**
	 * 	
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(UploadImagesServlet.class
			.getName());


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Image image = new Image();
		ImagePersistence imagePersistence = new ImagePersistence();

		//1. Get user data & GPS and direction info:
		String year = req.getParameter("Year");
		String latitude = req.getParameter("Lat");
		String longitude = req.getParameter("Long");
		String direction = req.getParameter("Direction");
		String description = req.getParameter("Description");
		String userNo = req.getParameter("UserNo");

		log.info("Year = " + year);
		log.info("Lat = " + latitude);
		log.info("Long = " + longitude);
		log.info("Direction = " + direction);


		//2. get image..

		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		List<BlobKey> keys = blobs.get("imgUpload");
		String keyString = keys.get(0).getKeyString();

		log.info("BlobKey = " +keyString);
		
		// construct img:
		image.setBlobKey(keys.get(0));
		image.setDirection(Double.valueOf(direction));
		image.setLatitude(Double.valueOf(latitude));
		image.setLongitude(Double.valueOf(longitude));
		image.setDescription(description);
		image.setUserId(Long.valueOf(userNo).longValue());
		image.setYear(Integer.valueOf(year));
		
		//3. Persist the new image:
		imagePersistence.SaveImage(image);
		
		//4 and have some feedback...

		if (keyString == null) {
			resp.sendRedirect("/");
		} else {
			
			resp.sendRedirect("/upload.jsp");
		}


	


		//		resp.setContentType("text/plain");
		//		resp.getOutputStream().println("OK");

		/////////
		// From GAE photo share demo:
		//		AppContext appContext = AppContext.getAppContext();
		//	    DemoUser user = appContext.getCurrentUser();
		//	    if (user == null) {
		//	      res.sendError(401, "You have to login to upload image.");
		//	      return;
		//	    }
		//	    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		//	    Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		//	    List<BlobKey> keys = blobs.get("photo");
		//	    String id = null;
		//	    boolean succeeded = false;
		//	    if (keys != null && keys.size() > 0) {
		//	      PhotoManager photoManager = appContext.getPhotoManager();
		//	      Photo photo = photoManager.newPhoto(user.getUserId());
		//	      String title = req.getParameter("title");
		//	      if (title != null) {
		//	        photo.setTitle(title);
		//	      }
		//
		//	      String isPrivate = req.getParameter(ServletUtils.REQUEST_PARAM_NAME_PRIVATE);
		//	      photo.setShared(isPrivate == null);
		//
		//	      photo.setOwnerNickname(ServletUtils.getProtectedUserNickname(user.getNickname()));
		//
		//	      BlobKey blobKey = keys.get(0);
		//	      photo.setBlobKey(blobKey);
		//
		//	      photo.setUploadTime(System.currentTimeMillis());
		//
		//	      photo = photoManager.upsertEntity(photo);
		//	      id = photo.getId().toString();
		//	      succeeded = true;
		//	    }
		//	    if (succeeded) {
		//	      res.sendRedirect(appContext.getPhotoServiceManager().getRedirectUrl(
		//	          req.getParameter(ServletUtils.REQUEST_PARAM_NAME_TARGET_URL), user.getUserId(), id));
		//	    } else {
		//	      res.sendError(400, "Request cannot be handled.");
		//	    }
		//
		/////////


	}




}
