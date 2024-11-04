package com.example.demo.Controlador;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entidades.Personas;
import com.example.demo.Entidades.Serie;
import com.example.demo.MetodosGrafos.GraphTraversal;
import com.example.demo.Repositorio.SerieRepository;
import com.example.demo.Services.SerieService;

@RestController
@RequestMapping("/series")
public class SerieController {
    @Autowired
    private SerieService serieService;

    @Autowired
    private GraphTraversal graphTraversal;

    @PostMapping
    public Serie createSerie(@RequestBody Serie serie) {
        // Agregar registros de depuración para verificar el flujo de datos
        System.out.println("Creando serie con nombre: " + serie.getName());
        Serie createdSerie = serieService.createSerie(serie);
        System.out.println("Serie creada: " + createdSerie.getName());
        return createdSerie;
    }

    @GetMapping("/{name}")
    public Optional<Serie> getSerie(@PathVariable String name) {
        Optional<Serie> serie = serieService.getSerie(name);
        if (serie.isPresent()) {
            System.out.println("Serie encontrada: " + serie.get().getName());
        } else {
            System.out.println("Serie no encontrada: " + name);
        }
        return serie;
    }

    @PostMapping("/{nombre}/actores")
    public Serie addActorToSerie(@PathVariable String nombre, @RequestBody Personas actor) {
        System.out.println("Buscando serie con nombre: " + nombre);
        Serie serie = serieService.getSerie(nombre).orElseThrow(() -> new RuntimeException("Serie no encontrada: " + nombre));
        System.out.println("Serie encontrada: " + serie.getName());
        
        // Verificar que el actor tenga el año de nacimiento
        if (actor.getNacido() == 0) {
            throw new RuntimeException("El actor debe tener un año de nacimiento");
        }
        
        serie.getActores().add(actor);
        Serie updatedSerie = serieService.createSerie(serie);
        System.out.println("Actor añadido a la serie: " + updatedSerie.getName());
        
        // Devolver la serie actualizada con los detalles completos de los actores
        return updatedSerie;
    }

    @PostMapping("/{nombre}/directores")
    public Serie addDirectorToSerie(@PathVariable String nombre, @RequestBody Personas director) {
        System.out.println("Buscando serie con nombre: " + nombre);
        Serie serie = serieService.getSerie(nombre).orElseThrow(() -> new RuntimeException("Serie no encontrada: " + nombre));
        System.out.println("Serie encontrada: " + serie.getName());
        serie.getDirectores().add(director);
        Serie updatedSerie = serieService.createSerie(serie);
        System.out.println("Director añadido a la serie: " + updatedSerie.getName());
        return updatedSerie;
    }

    @GetMapping("/{nombre}/combinaciones")
    public List<List<Personas>> getCombinaciones(@PathVariable String nombre) {
        Serie serie = serieService.getSerie(nombre).orElseThrow(() -> new RuntimeException("Serie no encontrada: " + nombre));
        List<Personas> actores = serie.getActores();
        List<Personas> directores = serie.getDirectores();
        List<List<Personas>> combinaciones = new ArrayList<>();
        backtrack(combinaciones, new ArrayList<>(), actores, directores, 0, 0);
        return combinaciones;
    }

    private void backtrack(List<List<Personas>> combinaciones, List<Personas> tempList, List<Personas> actores, List<Personas> directores, int startActor, int startDirector) {
        combinaciones.add(new ArrayList<>(tempList));
        for (int i = startActor; i < actores.size(); i++) {
            tempList.add(actores.get(i));
            backtrack(combinaciones, tempList, actores, directores, i + 1, startDirector);
            tempList.remove(tempList.size() - 1);
        }
        for (int j = startDirector; j < directores.size(); j++) {
            tempList.add(directores.get(j));
            backtrack(combinaciones, tempList, actores, directores, startActor, j + 1);
            tempList.remove(tempList.size() - 1);
        }
    }

    @GetMapping("/{nombre}/actores")
    public List<Personas> getActores(@PathVariable String nombre) {
        Serie serie = serieService.getSerie(nombre).orElseThrow(() -> new RuntimeException("Serie no encontrada: " + nombre));
        return serie.getActores();
    }

    @GetMapping("/{nombre}/directores")
    public List<Personas> getDirectores(@PathVariable String nombre) {
        Serie serie = serieService.getSerie(nombre).orElseThrow(() -> new RuntimeException("Serie no encontrada: " + nombre));
        return serie.getDirectores();
    }

    @GetMapping("/{nombre}/dfs")
    public List<Serie> dfsTraversal(@PathVariable String nombre) {
        Serie serie = serieService.getSerie(nombre).orElseThrow(() -> new RuntimeException("Serie no encontrada: " + nombre));
        List<Serie> result = new ArrayList<>();
        Set<Serie> visited = new HashSet<>();
        graphTraversal.dfs(serie, visited, result);
        return result;
    }

    @GetMapping("/{nombre}/bfs")
    public List<Serie> bfsTraversal(@PathVariable String nombre) {
        Serie serie = serieService.getSerie(nombre).orElseThrow(() -> new RuntimeException("Serie no encontrada: " + nombre));
        List<Serie> result = new ArrayList<>();
        graphTraversal.bfs(serie, result);
        return result;
    }

    @GetMapping("/{nombre}/branchAndBound")
    public List<Personas> branchAndBoundTraversal(@PathVariable String nombre, @RequestParam(defaultValue = "10") int maxDepth, @RequestParam(defaultValue = "10000") long maxTimeMillis) {
        Serie serie = serieService.getSerie(nombre).orElseThrow(() -> new RuntimeException("Serie no encontrada: " + nombre));
        return graphTraversal.branchAndBound(serie, maxDepth, maxTimeMillis);
    }
}
