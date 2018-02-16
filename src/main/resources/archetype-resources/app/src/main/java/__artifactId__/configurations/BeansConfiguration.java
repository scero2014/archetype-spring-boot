#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.configurations;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de los Beans
 *
 * @author jnogueira
 */
@Configuration
public class BeansConfiguration {
	@Resource
	private AppConfiguration ${artifactId}Configuration;

	@Bean
	public Object beanName(){
		return new Object();
	}
}
