package com.andromeda.cms.sitemap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andromeda.cms.model.Article;
import com.andromeda.cms.service.ArticleService;
import com.andromeda.cms.service.DataGeneratorService;

@Service
public class SitemapPageService 
{
	
	@Autowired
	DataGeneratorService dataGeneratorService;
	
	@Autowired
	ArticleService sitemapArticleService;
	
	/**
	 * function to create or update the news-sitemap.xml
	 * Generally called after creation of a new post
	 * @param sitemapArticle
	 * @throws Exception 
	 */
	public void createOrUpdateNewsSitemap() throws Exception
	{
		List<Article> lastestArticles =  sitemapArticleService.getLatestArticles();
		dataGeneratorService.generateNewSitemap(lastestArticles);
	}
	

}
