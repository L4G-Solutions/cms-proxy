package com.andromeda.cms.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.andromeda.cms.model.Article;
import com.andromeda.cms.model.Cartoon;
import com.andromeda.cms.model.PhotoGallery;
import com.andromeda.cms.service.ArticleService;

@RestController
public class ArticleController 
{
	@Autowired
	ArticleService sitemapArticleService;
	
	/*@ResponseBody
	@RequestMapping(value = "/cms/articles/sub-category-related-articles/{subCategoryId}", method = { RequestMethod.GET })
	public List<SitemapArticle> getSubCategoryRelatedArticles(@PathVariable String subCategoryId)
	{
		return sitemapArticleService.getSubCategoryRelatedArticles(subCategoryId);
	}*/
	
	@ResponseBody
	@RequestMapping(value = "/cms/articles/latest-articles", method = { RequestMethod.GET })
	public List<Article> getLatestArticles()
	{
		try 
		{
			return sitemapArticleService.getLatestArticles();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/cms/articles/latest-photos", method = { RequestMethod.GET })
	public List<PhotoGallery> getLatestPhotos()
	{
		try 
		{
			return sitemapArticleService.getLatestPhotos();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/cms/articles/latest-cartoons", method = { RequestMethod.GET })
	public List<Cartoon> getLatestCartoons()
	{
		try 
		{
			return sitemapArticleService.getLatestCartoons();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/cms/cartoons/category/{categoryId}/page/{pageNo}", method = { RequestMethod.GET })
	public List<Cartoon> getCartoonsByPage(@PathVariable Integer categoryId, @PathVariable Integer pageNo)
	{
		try 
		{
			return sitemapArticleService.getCartoonsByPage(categoryId,pageNo);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/cms/articles/priority-articles", method = { RequestMethod.GET })
	public List<Article> getPriorityArticles()
	{
		try 
		{
			return sitemapArticleService.getPriorityArticles();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/cms/articles/latest-videos", method = { RequestMethod.GET })
	public List<Article> getLatestVideos()
	{
		try 
		{
			return sitemapArticleService.getLatestVideos();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/cms/articles/category/{categoryId}", method = { RequestMethod.GET })
	public List<Article> getLandingPageArticles(@PathVariable String categoryId) throws Exception
	{
		return sitemapArticleService.getLatestByCategory(categoryId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/cms/photos/category/{categoryId}", method = { RequestMethod.GET })
	public List<PhotoGallery> getLandingPagePhotos(@PathVariable String categoryId) throws Exception
	{
		return sitemapArticleService.getLandingPagePhotos(categoryId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/cms/articles/category/{categoryId}/sub-category/{subCategoryId}", method = { RequestMethod.GET })
	public List<Article> getSubCategoryRelatedArticles(@PathVariable String categoryId, @PathVariable String subCategoryId)
	{
		try {
			return sitemapArticleService.getLatestBySubCategory(categoryId, subCategoryId, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/cms/photos/category/{categoryId}/sub-category/{subCategoryId}", method = { RequestMethod.GET })
	public List<PhotoGallery> getSubCategoryRelatedPhotos(@PathVariable String categoryId, @PathVariable String subCategoryId)
	{
		try {
			return sitemapArticleService.getLatestPhotosBySubCategory(categoryId, subCategoryId, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	@ResponseBody
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
	}
	
	@ResponseBody
	@RequestMapping(value = "/cms/articles/category/all", method = { RequestMethod.POST })
	public HashMap<String, List<Article>> getHomeArticles(@RequestBody String allCategoriesStr)
	{
		try {
			return sitemapArticleService.getHomeArticles(allCategoriesStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/*@ResponseBody
	@RequestMapping(value = "/cms/articles/category/{categoryId}/sub-category/{subCategoryId}/page/{pageNo}", method = { RequestMethod.GET })
	public List<Article> getListingPageArticles(@PathVariable String categoryId, @PathVariable String subCategoryId, @PathVariable Integer pageNo)
	{
		try {
			return sitemapArticleService.getLatestBySubCategory(categoryId, subCategoryId, pageNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/
	
	
/*	@ResponseBody
	@RequestMapping(value = "/cms/articles/category/{categoryId}/subCategory/{subCategoryId}", method = { RequestMethod.GET })
	public List<SitemapArticle> getBySubCategoryId(@PathVariable String categoryId, @PathVariable String subCategoryId ) throws Exception
	{
		return sitemapArticleService.getBySubCategoryId(categoryId, subCategoryId);
	}*/
	
	
}
