/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.framezap.bootstrap;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Akshay Sharma
 * Feb 15, 2012
 */
public class FrameZapBootstrap  {
	private static final Logger LOG = LoggerFactory.getLogger(FrameZapBootstrap.class);
	// The system arg passed from script during the startup: -Dapp.home=\COMPUTED_PATH\..
	private static final String JETTY_CONFIG_FILE = "jetty.configFile";
	private static final String SERVER_PORT = "server.port";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LOG.debug("Main class - coming in");
		
//		Server server = new Server(8080);
////		server.setHandler(new WelcomeHandler());
//
//        HandlerList handlers = new HandlerList();
//        handlers.setHandlers(new Handler[] {new FileHandler(), new WelcomeHandler(), new DefaultHandler()});
//        server.setHandler(handlers);
//
//		LOG.debug("Main class - starting server {} ", server.dump());
//		
//		server.start();
//		server.join();
		
		String jettyConfigFile = System.getProperty(JETTY_CONFIG_FILE);
		
		if (jettyConfigFile == null || "".equalsIgnoreCase(jettyConfigFile.trim())) {
			System.out.println("Looks like the Jetty Config file is not supplied. Existing the system");
			System.exit(0);
		}

		File configFile = new File(jettyConfigFile); 
		if (!configFile.isFile() && !configFile.exists()) {
			System.out.println("Looks like the Jetty Config file is not valid - " + jettyConfigFile + "\nxisting the system");
			System.exit(0);
		}
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream(configFile));
			XmlConfiguration xmlConfiguration = new XmlConfiguration(in);
			
			Server server = new Server(Integer.getInteger(SERVER_PORT).intValue());
			xmlConfiguration.configure(server);
			server.start();
			server.join();
		} catch (Exception e) {
			System.out.println("Error occurred starting the server. Check the trace. Will exit.");
			e.printStackTrace();
			System.exit(0);
		}
		
		
	}

}
