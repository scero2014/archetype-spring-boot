#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.daos;

import java.util.List;

import ${package}.${artifactId}.pojos.ExampleParent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Ejemplo de DAO
 * @author jlnogueira on 15/02/2018
 */
public interface ExampleParentDao  extends CrudRepository<ExampleParent, String> {
	Page<ExampleParent> findAll(Pageable pageRequest);
	long countByTitle(String title);

	@Query("")
	List<String> findAllXXXX();

}
