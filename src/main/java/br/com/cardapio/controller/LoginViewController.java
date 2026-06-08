package br.com.cardapio.controller;

import jakarta.persistence.Column;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginViewController {


    @GetMapping("/login")

    public String paginaLogin(){
        return "login";
    }
}
