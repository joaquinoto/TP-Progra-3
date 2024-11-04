package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Entidades.Personas;
import com.example.demo.Repositorio.PersonasRepository;

import java.util.Optional;

@Service
public class PersonasService {
    @Autowired
    private PersonasRepository personasRepository;

    public Personas createPersona(Personas persona) {
        return personasRepository.save(persona);
    }

    public Optional<Personas> getPersona(String nombre) {
        return personasRepository.findById(nombre);
    }

    
}
