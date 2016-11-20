package me.renhai.taurus.spider.rottentomatoes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ResultItemsCollectorPipeline;

@Service
public class RottenTomatoesSpriderV2 {
	private static final Logger LOG = LoggerFactory.getLogger(RottenTomatoesSpriderV2.class);
	private Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL).addOptions(Option.SUPPRESS_EXCEPTIONS);

	@PostConstruct
	private void init() {
		
	}
	
	@Autowired
	private RottenTomatoesMovieProcessor rottenTomatoesMovieProcessor;
	
	public RTMovie search(String conditions) throws Exception {
		conditions = URLEncoder.encode(conditions, "UTF-8");
		String url = "https://www.rottentomatoes.com/search/?search=" + conditions;
    	ResultItemsCollectorPipeline pipline = new ResultItemsCollectorPipeline();
        Spider.create(rottenTomatoesMovieProcessor)
        		.addPipeline(pipline)
        		.addUrl(url)
        		.run();
        
        RTMovie mv = new RTMovie();
        List<ResultItems> resultItemsList = pipline.getCollected();
        if (CollectionUtils.isEmpty(resultItemsList)) {
        	return null;
        }
        ResultItems items = resultItemsList.get(0);
        String title = items.get("title");
        String movieSynopsis = items.get("movieSynopsis");
        String link = items.get("link");
        mv.setTitle(title);
        mv.setLink(link);
        mv.setSynopsis(movieSynopsis);
        return mv;
	}
	

}
