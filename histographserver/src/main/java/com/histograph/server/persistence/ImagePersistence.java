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
	
	public List<Image> getLocalImagesInChronOrder(){
		
		List<Image> images = null;
		
		return images;
	}
	
	
	
	public void updateImage(Image image){
		//TODO:
	}
}
