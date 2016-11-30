package me.renhai.taurus.tools;

import java.io.File;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class MovieDataInitializer implements CommandLineRunner {
	
	private static final Logger LOG = LoggerFactory.getLogger(MovieDataInitializer.class);

	
	@Value("${movie.data.dir}")
	private String path;
	
	@Value("${movie.data.needimport}")
	private boolean needimport;
	
	@Value("${movie.data.needbuildindex}")
	private boolean needbuildindex;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private MovieDataImporter movieDataImporter;
	
    private ExecutorService executor = Executors.newFixedThreadPool(10);


	@Override
	public void run(String... args) throws Exception {
		if (needimport) {
			LOG.info("start importing data...");
			importData();
			LOG.info("finish importing data.");
		}
		if (needbuildindex) {
			LOG.info("**** start building index... ****");
			buildSearchIndex();
			LOG.info("**** finish building index ****");
		}
	}
	
	private void buildSearchIndex() {
		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			LOG.error("An error occurred trying to build the serach index: " + e.toString());
		}
	}
	
	@Transactional
	private void importData() throws Exception {
		Iterator<File> iter = FileUtils.iterateFiles(new File(path), new String[] {"json"}, false);
		while (iter.hasNext()) {
			File file = iter.next();
			String text = FileUtils.readFileToString(file, "utf-8");
			try {
				executor.execute(new Runnable() {
					
					@Override
					public void run() {
						movieDataImporter.processAndMergeData(text);
						LOG.info("finish processing " + file.getName());
					}
					
				});
			} catch (DataIntegrityViolationException e) {
				LOG.error("processAndMergeData error, content: " + text);
				LOG.error(e.getMessage(), e);
			} catch (Exception e) {
				throw e;
			}
		}
		executor.shutdown();
		
		try {
			while (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
				LOG.info("Awaiting completion of importing data.");
			}
		} catch (InterruptedException e) {
			LOG.error(e.getMessage(), e);
		}
		
	}

}
