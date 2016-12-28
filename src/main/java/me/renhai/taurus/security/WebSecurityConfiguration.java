package me.renhai.taurus.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/**", "/swagger**", "/webjars/**", "/swagger-resources/**");
		web.ignoring().antMatchers("/api/search/**");
		web.ignoring().antMatchers("/api/rt/**");
		web.ignoring().antMatchers("/api/db/**");
		web.ignoring().antMatchers("/api/celebrity/**");
		web.ignoring().antMatchers("/api/movie/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http.csrf().disable();
	}

	
}
