package com.andromeda.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andromeda.cms.dao.CJCategoryDao;
import com.andromeda.cms.dao.CategoryDao;
import com.andromeda.cms.model.CJCategory;
import com.andromeda.cms.model.Category;
import com.andromeda.cms.model.StrapiCategory;
import com.andromeda.commons.util.JsonUtils;

@Service
public class CJCategoryService {
	@Autowired
	CJCategoryDao cjCategoryDao;
	
	@Autowired
	private CmsProxyService cmsProxyService;

	public void setCjCategoryDao(CJCategoryDao cjCategoryDao)
	{
		this.cjCategoryDao = cjCategoryDao;
	}
	
	public void setCmsProxyService(CmsProxyService cmsProxyService)
	{
		this.cmsProxyService = cmsProxyService;
	}

	
	public void addBatch(List<CJCategory> cjCategories)
	{
		for (CJCategory cjCategory : cjCategories) 
		{
			add(cjCategory);
		}
	}
	
	public void add(CJCategory cjCategory)
	{
		cjCategoryDao.add(cjCategory);
	}
	
	public List<CJCategory> getAll()
	{
		return cjCategoryDao.getAll();
	}
	
	public List<StrapiCategory> getCjCategoryList() throws Exception
	{
		List<StrapiCategory> categoryList = new ArrayList<>();
		long length = cmsProxyService.getListLength("cj_allCategories");
		List<String> categoriesStrList = cmsProxyService.getList("cj_allCategories", 0, (int)length);
		for (String categoryStr : categoriesStrList) {
			StrapiCategory category = JsonUtils.deserialize(categoryStr, StrapiCategory.class);
			categoryList.add(category);
		}
		return categoryList;
	}
}
