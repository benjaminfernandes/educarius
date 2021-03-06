package br.gov.pr.guaira.educacao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetails;
	
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		.antMatchers("/layout/**")
		.antMatchers("/images/**")
		.antMatchers("/img/**")
		.antMatchers("/stylesheets/**")
		.antMatchers("/javascripts/**")
		.antMatchers("/arquivos/**")
		.antMatchers("/fragments/**");
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetails).passwordEncoder(passwordEncoder());
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/","/educacaoInfantil","/saudeEscola", "/encerramento-ano-letivo", "/atividadesBncc", "/videos", "/inclusao", "/brincadeiras", "/rotinas", 
						"/segunda", "/terca", "/quarta", "/quinta", "/sexta", "/sabado", "/domingo","/musicalizacao","/semanasLiberadas/*", 
						"/aulasOnline/*/*", "/aula/*/*/*").permitAll()
				.antMatchers("/materias/*").hasRole("CADASTRAR_MATERIA")
				.antMatchers("/series/*").hasRole("CADASTRAR_SERIE")
				.antMatchers("/semanas/*").hasRole("CADASTRAR_SEMANA")
				.antMatchers("/aulas/*").hasRole("CADASTRAR_AULA")
				.antMatchers("/usuarios/*").hasRole("CADASTRAR_USUARIO")
				.antMatchers("/materias").hasRole("PESQUISAR_MATERIA")
				.antMatchers("/series").hasRole("PESQUISAR_SERIE")
				.antMatchers("/semanas").hasRole("PESQUISAR_SEMANA")
				.antMatchers("/aulas").hasRole("PESQUISAR_AULA")
				.antMatchers("/usuarios").hasRole("PESQUISAR_USUARIO")
				.anyRequest().denyAll()//pra funcionar o denyAll deve retirar o authenticated
				//anyRequest().denyAll()
				.and()
				
			.formLogin()
				.loginPage("/login").defaultSuccessUrl("/aulas/nova", true)
				.permitAll()
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and()
			.exceptionHandling()
				.accessDeniedPage("/403")
				.and()
			.sessionManagement()
				.invalidSessionUrl("/login")
				.maximumSessions(1)
				.expiredUrl("/Logado");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
