package com.histograph.server.persistence;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.histograph.server.entities.Image;
/**
 * 
 * @author alandonohoe
 * Handles the persistence, updating and retrieval of Image entities
 */
public class ImagePersistence {
	
	private static Logger log = Logger.getLogger(ImagePersistence.class.getName());
	
	
	//TODO: review how I handled saving entities in GoFetch - and then copy here....
	
	public void SaveImage(Image image){
		
		//See: http://stackoverflow.com/questions/4418317/jdo-exception-in-google-app-engine-transaction
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		EntityManager mgr = emf.createEntityManager();
		EntityTransaction tx = mgr.getTransaction(); 
		tx.begin();
		
		try {
				mgr.persist(image);
				mgr.getTransaction().commit();

		}catch(Exception e){
			tx.rollback(); 
			String msg = "Exception thrown. ImagePersistence::SaveImage()";
			log.severe(msg + e.getMessage());
			throw(e);

		}
		finally {
			// see: http://www.datanucleus.org/products/accessplatform_2_1/guides/jpa/tutorial.html#step5
			if (tx.isActive()){
				tx.rollback(); 
			}
			mgr.close();
		}
	}

	public Image getImage(Long imageId){
		
		Image image = null;
	
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		EntityManager mgr = emf.createEntityManager();
	
		try {
			image= (Image) mgr.createQuery("select i from Image i where i.id = :imageId")
			.setParameter("imageId", imageId)
			.getSingleResult();

		}catch(Exception e){
			
			String msg = "Exception thrown. ImagePersistence::getImage()";
			log.severe(msg + e.getMessage());
			throw(e);

		}
		finally {
			mgr.close();
		}
		
		return image;
	}
	
	//TODO: delete after we've tested this....  - dont want to return 1000s of imgs...
	@SuppressWarnings("unchecked")
	public List<Image> getAllImages(){
		
		List<Image> images = null;
		
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		EntityManager mgr = emf.createEntityManager();
	
		try {
			images =  mgr.createQuery("select i from Image i").getResultList();
			
		}catch(Exception e){
			
			String msg = "Exception thrown. ImagePersistence::getAllImages() ";
			log.severe(msg + e.getMessage());
			throw(e);

		}
		finally {
			mgr.close();
		}
		
		return images;
	}
	
	/*
	 * This is the key method in the class:
	 * Retrieves list of images based on the GPS and direction passed,
	 * returned in chronological order
	 */
	
	/*
	 * See:
	 * 
//	 Talk to this DataSource to deal with the database 

	public DatabasePoiFinder(DataSource d) {
		this.dataSource = d;
	}

	
//	 Pull out a list of Points of Interest matching the contents of the
//	 PoiQuery passed in, and return them in a list.
	 

	public List<Poi> find(PoiQuery pq) throws SQLException {

		ArrayList<Poi> ret = new ArrayList<Poi>();

		Connection conn = dataSource.getConnection();

		PoiType type = null;

//		
//		  Algorithm taken from
//		  
//		  http://stackoverflow.com/questions/574691/mysql-great-circle-distance
//		  -haversine-formula
//		  
//		 

		String sql = "SELECT *, ( 6371 * acos( cos( radians(?) ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians(?) ) + sin( radians(?) ) * sin( radians( latitude ) ) ) ) AS distance  FROM poi WHERE type IN (";

		for (int i: pq.getTypes()) {
			sql = sql + i + ",";
		}
		sql = sql.substring(0, sql.length()-1);

		sql = sql + ") HAVING distance < ? ORDER BY distance";
		
		logger.debug("find() sql="+sql);

		PreparedStatement locationSearch = conn.prepareStatement(sql);
		locationSearch.setDouble(1, pq.getLatitude());
		locationSearch.setDouble(2, pq.getLongitude());
		locationSearch.setDouble(3, pq.getLatitude());
		float radiusInKm = ((float) pq.getRadius() / 1000);
		locationSearch.setDouble(4, radiusInKm);
		ResultSet rs = locationSearch.executeQuery();

		while (rs.next()) // Return false when there is not more data in the
							// table
		{
			type = new PoiType("",rs.getInt("type")); //TODO ought to populate these with names too, by joining on types in the SQL query above
			Poi newPoi = new Poi(rs.getString("name"),
					rs.getDouble("latitude"), rs.getDouble("longitude"), type,
					rs.getInt("Id"));
			ret.add(newPoi);

			logger.debug("Found " + rs.getObject("name") + ", Longitude: "
					+ rs.getObject("longitude") + ", Latitude: "
					+ rs.getObject("latitude") +", distance="+ rs.getString("distance"));
		}
		rs.close();

		return ret;
	}

	 */
	
	public List<Image> getLocalImagesInChronOrder(){
		
		List<Image> images = null;
		
		return images;
	}
	
	
	
	public void updateImage(Image image){
		//TODO:
	}
}
