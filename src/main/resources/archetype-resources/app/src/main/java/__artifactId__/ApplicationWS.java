#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Main principal de la aplicación Spring Boot
 * @author jlnogueira on 15/02/2018
 */
@SpringBootApplication
@EnableSwagger2
// @EnableScheduling
@ComponentScan(basePackages = {"${package}"})
public class ApplicationWS {
	public static void main(String[] args){
		SpringApplication.run(ApplicationWS.class, args);
	}
}
