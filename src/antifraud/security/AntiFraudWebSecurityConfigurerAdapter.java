package antifraud.security;

import antifraud.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class AntiFraudWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private final String[] listUsersRoles = {"ADMINISTRATOR", "SUPPORT"};

    private final String administratorRole = "ADMINISTRATOR";

    private final String[] antifraudTransactionRoles = {"MERCHANT"};

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint())
                .and()
                .csrf().disable().headers().frameOptions().disable().and()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/api/auth/list").hasAnyRole(listUsersRoles)
                .mvcMatchers(HttpMethod.DELETE, "/api/auth/user/*").hasRole(administratorRole)
                .mvcMatchers(HttpMethod.POST, "/api/antifraud/transaction").hasAnyRole(antifraudTransactionRoles)
                .mvcMatchers(HttpMethod.PUT, "/api/auth/role").hasRole(administratorRole)
                .mvcMatchers(HttpMethod.PUT, "/api/auth/access").hasRole(administratorRole)
                .mvcMatchers(HttpMethod.POST, "/api/auth/user").permitAll()
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationEntryPoint restAuthenticationEntryPoint() {
        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
}
