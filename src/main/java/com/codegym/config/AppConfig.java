package com.codegym.config;

import com.codegym.formatter.ProvinceFormatter;
import com.codegym.service.customer.CustomerService;
import com.codegym.service.customer.ICustomerService;
import com.codegym.service.province.IProvinceService;
import com.codegym.service.province.ProvinceService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.FormatterRegistry;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("com.codegym.controller")
@EnableJpaRepositories("com.codegym.repository")
@PropertySource("classpath:imageFile.properties")
@EnableSpringDataWebSupport
public class AppConfig implements WebMvcConfigurer, ApplicationContextAware {
    @Value("${file-image}")
    private String imageFile;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //Cấu hình Thymleaf
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        System.out.println("--------5-------");
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setPrefix("WEB-INF/views/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        System.out.println("--------6-------");
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        System.out.println("--------7-------");
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setTemplateEngine(templateEngine());
        return viewResolver;
    }


    //    Cấu hình uploadFile
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("--------8-------");
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:" + imageFile);
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getResolver() throws IOException {
        System.out.println("--------9-------");
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSizePerFile(52428800);
        return resolver;
    }

    //Cấu hình JPA

    public Properties properties() {
        System.out.println("--------10-------");
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return properties;
    }

    @Bean
    public DataSource dataSource() {
        System.out.println("--------11-------");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/md4_b7_th_customer_province");
        dataSource.setUsername("root");
        dataSource.setPassword("vubinh111111");
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        System.out.println("--------12-------");
        LocalContainerEntityManagerFactoryBean managerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        managerFactoryBean.setDataSource(dataSource());
        managerFactoryBean.setJpaProperties(properties());
        managerFactoryBean.setPackagesToScan("com.codegym.model");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        managerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        return managerFactoryBean;
    }

    @Bean
    @Qualifier(value = "entityManager")
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        System.out.println("--------13-------");
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory managerFactory) {
        System.out.println("--------14-------");
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(managerFactory);
        return transactionManager;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new ProvinceFormatter(applicationContext.getBean(ProvinceService.class)));
    }

    @Bean
    public ICustomerService customerService (){
        return new CustomerService();
    }

    @Bean
    public IProvinceService provinceService (){
        return new ProvinceService();
    }

}