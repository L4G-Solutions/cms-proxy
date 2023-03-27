package com.andromeda.cms.service;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andromeda.cms.dao.ArticleDao;
import com.andromeda.cms.dao.ValueCmsProxyRepository;
import com.andromeda.cms.defs.StrapiConstants;
import com.andromeda.cms.model.Article;
import com.andromeda.cms.model.Cartoon;
import com.andromeda.cms.model.PhotoGallery;
import com.andromeda.commons.util.Base64Utils;
import com.andromeda.commons.util.JsonUtils;



@Service
public class ArticleService 
{
	private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
	
	@Autowired
	private DataGeneratorService dataGeneratorService;

	@Autowired
	private CmsProxyService cmsProxyService;
	
	

	/*public List<SitemapArticle> getSubCategoryRelatedArticles(String subCategoryId) {
		
		return sitemapArticleDao.getSubCategoryRelatedArticles(subCategoryId);
		// TODO Auto-generated method stub
		
	}*/

	/*public SitemapArticle getById(String articleId) {
		return sitemapArticleDao.getById(articleId);
	}*/

	/*public List<SitemapArticle> getByCategory() {
		// TODO Auto-generated method stub
		return null;
	}*/

	/*public List<SitemapArticle> getByCategoryId(String categoryId) {
			return sitemapArticleDao.getByCategoryId(categoryId);
	}*/
	
	/*public List<SitemapArticle> getLatestBySubCategory(HashMap<String, String> params) 
	{
		Integer limit = StrapiConstants.LIMIT;
		String subCategoryId = null;
		Integer offset = null;
		if(params.containsKey("subCategoryId"))
			subCategoryId = params.get("subCategoryId");
		if(params.containsKey("limit"))
			limit = Integer.parseInt(params.get("limit"));
		if(params.containsKey("offset"))
			offset = Integer.parseInt(params.get("offset"));
		return sitemapArticleDao.getLatestBySubCategoryId(subCategoryId, limit, offset);
	}*/

	/*public List<SitemapArticle> getBySubCategoryId(String categoryId, String subCategoryId) throws Exception 
	{
		List<SitemapArticle> sitemapArticles = new ArrayList<>();
		String articlesStr = cmsProxyService.get("subCat_"+subCategoryId);
		List<String> sitemapArticlesStr = JsonUtils.deserialize(articlesStr, List.class);
		
		for (String sitemapArticleStr : sitemapArticlesStr) {
			SitemapArticle sa = JsonUtils.deserialize(sitemapArticleStr, SitemapArticle.class);
			sitemapArticles.add(sa);
		}
		
		return sitemapArticles;
	}*/

	/**
	 * function to retreive latest articles from redis
	 * @return
	 * @throws Exception 
	 */
	public List<Article> getLatestArticles() throws Exception 
	{
		List<String> articlesStrList = cmsProxyService.getList("latestArticles", 0, StrapiConstants.LIMIT);
		List<Article> latestArticles = new ArrayList<>();
		if(!articlesStrList.isEmpty())
		{
			Article sitemapArticle;
			for (String saStr : articlesStrList) 
			{
				sitemapArticle = JsonUtils.deserialize(saStr, Article.class);
				latestArticles.add(sitemapArticle);
			}
		}
		return latestArticles;
	}

	
	public HashMap<String, List<Article>> getHomeArticles(String allCategoriesStr) throws Exception {
		
		HashMap<String, List<Article>> allCategoriesArticles = new HashMap<>();
		String[] allCatsStrArray = JsonUtils.deserialize(allCategoriesStr, String[].class);
		
		if(allCatsStrArray != null && allCatsStrArray.length > 0)
		{
			for (String catIdStr : allCatsStrArray) 
			{
				List<Article> categoryArticles = new ArrayList<>();
				String key = "cat_"+ catIdStr ;
				List<String> articlesStrList = cmsProxyService.getList(key, 0, StrapiConstants.LIMIT);
				if(!articlesStrList.isEmpty())
				{
					Article sitemapArticle;
					for (String saStr : articlesStrList) 
					{
						sitemapArticle = JsonUtils.deserialize(saStr, Article.class);
						categoryArticles.add(sitemapArticle);
					}
					allCategoriesArticles.put(key, categoryArticles);
				}
				
			}
		}
		
		List<Article> priorityArticles = getPriorityArticles();
		allCategoriesArticles.put("priorityArticles", priorityArticles);
		List<Article> latestArticles = getLatestArticles();
		allCategoriesArticles.put("latestArticles", latestArticles);
		List<Article> latestVideos = getLatestVideos();
		allCategoriesArticles.put("latestVideos", latestVideos);
		
		return allCategoriesArticles;
	}
	
	public List<Article> getLatestByCategory(String categoryId) throws Exception 
	{
		List<Article> latestArticles = new ArrayList<>();
		if(categoryId != null)
		{
			List<String> articlesStrList = new ArrayList<>();
			articlesStrList = cmsProxyService.getList("cat_"+categoryId, 0, StrapiConstants.LIMIT);
			
			
			if(!articlesStrList.isEmpty())
			{
				Article sitemapArticle;
				for (String saStr : articlesStrList) 
				{
					sitemapArticle = JsonUtils.deserialize(saStr, Article.class);
					latestArticles.add(sitemapArticle);
				}
			}
			
		}
		return latestArticles;
	}
	
	public List<PhotoGallery> getLandingPagePhotos(String categoryId) throws Exception 
	{
		List<PhotoGallery> latestPhotos = new ArrayList<>();
		if(categoryId != null)
		{
			List<String> articlesStrList = new ArrayList<>();
			articlesStrList = cmsProxyService.getList("cat_"+categoryId, 0, StrapiConstants.LIMIT);
			
			
			if(!articlesStrList.isEmpty())
			{
				PhotoGallery sitemapArticle;
				for (String saStr : articlesStrList) 
				{
					sitemapArticle = JsonUtils.deserialize(saStr, PhotoGallery.class);
					latestPhotos.add(sitemapArticle);
				}
			}
			
		}
		return latestPhotos;
	}
	
	public List<Article> getLatestBySubCategory(String categoryId, String subCategoryId, Integer pageNo) throws Exception 
	{
		List<Article> latestArticles = new ArrayList<>();
		if(categoryId != null && subCategoryId != null)
		{
			List<String> articlesStrList = new ArrayList<>();
			String redisKey = "cat_"+categoryId+"_"+ subCategoryId ;
			if(pageNo == null)
				articlesStrList = cmsProxyService.getList(redisKey, 0, StrapiConstants.LIMIT);
			else
			{
				int from = pageNo * StrapiConstants.ABN_LISTING_PAGE_LIMIT - 1;
				int to = pageNo * StrapiConstants.ABN_LISTING_PAGE_LIMIT;
				articlesStrList = cmsProxyService.getList(redisKey, from, to);
			}
				
			
			if(!articlesStrList.isEmpty())
			{
				Article sitemapArticle;
				for (String saStr : articlesStrList) 
				{
					sitemapArticle = JsonUtils.deserialize(saStr, Article.class);
					latestArticles.add(sitemapArticle);
				}
			}
			
		}
		return latestArticles;
	}

	public List<PhotoGallery> getLatestPhotos() throws Exception {
		List<String> photosStrList = cmsProxyService.getList("latestPhotos", 0, StrapiConstants.LIMIT);
		List<PhotoGallery> latestPhotos = new ArrayList<>();
		if(!photosStrList.isEmpty())
		{
			PhotoGallery photo;
			for (String saStr : photosStrList) 
			{
				photo = JsonUtils.deserialize(saStr, PhotoGallery.class);
				latestPhotos.add(photo);
			}
		}
		return latestPhotos;
	}
	
	public List<Article> getLatestVideos() throws Exception {
		List<String> articlesStrList = cmsProxyService.getList("latestVideos", 0, StrapiConstants.LIMIT);
		List<Article> latestArticles = new ArrayList<>();
		if(!articlesStrList.isEmpty())
		{
			Article sitemapArticle;
			for (String saStr : articlesStrList) 
			{
				sitemapArticle = JsonUtils.deserialize(saStr, Article.class);
				latestArticles.add(sitemapArticle);
			}
		}
		return latestArticles;
	}

	public List<Cartoon> getLatestCartoons() throws Exception {
		List<String> cartoonsStrList = cmsProxyService.getList("latestCartoons", 0, StrapiConstants.CARTOON_DATA_LIMIT-1);
		List<Cartoon> latestCartoons = new ArrayList<>();
		if(!cartoonsStrList.isEmpty())
		{
			Cartoon cartoon;
			for (String saStr : cartoonsStrList) 
			{
				cartoon = JsonUtils.deserialize(saStr, Cartoon.class);
				latestCartoons.add(cartoon);
			}
		}
		return latestCartoons;
	}

	public List<Cartoon> getCartoonsByPage(Integer categoryId, Integer pageNo) throws Exception {
		List<Cartoon> latestcartoons = new ArrayList<>();
		if(categoryId != null)
		{
			List<String> cartoonsStrList = new ArrayList<>();
			int end = pageNo * StrapiConstants.CARTOON_DATA_LIMIT;
			int start = end - StrapiConstants.CARTOON_DATA_LIMIT;
			cartoonsStrList = cmsProxyService.getList("cat_"+categoryId, start, end-1);
			
			
			if(!cartoonsStrList.isEmpty())
			{
				Cartoon cartoon;
				for (String saStr : cartoonsStrList) 
				{
					cartoon = JsonUtils.deserialize(saStr, Cartoon.class);
					latestcartoons.add(cartoon);
				}
			}
			
		}
		return latestcartoons;
	}

	public List<Article> getPriorityArticles() throws Exception {
		List<String> articlesStrList = cmsProxyService.getList("priorityArticles", 0, StrapiConstants.LIMIT);
		List<Article> priorityArticles = new ArrayList<>();
		if(!articlesStrList.isEmpty())
		{
			Article sitemapArticle;
			for (String saStr : articlesStrList) 
			{
				sitemapArticle = JsonUtils.deserialize(saStr, Article.class);
				priorityArticles.add(sitemapArticle);
			}
		}
		return priorityArticles;
	}

	public List<Article> getOpenHeartArticles(String currentLetter) throws Exception {
		currentLetter = currentLetter.toLowerCase();
		List<String> articlesStrList = cmsProxyService.getList("RK_"+currentLetter, 0, StrapiConstants.RK_LIMIT);
		List<Article> priorityArticles = new ArrayList<>();
		if(!articlesStrList.isEmpty())
		{
			Article sitemapArticle;
			for (String saStr : articlesStrList) 
			{
				sitemapArticle = JsonUtils.deserialize(saStr, Article.class);
				priorityArticles.add(sitemapArticle);
			}
		}
		return priorityArticles;
	}

	public List<PhotoGallery> getLatestPhotosBySubCategory(String categoryId, String subCategoryId, Integer pageNo) throws Exception {
		List<PhotoGallery> latestPhotos = new ArrayList<>();
		if(categoryId != null && subCategoryId != null)
		{
			List<String> articlesStrList = new ArrayList<>();
			String redisKey = "cat_"+categoryId+"_"+ subCategoryId ;
			if(pageNo == null)
				articlesStrList = cmsProxyService.getList(redisKey, 0, StrapiConstants.LIMIT);
			else
			{
				int from = pageNo * StrapiConstants.ABN_LISTING_PAGE_LIMIT - 1;
				int to = pageNo * StrapiConstants.ABN_LISTING_PAGE_LIMIT;
				articlesStrList = cmsProxyService.getList(redisKey, from, to);
			}
				
			
			if(!articlesStrList.isEmpty())
			{
				PhotoGallery photo;
				for (String saStr : articlesStrList) 
				{
					photo = JsonUtils.deserialize(saStr, PhotoGallery.class);
					latestPhotos.add(photo);
				}
			}
			
		}
		return latestPhotos;
	}

	
}
