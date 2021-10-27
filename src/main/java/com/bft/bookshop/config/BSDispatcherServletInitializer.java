package com.bft.bookshop.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
// замена файла web.xml (который в свою очеред считывался Tomcat)
public class BSDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}
