package com.example.demo.MetodosGrafos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Entidades.Personas;
import com.example.demo.Entidades.Serie;
import com.example.demo.Services.SerieService;

@Component
public class GraphTraversal {

    private static SerieService serieService;

    @Autowired
    public GraphTraversal(SerieService serieService) {
        GraphTraversal.serieService = serieService;
    }

    public static void dfs(Serie serie, Set<Serie> visited, List<Serie> result) {
        if (serie == null || visited.contains(serie)) {
            return;
        }
        visited.add(serie);
        result.add(serie);
        for (Personas actor : serie.getActores()) {
            Serie relatedSerie = getRelatedSerie(actor);
            dfs(relatedSerie, visited, result);
        }
        for (Personas director : serie.getDirectores()) {
            Serie relatedSerie = getRelatedSerie(director);
            dfs(relatedSerie, visited, result);
        }
    }

    public static void bfs(Serie startSerie, List<Serie> result) {
        if (startSerie == null) {
            return;
        }
        Set<Serie> visited = new HashSet<>();
        Queue<Serie> queue = new LinkedList<>();
        queue.add(startSerie);
        visited.add(startSerie);

        while (!queue.isEmpty()) {
            Serie current = queue.poll();
            result.add(current);

            for (Personas actor : current.getActores()) {
                Serie relatedSerie = getRelatedSerie(actor);
                if (relatedSerie != null && !visited.contains(relatedSerie)) {
                    queue.add(relatedSerie);
                    visited.add(relatedSerie);
                }
            }
            for (Personas director : current.getDirectores()) {
                Serie relatedSerie = getRelatedSerie(director);
                if (relatedSerie != null && !visited.contains(relatedSerie)) {
                    queue.add(relatedSerie);
                    visited.add(relatedSerie);
                }
            }
        }
    }

    public static List<Personas> branchAndBound(Serie serie, int maxDepth, long maxTimeMillis) {
        List<Personas> bestCombination = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        branchAndBoundHelper(serie, new ArrayList<>(), bestCombination, 0, maxDepth, startTime, maxTimeMillis);
        return bestCombination;
    }

    private static void branchAndBoundHelper(Serie serie, List<Personas> currentCombination, List<Personas> bestCombination, int currentDepth, int maxDepth, long startTime, long maxTimeMillis) {
        // Establecer un límite de tiempo para evitar que el algoritmo se ejecute indefinidamente
        if (System.currentTimeMillis() - startTime > maxTimeMillis) {
            return;
        }

        // Establecer un límite de profundidad para evitar que el algoritmo se ejecute indefinidamente
        if (currentDepth > maxDepth) {
            return;
        }

        // Implementa la lógica de ramificación y poda aquí
        // Este es un ejemplo simple que solo busca la combinación con el menor número de personas
        if (currentCombination.size() < bestCombination.size() || bestCombination.isEmpty()) {
            bestCombination.clear();
            bestCombination.addAll(currentCombination);
        }

        for (Personas actor : serie.getActores()) {
            if (!currentCombination.contains(actor)) {
                currentCombination.add(actor);
                branchAndBoundHelper(serie, currentCombination, bestCombination, currentDepth + 1, maxDepth, startTime, maxTimeMillis);
                currentCombination.remove(actor);
            }
        }

        for (Personas director : serie.getDirectores()) {
            if (!currentCombination.contains(director)) {
                currentCombination.add(director);
                branchAndBoundHelper(serie, currentCombination, bestCombination, currentDepth + 1, maxDepth, startTime, maxTimeMillis);
                currentCombination.remove(director);
            }
        }
    }

    private static Serie getRelatedSerie(Personas persona) {
        return serieService.getRelatedSerie(persona.getNombre());
    }
}