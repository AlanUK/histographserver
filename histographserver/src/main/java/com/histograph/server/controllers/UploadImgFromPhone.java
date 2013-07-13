package com.histograph.server.controllers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.BlobstoreService;

/**
 * 
 * @author alandonohoe This handles accepting and then passing the image from
 *         user's phone into the GAE blobstore
 */
public class UploadImgFromPhone extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(UploadImgFromPhone.class
			.getName());

	private final String USER_AGENT = "Mozilla/5.0"; // ?? necessary??? why ??
	
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}



	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String blobStoreURL, paramName, paramValue;
		URL blobURL;
		String urlParameters = "";// "useCase=Case123&imgUpload=HistoGraph-Logo-with-Tag.jpg&Description=DESCRIBEsomthing&UserNo=1&Direction=123321&Long=123&Lat=432&Year=543";

		// User has posted img from phone, there will the img, phone unique ID,
		// location details
		BlobstoreService blobstoreService = BlobstoreServiceFactory
				.getBlobstoreService();
		
		// creates new blobstore url with  callback servlet -  uploadimages - to be called when asynx upload of img is complete
		blobStoreURL = blobstoreService.createUploadUrl("/uploadimages"); 

		log.info("blobStoreURL = " + blobStoreURL);

		// now get the img thats been uploaded.....

		// and all relevant parameters & assoc values:
		// See: http://www.java-programming.info/tutorial/pdf/csajsp2/03-Form-Data.pdf
		Enumeration<String> paramNames = req.getParameterNames();
		while (paramNames.hasMoreElements()) {

			// check that we're not at the first param, if not - then need to
			// add & to the string
			if (!urlParameters.isEmpty())
				urlParameters += "&";

			paramName = (String) paramNames.nextElement();
			urlParameters += paramName;
			urlParameters += "=";

			paramValue = req.getParameter(paramName);
			urlParameters += paramValue;
		}
		
		log.info("urlParameters = "+ urlParameters);

		// and post it to the blobstore:

		blobURL = new URL(blobStoreURL);
		HttpURLConnection con = (HttpURLConnection) blobURL.openConnection();

		// add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("ENCTYPE", "multipart/form-data");

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + blobStoreURL);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// set resp.responsecode = responseCode???

	}

}
