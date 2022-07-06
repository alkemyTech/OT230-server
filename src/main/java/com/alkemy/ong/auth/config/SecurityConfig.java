package com.alkemy.ong.auth.config;

import com.alkemy.ong.auth.utility.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.alkemy.ong.auth.filter.JwtFilter;
import com.alkemy.ong.auth.service.impl.UserDetailsServiceImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl myUserDetailsService;

	@Autowired
	JwtFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder managerBuilder) throws Exception {
        managerBuilder.userDetailsService(myUserDetailsService);
    }
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
    	return super.authenticationManagerBean();
    }
    
	private static final String[] publicEndpoint = {
			//Swagger routes and docs
	};


	@Override
	public void configure(WebSecurity web) {
		web.ignoring()
				.antMatchers("/auth/register","/auth/login")
				.antMatchers("/users/**");
	}

    
    @Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
				.disable()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/auth/register", "/auth/login").permitAll()
				.antMatchers(HttpMethod.PATCH,"/users/**").hasRole(RoleEnum.ADMIN.getSimpleRoleName())
				.antMatchers(HttpMethod.DELETE,"/users/**").hasRole(RoleEnum.ADMIN.getSimpleRoleName())
			   .antMatchers(HttpMethod.POST, "/contacts/**").hasRole(RoleEnum.ADMIN.getSimpleRoleName())
                                //organization
                                .antMatchers(HttpMethod.GET, "/organization/public").permitAll()
                                .antMatchers(HttpMethod.PUT, "/organization/public").hasRole(RoleEnum.ADMIN.getSimpleRoleName())
                                .antMatchers(HttpMethod.POST, "/organization").hasRole(RoleEnum.ADMIN.getSimpleRoleName())
                                //slides
                                .antMatchers(HttpMethod.GET, "/slides").hasRole(RoleEnum.ADMIN.getSimpleRoleName())
                                .antMatchers(HttpMethod.DELETE, "/slides/**").hasRole(RoleEnum.ADMIN.getSimpleRoleName())
								.antMatchers(HttpMethod.POST, "/slides").hasRole(RoleEnum.ADMIN.getSimpleRoleName())
								.antMatchers(HttpMethod.PUT, "/slides/**").hasRole(RoleEnum.ADMIN.getSimpleRoleName())
                                //Activity
                                .antMatchers(HttpMethod.POST, "/activity").hasRole(RoleEnum.ADMIN.getSimpleRoleName())
								//Contacts
				                .antMatchers(HttpMethod.GET, "/contacts").hasRole(RoleEnum.ADMIN.getSimpleRoleName())
								//Comments
								.antMatchers(HttpMethod.POST,"/comments").permitAll()
								.antMatchers(HttpMethod.PUT,"/comments/**").permitAll()

				.antMatchers(publicEndpoint).permitAll()
				.anyRequest().authenticated()
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class).exceptionHandling()
		.authenticationEntryPoint(new Http403ForbiddenEntryPoint());
		
	}

}
