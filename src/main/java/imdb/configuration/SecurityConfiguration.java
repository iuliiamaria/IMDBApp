package imdb.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/login").
		 * permitAll().antMatchers("/signup")
		 * .permitAll().antMatchers("/home/**").hasAuthority("ADMIN").anyRequest().
		 * authenticated().and().csrf()
		 * .disable().formLogin().loginPage("/login").failureUrl("/login?error=true")
		 * .defaultSuccessUrl("/home/home").usernameParameter("email").passwordParameter
		 * ("password").and().logout() .logoutRequestMatcher(new
		 * AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").and().rememberMe()
		 * .tokenRepository(persistentTokenRepository()).tokenValiditySeconds(60 *
		 * 60).and().exceptionHandling() .accessDeniedPage("/access_denied");
		 */

		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/login").hasAuthority("ADMIN").anyRequest()
				.authenticated().and().formLogin().loginPage("/login").failureUrl("/login?error=true").usernameParameter("email")
				.passwordParameter("password").and().logout().permitAll();
	}

}
