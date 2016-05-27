package com.appengine.bookingportal;

import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class BookingPortalServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		
		
		/*UserDetails user = new UserDetails();
		
		user.setName("Alag");
		user.setEmail("alagappan006@gmail.com");
		user.setMobileNo(998696021);
		user.setUserId("alag006");
		
		List<UserDetails> userList = new ArrayList<UserDetails>();
		
		userList.add(user);
		
		System.out.println("User Object ----->"+user.toString());
		*/
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		UserDetails user = new UserDetails();
		
		user.setUserId(req.getParameter("user_id"));
		user.setName(req.getParameter("user_name"));
		user.setEmail(req.getParameter("email_id"));
		user.setMobileNo(Integer.parseInt(req.getParameter("mobile_no")));
		
		AddressTemp pAddress = new AddressTemp();
		pAddress.setLine1(req.getParameter("pa_line1"));
		pAddress.setLine2(req.getParameter("pa_line1"));
		pAddress.setState(req.getParameter("pa_state"));
		pAddress.setLine1(req.getParameter("pa_pincode"));
		
		AddressTemp oAddress = new AddressTemp();
		oAddress.setLine1(req.getParameter("oa_line1"));
		oAddress.setLine2(req.getParameter("oa_line1"));
		oAddress.setState(req.getParameter("oa_state"));
		oAddress.setLine1(req.getParameter("oa_pincode"));
		
		List<AddressTemp> addressList = new ArrayList<AddressTemp>();
		
		addressList.add(pAddress);
		addressList.add(oAddress);
		
				
		log(user.toString());
		
		UserDetailsEndpoint userEndpoint = new UserDetailsEndpoint();
		
		UserDetails res = userEndpoint.insertUserDetails(user);
		log(res.toString());
		
		/*resp.setContentType("text/plain");
		resp.getWriter().println("Successfully Registered");
		*/
		
		resp.sendRedirect("/query.html");
		
	}
	
}
