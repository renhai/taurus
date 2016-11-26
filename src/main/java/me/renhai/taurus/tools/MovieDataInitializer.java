package me.renhai.taurus.tools;

import java.io.File;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MovieDataInitializer implements CommandLineRunner {
	
	private static final Logger LOG = LoggerFactory.getLogger(MovieDataInitializer.class);

	
	@Value("${movie.data.dir}")
	private String path;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private MovieDataImporter movieDataImporter;

	@Override
	public void run(String... args) throws Exception {
		LOG.info("start importing data...");
		importData();
		LOG.info("finish importing data.");
	}
	
	@SuppressWarnings("unused")
	private void buildSearchIndex() {
		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			System.out.println("An error occurred trying to build the serach index: " + e.toString());
		}
		
		LOG.info("**** finish building index ****");
	}
	
	@Transactional
	private void importData() throws Exception {
		Iterator<File> iter = FileUtils.iterateFiles(new File(path), new String[] {"json"}, false);
		while (iter.hasNext()) {
			File file = iter.next();
			String text = FileUtils.readFileToString(file, "utf-8");
			movieDataImporter.processAndMergeData(text);
		}
	}

}
