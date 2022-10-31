package com.kunal.springboot.ws.ws.config;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import com.kunal.springboot.ws.exception.DetailSoapFaultDefinitionExceptionResolver;
import com.kunal.springboot.ws.exception.ServiceFaultException;

/**
 * Kumar.Kunal
 */

@Configuration
@EnableWs
public class WebserviceConfig extends WsConfigurerAdapter {
	
	private static final Logger logger = LogManager.getLogger(WebserviceConfig.class);
	
	@Value("${soap.server.endpoint}")
    public static String soap_server_endpoint;
	
	public static final String NAMESPACE_URL = "http://kunal.soapws.com/types/v1";
	
	@Bean
    public SoapFaultMappingExceptionResolver soapFaultMappingExceptionResolver(){
        SoapFaultMappingExceptionResolver soapFaultMappingExceptionResolver = new DetailSoapFaultDefinitionExceptionResolver();

        SoapFaultDefinition soapFaultDefinition = new SoapFaultDefinition();
        soapFaultDefinition.setFaultCode(SoapFaultDefinition.SERVER);
        soapFaultDefinition.setFaultCode(SoapFaultDefinition.CLIENT);
        soapFaultMappingExceptionResolver.setDefaultFault(soapFaultDefinition);

        Properties errorMappings = new Properties();
        errorMappings.setProperty(Exception.class.getName(), SoapFaultDefinition.SERVER.toString());
        errorMappings.setProperty(ServiceFaultException.class.getName(), SoapFaultDefinition.SERVER.toString());
        soapFaultMappingExceptionResolver.setExceptionMappings(errorMappings);
        soapFaultMappingExceptionResolver.setOrder(1);
        return soapFaultMappingExceptionResolver;
    }
	
	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<>(servlet, "/ws/*");
	}

	@Bean(name = "customer")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema customersSchema) {
		
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("CustomerServicePort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace(soap_server_endpoint);
		logger.info("Webservice Endpoint {} "  , soap_server_endpoint);
		wsdl11Definition.setSchema(customerSchema());
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema customerSchema() {
		return new SimpleXsdSchema(new ClassPathResource("customer-service.xsd"));
	}
	
	@Bean
	public SimplePasswordValidationCallbackHandler callbackHandler() {
		SimplePasswordValidationCallbackHandler callbackHandler = new SimplePasswordValidationCallbackHandler();
		callbackHandler.setUsersMap(Collections.singletonMap("admin", "pwd123"));
		return callbackHandler;
	}

	@Override
	public void addInterceptors(List<EndpointInterceptor> endPointInterceptors) {
		endPointInterceptors.add(payloadLoggingInterceptor());
		endPointInterceptors.add(payloadValidatingInterceptor());
		
	}

	@Bean
	public PayloadLoggingInterceptor payloadLoggingInterceptor() {
		return new PayloadLoggingInterceptor();
	}

	@Bean
	public PayloadValidatingInterceptor payloadValidatingInterceptor() {
		final PayloadValidatingInterceptor payloadValidatingInterceptor = new PayloadValidatingInterceptor();
		payloadValidatingInterceptor.setSchema(new ClassPathResource("customer-service.xsd"));
		return payloadValidatingInterceptor;
	}

}
