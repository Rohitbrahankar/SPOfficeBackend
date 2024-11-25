// package com.Backend.Security;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.config.Customizer;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfiguration {
    
    
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//         http.csrf(csrf -> csrf.disable());
//         http.authorizeHttpRequests(
//             authz->authz.anyRequest().permitAll()  
//         );
//         http.httpBasic(Customizer.withDefaults());
//         return http.build();
//     }

// }
