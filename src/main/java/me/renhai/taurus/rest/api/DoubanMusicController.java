package me.renhai.taurus.rest.api;

import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.renhai.taurus.crawler.douban.DoubanAlbum;
import me.renhai.taurus.crawler.douban.DoubanMusicCrawler;
import me.renhai.taurus.interceptors.RateLimit;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/db")
@ApiIgnore
public class DoubanMusicController {
	private static final Logger LOG = LoggerFactory.getLogger(DoubanMusicController.class);

	@Autowired
	private DoubanMusicCrawler doubanMusicCrawler;

	@RateLimit(5)
    @GetMapping({"/1.0/albums"})
    public ResponseEntity<DoubanAlbum> albums(
    		@RequestParam(value = "q", required = true) String query) throws Exception {
    	LOG.info("query: " + query);
    	query = StringUtils.trimToEmpty(query);
		query = URLEncoder.encode(query, "UTF-8");
    	DoubanAlbum body = doubanMusicCrawler.search(query);
    	return new ResponseEntity<DoubanAlbum>(body, HttpStatus.OK);
    }

}
