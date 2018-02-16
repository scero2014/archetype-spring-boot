#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.configurations;

import java.util.Properties;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuración de las conexiones con BD
 *
 * @author jnogueira
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("${package}.db")
public class DatabaseConfiguration {
	private enum  ConexionType{
		H2,
		MYSQL
	}

	private ConexionType conexionType;

	/**
	 * Database bean
	 *
	 * @return
	 */
	@ConditionalOnExpression("'${symbol_dollar}{datasources.h2.url:}' == ''")
	@ConditionalOnProperty("datasources.h2")
	@Bean(name="dataSource", destroyMethod = "")
	@Primary
	public DataSource dataSourceH2() {
		conexionType = ConexionType.H2;
		return new HikariDataSource();
	}

	@ConditionalOnExpression("'${symbol_dollar}{datasources.mysql.url:}' == ''")
	@Bean(name="dataSource", destroyMethod = "")
	@ConfigurationProperties(prefix = "datasources.mysql")
	@Primary
	public DataSource dataSourceMysql() {
		conexionType = ConexionType.MYSQL;
		return new HikariDataSource();
	}

	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource") DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan("${groupId}.mediaservice.db");

		Properties jpaProperties = new Properties();
		if (conexionType == ConexionType.H2){
			jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		} else if (conexionType == ConexionType.MYSQL){
			jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
		}
		// jpaProperties.put("hibernate.show_sql", "true");
		entityManagerFactoryBean.setJpaProperties(jpaProperties);
		return entityManagerFactoryBean;
	}

	@Bean
	public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory(dataSource).getObject());
		return txManager;
	}

	@Bean
	public Flyway flyway(@Qualifier("dataSource") DataSource dataSource){
		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource);
		if (conexionType == ConexionType.H2){
			flyway.setLocations("/db/migration/h2/");
		} else if (conexionType == ConexionType.MYSQL){
			flyway.setLocations("/db/migration/mysql/");
		}
		flyway.migrate();
		return flyway;
	}
}
