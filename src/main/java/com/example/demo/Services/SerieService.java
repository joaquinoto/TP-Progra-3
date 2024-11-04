package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.example.demo.Entidades.Serie;
import com.example.demo.Repositorio.SerieRepository;

@Service
public class SerieService {
    @Autowired
    private SerieRepository serieRepository;

    public Serie createSerie(Serie serie) {
        // Asegurarse de que la serie tenga un identificador único
        if (serie.getName() == null || serie.getName().isEmpty()) {
            throw new RuntimeException("La serie debe tener un nombre único");
        }
        return serieRepository.save(serie);
    }

    public Optional<Serie> getSerie(String name) {
        return serieRepository.findById(name);
    }

    public Serie getRelatedSerie(String personaNombre) {
        return serieRepository.findSerieByPersonaNombre(personaNombre);
    }
}