#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Lectura de las configuraciones de la aplicación
 *
 * @author jnogueira
 */
@Configuration
@ConfigurationProperties(prefix = "${parentArtifactId}")
@Getter
@Setter
public class AppConfiguration {
	private String configurationValue;
}
