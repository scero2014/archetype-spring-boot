#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.listeners;

import java.lang.reflect.Field;
import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Trunca aquellos cambios que excedan su longitud máxima. Se ejecuta antes de persistir los objetos
 */
public class TruncateListener {
	@PrePersist
	@PreUpdate
	public void prePersist(Object obj){
		Arrays.stream(obj.getClass().getDeclaredFields()).filter(e -> e.getType() == String.class && e.getAnnotation(Column.class) != null).
				forEach(e -> {
					boolean accesible = e.isAccessible();
					if (!accesible){
						e.setAccessible(true);
					}
					String value;
					try {
						int maxLenght = e.getAnnotation(Column.class).length();
						value = (String) e.get(obj);
						if (value != null && value.length() > maxLenght){
							e.set(obj, value.substring(0, maxLenght));
						}
					} catch (IllegalAccessException e1) {
						return;
					} finally {
						if (!accesible){
							e.setAccessible(false);
						}
					}
				});
	}

	private void filterString(Field field){
		Column column = field.getAnnotation(Column.class);
		if (column != null){
			column.length();
		}
	}
}
