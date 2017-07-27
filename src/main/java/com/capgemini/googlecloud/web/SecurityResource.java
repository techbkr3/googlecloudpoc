package com.capgemini.googlecloud.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.googlecloud.model.User;

/**
 * This class exposes the REST service related to security
 *
 * @author
 *
 */
@Component
@Path("/security")
public class SecurityResource extends BaseResource {

	private static final Logger logger = Logger.getLogger(SecurityResource.class);

	/**
	 * This method will authenticate the user
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/authenticate")
	public Response doAuthentication(@Valid User user, @Context HttpServletRequest request) {
		logger.info("doAuthentication starts");
		Response response = null;
		try {
			if (user == null) {
				user = new User();
				user.setUserName(request.getParameter("username"));
				user.setPassword(request.getParameter("password"));
			}
			if (user.getPassword().equals("password")) {
				System.out.println("Authentication success for the user : " + user.getUserName());
				user.setAuthResult("success");
			} else {
				System.out.println("Authentication failure for the user : " + user.getUserName());
				user.setAuthResult("failure");
			}
			// This will create a session. This uses the timeout parameter in
			// web.xml

			HttpSession session = request.getSession(true);
			session.setAttribute("user", user);
			response = getNoCacheResponseBuilder(Response.Status.OK).entity(user).build();
		} catch (Exception e) {
			logger.error(e);
			response = getNoCacheResponseBuilder(Response.Status.INTERNAL_SERVER_ERROR).entity("").build();
		} finally {
			logger.info("doAuthentication ends");
		}
		return response;
	}

	/**
	 * This method will authenticate the user
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/login")
	public Response doLogin(@Valid User user, @Context HttpServletRequest request) {
		logger.info("login starts");
		Response response = null;
		try {
			if (user == null) {
				user = new User();
				user.setUserName(request.getParameter("username"));
				user.setPassword(request.getParameter("password"));
			}
			if (user == null) {
				logger.info("Authentication failed for the user : " + user.getUserName());
				response = getNoCacheResponseBuilder(Response.Status.UNAUTHORIZED).entity("").build();
			} else {
				// This will create a session. This uses the timeout parameter
				// in web.xml
				logger.info("Authentication success for the user : " + user.getUserName());
				HttpSession session = request.getSession(true);
				session.setAttribute("user", user);
				response = getNoCacheResponseBuilder(Response.Status.OK).entity(user).build();
			}
		} catch (Exception e) {
			logger.error(e);
			response = getNoCacheResponseBuilder(Response.Status.INTERNAL_SERVER_ERROR).entity("").build();
		} finally {
			logger.info("doAuthentication ends");
		}
		return response;
	}

}
