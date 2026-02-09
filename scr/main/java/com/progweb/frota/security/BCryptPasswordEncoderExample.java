package com.progweb.frota.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderExample {
    public static void main(String[] args) {
        String plainPassword = "admin123";
        
        // Criando um encoder BCrypt
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Codificando a senha
        String encodedPassword = encoder.encode(plainPassword);
        
        // Exibindo a hash gerada
        System.out.println("Senha original: " + plainPassword);
        System.out.println("Hash gerada: " + encodedPassword);
    }
}
