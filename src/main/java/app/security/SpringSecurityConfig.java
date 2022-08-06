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
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/auth/register").permitAll()
                .antMatchers("/charge/find/all").permitAll()
                .antMatchers( "/charge/find/id").permitAll()
                .antMatchers( "/sale/find/id").permitAll()
                .antMatchers( "/sale/find/warehouse/name").permitAll()
                .antMatchers( "/sale/find/all").permitAll()
                .antMatchers( "/warehouse/find/all").permitAll()
                .antMatchers( "/sale/delete/{id}").permitAll()
                .antMatchers( "/sale/add").permitAll()
                .antMatchers( "/charge/delete/{id}").permitAll()
                .antMatchers("/charge/add").permitAll()
                .antMatchers( "/warehouse/add").permitAll()
                .antMatchers( "/warehouse/delete/{id}").permitAll()
                .antMatchers( "/expense/delete/{id}").permitAll()
                .antMatchers( "/expense/delete/name").permitAll()
                .antMatchers( "/expense/add").permitAll()
                .antMatchers( "/expense/find/all").permitAll()
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
