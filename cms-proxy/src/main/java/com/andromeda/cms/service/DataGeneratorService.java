package com.andromeda.cms.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.xml.sax.SAXException;

import com.andromeda.cms.model.Article;
import com.andromeda.cms.model.Category;
import com.andromeda.cms.model.Feed;
import com.andromeda.cms.sitemap.model.SitemapLocation;
import com.andromeda.commons.util.FileNDirUtils;
import com.andromeda.commons.util.PropertiesUtils;
import com.lowagie.text.DocumentException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

@Service
public class DataGeneratorService
{
	private static final Logger logger = LoggerFactory.getLogger(DataGeneratorService.class);

	@Value("${data-csv-file}")
	private static String INPUT_FILE_NAME;

	@Value("${report-csv-file}")
	private static String OUTPUT_FILE_NAME;
	
	@Value("${domain-name}")
	private static String DOMAIN_NAME;
	
	private String emailContent;
	private Configuration cfg;
	private DataWriter dataWriter;

	@PostConstruct
	public void init()
	{
		configureFreemarker();
		dataWriter = new DataWriter();
	}
	
	public String generateHtmlforFeed(Feed feed) throws Exception
	{
		String htmlFileName = dataWriter.writeFeedInHtml(cfg, feed);
		return htmlFileName;
	}

	public void configureFreemarker()
	{
		// 1. Configure FreeMarker
		//
		// You should do this ONLY ONCE, when your application starts,
		// then reuse the same Configuration object elsewhere.

		cfg = new Configuration();

		// Where do we load the templates from:
		cfg.setClassForTemplateLoading(DataGeneratorService.class, "/templates");

		// Some other recommended settings:
		cfg.setIncompatibleImprovements(new Version(2, 3, 20));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setLocale(Locale.US);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	}

	public void generateSitemapIndexXml(List<SitemapLocation> sitemapLocations) 
	{
		if (sitemapLocations.size() != 0)
		{
			try
			{
				Template template = cfg.getTemplate("sitemapIndex.ftl");
				String xmlFileName = "output" + File.separator +  "sitemap_index.xml";
				createFile(xmlFileName);
				Writer fileWriter = new FileWriter(xmlFileName);
				try
				{
					Map<String, Object> root = new HashMap<String, Object>();
				    root.put( "sitemapLocations", sitemapLocations );
					template.process(root, fileWriter);
				}
				finally
				{
					fileWriter.close();
				}
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
	public void generateArticle(Article sitemapArticle, List<Category> categoryList, List<Article> relatedArticles) 
	{
		if (sitemapArticle != null)
		{
			try
			{
				Template template = cfg.getTemplate("article.ftl");
				if(template != null)
					{
					String xmlFileName = "output" + File.separator +  "articles" + File.separator + sitemapArticle.getAbnStoryId() + ".html";
					createFile(xmlFileName);
					Writer fileWriter = new FileWriter(xmlFileName);
					try
					{
						Map<String, Object> root = new HashMap<String, Object>();
					    root.put( "article", sitemapArticle );
					    root.put("primaryCategoryList", categoryList);
					    root.put("relatedArticles", relatedArticles);
						template.process(root, fileWriter);
					}
					finally
					{
						fileWriter.close();
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private static void createFile(String htmlFile)
	{
		File htmlFileName = new File(htmlFile);
		if (!htmlFileName.exists())
			try
			{
				htmlFileName.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
	}

	public void generateNewSitemap(List<Article> lastestArticles) {
		if(! lastestArticles.isEmpty())
		{
			try
			{
				Template template = cfg.getTemplate("news-sitemap.ftl");
				if(template != null)
					{
					String xmlFileName = "output" + File.separator +  "sitemaps" + File.separator +  "news-sitemap.xml";
					createFile(xmlFileName);
					Writer fileWriter = new FileWriter(xmlFileName);
					try
					{
						Map<String, Object> root = new HashMap<String, Object>();
					    root.put( "domainName", DOMAIN_NAME );
					    root.put("sitemapArticles", lastestArticles);
						template.process(root, fileWriter);
					}
					finally
					{
						fileWriter.close();
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
}
