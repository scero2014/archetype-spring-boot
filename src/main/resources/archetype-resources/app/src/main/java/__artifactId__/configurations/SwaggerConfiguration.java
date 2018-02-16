#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.configurations;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;

/**
 * SwaggerConfiguration
 *
 * @author jnogueira
 */
@Configuration
@ConditionalOnClass(UiConfiguration.class)
public class SwaggerConfiguration {
	@Bean
	@ConditionalOnProperty(name = "database.server", havingValue = "true")
	public Docket docketCrud() {
//		Predicate<String> path = regex("/crud/1.0/.*");
//		.paths(path)
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("API")
				.apiInfo(metadata())
				.select()
				.apis(RequestHandlerSelectors.basePackage("${groupId}.mediaservice.ws.endpoints.group1"))
				.paths(PathSelectors.any())
				.build();
	}

	@Bean
	public Docket docketOperations() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Crud")
				.apiInfo(metadata())
				.select()
				.apis(RequestHandlerSelectors.basePackage("${groupId}.mediaservice.ws.endpoints.group2"))
				.paths(PathSelectors.any())
				.build();
	}

	/**
	 * Return metadata
	 *
	 * @return ApiInfo
	 */
	private ApiInfo metadata() {
		return new ApiInfoBuilder()
				.title("Database service")  // Service name
				.description("Database service")  // Service description
				.version("1.0")
				.contact(new Contact("by Scero", "http://scero.net", "scero@scero.net"))
				.build();
	}

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry
//            .addResourceHandler("/swagger-ui.html")
//            .addResourceLocations("classpath:/META-INF/resources/");
//        registry
//            .addResourceHandler("/webjars/**")
//            .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
}