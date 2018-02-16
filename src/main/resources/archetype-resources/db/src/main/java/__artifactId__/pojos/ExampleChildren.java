#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.Min;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ${package}.${artifactId}.listeners.TruncateListener;
import ${package}.${artifactId}.pojos.keys.ExampleChildrenKey;

/**
 * Ejemplo de entidad hija
 *
 * @author jlnogueira on 15/02/2018
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Entity(name = "table2")
@EntityListeners(TruncateListener.class)
@IdClass(ExampleChildrenKey.class)
public class ExampleChildren {
	@Id
	@NonNull
	@Column(length = 150, unique = true, nullable = false)
	private String name;

	@Id
	@NonNull
	@Min(0)
	private int id;

	private String data;
}
