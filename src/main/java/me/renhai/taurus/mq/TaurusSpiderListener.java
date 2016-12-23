package me.renhai.taurus.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import me.renhai.taurus.tools.MovieDataImporter;

@Component
@RabbitListener(containerFactory = "rabbitListenerContainerFactory", bindings = @QueueBinding(
        value = @Queue(value = "${rabbitmq.taurus.queue}", durable = "true"),
        exchange = @Exchange(value = "${rabbitmq.taurus.exchange}", durable ="true", type = ExchangeTypes.FANOUT),
        key = "${rabbitmq.taurus.routingkey}"), admin = "rabbitAdmin")
public class TaurusSpiderListener {
	
	private static final Logger log = LoggerFactory.getLogger(TaurusSpiderListener.class);
	
	@Autowired
	private MovieDataImporter movieDataImporter;
	
	@RabbitHandler
    public void receive(JSONObject message) {
        try {
        	movieDataImporter.processAndMergeData(message);
        } catch (Exception e) {
            log.error("process and merge data error." + message.toString(), e);
        }
    }
	@RabbitHandler
	public void receive(String content) {
		try {
			movieDataImporter.processAndMergeData(content);
		} catch (Exception e) {
            log.error("process and merge data error." + content, e);
		}
	}
}
