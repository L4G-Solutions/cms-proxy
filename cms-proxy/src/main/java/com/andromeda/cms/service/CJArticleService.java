package com.andromeda.cms.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andromeda.cms.defs.StrapiConstants;
import com.andromeda.cms.model.CJArticle;
import com.andromeda.cms.model.CJPhotoGallery;
import com.andromeda.commons.util.JsonUtils;

@Service
public class CJArticleService 
{
	private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
	

	@Autowired
	private CmsProxyService cmsProxyService;
		

	/**
	 * function to retreive latest articles from redis
	 * @return
	 * @throws Exception 
	 */
	public List<CJArticle> getLatestCjArticles() throws Exception 
	{
		List<String> articlesStrList = cmsProxyService.getList("cj_latestArticles", 0, StrapiConstants.LIMIT);
		List<CJArticle> latestCjArticles = new ArrayList<>();
		if(!articlesStrList.isEmpty())
		{
			CJArticle cjArticle;
			for (String cjStr : articlesStrList) 
			{
				cjArticle = JsonUtils.deserialize(cjStr, CJArticle.class);
				latestCjArticles.add(cjArticle);
			}
		}
		return latestCjArticles;
	}

		
		public HashMap<String, List<CJArticle>> getCjHomeArticles(String allCategoriesStr) throws Exception {
			
			HashMap<String, List<CJArticle>> allCategoriesCjArticles = new HashMap<>();
			String[] allCatsStrArray = JsonUtils.deserialize(allCategoriesStr, String[].class);
			
			if(allCatsStrArray != null && allCatsStrArray.length > 0)
			{
				for (String catIdStr : allCatsStrArray) 
				{
					List<CJArticle> categoryArticles = new ArrayList<>();
					String key = "cj_cat_"+ catIdStr ;
					List<String> articlesStrList = cmsProxyService.getList(key, 0, StrapiConstants.LIMIT);
					if(!articlesStrList.isEmpty())
					{
						CJArticle cjArticle;
						for (String cjStr : articlesStrList) 
						{
							cjArticle = JsonUtils.deserialize(cjStr, CJArticle.class);
							categoryArticles.add(cjArticle);
						}
						allCategoriesCjArticles.put(key, categoryArticles);
					}
					
				}
			}
			
			List<CJArticle> priorityArticles = getPriorityCjArticles();
			allCategoriesCjArticles.put("priorityArticles", priorityArticles);
			List<CJArticle> latestArticles = getLatestCjArticles();
			allCategoriesCjArticles.put("latestArticles", latestArticles);
			List<CJArticle> latestVideos = getLatestCjVideos();
			allCategoriesCjArticles.put("latestVideos", latestVideos);
			
			return allCategoriesCjArticles;
		}
		
		public List<CJArticle> getLatestByCategory(String categoryId) throws Exception 
		{
			List<CJArticle> latestCjArticles = new ArrayList<>();
			if(categoryId != null)
			{
				List<String> articlesStrList = new ArrayList<>();
				articlesStrList = cmsProxyService.getList("cj_cat_"+categoryId, 0, StrapiConstants.LIMIT);
				
				
				if(!articlesStrList.isEmpty())
				{
					CJArticle cjArticle;
					for (String cjStr : articlesStrList) 
					{
						cjArticle = JsonUtils.deserialize(cjStr, CJArticle.class);
						latestCjArticles.add(cjArticle);
					}
				}
				
			}
			return latestCjArticles;
		}
		
		public List<CJPhotoGallery> getLandingPagePhotos(String categoryId) throws Exception 
		{
			List<CJPhotoGallery> latestPhotos = new ArrayList<>();
			if(categoryId != null)
			{
				List<String> articlesStrList = new ArrayList<>();
				articlesStrList = cmsProxyService.getList("cj_cat_"+categoryId, 0, StrapiConstants.LIMIT);
				
				
				if(!articlesStrList.isEmpty())
				{
					CJPhotoGallery cjPhotoGallery;
					for (String cjStr : articlesStrList) 
					{
						cjPhotoGallery = JsonUtils.deserialize(cjStr, CJPhotoGallery.class);
						latestPhotos.add(cjPhotoGallery);
					}
				}
				
			}
			return latestPhotos;
		}
		
		public List<CJArticle> getLatestBySubCategory(String categoryId, String subCategoryId, Integer pageNo) throws Exception 
		{
			List<CJArticle> latestCjArticles = new ArrayList<>();
			if(categoryId != null && subCategoryId != null)
			{
				List<String> articlesStrList = new ArrayList<>();
				String redisKey = "cj_cat_"+categoryId+"_"+ subCategoryId ;
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
					CJArticle cjArticle;
					for (String cjStr : articlesStrList) 
					{
						cjArticle = JsonUtils.deserialize(cjStr, CJArticle.class);
						latestCjArticles.add(cjArticle);
					}
				}
				
			}
			return latestCjArticles;
		}

		public List<CJPhotoGallery> getLatestCjPhotos() throws Exception {
			List<String> photosStrList = cmsProxyService.getList("cj_latestPhotos", 0, StrapiConstants.LIMIT);
			List<CJPhotoGallery> latestCjPhotos = new ArrayList<>();
			if(!photosStrList.isEmpty())
			{
				CJPhotoGallery photo;
				for (String cjStr : photosStrList) 
				{
					photo = JsonUtils.deserialize(cjStr, CJPhotoGallery.class);
					latestCjPhotos.add(photo);
				}
			}
			return latestCjPhotos;
		}
		
		public List<CJArticle> getLatestCjVideos() throws Exception {
			List<String> articlesStrList = cmsProxyService.getList("cj_latestVideos", 0, StrapiConstants.LIMIT);
			List<CJArticle> latestCjArticles = new ArrayList<>();
			if(!articlesStrList.isEmpty())
			{
				CJArticle cjArticle;
				for (String saStr : articlesStrList) 
				{
					cjArticle = JsonUtils.deserialize(saStr, CJArticle.class);
					latestCjArticles.add(cjArticle);
				}
			}
			return latestCjArticles;
		}



		
		public List<CJArticle> getPriorityCjArticles() throws Exception {
			List<String> articlesStrList = cmsProxyService.getList("cj_priorityArticles", 0, StrapiConstants.LIMIT);
			List<CJArticle> priorityArticles = new ArrayList<>();
			if(!articlesStrList.isEmpty())
			{
				CJArticle cjArticle;
				for (String cjStr : articlesStrList) 
				{
					cjArticle = JsonUtils.deserialize(cjStr, CJArticle.class);
					priorityArticles.add(cjArticle);
				}
			}
			return priorityArticles;
		}

		/*public List<CJArticle> getOpenHeartArticles(String currentLetter) throws Exception {
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
		}*/

		public List<CJPhotoGallery> getLatestPhotosBySubCategory(String categoryId, String subCategoryId, Integer pageNo) throws Exception {
			List<CJPhotoGallery> latestCjPhotos = new ArrayList<>();
			if(categoryId != null && subCategoryId != null)
			{
				List<String> articlesStrList = new ArrayList<>();
				String redisKey = "cj_cat_"+categoryId+"_"+ subCategoryId ;
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
					CJPhotoGallery photo;
					for (String cjStr : articlesStrList) 
					{
						photo = JsonUtils.deserialize(cjStr, CJPhotoGallery.class);
						latestCjPhotos.add(photo);
					}
				}
				
			}
			return latestCjPhotos;
		}
}
