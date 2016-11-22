package me.renhai.taurus.controllers;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.renhai.taurus.spider.task.RottenTomatoesProcessor;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Spider.Status;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

@RestController
@RequestMapping("/task/rt")
public class RottenTomatoesTaskController {

	private Spider spider;
	
	@PostConstruct
	private void init() {
		spider = Spider.create(new RottenTomatoesProcessor())
		.addUrl("https://www.rottentomatoes.com/")
		.thread(5);
	}
	
	@GetMapping("/start")
	public ResponseEntity<String> start(
			@RequestParam (value = "path", required = true) String path,
			@RequestParam (value = "thread", defaultValue = "5") int threadNum) throws Exception {
		if (threadNum <= 0 || threadNum > 10) {
			throw new IllegalArgumentException("thread number illegal");
		}
		if (spider.getStatus() == Status.Running) {
			return new ResponseEntity<>("Task is runing, don't do this again.", HttpStatus.OK);
		} else if (spider.getStatus() == Status.Stopped) {
			spider.start();
			return new ResponseEntity<>("Task is being restared.", HttpStatus.OK);
		} else {
			spider.clearPipeline()
				  .addPipeline(new JsonFilePipeline(path))
				  .thread(threadNum)
				  .start();
			return new ResponseEntity<>("Create Task Successfully", HttpStatus.OK);
		}
		
	}
	
	@GetMapping("/stop")
	public ResponseEntity<String> stop() {
		Status status = spider.getStatus();
		if (status == Status.Running) {
			spider.stop();
		} 
		return new ResponseEntity<>("Task is Stoped.", HttpStatus.OK);
	}

}
