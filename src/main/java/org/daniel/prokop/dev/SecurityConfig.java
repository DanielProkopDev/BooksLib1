package org.daniel.prokop.dev;

import org.daniel.prokop.dev.DAO.exceptions.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
//@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/resources/**","/images/**","/styles/**","/register/**","/addUser");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        try {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            auth
                    .inMemoryAuthentication()
                    .passwordEncoder(passwordEncoder)
                    .withUser("dawid").password(passwordEncoder.encode("dawid")).roles("USER")
                    .and().withUser("dawid1").password(passwordEncoder.encode("dawid1")).roles("USER", "ADMIN")
                    .and().withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN");
        } catch (Exception e) {
            throw new ConfigurationException("In-Memory authentication was not configured.", e);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/resources/**","/images/**","/styles/**","/register","/addUser").permitAll()
                .mvcMatchers("/users/saveUsers").hasRole("ADMIN")
                .mvcMatchers("/books/saveBooks").hasRole("ADMIN")
                .mvcMatchers("/authors/saveAuthors").hasRole("ADMIN")
                .mvcMatchers("/**").hasAnyRole("ADMIN","USER")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login")
                .loginPage("/login")
                .failureUrl("/auth?auth_error=1")
                .defaultSuccessUrl("/home")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
                .csrf().csrfTokenRepository(repo());
    }

    @Bean
    public CsrfTokenRepository repo() {
        HttpSessionCsrfTokenRepository repo = new HttpSessionCsrfTokenRepository();
        repo.setParameterName("_csrf");
        repo.setHeaderName("X-CSRF-TOKEN");
        return repo;
    }
}