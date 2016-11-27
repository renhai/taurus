package me.renhai.taurus;

import javax.annotation.PostConstruct;

import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemPropertiesSetting {
	
	@Value("${phantomjs.binary.path}")
	private String phantompath;
	
	@PostConstruct
	public void setting() {
		System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantompath);
	}
}
