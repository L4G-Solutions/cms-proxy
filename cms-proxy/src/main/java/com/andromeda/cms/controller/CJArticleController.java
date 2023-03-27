package com.andromeda.cms.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.andromeda.cms.model.CJArticle;
import com.andromeda.cms.model.CJPhotoGallery;
import com.andromeda.cms.service.CJArticleService;



@RestController
public class CJArticleController {
	@Autowired
	CJArticleService cjArticleService;
	
	
	@ResponseBody
	@RequestMapping(value = "/cms/cj-articles/latest-articles", method = { RequestMethod.GET })
	public List<CJArticle> getLatestCjArticles()
	{
		try 
		{
			return cjArticleService.getLatestCjArticles();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/cms/cj-articles/latest-photos", method = { RequestMethod.GET })
	public List<CJPhotoGallery> getLatestCjPhotos()
	{
		try 
		{
			return cjArticleService.getLatestCjPhotos();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@ResponseBody
	@RequestMapping(value = "/cms/cj-articles/priority-articles", method = { RequestMethod.GET })
	public List<CJArticle> getPriorityCjArticles()
	{
		try 
		{
			return cjArticleService.getPriorityCjArticles();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/cms/cj-articles/latest-videos", method = { RequestMethod.GET })
	public List<CJArticle> getLatestCjVideos()
	{
		try 
		{
			return cjArticleService.getLatestCjVideos();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/cms/cj-articles/category/{categoryId}", method = { RequestMethod.GET })
	public List<CJArticle> getLandingPageCjArticles(@PathVariable String categoryId) throws Exception
	{
		return cjArticleService.getLatestByCategory(categoryId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/cms/cj-photos/category/{categoryId}", method = { RequestMethod.GET })
	public List<CJPhotoGallery> getCjLandingPagePhotos(@PathVariable String categoryId) throws Exception
	{
		return cjArticleService.getLandingPagePhotos(categoryId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/cms/cj-articles/category/{categoryId}/sub-category/{subCategoryId}", method = { RequestMethod.GET })
	public List<CJArticle> getSubCategoryRelatedCjArticles(@PathVariable String categoryId, @PathVariable String subCategoryId)
	{
		try {
			return cjArticleService.getLatestBySubCategory(categoryId, subCategoryId, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/cms/cj-photos/category/{categoryId}/sub-category/{subCategoryId}", method = { RequestMethod.GET })
	public List<CJPhotoGallery> getSubCategoryRelatedCjPhotos(@PathVariable String categoryId, @PathVariable String subCategoryId)
	{
		try {
			return cjArticleService.getLatestPhotosBySubCategory(categoryId, subCategoryId, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/*@ResponseBody
	@RequestMapping(value = "/cms/category/rk/key/{currentLetter}", method = { RequestMethod.GET })
	public List<Article> getOpenHeartArticles(@PathVariable String currentLetter)
	{
		try {
			return sitemapArticleService.getOpenHeartArticles(currentLetter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/
	
	@ResponseBody
	@RequestMapping(value = "/cms/cj-articles/category/all", method = { RequestMethod.POST })
	public HashMap<String, List<CJArticle>> getHomeArticles(@RequestBody String allCategoriesStr)
	{
		try {
			return cjArticleService.getCjHomeArticles(allCategoriesStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
