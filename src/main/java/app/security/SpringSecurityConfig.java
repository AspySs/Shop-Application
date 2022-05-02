package app.security;

import app.security.jwt.JwtSecurityConfigurer;
import app.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/singin").permitAll()
                .antMatchers(HttpMethod.GET, "/sale/delete/id").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/sale/add").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/charge/delete").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/charge/add").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/warehouse/add").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/warehouse/delete/id").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/warehouse/delete/name").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/expense/delete/id").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/expense/delete/name").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/expense/add").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfigurer(tokenProvider));
    }


/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("{noop}pwd")
                .roles("USER")
                .and()
                .withUser("admin")
                .password("{noop}pwd")
                .roles("ADMIN");
//        auth.inMemoryAuthentication().withUser("user").password("pwd").roles("USER").and().withUser("admin").password("pwd").roles("ADMIN");

    }*/
}
