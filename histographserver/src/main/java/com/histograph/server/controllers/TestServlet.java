package com.histograph.server.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 public void doGet(HttpServletRequest req, HttpServletResponse res)
			    throws IOException {
		 
		 res.setContentType("text/html");
		 
		 res.getWriter().println("TestServlet Says Hello World ");
		 
	 
	 }

}
