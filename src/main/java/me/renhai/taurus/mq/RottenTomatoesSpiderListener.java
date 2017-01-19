package me.renhai.taurus.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import me.renhai.taurus.tools.MovieDataImporter;

@Component
public class RottenTomatoesSpiderListener {
	private static final Logger log = LoggerFactory.getLogger(RottenTomatoesSpiderListener.class);
	
	@Autowired
	private MovieDataImporter movieDataImporter;

	@JmsListener(destination = "${aws.sqs.queue.spider}")
	public void receiveMessage(String message) {
		try {
			movieDataImporter.processAndMergeData(message);
		} catch (Exception e) {
			log.error("process and merge data error." + message, e);
		}
	}

}
