package com.histograph.server.imageprocessing;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.blobstore.BlobKey;
import com.histograph.server.entities.Image;
import com.histograph.server.utils.DateUtil;
import com.histograph.server.utils.HistographConstants;
/**
 * 
 * @author alandonohoe
 * Utility class for parsing different params and returning new Image objects based on param values.
 */
public class ImageParser {
	
	/**
	 * 
	 * @param req
	 * @param userID
	 * @return New Image object, based on params in HTTP req param
	 * Pulls all required / available details from 
	 * a HTTP req and constructs an Image object.
	 */

	public static Image createImage(HttpServletRequest req, Long userID, BlobKey blobKey){
		
		Image image = new Image();
		
		String direction, latitude, longitude;

		direction = req.getParameter(HistographConstants.DIRECTION_PARAM_NAME);
		latitude = req.getParameter(HistographConstants.LATITUDE_PARAM_NAME);
		longitude = req.getParameter(HistographConstants.LONGITUDE_PARAM_NAME );
				
		image.setDirection(Double.valueOf((direction)));
		image.setLatitude(Double.valueOf(latitude));
		image.setLongitude(Double.valueOf(longitude));
		image.setUserId(Long.valueOf(userID).longValue());
		image.setYear(DateUtil.getCurrentYear());
		image.setBlobKey(blobKey);
		
		//TODO: maybe later - user can add a short description.... here before img is sent to server:
		//image.setDescription(description);
		
		return image;
		
	}

}
