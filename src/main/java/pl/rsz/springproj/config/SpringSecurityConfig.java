package pl.rsz.springproj.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pl.rsz.springproj.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Konfiguracja dostawcy uwierzytelniania opartego na bazie danych
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/product-images/**").permitAll() // + product-images
                        .requestMatchers("/", "/index", "/product-list", "/login", "/register").permitAll()
                        .requestMatchers("/cart/**").permitAll()
                        .requestMatchers("/", "/index", "/product-list", "/register").permitAll() // Dostępne dla wszystkich
                        .requestMatchers("/cart/**").permitAll() // Koszyk dostępny dla wszystkich
                        .requestMatchers("/order/checkout", "/order/submit", "/panel").authenticated() // Zamawianie tylko dla zalogowanych
                        .requestMatchers("/product-details").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/product/add", "/product/edit/**", "/product/save", "/product-delete").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );

        http.formLogin((form) -> form
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/product-list", true)
        );

        http.logout((logout) -> logout
                .logoutSuccessUrl("/")
                .permitAll()
        );

        // H2 Console fix
        http.csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()));
        http.headers(headers -> headers.frameOptions(headersConfig -> headersConfig.sameOrigin()));

        http.exceptionHandling((config) -> config.accessDeniedPage("/error403"));

        // Rejestracja naszego providera
        http.authenticationProvider(authenticationProvider());

        return http.build();
    }
}