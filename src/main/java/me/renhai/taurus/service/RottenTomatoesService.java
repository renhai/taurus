package me.renhai.taurus.service;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import me.renhai.taurus.entity.Author;
import me.renhai.taurus.repository.AuthorRepository;
import me.renhai.taurus.spider.rottentomatoes.RTMovie;

@Service
public class RottenTomatoesService implements CommandLineRunner {
	private static final Logger LOG = LoggerFactory.getLogger(RottenTomatoesService.class);

	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Override
	public void run(String... args) throws Exception {
//		Iterator<File> iter = FileUtils.iterateFiles(new File("/Users/andy/Downloads/rottentomatoes.com 1121"), new String[] {"json"}, false);
//		while (iter.hasNext()) {
//			File file = iter.next();
//			String text = FileUtils.readFileToString(file, "utf-8");
//			try {
//				RTMovie mv = JSONObject.parseObject(text, RTMovie.class);
//				
//			} catch (Exception e) {
//				LOG.error(e.getMessage() + ": " + text);
//				continue;
//			}
//		}
	}
	
	
}
