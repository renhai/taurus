package me.renhai.taurus.service;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import me.renhai.taurus.spider.task.NightstandPageProcessor;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;

@Component
public class ScheduledTask {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

	@Autowired
	private SmtpMailSender smtpMailSender;
	
	private String prePrice;
	
	//execute every 30 minutes
    @Scheduled(fixedRate = 30 * 60 * 1000, initialDelay = 30 * 1000)
	public void sendNotificationIfPriceChange() {
		String url = "https://www.wayfair.com/Hayward-Nightstand-56507-MCRR2276.html";
		Spider spider = Spider.create(new NightstandPageProcessor()).thread(1);
		ResultItems resultItems = spider.get(url);
		String currPrice = resultItems.get("price");
		if (StringUtils.isNotBlank(prePrice) && !StringUtils.equals(prePrice, currPrice)) {
			try {
				smtpMailSender.send("myrenhai@gmail.com", "Nightstand price change!", prePrice + "->" + currPrice + "<br/>" + url);
			} catch (MessagingException e) {
				log.error(e.getMessage(), e);
			}
		}
		prePrice = currPrice;
		spider.close();
		spider = null;
	}
	
}
