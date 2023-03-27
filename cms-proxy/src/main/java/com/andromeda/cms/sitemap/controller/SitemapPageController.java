package com.andromeda.cms.sitemap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.andromeda.cms.sitemap.service.SitemapPageService;

@RestController
public class SitemapPageController {
	
	@Autowired
	SitemapPageService sitemapPageService;
	
	@ResponseBody
	@RequestMapping(value = "/cms/sitemap/new-sitemap", method = { RequestMethod.GET })
	public void createOrUpdateNewsSitemap() throws Exception
	{
		sitemapPageService.createOrUpdateNewsSitemap();
	}
	

}
