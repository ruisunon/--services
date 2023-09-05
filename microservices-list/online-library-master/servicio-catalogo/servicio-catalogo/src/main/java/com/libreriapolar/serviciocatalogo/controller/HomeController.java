package com.libreriapolar.serviciocatalogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    Controlador controlador; // Declara pero sin preocuparse por la inicialación ni administarción
    public HomeController(Controlador controlador){ // indicamos a spring que no la tiene que pasar lista para utilizar
        this.controlador = controlador; // se la asignamos a nuestra variable
    }

    @GetMapping("/")
    public String getGreeting() {
        return "Welcome to the book catalog!";
    }
}
