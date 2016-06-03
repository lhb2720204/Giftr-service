package com.akhahaha.giftr.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
    private GiftrAuthenticationProvider giftrAuthenticationProvider;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(giftrAuthenticationProvider);
    }


	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/"
                		,"/index.html"
                		,"/signup.html"
                		,"/assets/**"
                		,"/css/**"
                		,"/fonts/**"
                		,"/js/**"
                		,"/users/signup"
                		).permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/")
                .defaultSuccessUrl("/about_me.html", true)
                .permitAll()
                .and()
            .logout()
    			.logoutSuccessUrl("/") 
                .permitAll()
                .and()
            .csrf()
            	.disable();
    }
}
