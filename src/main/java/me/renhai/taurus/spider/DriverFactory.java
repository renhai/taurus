package me.renhai.taurus.spider;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DriverFactory {
	private static final Logger LOG = LoggerFactory.getLogger(DriverFactory.class);

	private ThreadLocal<WebDriver> pool = new ThreadLocal<WebDriver>() {
		protected WebDriver initialValue() {
			LOG.info("init webdriver");
			System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/Users/andy/Documents/workspace/phantomjs-2.1.1-macosx/bin/phantomjs");
			return new PhantomJSDriver();
	    }
	};
	
	public WebDriver getDriver() {
		return pool.get();
	}
	
	public void quitDriver() {
		WebDriver driver = getDriver();
		if (driver != null) {
			driver.quit();
			pool.remove();
			LOG.info("quit webdriver");
		}
	}
	
}
