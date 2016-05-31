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
                		,"/assets/phone1.png"
                		,"/assets/phone2.png"
                		,"/assets/phone3.png"
                		,"/assets/phone4.png"
                		,"/assets/phone5.png"
                		,"/assets/phone6.png"
                		,"/assets/phone7.png"
                		,"/assets/giftrlogo.svg"
                		,"/css/style.css"
                		,"/css/bootstrap.min.css"
                		,"/users/signup"
				,"/products"
                		).permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login.html")
                .permitAll()
                .and()
            .logout()
    			.logoutSuccessUrl("/login.html") 
                .permitAll()
                .and()
            .csrf()
            	.disable();
    }
}
