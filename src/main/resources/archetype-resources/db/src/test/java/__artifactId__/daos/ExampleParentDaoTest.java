#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.daos;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.annotation.Resource;
import javax.sql.DataSource;

import ${package}.${artifactId}.pojos.ExampleChildren;
import ${package}.${artifactId}.pojos.ExampleParent;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.j${artifactId}c.core.J${artifactId}cTemplate;
import org.springframework.j${artifactId}c.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.junit.Assert.*;

/**
 * @author jlnogueira on 15/02/2018
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ExampleParentDaoTest {
	@Resource
	private DataSource dataSource;

	@Resource
	private ExampleParentDao exampleParentDao;

	@Configuration
	@EnableTransactionManagement
	@EnableJpaRepositories("${package}.${artifactId}")
	@ComponentScan("${package}.${artifactId}")
	static class ContextConfigurationTest {
		@Bean
		public DataSource dataSource() {
			SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
			dataSource.setDriverClass(org.h2.Driver.class);
			dataSource.setUsername("sa");
			dataSource.setUrl("j${artifactId}c:h2:mem:test${artifactId};DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
			dataSource.setPassword("");
			return dataSource;
		}

		@Bean
		LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

			LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
			entityManagerFactoryBean.setDataSource(dataSource);
			entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
			entityManagerFactoryBean.setPackagesToScan("${groupId}.mediaservice.${artifactId}");

			Properties jpaProperties = new Properties();
			jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
			entityManagerFactoryBean.setJpaProperties(jpaProperties);

			return entityManagerFactoryBean;

		}

		@Bean
		public PlatformTransactionManager transactionManager() {
			JpaTransactionManager txManager = new JpaTransactionManager();
			txManager.setEntityManagerFactory(entityManagerFactory(dataSource()).getObject());
			return txManager;
		}
	}

	@Before
	public void init() throws Exception {
		J${artifactId}cTemplate j${artifactId}cTemplate = new J${artifactId}cTemplate(dataSource);
		String script = IOUtils.toString(this.getClass().getResourceAsStream("/completeSchema.sql"));
		for (String query : script.split(";")) {
			j${artifactId}cTemplate.execute(query);
		}

		testBase();
	}


	@Test
	public void findAll() {
		Page<ExampleParent> exampleParents = exampleParentDao.findAll(new PageRequest(1, 10));
		assertTrue(exampleParents.getTotalElements() == 1);
		List<ExampleParent> exampleParents1 = exampleParents.getContent();
	}

	@Test
	public void countByTitle() {
	}

	@Test
	public void findAllXXXX() {
	}

	public void testBase() {
		ExampleParent exampleParent = new ExampleParent("example1");

		Set<ExampleChildren> exampleChildrens = new HashSet<>();
		exampleChildrens.add(new ExampleChildren());
		exampleChildrens.add(new ExampleChildren());

		exampleParent.setExampleChildrens(exampleChildrens);
		exampleParentDao.save(exampleParent);
	}
}