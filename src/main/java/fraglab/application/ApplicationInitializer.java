package fraglab.application;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.EnumSet;

public class ApplicationInitializer implements WebApplicationInitializer {

    public static final String API_CONTEXT = "/api/*";

    @Override
    public void onStartup(ServletContext container) {
        container.setInitParameter("contextConfigLocation", "/WEB-INF/securityContext.xml");
        container.addListener(ContextLoaderListener.class);

        ServletRegistration.Dynamic registration = container.addServlet("dispatcher", new DispatcherServlet());
        registration.setLoadOnStartup(1);
        registration.addMapping(API_CONTEXT);

        FilterRegistration.Dynamic filterRegistration =
                container.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
        filterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
    }

}