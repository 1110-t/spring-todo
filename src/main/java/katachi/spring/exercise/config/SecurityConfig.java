package katachi.spring.exercise.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    // ログイン後は/homeに遷移させる
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers(header -> {
            header.frameOptions().disable();
        });
        http.authorizeHttpRequests(authz -> authz
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .mvcMatchers("/").permitAll()
            .mvcMatchers("/general").hasRole("GENERAL")
            .mvcMatchers("/admin").hasRole("ADMIN")
            .anyRequest().authenticated()
        );
        http.formLogin(form -> form
    		.loginProcessingUrl("/login")
        	.loginPage("/login")
            .defaultSuccessUrl("/home")
            .failureUrl("/login?error")
            .permitAll()
        );
        return http.build();
    }
//    @Bean
//    public UserDetailsManager userDetailsService(){
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(this.dataSource);
//        // データベースにuser/userというレコードが追加されます
//        UserDetails user = User.withUsername("user")
//                .password(
//                PasswordEncoderFactories
//                        .createDelegatingPasswordEncoder()
//                        .encode("user"))
//                .roles("USER")
//                .build();
//        users.createUser(user);
//        return users;
//    }
    // shokuhou/misakiでログインする
    @Bean
    public UserDetailsManager userDetailsService(){
        // 既存User : user/user
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(this.dataSource);
        return users;
    }

}