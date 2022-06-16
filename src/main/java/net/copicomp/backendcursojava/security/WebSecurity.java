package net.copicomp.backendcursojava.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import net.copicomp.backendcursojava.services.UserServiceInterface;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
    
    private final UserServiceInterface userService;
    private final BCryptPasswordEncoder bcrCryptPasswordEncoder;


    public WebSecurity(UserServiceInterface userService, BCryptPasswordEncoder bcrCryptPasswordEncoder) {
        this.userService = userService;
        this.bcrCryptPasswordEncoder = bcrCryptPasswordEncoder;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, "/users").permitAll()
        .antMatchers(HttpMethod.GET, "/posts/last").permitAll()
        .antMatchers(HttpMethod.GET, "/posts/{id}").permitAll()
        .anyRequest().authenticated().and().addFilter(geAuthenticationFilter())
        .addFilter(new AuthorizationFilter(authenticationManager()))
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService).passwordEncoder(bcrCryptPasswordEncoder);
    }

    public AuthenticationFilter geAuthenticationFilter() throws Exception{
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());

        filter.setFilterProcessesUrl("/users/login");

        return filter;
    }


}
