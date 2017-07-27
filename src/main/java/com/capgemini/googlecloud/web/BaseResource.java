package com.capgemini.googlecloud.web;

import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * This class acts a base class for all REST service classes
 * @author mahesh.n
 *
 */
public class BaseResource {

	@Context
	private ResourceContext resourceContext;

	/**
	 * Creates no cache response builder
	 * @param status
	 * @return
	 */
    protected Response.ResponseBuilder getNoCacheResponseBuilder(Response.Status status) {
        CacheControl cc = new CacheControl();
        cc.setNoCache( true );
        cc.setMaxAge( -1 );
        cc.setMustRevalidate( true );
        return Response.status(status).cacheControl( cc );
    }
    protected <T> T getResouce(Class<T> clazz){
    	return resourceContext.getResource(clazz);
    }
}
