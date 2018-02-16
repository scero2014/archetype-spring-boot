#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.pojos.keys;

import java.io.Serializable;

import lombok.Data;

/**
 * Key compuesta de ExampleChildren
 * @author jlnogueira on 15/02/2018
 */
@Data
public class ExampleChildrenKey implements Serializable {
	private static final long serialVersionUID = 5981718567883055950L;

	private String name;
	private int id;
}
