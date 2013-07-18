package com.histograph.server.controllers;

import java.io.IOException;
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
import com.histograph.server.persistence.ImagePersistence;
import com.histograph.server.persistence.UserPersistence;
import com.histograph.server.utils.HistographConstants;
import com.histograph.server.utils.LogUtils;
import com.histograph.server.entities.Image;
import com.histograph.server.entities.User;
import com.histograph.server.imageprocessing.ImageParser;

/**
 * 
 * @author alandonohoe
 * Callback servlet, called by the blobstore when it's completed asynch'ing saving the img to blobstore
 * Once this is called we have the user's img saved to the blobstore, now need to get that img's blobstore key
 * and save this as a new Image entry in the datastore.
 * Also to be persisted:
 * GPS location of this img
 * user id/ unique phone id
 * 	- this needs to be checked with existing unique device id's so that we associate photos from the same device
 * 		to the same user... - later when we have more details for user we can associate the device id with a user account
 * 
 * 
 *  
 */
public class PhoneImgBlobsCallback extends HttpServlet{

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(PhoneImgBlobsCallback.class
			.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("Entering PhoneImgBlobsCallback::doPost");

		log.info("Parameters = " + LogUtils.listParams(req));

		String uniqueDeviceID;
		Image image;
		User user = null;
		BlobKey blobKey = null;
		UserPersistence userPersistence = new UserPersistence();
		ImagePersistence imagePersistence = new ImagePersistence(); 

		////////////////
		// Get blobstore key of image

		try {
			blobKey = getBlobkeyFromReq(req);
		} catch (Exception e) {
			log.warning(e.getMessage());
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

		log.info("Key String = " + blobKey.getKeyString());
		//
		////////////

		// check if unique device ID exists in datastore as user already..
		// if not - then create new user, with this unique device id

		uniqueDeviceID = req.getParameter(HistographConstants.DEVICE_ID_PARAM_NAME);

		try{
			user = userPersistence.getUserByDeviceID(uniqueDeviceID);
		}catch(Exception e){

			log.warning(e.getMessage());
			// can't continue without this.. so quit
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

		}

		// if this user has not posted before...
		if(null == user){

			user = new User();
			user.setUniqueDeviceID(uniqueDeviceID);
			try {
				// thats all the data we have on this user at the moment,
				// user can add details later if they choose.
				userPersistence.SaveUser(user);

			} catch (Exception e) {

				log.warning(e.getMessage());
				// can't continue without this.. so quit
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}

			log.info("Successfully created new user");
			//just check to see if user has GAE generated primary id, 
			log.info("User details: " + Long.toString(user.getId()));
		}else{

			log.info("User already exists. Device ID: " + uniqueDeviceID);

		}

		image = ImageParser.createImage(req, user.getId(), blobKey); 

		//3. Persist the new image:
		try{
			imagePersistence.SaveImage(image);
		}catch(Exception e){
			log.warning(e.getMessage());
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		
		//else: Success:
		resp.setStatus(HttpServletResponse.SC_OK);

		/////////////////////////////////////////////////////////////////
		//

		//		//TODO: move this to when we are serving images / creating the composite... this will be called, when the user has selected the imgs they want in the app
		//		// and the composite is being made. So after the stack screen, will need a moment to create composite, from resized user img and 
		//		// ids of the historical imgs...
		//		// means that the user's orig client side img will not be displayed on teh stack - instead the cropped version will only be seen in the composite
		//		//	- as the orig will not be square...
		//		
		//		// may need to edit image saved, then resave, delete the original, and use the new edited img blobstore key 
		//		//	in the Image datastore entry...
		//		
		//		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		//
		//		BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(key);
		//		
		//		byte[] bytes = blobstoreService.fetchData(key, 0, blobInfo.getSize());
		//		Image oldImage =ImagesServiceFactory.makeImage(bytes);
		//		
		//        //Image oldImage = ImagesServiceFactory.makeImageFromBlob(key);
		//        
		//        ////
		//        // crop based on longest dimension - height or width:
		//        imgHeight = oldImage.getHeight();
		//        imgWidth = oldImage.getWidth();
		//        
		//        if(imgWidth != 300 && imgHeight != 300){
		//        	//crop before serving
		//        }
		//        
		//        log.info("imgHeight = " + Integer.toString(imgHeight));
		//        log.info("imgWidth = " + Integer.toString(imgWidth));
		//        
		//        // ref: https://developers.google.com/appengine/docs/java/javadoc/com/google/appengine/api/images/ImagesServiceFactory#makeResize(int, int, double, double)
		//        // use this when we serve imgs that have been persisted by users, they might not be in 300 x 300 px dimensions
		//        // rather than use two writes for every new img - just have the one write and then transfrom on request
		//        // institutional imgs will have been batched processed into the correct dimensions before upload
		//        cropTransform = ImagesServiceFactory.makeResize(300, 300, 0.5, 0.5);
		////        if(imgHeight > imgWidth){
		////        	cropTransform = ImagesServiceFactory.makeResize(300, 300, 0.5, 0.5);
		////        }
		////        
		////        if(imgHeight < imgWidth){
		////        	
		////        	
		////        }
		//        
		//        //else - img is already square
		//        
		//       
		//        
		//       // Transform resize = ImagesServiceFactory.makeResize(300, 300);
		//
		//        Image newImage = imagesService.applyTransform(cropTransform, oldImage);
		//        
		//        int newH  = newImage.getHeight();
		//        int newW = newImage.getWidth();
		//        
		//        log.info("NEW imgHeight = " + Integer.toString(newH));
		//        log.info("NEW imgWidth = " + Integer.toString(newW));
		//
		//        byte[] newImageData = newImage.getImageData();



	}



	private BlobKey getBlobkeyFromReq(HttpServletRequest req) throws Exception {

		List<BlobKey> keys = null;
		BlobKey key = null;
		Map<String, List<BlobKey>> blobs = null;
		BlobstoreService blobstoreService = null;

		blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		blobs = blobstoreService.getUploads(req);
		if (null == blobs) {
			throw (new Exception(
					"No uploads in this request registered with blobstore"));
		}

		keys = blobs.get(HistographConstants.IMG_FROM_DEVICE_PARAM_NAME);

		if (null == keys) {
			throw (new Exception("Could not get key from blobstore"));
		}
		return(keys.get(0));

	}

}
