#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.endpoints.group1;

/**
 * @author jlnogueira on 15/02/2018
 */

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import ${package}.${artifactId}.endpoints.group1.responses.ResponseExample;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestM${artifactId}ing;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ejemplo de controlador
 *
 * @author jlnogueira
 */
@Validated
@RequestM${artifactId}ing(value = "/admin/example", produces = {MediaType.APPLICATION_JSON_VALUE}, headers = "Accept=${artifactId}lication/json")
@RestController
@Slf4j
public class ExampleController {
	@RequestM${artifactId}ing(method = RequestMethod.GET, value = "/example/{value}")
	@ApiOperation(value = "Importa todos los elementos")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ok"),
			@ApiResponse(code = 500, message = "Failure")})
	public ResponseEntity<ResponseExample> exampleController(@PathVariable("value") String value) {
		HttpHeaders headers = new HttpHeaders();
		ResponseExample responseExample;
		HttpStatus httpStatus;
		try {
			responseExample = new ResponseExample();
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			log.error("Scr${artifactId}ing error", e);
			responseExample = new ResponseExample();
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(responseExample, headers, httpStatus);
	}

}
