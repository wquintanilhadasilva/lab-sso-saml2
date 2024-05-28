package lab.sso.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity  // hasAuthority() preAuthorize()
@EnableWebSecurity     // hasRole()
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        // Ajusta as rotas que precisam de autenticação
                        // Ajusta as rotas que são de acesso público (primeiro)
                        authorizeConfig -> {
                            authorizeConfig.requestMatchers("/public").permitAll();
                            authorizeConfig.requestMatchers("/logout").permitAll();
                            authorizeConfig.anyRequest().authenticated();
                        }
                )
                // .oauth2Login(Customizer.withDefaults()) Caso queira fazer o fluxo OAuth para redirecionar para a tela de login do IDP externo
                .oauth2ResourceServer(config -> config.jwt(jwt -> jwt.jwtAuthenticationConverter(new JWTConverter())));
        return http.build();
    }

}
