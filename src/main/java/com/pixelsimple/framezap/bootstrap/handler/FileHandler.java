/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.framezap.bootstrap.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Akshay Sharma
 * Feb 15, 2012
 */
public class FileHandler extends AbstractHandler {
	private static final Logger LOG = LoggerFactory.getLogger(FileHandler.class);
			
	/* (non-Javadoc)
	 * @see org.eclipse.jetty.server.Handler#handle(java.lang.String, org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {
		
		LOG.debug("Handling file request. Target - {}", target);
		
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[]{ "index.html" });
 
        resource_handler.setResourceBase("C:/Data/");
        
        resource_handler.handle(target, baseRequest, request, response);
        
        LOG.debug("Resource handler data - {}", resource_handler.getBaseResource());
	}

	
}
