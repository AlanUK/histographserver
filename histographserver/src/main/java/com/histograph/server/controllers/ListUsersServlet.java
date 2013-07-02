package com.histograph.server.controllers;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.histograph.server.entities.User;
import com.histograph.server.persistence.UserPersistence;

/*
 * Refs:
 * http://stackoverflow.com/questions/2370960/how-to-generate-html-response-in-a-servlet
 * 
 */

public class ListUsersServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ListUsersServlet.class
			.getName());


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		log.info("Entering ListUsersServlet ");
		
		resp.setContentType("text/plain");
		String output ="";

		List<User> users;// = new User();
		UserPersistence userPersistence = new UserPersistence();
		
		users = userPersistence.getAllUsers();
		
		if(null != users)
			log.info("No of users: " + users.size());
		
		
		if(null == users){
			output += "No Users </body> </html>";
			
			resp.getOutputStream().println(output);
		}
		for(User user : users){
			
			output += "<br/><br/>";
			output += " id :";
			output += user.getId();
			output += "\n";
			output += " Name :";
			output += user.getName();
			output += "\n";
			output += " Copyright info :";
			output += user.getCopyrightInfo();
			
			
		}
		
		req.setAttribute("message", output); // This will be available as ${message}
        req.getRequestDispatcher("/infopage.jsp").forward(req, resp);
  
	
	}
	
	

}
