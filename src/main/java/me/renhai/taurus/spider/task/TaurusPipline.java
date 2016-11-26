package me.renhai.taurus.spider.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSON;

import me.renhai.taurus.tools.MovieDataImporter;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


public class TaurusPipline implements Pipeline {
	private static final Logger LOG = LoggerFactory.getLogger(MovieDataImporter.class);

	private MovieDataImporter movieDataImporter;
	
	public TaurusPipline(ApplicationContext ctx) {
		movieDataImporter = ctx.getBean(MovieDataImporter.class);
	}

    @Override
    public void process(ResultItems resultItems, Task task) {
        try {
        	movieDataImporter.processAndMergeData(JSON.toJSONString(resultItems.getAll()));
        } catch (Exception e) {
            LOG.warn("process and merge data error", e);
        }
    }
}