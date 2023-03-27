package com.andromeda.cms.proxy.app;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.andromeda.cms.dao.ArticleDao;
import com.andromeda.cms.dao.CJArticleDao;
import com.andromeda.cms.dao.CJCategoryDao;
import com.andromeda.cms.dao.CategoryDao;
import com.andromeda.cms.dao.ValueCmsProxyRepository;
import com.andromeda.cms.service.CmsProxyService;
import com.andromeda.cms.sitemap.dao.SitemapLocationDao;


/**
 * Application configurations.
 * 
 * @author Prakash K
 * @date 2020-09-14
 *
 */
@Configuration
public class ApplicationConfig
{
	@Value("${database.redis.hostname}")
	private String redisHostName;

	@Value("${database.redis.port}")
	private int redisPort;



	@Primary
	@Bean
	JedisConnectionFactory jedisConnectionFactory0()
	{
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(redisHostName);
		configuration.setPort(redisPort);
		configuration.setDatabase(0);

		return new JedisConnectionFactory(configuration);
	}

	@Bean
	JedisConnectionFactory jedisConnectionFactory1()
	{
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(redisHostName);
		configuration.setPort(redisPort);
		configuration.setDatabase(1);

		return new JedisConnectionFactory(configuration);
	}

	@Bean
	JedisConnectionFactory jedisConnectionFactory2()
	{
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(redisHostName);
		configuration.setPort(redisPort);
		configuration.setDatabase(2);

		return new JedisConnectionFactory(configuration);
	}

	@Bean
	JedisConnectionFactory jedisConnectionFactory3()
	{
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(redisHostName);
		configuration.setPort(redisPort);
		configuration.setDatabase(3);

		return new JedisConnectionFactory(configuration);
	}

	@Bean
	public StringRedisSerializer stringRedisSerializer()
	{
		return new StringRedisSerializer();
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate0()
	{
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory0());
		// For Strings
		redisTemplate.setKeySerializer(stringRedisSerializer());
		redisTemplate.setHashKeySerializer(stringRedisSerializer());
		redisTemplate.setValueSerializer(stringRedisSerializer());
		redisTemplate.setHashValueSerializer(stringRedisSerializer());

		redisTemplate.afterPropertiesSet();

		return redisTemplate;
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate()
	{
		return redisTemplate0();
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate1()
	{
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory1());
		// For Strings
		redisTemplate.setKeySerializer(stringRedisSerializer());
		redisTemplate.setHashKeySerializer(stringRedisSerializer());
		redisTemplate.setValueSerializer(stringRedisSerializer());
		redisTemplate.setHashValueSerializer(stringRedisSerializer());

		redisTemplate.afterPropertiesSet();

		return redisTemplate;
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate2()
	{
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory2());
		// For Strings
		redisTemplate.setKeySerializer(stringRedisSerializer());
		redisTemplate.setHashKeySerializer(stringRedisSerializer());
		redisTemplate.setValueSerializer(stringRedisSerializer());
		redisTemplate.setHashValueSerializer(stringRedisSerializer());

		redisTemplate.afterPropertiesSet();

		return redisTemplate;
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate3()
	{
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory3());
		// For Strings
		redisTemplate.setKeySerializer(stringRedisSerializer());
		redisTemplate.setHashKeySerializer(stringRedisSerializer());
		redisTemplate.setValueSerializer(stringRedisSerializer());
		redisTemplate.setHashValueSerializer(stringRedisSerializer());

		redisTemplate.afterPropertiesSet();

		return redisTemplate;
	}
	
	@Bean
	@Primary
	public DataSource postgresDataSource()
	{
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName("org.postgresql.Driver");
		String url = String.format("jdbc:postgresql://10.1.15.219:5432/abndemo");
		dataSource.setUrl(url);
		dataSource.setUsername("abn");
		dataSource.setPassword("LbgkGVs8Uhgfx3J3");
		Properties connectionProperties = new Properties();
		connectionProperties.setProperty("socketTimeout", "10");
		dataSource.setConnectionProperties(connectionProperties);

		return dataSource;
	}

	@Bean
	@Primary
	public SqlSessionFactory postgresSqlSessionFactory() throws Exception
	{
		String configLocation = "sqlMapConfig.xml";
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(postgresDataSource());
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(configLocation));

		return sqlSessionFactoryBean.getObject();
	}

	@Bean
	public SqlSessionTemplate postgresSqlSessionTemplate() throws Exception
	{
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(postgresSqlSessionFactory());
		return sqlSessionTemplate;
	}
	
	@Bean
	public ArticleDao sitemapArticleDao() throws Exception
	{
		ArticleDao sitemapArticleDao = new ArticleDao();
		sitemapArticleDao.setSqlSessionFactory(postgresSqlSessionFactory());
		sitemapArticleDao.setSqlSessionTemplate(postgresSqlSessionTemplate());

		return sitemapArticleDao;
	}
	
	@Bean
	public CJArticleDao cjArticleDao() throws Exception
	{
		CJArticleDao cjArticleDao = new CJArticleDao();
		cjArticleDao.setSqlSessionFactory(postgresSqlSessionFactory());
		cjArticleDao.setSqlSessionTemplate(postgresSqlSessionTemplate());

		return cjArticleDao;
	}
	
	@Bean
	public CategoryDao sitemapCategoryDao() throws Exception
	{
		CategoryDao sitemapCategoryDao = new CategoryDao();
		sitemapCategoryDao.setSqlSessionFactory(postgresSqlSessionFactory());
		sitemapCategoryDao.setSqlSessionTemplate(postgresSqlSessionTemplate());

		return sitemapCategoryDao;
	}
	
	@Bean
	public CJCategoryDao cjCategoryDao() throws Exception
	{
		CJCategoryDao cjCategoryDao = new CJCategoryDao();
		cjCategoryDao.setSqlSessionFactory(postgresSqlSessionFactory());
		cjCategoryDao.setSqlSessionTemplate(postgresSqlSessionTemplate());

		return cjCategoryDao;
	}
	
	@Bean
	public SitemapLocationDao sitemapLocationDao() throws Exception
	{
		SitemapLocationDao sitemapLocationDao = new SitemapLocationDao();
		sitemapLocationDao.setSqlSessionFactory(postgresSqlSessionFactory());
		sitemapLocationDao.setSqlSessionTemplate(postgresSqlSessionTemplate());

		return sitemapLocationDao;
	}

	
	@Primary
	@Bean
	public ValueCmsProxyRepository repository()
	{
		ValueCmsProxyRepository repository = new ValueCmsProxyRepository();
		repository.setRedisTemplate(redisTemplate0());
		repository.setListOps(redisTemplate0().opsForList());
		return repository;
	}

	@Bean
	public CmsProxyService cmsProxyService()
	{
		CmsProxyService service = new CmsProxyService();
		service.setRepository(repository());
		return service;
	}
}
