package com.appengine.bookingportal;

import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;



import javax.servlet.ServletException;
import javax.servlet.http.*;
/*
import com.google.appengine.repackaged.org.codehaus.jackson.map.ObjectMapper;
import com.google.appengine.repackaged.org.codehaus.jackson.map.ObjectWriter;*/

@com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnoreProperties({"users"})
@SuppressWarnings("serial")
public class RetrieveUserData extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
	
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		log(req.getParameter("user_id"));
		
		UserDetailsEndpoint userEndpoint = new UserDetailsEndpoint();
		
		UserDetails res = userEndpoint.getUserDetails(req.getParameter("user_id"));
		log(res.toString());
		
		/*UserDetails obj = new UserDetails();
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(obj);*/
		
		

		resp.setContentType("text/plain");
		resp.getWriter().println("User Data Retrieved Successfully "+res.toString());
		
		
		/*if(res != null) {
			req.setAttribute("userdetails", res);
			req.getRequestDispatcher("/querysuccess.jsp").forward(req, resp);
		} else {
			req.setAttribute("userdetails", "Error Found");
			req.getRequestDispatcher("/queryerror.jsp").forward(req, resp);
		}*/
        
        
		//resp.sendRedirect("/war/query.html");
		
	}
	
}
