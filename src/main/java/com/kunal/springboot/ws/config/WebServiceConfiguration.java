package com.kunal.springboot.ws.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.net.URI;
import java.util.Collections;
import java.util.List;
/**
 * @author kunal
 * @project SpringBoot-SOAP-WS-main
 */

@Configuration
@EnableWs
public class WebServiceConfiguration extends WsConfigurerAdapter {

    @Bean
    ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
        messageDispatcherServlet.setApplicationContext(applicationContext);
        messageDispatcherServlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(messageDispatcherServlet, "/ws/*");
    }

    @Bean
    public XsdSchema customersSchema() {
        return new SimpleXsdSchema(new ClassPathResource("customer-service.xsd"));
    }

    @Bean(name = "customers")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema customersSchema) {
        DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
        defaultWsdl11Definition.setPortTypeName("CustomerPort");
        defaultWsdl11Definition.setTargetNamespace("https://www.kunal.soapws.com/customers");
        defaultWsdl11Definition.setLocationUri("/ws");
        defaultWsdl11Definition.setSchema(customersSchema);
        return defaultWsdl11Definition;
    }

    @Bean
    public XwsSecurityInterceptor securityInterceptor(){
        XwsSecurityInterceptor xwsSecurityInterceptor = new XwsSecurityInterceptor();
        xwsSecurityInterceptor.setCallbackHandler(callbackHandler());
        xwsSecurityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
        return xwsSecurityInterceptor;
    }

    @Bean
    public SimplePasswordValidationCallbackHandler callbackHandler() {
        SimplePasswordValidationCallbackHandler simplePasswordValidationCallbackHandler = new SimplePasswordValidationCallbackHandler();
        simplePasswordValidationCallbackHandler.setUsersMap(Collections.singletonMap("user", "password"));
        return simplePasswordValidationCallbackHandler;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> endpointInterceptors) {
        endpointInterceptors.add(securityInterceptor());
    }
}
