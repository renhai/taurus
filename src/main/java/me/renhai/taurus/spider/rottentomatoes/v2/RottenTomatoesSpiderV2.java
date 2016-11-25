package me.renhai.taurus.spider.rottentomatoes.v2;

import java.net.URLEncoder;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import me.renhai.taurus.spider.rottentomatoes.RTMovie;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ResultItemsCollectorPipeline;

@Service
public class RottenTomatoesSpiderV2 {
	private static final Logger LOG = LoggerFactory.getLogger(RottenTomatoesSpiderV2.class);

	@PostConstruct
	private void init() {
		
	}
	
	@Autowired
	private RottenTomatoesMovieProcessor rottenTomatoesMovieProcessor;
	
	public RTMovie search(String conditions) throws Exception {
		conditions = URLEncoder.encode(conditions, "UTF-8");
//		String url = "https://www.rottentomatoes.com/search/?search=" + conditions;
		String url = "https://www.rottentomatoes.com/api/private/v1.0/search/?catCount=2&q=" + conditions;
    	ResultItemsCollectorPipeline pipline = new ResultItemsCollectorPipeline();
        Spider.create(rottenTomatoesMovieProcessor)
        		.addPipeline(pipline)
        		.addUrl(url)
        		.run();
        
        RTMovie movie = new RTMovie();
        List<ResultItems> resultItemsList = pipline.getCollected();
        if (CollectionUtils.isEmpty(resultItemsList)) {
        	return null;
        }
        ResultItems items = resultItemsList.get(0);
        movie.setMovieId(Long.parseLong(items.get("movieId")));
		movie.setTitle(items.get("title"));
		movie.setDirectors(items.<net.minidev.json.JSONArray>get("director"));
		movie.setAuthors(items.<net.minidev.json.JSONArray>get("author"));
		movie.setGenres(items.get("genre"));
		movie.setStudio(items.get("studio"));
		movie.setYear(items.get("year"));
		movie.setMpaaRating(items.get("mpaaRating"));
		movie.setImage(items.get("image"));
		
        movie.setLink(items.get("link"));
        movie.setSynopsis(items.get("movieSynopsis"));
        movie.setInTheatersDate(items.get("inTheaters"));
        movie.setOnDvdDate(items.get("onDvd"));
        movie.setRuntime(items.get("runTime"));
        movie.setCast(items.get("cast"));
		movie.setRating(items.get("rating"));
		movie.setTimestamp(System.currentTimeMillis());
        return movie;
	}

}
