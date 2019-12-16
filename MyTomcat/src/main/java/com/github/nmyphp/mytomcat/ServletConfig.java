package com.github.nmyphp.mytomcat;

import com.github.nmyphp.mytomcat.util.ExceptionUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

@Slf4j
@Getter
public class ServletConfig {

    private final String XML_PATH = "WEB-INFO/web.xml";
    private final String SERVLET_NAME_NODE = "servlet-mapping.name";
    private final String SERVLET_URL_NODE = "servlet-mapping.url-pattern";
    private final String SERVLET_CLAZZ_NODE = "servlet-mapping.class";
    private final List<String> ignoreUri = Arrays.asList("/favicon.ico");
    private List<ServletMapping> servletMappings = new ArrayList<>(20);

    public ServletConfig() {
        XMLConfiguration config = getConfig();
        List<Object> servletNames = config.getList(SERVLET_NAME_NODE);
        for (int i = 0; i < servletNames.size(); i++) {
            List<Object> servletUrls = config.getList(SERVLET_URL_NODE);
            List<Object> servletClazzs = config.getList(SERVLET_CLAZZ_NODE);
            String servletName = String.valueOf(servletNames.get(i));
            String urlPattern = String.valueOf(servletUrls.get(i));
            String clazz = String.valueOf(servletClazzs.get(i));
            ServletMapping servletMapping = new ServletMapping(servletName, urlPattern, clazz);
            servletMappings.add(servletMapping);
            log.debug("Servlet mapping [name:{},url:{},class:{}]", servletName, urlPattern, clazz);
        }
    }

    public XMLConfiguration getConfig() {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<XMLConfiguration> builder =
            new FileBasedConfigurationBuilder<>(XMLConfiguration.class)
                .configure(params.xml()
                    .setFileName(XML_PATH));
        try {
            return builder.getConfiguration();
        } catch (ConfigurationException ex) {
            throw ExceptionUtil.wrap(ex);
        }
    }
}
