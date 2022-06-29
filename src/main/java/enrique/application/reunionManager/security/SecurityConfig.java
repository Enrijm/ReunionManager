package enrique.application.reunionManager.security;


import enrique.application.reunionManager.filter.CustomAuthentificationFilter;
import enrique.application.reunionManager.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
                                    // This is because the developers of Spring framework encourage users to move towards a component-based security configuration.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // edit of the default config login of SpringBoot
        // injection of the customFilter
        CustomAuthentificationFilter customAuthentificationFilter = new CustomAuthentificationFilter(authenticationManagerBean());
        customAuthentificationFilter.setFilterProcessesUrl("/api/login");

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        // Permissions
        http.authorizeRequests().antMatchers("/api/login/**").permitAll(); // This login is SB who do it but I can edit it
        http.authorizeRequests().antMatchers("/token/refresh/**").permitAll();
        http.authorizeRequests().antMatchers(GET,"/api/reventaos").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(PUT,"/api/update").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET,"/api/nombre/**").hasAnyAuthority("ROLE_SUPER_ADMIN");
        http.authorizeRequests().antMatchers(POST,"/api/reventao/add").hasAnyAuthority("ROLE_MANAGER");
        http.authorizeRequests().antMatchers(POST,"/api/role/**").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(DELETE,"/api/delete/**").hasAnyAuthority("ROLE_SUPER_ADMIN");

        // http.authorizeRequests().anyRequest().permitAll(); Noe we do not want all people enter in the app so we update the code with :
        http.authorizeRequests().anyRequest().authenticated();

        http.addFilter(customAuthentificationFilter);
        // It is important to add the BeforeFilter before the rest of the filters
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean()throws Exception{
        return super.authenticationManagerBean();
    }



}
