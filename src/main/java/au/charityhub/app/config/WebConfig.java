package au.charityhub.app.config;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import au.charityhub.app.service.CharityManager;
import au.charityhub.app.service.PostManager;
import au.charityhub.app.service.UserManager;

@Configuration
@EnableWebMvc
@ImportResource({"/WEB-INF/spring/root-context.xml", "/WEB-INF/spring/appServlet/servlet-context.xml", "/WEB-INF/spring/appServlet/persistence-context.xml"})
@ComponentScan(basePackages = { "au.charityhub.app.web", "au.charityhub.app.domain", "au.charityhub.app.service" }, excludeFilters = { @Filter( value = Configuration.class) })
public class WebConfig extends WebMvcConfigurerAdapter {

	@Resource(name="charityManager")
	private CharityManager charityManager;
	
	@Resource(name="userManager")
	private UserManager userManager;
	
	@Resource(name="postManager")
	private PostManager postManager;
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
//	}
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//
//		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
//		localeChangeInterceptor.setParamName("lang");
//		registry.addInterceptor(localeChangeInterceptor);
//	}
//
//	@Bean
//	public LocaleResolver localeResolver() {
//
//		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
//		cookieLocaleResolver.setDefaultLocale(StringUtils.parseLocaleString("en"));
//		return cookieLocaleResolver;
//	}
//
//	@Bean
//	public ViewResolver viewResolver() {
//
//		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//		viewResolver.setViewClass(JstlView.class);
//		viewResolver.setPrefix("/WEB-INF/views");
//		viewResolver.setSuffix(".jsp");
//		return viewResolver;
//	}
	
//	@Bean
//	public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
//		PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
//		propertyPlaceholderConfigurer.setLocation(new ClassPathResource("classpath:/database2.properties"));
//		return propertyPlaceholderConfigurer;
//	}

//	@Bean
//	public MessageSource messageSource() {
//
//		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//		messageSource.setBasenames("classpath:messages/messages", "classpath:messages/validation");
//		// if true, the key of the message will be displayed if the key is not
//		// found, instead of throwing a NoSuchMessageException
//		messageSource.setUseCodeAsDefaultMessage(true);
//		messageSource.setDefaultEncoding("UTF-8");
//		// # -1 : never reload, 0 always reload
//		messageSource.setCacheSeconds(0);
//		return messageSource;
//	}
//	
	@Bean
	public CharityManager charityManager() {
		return this.charityManager;
	}
	
	@Bean
	public PostManager postManager() {
		return this.postManager;
	}
	
	@Bean
	public UserManager userManager() {
		return this.userManager;
	}
	
	@Bean
	public SessionFactory sessionFactory() {
		return this.sessionFactory;
	}
}