package com.example.demo.Controlador;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entidades.Personas;
import com.example.demo.Services.PersonasService;

@RestController
@RequestMapping("/personas")
public class PersonasController {
    @Autowired
    private PersonasService personasService;

    @PostMapping
    public Personas createPersona(@RequestBody Personas persona) {
        return personasService.createPersona(persona);
    }

    @GetMapping("/{nombre}")
    public Optional<Personas> getPersona(@PathVariable String nombre) {
        return personasService.getPersona(nombre);
    }
}
