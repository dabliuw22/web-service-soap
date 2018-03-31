package com.leysoft.configuration;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class AppConfiguration extends WsConfigurerAdapter {
	
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(context);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/ws/*");
	}
	
	@Bean(name = {"autores"})
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema autoresSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("AutoresPort");
		wsdl11Definition.setTargetNamespace("http://leysoft.com/autor");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setSchema(autoresSchema);
		return wsdl11Definition;
	}
	
	@Bean
	public XsdSchema autoresSchema() {
		return new SimpleXsdSchema(new ClassPathResource("autores.xsd"));
	}
}