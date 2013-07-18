package com.histograph.server.persistence;


import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.histograph.server.entities.User;

/**
 * 
 * @author alandonohoe
 * Handles the persistence, updating and retrieval of User entities
 */
public class UserPersistence {
	
	private static Logger log = Logger.getLogger(UserPersistence.class.getName());
	

	//TODO: review how I handled saving entities in GoFetch - and then copy here....

	public void SaveUser(User user) throws Exception {

		//See: http://stackoverflow.com/questions/4418317/jdo-exception-in-google-app-engine-transaction
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		EntityManager mgr = emf.createEntityManager();
		EntityTransaction tx = mgr.getTransaction(); 
		tx.begin();

		try {
			mgr.persist(user);
			mgr.getTransaction().commit();

		}catch(Exception e){
			tx.rollback(); 
			String msg = "Exception thrown. UserPersistence::SaveUser()";
			log.severe(msg + e.getMessage());
			throw(e);

		}
		finally {
			//See: http://www.datanucleus.org/products/accessplatform_2_1/guides/jpa/tutorial.html#step5
			if (tx.isActive()){
				tx.rollback(); 
			}
			mgr.close();
		}
	}

	public User getUser(Long userId){
		//TODO:
		return null;
	}

	public void updateUser(User user){
		//TODO:
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers(){
		
		log.entering("UserPersistence", "getAllUsers");
		
		List<User> users = null;
		
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		EntityManager mgr = emf.createEntityManager();
		
		try{
			users = (List<User>) mgr.createQuery("SELECT u FROM User u").getResultList();
		}catch(Exception e){
			
			log.severe("Exception thrown in UserPersistence::getAllUsers. " + e.getMessage());
			
		}finally{
			
			mgr.close();
		}
		
		return users;
	}

	public User getUserByDeviceID(String uniqueDeviceID) throws Exception {
		
		log.entering("UserPersistence", "getUserByDeviceID(...)");
		
		User user = null;
		
		/*
		 * 			result = (Integer)mgr.createQuery("SELECT u.links_id FROM Link u WHERE u.target_id = :targetID AND u.source_id = :sourceID")
					.setParameter("sourceID",  sourceID)
					.setParameter("targetID",  targetID)
					.getSingleResult();

		 */
		
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		EntityManager mgr = emf.createEntityManager();
		
		try{
			user = (User) mgr.createQuery("SELECT u FROM User u WHERE u.uniqueDeviceID = :uniqueDeviceID")
					.setParameter("uniqueDeviceID", uniqueDeviceID)
					.getSingleResult();
			
		}catch(Exception e){
			
			throw(e); //handle upstream...
			//log.severe("Exception thrown in UserPersistence::getAllUsers. " + e.getMessage());
			
		}finally{
			
			mgr.close();
		}
		
		return user; 
		
	}
}
