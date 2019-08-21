package imdb.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;

	private final String USERS_QUERY = "select email, password from user where email=?";
	private final String ROLE_QUERY = "select u.email, r.role from user u inner join user_role ur on (u.id_user = ur.user_id) inner join role r on (ur.role_id = r.id_role) where u.email=?";

	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.jdbcAuthentication().usersByUsernameQuery(USERS_QUERY).authoritiesByUsernameQuery(ROLE_QUERY)
				.dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/login").permitAll().antMatchers("/signup")
				.permitAll().antMatchers("/home/**").hasAuthority("ADMIN").anyRequest().authenticated().and().csrf()
				.disable().formLogin().loginPage("/login").failureUrl("/login?error=true")
				.defaultSuccessUrl("/home/home").usernameParameter("email").passwordParameter("password").and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").and().rememberMe()
				.tokenRepository(persistentTokenRepository()).tokenValiditySeconds(60 * 60).and().exceptionHandling()
				.accessDeniedPage("/access_denied");

//		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/login").hasAuthority("ADMIN").anyRequest()
//				.authenticated().and().formLogin().loginPage("/login").failureUrl("/login?error=true")
//				.usernameParameter("email").passwordParameter("password").and().logout().permitAll();
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);

		return db;
	}

}