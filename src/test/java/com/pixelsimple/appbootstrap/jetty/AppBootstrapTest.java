/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appbootstrap.jetty;

import static org.junit.Assert.*;

import java.security.SecureRandom;

import junit.framework.Assert;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

/**
 * @author Akshay Sharma
 *
 * Jun 12, 2012
 */
public class AppBootstrapTest {

	@Test
	public void randomNumber() {
		
		String secret = RandomStringUtils.random(128, 0, 0, true, true, null, new SecureRandom());
		System.out.println("secret = " + secret);
		Assert.assertEquals(secret.length(), 128);
	}

}
