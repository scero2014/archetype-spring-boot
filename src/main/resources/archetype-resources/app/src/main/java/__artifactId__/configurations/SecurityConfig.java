#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.configurations;

import javax.annotation.Resource;

import ${package}.${artifactId}.authentication.MD5AuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Configuración de spring security
 *
 * @author jnogueira
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Resource
	private MD5AuthenticationProvider md5AuthenticationProvider;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.
				authorizeRequests().antMatchers("/console/h2/**").hasRole("ADMIN").and().
				authorizeRequests().antMatchers("/manage/**").hasRole("ADMIN").and().
				authorizeRequests().antMatchers("/crud/**").hasRole("ADMIN").and().
				authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN").and().
						authorizeRequests().anyRequest().permitAll().and().httpBasic();
		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(md5AuthenticationProvider);
	}
}
