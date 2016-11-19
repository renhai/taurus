package me.renhai.taurus.spider;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSpider<T, C> {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractSpider.class);
	private static final int MAX_RETRY = 3;
	
	private ThreadLocal<WebDriver> pool = new ThreadLocal<WebDriver>() {
		protected WebDriver initialValue() {
			LOG.info("init webdriver");
			WebDriver driver = new PhantomJSDriver();
			driver.manage().window().maximize();
//			driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
//			driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
			return driver;
			
//			System.setProperty("webdriver.chrome.driver", "/Users/andy/Documents/workspace/chromedriver");
//			return new ChromeDriver();
			
//			DesiredCapabilities caps = new DesiredCapabilities();
//			caps.setJavascriptEnabled(true); // enabled by default
//			caps.setCapability(
//			    PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
//			    "/Users/andy/Documents/workspace/phantomjs-2.1.1-macosx/bin/phantomjs"
//			);
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
	
	protected boolean isElementPresent(By by) {
		List<WebElement> results = getDriver().findElements(by);
		return results.size() > 0;
	}
	
	public T search(C conditions) {
		int count = 0;
		while (true) {
			try {
				String url = getUrl(conditions);
				if (StringUtils.isBlank(url)) {
					return null;
				}
				getDriver().get(url);
				T res = process(conditions);
				return res;
			} catch (Exception e) {
				LOG.warn("process failed, retry..." + e.getMessage());
				if (++count == retry()) {
					throw e;
				}
			} finally {
				quitDriver();
			} 
		}
	}
	
	abstract protected T process(C conditions);
	
	abstract protected String getUrl(C conditions);
	
	protected int retry() {
		return MAX_RETRY;
	}
}
