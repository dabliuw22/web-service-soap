# Web Service SOAP

Servicio web SOAP para Autore. Se deben tener en cuenta los siguientes aspectos:

1. Dependencias:
	```[xml]
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web-services</artifactId>
	</dependency>
	<dependency>
		<groupId>wsdl4j</groupId>
		<artifactId>wsdl4j</artifactId>
	</dependency>
	```
2. Puerto en *application.yml*:
	```[yml]
	spring:
	  application:
	    name: soap-ws
	server:
	  port: 8888
	```
3. Schema *autores.xsd* ubicado **src/main/resources**.

4. Agregar plugin a *pom.xml*
	```[xml]
	<!-- JAXB2 plugin -->
	<plugin>
		<groupId>org.codehaus.mojo</groupId>
		<artifactId>jaxb2-maven-plugin</artifactId>
		<version>1.6</version>
		<executions>
			<execution>
				<id>xjc</id>
				<goals>
					<goal>xjc</goal>
				</goals>
			</execution>
		</executions>
		<configuration>
			<!-- XSD source folder -->
			<schemaDirectory>${project.basedir}/src/main/resources/</schemaDirectory>
			<!-- Java objects source folder -->
			<outputDirectory>${project.basedir}/src/main/java</outputDirectory>
			<!-- Clear folder -->
			<clearOutputDir>false</clearOutputDir>
		</configuration>
	</plugin>
	```
	Guardar luego de agregarlo, lo cual generara las clases corespondientes al schema.

5. Clase de configuración:
	```[java]
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
	```
6. Instalar el plugin **wizdler** para google chrome o firefox.

7. Probar el web service en *http://localhost:8888/ws/autores.wsdl* y con el plugin seleccionar la operación.