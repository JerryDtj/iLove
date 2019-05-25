package com.love.ilove;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jerry
 */
@SpringBootApplication
@MapperScan("com.love.ilove.mapper")
public class ILoveApplication {

	public static void main(String[] args) {
		SpringApplication.run(ILoveApplication.class, args);
	}

	/**
	 * it's for set http url auto change to https
	 */
//	@Bean
//	public TomcatServletWebServerFactory tomcatServletWebServerFactory(Connector connector){
//		TomcatServletWebServerFactory tomcat=new TomcatServletWebServerFactory(){
//			@Override
//			protected void postProcessContext(Context context) {
//				SecurityConstraint securityConstraint=new SecurityConstraint();
//				securityConstraint.setUserConstraint("CONFIDENTIAL");
//				SecurityCollection collection=new SecurityCollection();
//				collection.addPattern("/*");
//				securityConstraint.addCollection(collection);
//				context.addConstraint(securityConstraint);
//			}
//		};
//		tomcat.addAdditionalTomcatConnectors(connector);
//		return tomcat;
//	}
//
//	@Bean
//	public Connector httpConnector(){
//		Connector connector=new Connector("org.apache.coyote.http11.Http11NioProtocol");
//		connector.setScheme("http");
//		connector.setPort(8080);
//		connector.setSecure(false);
//		connector.setRedirectPort(8443);
//		return connector;
//	}


}

