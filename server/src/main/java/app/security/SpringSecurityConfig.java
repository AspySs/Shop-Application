package app.security;

import app.security.jwt.JwtSecurityConfigurer;
import app.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
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
                .antMatchers("/charge/find_id/{id}").permitAll()
                .antMatchers("/charge/find/between/date").permitAll()
                .antMatchers("/charge/find/between/amount").permitAll()
                .antMatchers("/charge/find/expanse/id").permitAll()
                .antMatchers("/charge/find/expanse/name").permitAll()
                .antMatchers("/sale/find_id/{id}").permitAll()
                .antMatchers("/sale/find/warehouse/name").permitAll()
                .antMatchers("/sale/find/warehouse/id").permitAll()
                .antMatchers("/sale/find/all").permitAll()
                .antMatchers("/sale/find/amount").permitAll()
                .antMatchers("/sale/find/quantity").permitAll()
                .antMatchers("/sale/find/date").permitAll()
                .antMatchers("/sale/update/{id}").authenticated()
                .antMatchers("/warehouse/find/all").permitAll()
                .antMatchers("/warehouse/find/amount/between").permitAll()
                .antMatchers("/warehouse/find/quantity/less").permitAll()
                .antMatchers("/warehouse/find/quantity/greater").permitAll()
                .antMatchers("/warehouse/find_id/{id}").permitAll()
                .antMatchers("/warehouse/find/name").permitAll()
                .antMatchers("/sale/delete/{id}").hasRole("ADMIN")
                .antMatchers("/sale/add").authenticated()
                .antMatchers("/charge/delete/{id}").hasRole("ADMIN")
                .antMatchers("/charge/update/{id}").authenticated()
                .antMatchers("/charge/add").authenticated()
                .antMatchers("/warehouse/add").authenticated()
                .antMatchers("/warehouse/delete/{id}").hasRole("ADMIN")
                .antMatchers("/warehouse/update/{id}").authenticated()
                .antMatchers("/warehouse/add").authenticated()
                .antMatchers("/expense/delete/{id}").hasRole("ADMIN")
                .antMatchers("/expense/delete/name").hasRole("ADMIN")
                .antMatchers("/expense/add").authenticated()
                .antMatchers("/expense/find/all").permitAll()
                .antMatchers("/expense/find_id/{id}").permitAll()
                .antMatchers("/expense/find/name").permitAll()
                .antMatchers("/expense/update").authenticated()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
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
