package com.andromeda.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andromeda.cms.dao.ArticleDao;
import com.andromeda.cms.dao.CategoryDao;
import com.andromeda.cms.model.Article;
import com.andromeda.cms.model.Category;
import com.andromeda.cms.model.StrapiCategory;
import com.andromeda.commons.util.JsonUtils;

@Service
public class CategoryService 
{
	@Autowired
	CategoryDao sitemapCategoryDao;
	
	@Autowired
	private CmsProxyService cmsProxyService;

	public void setSitemapCategoryDao(CategoryDao sitemapCategoryDao)
	{
		this.sitemapCategoryDao = sitemapCategoryDao;
	}
	
	public void setCmsProxyService(CmsProxyService cmsProxyService)
	{
		this.cmsProxyService = cmsProxyService;
	}

	
	public void addBatch(List<Category> sitemapCategories)
	{
		for (Category sitemapCategory : sitemapCategories) 
		{
			add(sitemapCategory);
		}
	}
	
	public void add(Category sitemapCategory)
	{
		sitemapCategoryDao.add(sitemapCategory);
	}
	
	public List<Category> getAll()
	{
		return sitemapCategoryDao.getAll();
	}
	
	public List<StrapiCategory> getCategoryList() throws Exception
	{
		List<StrapiCategory> categoryList = new ArrayList<>();
		long length = cmsProxyService.getListLength("allCategories");
		List<String> categoriesStrList = cmsProxyService.getList("allCategories", 0, (int)length);
		for (String categoryStr : categoriesStrList) {
			StrapiCategory category = JsonUtils.deserialize(categoryStr, StrapiCategory.class);
			categoryList.add(category);
		}
		return categoryList;
	}
}
