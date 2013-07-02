package com.histograph.server.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;


/**
 * 
 * @author alandonohoe
 *	Wraps all persistence of entities operations
 */
public class PersistenceManager {
	
	 private static final PersistenceManager singleton = new PersistenceManager();
	  
	  @PersistenceUnit(unitName="histograph")
	  protected EntityManagerFactory emf;
	  
	  public static PersistenceManager getInstance() {
	    
	    return singleton;
	  }
	  
	  private PersistenceManager() {
	  }
	 
	  public EntityManagerFactory getEntityManagerFactory() {
	    
	    if (emf == null)
	      createEntityManagerFactory();
	    return emf;
	  }
	  
	  public void closeEntityManagerFactory() {
	    
	    if (emf != null) {
	      emf.close();
	      emf = null;

	    }
	  }
	  
	  protected void createEntityManagerFactory() {
	    
	    this.emf = Persistence.createEntityManagerFactory("histograph");

	  }

}
