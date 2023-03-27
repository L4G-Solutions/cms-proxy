package com.andromeda.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.andromeda.cms.model.StrapiCategory;
import com.andromeda.cms.service.CJCategoryService;
import com.andromeda.cms.service.CategoryService;

@RestController
public class CJCategoryController {
	@Autowired
	CJCategoryService cjCategoryService;
	
		
	@ResponseBody
	@RequestMapping(value = "/cms/cj-all-categories", method = { RequestMethod.GET })
	public List<StrapiCategory> getCjCategoryList() throws Exception
	{
		return cjCategoryService.getCjCategoryList();
	}
	
}
