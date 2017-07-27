package com.capgemini.googlecloud;

import java.io.IOException;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.glassfish.jersey.internal.util.collection.Value;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.WebConfig;

/**
 * This class overrides the default Servlet Container to enable multi-tenancy
 *
 */
public class ImportBillsServlet extends ServletContainer {
	private static final Logger LOGGER = Logger.getLogger(ImportBillsServlet.class);

	private static final long serialVersionUID = 1L;

    public ImportBillsServlet() {
        super();
    }

    public ImportBillsServlet(ResourceConfig resourceConfig) {
        super(resourceConfig);
    }

	@Override
	protected void init(WebConfig webConfig) throws ServletException {
		super.init(webConfig);
	}

	/**
	 * This method gets called for every /webapi/ request and is suited for audit trail
	 */
	@Override
	public Value<Integer> service(URI baseUri, URI requestUri,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("In ImportBillsServlet");
		LOGGER.debug("service starts..." + request.getRemoteAddr() + "," + request.getRemoteHost());
		request.setAttribute("remoteAddr", request.getRemoteAddr());
		try {
			return super.service(baseUri, requestUri, request, response);

		} finally {
			LOGGER.debug("service ends..." + request.getRemoteAddr() + "," + request.getRemoteHost());
		}
	}

}
