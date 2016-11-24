package me.renhai.taurus.es;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.alibaba.fastjson.JSONObject;

//@Component
public class ESRunner implements CommandLineRunner {
	private static final Logger LOG = LoggerFactory.getLogger(ESRunner.class);

	@Autowired
	private MovieRepository movieRepository;
	
	@Override
	public void run(String... args) throws Exception {
		movieRepository.deleteAll();
		Iterator<File> iter = FileUtils.iterateFiles(new File("/Users/andy/Downloads/rottentomatoes.com 1121"), new String[] {"json"}, false);
		while (iter.hasNext()) {
			File file = iter.next();
			String text = FileUtils.readFileToString(file, "utf-8");
			JSONObject json;
			try {
				json = (JSONObject)JSONObject.parse(text);
			} catch (Exception e) {
				LOG.error(e.getMessage() + ": " + text);
				continue;
			}
			MovieDoc m = new MovieDoc();
			m.setId(json.getString("movieId"));
			m.setName(json.getString("title"));
			m.setYear(json.getInteger("year"));
			m.setSource(1);
			if (StringUtils.isBlank(m.getId()) || StringUtils.isBlank(m.getName())) {
				LOG.warn("illegal data: " + json);
				continue;
			}
			MovieDoc one = movieRepository.findOne(m.getId());
			if (one == null) {
				movieRepository.save(m);
			} else {
				LOG.warn("duplicate data: " + one.toString());
			}
		}
	}

}
