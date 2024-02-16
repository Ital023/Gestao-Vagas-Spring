package br.com.italomiranda.gestao_vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    SecurityFilter securityFilter;

    @Autowired
    private SecurityCandidateFilter securityCandidateFilter;

    @Bean //Indica que o metodo dentro da classe vai redefinir um metodo ja feito dentro do spring
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // desabilita a função proteção do spring security para todas as rotas
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/company/").permitAll() // define as rotas publicas
                            .requestMatchers("/candidate/").permitAll()
                            .requestMatchers("company/auth").permitAll()
                            .requestMatchers("/candidate/auth").permitAll();

                    auth.anyRequest().authenticated(); // coloca todas as outras privadas, precisando de autenticação

                }).addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(securityFilter, BasicAuthenticationFilter.class); //eh chamado nas requisicoes q precisa de auth

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
