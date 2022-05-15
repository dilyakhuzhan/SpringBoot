package com.springboot.bootstrap.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired //create this method
    public void configAuthentication(AuthenticationManagerBuilder auth)throws  Exception{
        auth.jdbcAuthentication() // to inform authentication using jdbc/database authentication
                .passwordEncoder(new BCryptPasswordEncoder())//to inform encoder and decoder password using bcrypt
                .dataSource(dataSource) //produce connection object to database
                .usersByUsernameQuery("select username, password, enabled from users where username=?")//query to select user by username
                .authoritiesByUsernameQuery("select username, role from users where username=?");//to give authorized user when username and password true
    }

    @Override//create method configure to handle request
    protected void configure(HttpSecurity http)throws Exception{
        http.authorizeRequests()//Allows restricting access based upon the HttpServletRequest using antMatchers
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/icon/**").permitAll()//which suppose to mean that the visitors can see all site at path resources, static, css etc
                .anyRequest().authenticated()//any request must be authenticated otherwise spring app will return a 401 response, other than resources, css etc
                .and()
                .formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password")//Creating a Custom Login Form
                .successForwardUrl("/home")//Redirect to Different Pages after Login with Spring Security
                .permitAll()//allow user to acess all url after sucess login without restrictions(authorization)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//when user click url logout, this will handle logout event
                .logoutSuccessUrl("/login");//redirect to login form after logout succes
    }
}