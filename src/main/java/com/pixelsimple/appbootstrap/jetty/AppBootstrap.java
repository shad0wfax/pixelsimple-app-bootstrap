/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appbootstrap.jetty;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

import com.pixelsimple.appbootstrap.util.OSUtils;

/**
 *
 * @author Akshay Sharma
 * Feb 15, 2012
 */
public class AppBootstrap  {
	private static final Logger LOG = LoggerFactory.getLogger(AppBootstrap.class);
	private static final String JETTY_CONFIG_FILE = "jetty.configFile";
	private static final String SERVER_PORT = "server.port";
	private static final String APP_STARTUP_LOGBACK_CONFIG_FILE = "startup-logConfig.xml";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		initLog();
		
		LOG.debug("Going to bootstrap the app using ");
		
		String playSecret = generatePlaySecretKey();
		System.setProperty("PLAY_SECRET_KEY", playSecret);
		
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


	/*
	 * The log initialization happens with the assumption that a system property is set/passed during startup.
	 * Each app (ex: framezap/nova) can have its own log folders set. 
	 */
	private static void initLog() {
		// The system arg passed from script during the startup: -Dapp.home=\COMPUTED_PATH\..
		String appDir = System.getProperty("app.home");
		
		// Do nothing, so logging is not initialized and will log to the console (logback's default way)
		if (appDir == null || "".equalsIgnoreCase(appDir.trim()))
			return;
		
		// The log config for framezap will be in config folder.
		String logDir = OSUtils.appendFolderSeparator(appDir) + "config" + OSUtils.folderSeparator();
		
		// For framezap we will asume that the log configuration file is also located in the config directory.
		String logConfigFile = logDir + APP_STARTUP_LOGBACK_CONFIG_FILE;
		initLogFromConfigFile(logConfigFile);
	}
	
	private static void initLogFromConfigFile(String logConfigFile) {
		File file = new File(logConfigFile);
		
		if (!file.exists()) {
			return;
		}
		
		// Taken from - http://logback.qos.ch/manual/configuration.html
		// assume SLF4J is bound to logback in the current environment
	    LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
	    
	    try {
	      JoranConfigurator configurator = new JoranConfigurator();
	      configurator.setContext(context);
	      // Call context.reset() to clear any previous configuration, e.g. default 
	      // configuration. For multi-step configuration, omit calling context.reset().
	      context.reset(); 
	      configurator.doConfigure(logConfigFile);
	    } catch (JoranException je) {
	      // StatusPrinter will handle this
	    }
	    StatusPrinter.printInCaseOfErrorsOrWarnings(context);
	}
	
	private static String generatePlaySecretKey() {
//		SecureRandom random = new SecureRandom();
//		return new BigInteger(128, random).toString();
		return RandomStringUtils.randomAlphanumeric(128);
	}

}
