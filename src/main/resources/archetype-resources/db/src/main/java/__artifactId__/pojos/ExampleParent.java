#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.pojos;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ${package}.${artifactId}.listeners.TruncateListener;

/**
 * Ejemplo de una entidad padre
 *
 * @author jlnogueira on 15/02/2018
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Entity(name = "table1")
@EntityListeners(TruncateListener.class)
public class ExampleParent {
	@Id
	@NonNull
	@Column(length = 150, unique = true, nullable = false)
	private String name;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "name", referencedColumnName = "name", updatable = false)
	private Set<ExampleChildren> exampleChildrens;

}
