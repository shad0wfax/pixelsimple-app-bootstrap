/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appbootstrap.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Akshay Sharma
 * Jan 1, 2012
 */
public class OSUtils {
	private static final Logger LOG = LoggerFactory.getLogger(OSUtils.class);
	private static final String WINDOWS_OS_LIKE = "windows"; 
	private static final String MAC_OS_LIKE = "mac"; 

	// Can probably evolve - Consider making it dynamic if specific versions are needed.
	public static enum OS {MAC, WINDOWS, LINUX};
	
	public static final OS CURRENT_OS;
	public static final String NEW_LINE_CHARACTER = System.getProperty("line.separator");
	public static final String USER_SYSTEM_HOME_DIR = System.getProperty("user.home");
	public static final String MAC_LINUX_FOLDER_SEPARATOR = "/";
	public static final String WINDOWS_FOLDER_SEPARATOR = "\\";
	
	static {
		String os = System.getProperty("os.name").toLowerCase();
		
		if (os.contains(WINDOWS_OS_LIKE)) {
			CURRENT_OS = OS.WINDOWS;
		} else if (os.contains(MAC_OS_LIKE)) {
			CURRENT_OS = OS.MAC;
		} else {
			CURRENT_OS = OS.LINUX;
		}
	}
	
	public static String folderSeparator() {
		if (CURRENT_OS == OS.WINDOWS) {
			return WINDOWS_FOLDER_SEPARATOR;
		} else{
			return MAC_LINUX_FOLDER_SEPARATOR;
		}
	}
	
	public static boolean isWindows() {
		return CURRENT_OS == OS.WINDOWS;
	}
	
	public static boolean isMac() {
		return CURRENT_OS == OS.MAC;
	}
	
	public static boolean isLinux() {
		return CURRENT_OS == OS.LINUX;
	}
	
	public static String appendFolderSeparator(String folderPath) {
		// Sanitize this input - add the trailing / or \ if needed. Will simplify the paths to other directories.
		if (folderPath.endsWith(OSUtils.folderSeparator()))
				return folderPath;
		
		// Windows will accept '/' but have it changed to \ to ensure any scripts receiving folders don't break accidentally
		if (OSUtils.CURRENT_OS == OSUtils.OS.WINDOWS && folderPath.endsWith("/")) {
			// Trim off the last character.
			folderPath = folderPath.substring(0, folderPath.length() - 1);
		}
				
		
		return folderPath + OSUtils.folderSeparator();
		
	}
	
	/**
	 * Just to print out in case needed.
	 * @param args
	 */
	public static void main(String[] args) {
		String CURRENT_OS = System.getProperty("os.name");

		LOG.debug("The current OS used is::" + CURRENT_OS);
		LOG.debug("\n Other java properties include::");
		
		for (Map.Entry<Object, Object> e : System.getProperties().entrySet()) {
			LOG.debug("{}", e);
        }
	}

}
