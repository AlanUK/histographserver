package com.histograph.server.user;

import java.util.logging.Logger;

/** 
 * 
 * @author alandonohoe
 * Represents a user's location,
 * with Lat, Long & Direction
 */
public class Location {
	
	private static Logger log = Logger.getLogger(Location.class.getName());

	private double latitude;			/* Latitude of the position to search from */
	private double longitude;			/* Longitude of the position to search from */	
	private Integer direction;		    /* Direction phone is facing */
	
	
	/**
	 * Constructor breaks down path param, creating a Location object, with
	 * Long, Lat and Direction.
	 * @param path
	 */
	
	/*
	 * Example Path: 
	 */
	public Location(Double latitude, Double longitude, Integer direction){
		
		this.latitude = latitude;
		this.longitude = longitude;
		this.direction = direction;
//		String tempString;
//		
//		try {
//			StringTokenizer st = new StringTokenizer(path, "=");
//			
//			tempString = st.nextToken();
//			log.info("Long = " + tempString);
//			this.longitude = Double.parseDouble(st.nextToken());
//			
//			tempString = st.nextToken();
//			log.info("Lat = " + tempString);
//			this.latitude = Double.parseDouble(st.nextToken());
//			
//			tempString = st.nextToken();
//			log.info("Dir = " + tempString);
//			this.direction = Integer.parseInt(st.nextToken());
//
//
			/**
			 * Now parse any ?t= suffix at the end of the URL to extract a list of requested types
			 */
			
//			if (st.hasMoreTokens()) {
//				String nextTok = st.nextToken();
//				if (nextTok.equals("t")) {
//					StringTokenizer typeTok = new StringTokenizer(st.nextToken(), ",");
//					while (typeTok.hasMoreTokens()) {
//						nextTok = typeTok.nextToken();
//						types.add(new Integer(Integer.parseInt(nextTok)));
//					}
//				} else logger.warn("unexpected token " + nextTok);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new IllegalArgumentException("parsing " + path + " threw " + e);
//		}
		
	}


	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public Integer getDirection() {
		return direction;
	}


	public void setDirection(Integer direction) {
		this.direction = direction;
	}
	
	

}
