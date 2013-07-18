package com.histograph.server.imageprocessing;

// ref: http://stackoverflow.com/questions/7551656/how-to-join-2-images-in-google-app-engine-in-java

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

/**
 * 
 * @author alandonohoe
 * Wraps the functionality of GAE's Image API into a class that suits Histogram requirements
 */
public class GAEImageWrapper {
	
	/**
	 * 
	 * @param image - Image to be cropped
	 * Method takes image, gets biggest dimension in pixels, of either the width or height 
	 * of the image. Then crops the image based on this distance.
	 * Equal amounts of either side will be cropped from the length or width, depending
	 * which of either is too long. The result is an image that has had the minimal amount
	 * of cropping to allow it to be converted into a square.
	 */
	public void squareCropImage(Image image){
		
		int imgHeight, imgWidth;
		
		imgHeight = image.getHeight();
		imgWidth =  image.getWidth();
		
		//img is already square
		if(imgHeight == imgWidth){
			return; // ????
		}
		
		
		if(imgHeight > imgWidth){
			//crop()
		}else{
			
		}
		
		
	}
	
	/**
	 * 
	 * @param image
	 * @return true if image length is longer than width, false otherwise 
	 */
//	private bool getLongestDimension(Image image){
//		
//		if(image.getHeight() 
//		
//	}
//	
	

}
