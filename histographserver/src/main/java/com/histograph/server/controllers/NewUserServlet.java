package com.histograph.server.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.histograph.server.entities.User;
import com.histograph.server.persistence.UserPersistence;
import com.histograph.server.utils.PasswordHash;


public class NewUserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(NewUserServlet.class
			.getName());

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		log.info("In NewUserServlet::doPost()");
		
		boolean institution, success= true;
		String password, passwordHash = "", userName, copyrightInfo;

		
		User user = new User();
		UserPersistence userPersistence = new UserPersistence();
		
		resp.setContentType("text/plain");
		
		userName = req.getParameter("userName");
		password = req.getParameter("password");
		copyrightInfo = req.getParameter("copyrightInfo");
		
		log.info("userName= " + userName);
		log.info("password= " + password);
		log.info("copyrightInfo= " + copyrightInfo);

		if(null != req.getParameter("institution"))
			institution = true;
		else 
			institution = false;
		
		try {
			passwordHash = PasswordHash.createHash(password);
		} catch (Exception e) {
			log.severe(e.getMessage());
			resp.getOutputStream().println("Error with username/password");
			success = false;
		}
		
		if(success){
		user.setCopyrightInfo(copyrightInfo);
		user.setInstitution(institution);
		user.setName(userName);
		user.setPassword(passwordHash);
		
		try {
			userPersistence.SaveUser(user);
		} catch (Exception e) {
			success = false;
		}
		
		if(success)
			resp.sendRedirect("newuser.html");
		else
			resp.getOutputStream().println("Problem saving user");
		}
		
	}

}
