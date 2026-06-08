package br.com.cardapio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${admin.username}")
    private String username;

    @Value("${admin.password}")
    private String password;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                // 1. Configuração de Autorização de Rotas
                .authorizeHttpRequests(authorize -> authorize
                        // Recursos estáticos (CSS, JS, Imagens) ficam liberados
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        // Qualquer rota que comece com /admin exige a role ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // Qualquer outra rota do sistema (ex: visualização pública do cardápio) é liberada
                        .anyRequest().permitAll()
                )
                // 2. Configuração do Formulário de Login padrão do Spring
                .formLogin(form -> form
                        .loginPage("/login") // Se você criar uma página de login própria futuramente, mapeia aqui
                        .defaultSuccessUrl("/admin", true) // Para onde vai após logar com sucesso
                        .permitAll()
                )
                // 3. Configuração de Logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }

    // 4. BCrypt para criptografia de senhas (essencial para segurança bem feita)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 5. Usuário básico em memória para o Admin (para não precisar criar tabela de usuário agora)
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .username(username)
                // Usando o encoder para encodar a senha "admin123"
                .password(passwordEncoder.encode(password))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }
}