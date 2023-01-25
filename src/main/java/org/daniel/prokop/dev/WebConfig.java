package org.daniel.prokop.dev;


import org.daniel.prokop.dev.webexceptions.MissingExceptionResolver;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.theme.CookieThemeResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.ServletContext;
import java.util.Locale;

@Configuration
@Import({WebInitializer.class,ServiceConfig.class})
@EnableWebMvc
@ComponentScan(basePackages = {"org.daniel.prokop.dev.controllers","org.daniel.prokop.dev.DAO","org.daniel.prokop.dev.dto"
      ,"org.daniel.prokop.dev.REPO","org.daniel.prokop.dev.SERVICE","org.daniel.prokop.dev.webexceptions"})
public class WebConfig implements WebMvcConfigurer, ApplicationContextAware, ServletContextAware {

    private ApplicationContext applicationContext;
    private ServletContext servletContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }


    @Bean
    SimpleMappingExceptionResolver simpleMappingExceptionResolver(){
        var resolver = new MissingExceptionResolver();
        resolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return resolver;
    }

    // Commented this so we can implement exception handling
   /* @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }*/

    @Bean
    ViewResolver viewResolver(){
        var resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/");
        resolver.setSuffix(".jsp" );
        resolver.setRequestContextAttribute("requestContext");

        return resolver;
    }

   /* @Bean
    public TestTestTest2 getTestClass(){
        return new TestClass();
    }*/

    @Bean
    public Validator validator() {
        final var validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }

    @Override
    public Validator getValidator() {
        return validator();
    }

    @Bean
    MessageSource messageSource() {
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("/WEB-INF/messages/global");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(1);
        return messageSource;
    }

    //Declare our static resources. I added cache to the java config but it?s not required.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("/images/").setCachePeriod(31556926);
        registry.addResourceHandler("/styles/**").addResourceLocations("/styles/").setCachePeriod(31556926);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/home");
        registry.addViewController("/auth").setViewName("login");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(themeChangeInterceptor());
        registry.addInterceptor(webChangeInterceptor());
    }


    @Bean
    LocaleChangeInterceptor localeChangeInterceptor() {
        return new LocaleChangeInterceptor();
    }

    @Bean
    ThemeChangeInterceptor themeChangeInterceptor() {
        return new ThemeChangeInterceptor();
    }

    @Bean
    WebContentInterceptor webChangeInterceptor() {
        var webContentInterceptor = new WebContentInterceptor();
        webContentInterceptor.setCacheSeconds(0);
        webContentInterceptor.setSupportedMethods("GET", "POST", "PUT", "DELETE");
        return webContentInterceptor;
    }

    @Bean
    CookieLocaleResolver localeResolver() {
        var cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        cookieLocaleResolver.setCookieMaxAge(3600);
        cookieLocaleResolver.setCookieName("locale");
        return cookieLocaleResolver;
    }

    @Bean
    CookieThemeResolver themeResolver() {
        var cookieThemeResolver = new CookieThemeResolver();
        cookieThemeResolver.setDefaultThemeName("green");
        cookieThemeResolver.setCookieMaxAge(3600);
        cookieThemeResolver.setCookieName("theme");
        return cookieThemeResolver;
    }


}
