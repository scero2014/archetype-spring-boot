#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.authentication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Provider de autentificación con claves MD5
 *
 * @author jnogueira
 */
@Service
public class MD5AuthenticationProvider implements AuthenticationProvider {
	@Resource
	private AppSecurity ${artifactId}Security;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		List<GrantedAuthority> grantedAuths = new ArrayList<>();

		String name = authentication.getName();
		String password;
		try {
			password = md5(authentication.getCredentials().toString());
		} catch (NoSuchAlgorithmException e) {
			throw new AuthenticationServiceException("Error encoding md5", e);
		}
		if (${artifactId}Security.getUser().equals(name) && ${artifactId}Security.getPassword().equals(password)){
			grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}

		return new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	private String md5(String str) throws NoSuchAlgorithmException {
		return (new HexBinaryAdapter()).marshal(MessageDigest.getInstance("MD5").digest(str.getBytes()));
	}

	@ConfigurationProperties(prefix = "${artifactId}Security")
	@Component
	@Getter
	@Setter
	class AppSecurity {
		private String user;
		private String password;
	}
}
