package me.renhai.taurus;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class AbstractTaurusTest {
	
    @Autowired
    protected MockMvc mockMvc;
    
	@Value("${phantomjs.binary.path}")
	private String phantompath;
	
	private static boolean initialized;
    
    @Before
    public void before() {
    	if (!initialized) {
    		System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantompath);
    		initialized = true;
    	}
    }
    
    @After
    public void after() {
    	
    }
}
