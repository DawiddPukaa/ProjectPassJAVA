package pl.kul.blog.infrastructure.authentication;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;
import pl.kul.blog.infrastructure.authentication.filters.AuthenticationFiltersFactory;
import pl.kul.blog.infrastructure.authentication.filters.token.TokenAuthenticationProvider;

@AllArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final AuthenticationFiltersFactory authenticationFiltersFactory;
    private final UserDetailsService userDetailsService;
    private final TokenAuthenticationProvider tokenAuthenticationProvider;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(it -> it
                .antMatchers("/api/**").hasRole("REGULAR")
                .antMatchers("/authentication/signup").permitAll()
                .antMatchers("/authentication/signin").permitAll()
                .antMatchers("/upload").permitAll()
                .antMatchers("/log4j").permitAll()
            )
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .sessionManagement(it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(authenticationFiltersFactory.createUsernameAndPasswordAuthenticationFilter(authenticationManager(), "/authentication/signin"), RequestCacheAwareFilter.class)
            .addFilterBefore(authenticationFiltersFactory.createTokenAuthenticationFilter(authenticationManager(), "/api/**"), RequestCacheAwareFilter.class)
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);

        auth.authenticationProvider(tokenAuthenticationProvider)
            .authenticationProvider(daoAuthenticationProvider)
        ;
    }
}
