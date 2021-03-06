package org.example.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;

public class MySpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    @NotNull
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(@NotNull ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);
        registerHiddenFieldFilter(aServletContext);

    }

    private void registerHiddenFieldFilter(ServletContext aContext) {           //нужно для "удалить" и "изменить"
        aContext.addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter()).
                addMappingForUrlPatterns(null, true, "/*");
    }


}
