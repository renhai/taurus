package me.renhai.taurus.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.renhai.taurus.spider.task.RottenTomatoesProcessor;
import springfox.documentation.annotations.ApiIgnore;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Spider.Status;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

@RestController
@RequestMapping("/task/rt")
@ApiIgnore
public class RottenTomatoesTaskController {

	private Spider spider;
	
	@Autowired
	private ApplicationContext context;
	
	private void initSpider(String path, int threadNum) {
		spider = Spider.create(new RottenTomatoesProcessor())
					   .addUrl("https://www.rottentomatoes.com/")
					   .addPipeline(new JsonFilePipeline(path))
//					   .addPipeline(new TaurusPipline(context))
					   .thread(threadNum);
	}
	
	@GetMapping("/start")
	public ResponseEntity<String> start(
			@RequestParam (value = "path", defaultValue = "", required = false) String path,
			@RequestParam (value = "thread", defaultValue = "5") int threadNum) throws Exception {
		if (threadNum <= 0 || threadNum > 10) {
			throw new IllegalArgumentException("thread number illegal");
		}
		if (spider == null) {
			if (StringUtils.isBlank(path)) {
				throw new IllegalArgumentException("path is empty.");
			}
			initSpider(path, threadNum);
			spider.start();
			return new ResponseEntity<>("Task is started Running.", HttpStatus.OK);
		}
		if (spider.getStatus() == Status.Stopped) {
			spider.start();
			return new ResponseEntity<>("Task is being restared.", HttpStatus.OK);
		}
		return new ResponseEntity<>("unsupported operation, don't do this again!", HttpStatus.OK);
	}
	
	@GetMapping("/stop")
	public ResponseEntity<String> stop() {
		if (spider != null && spider.getStatus() == Status.Running) {
			spider.stop();
		}
		return new ResponseEntity<>("Task is being Stoped.", HttpStatus.OK);
	}
	
	@GetMapping("/close")
	public ResponseEntity<String> close() throws Exception {
		if (spider != null) {
			spider.stop();
			Thread.sleep(5000);
			spider.close();
			spider = null;
		} 
		return new ResponseEntity<>("Task is being Closed.", HttpStatus.OK);
	}

}
