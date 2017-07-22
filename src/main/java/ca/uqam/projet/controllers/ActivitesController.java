package ca.uqam.projet.controllers;

import ca.uqam.projet.repositories.*;
import ca.uqam.projet.resources.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActivitesController {

    @Autowired
    ActivitesRepository repository;

    @RequestMapping(value = "/activites-375e", method = RequestMethod.GET)
    public List<Activites> findByDistanceLocationAndTime(@RequestParam(value = "du", required = false) String du,
            @RequestParam(value = "au", required = false) String au,
            @RequestParam(value = "lng", required = false) Double lng,
            @RequestParam(value = "lat", required = false) Double lat,
            @RequestParam(value = "rayon", required = false) Double rayon) {
        if (du != null && au != null && lng != null && lat != null && rayon != 0) {
            return repository.findByDistanceLocationAndTime(du, au, lng, lat, rayon);
        } else if (du != null && au != null) {
            return repository.findByTime(du, au);
        } else if (lng != null && lat != null && rayon != null) {
            return repository.findByDistanceLocation(lng, lat, rayon);
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/activites-375e", method = RequestMethod.PUT)
    public Activites updateEvent(
            @RequestParam(value = "id", required = true) int id,
            @RequestParam(value = "nom", required = false) String nom,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "arrondissement", required = false) String arrondissement,
            @RequestParam(value = "nomLieu", required = false) String nomLieu,
            @RequestParam(value = "lng", required = false) Double lng,
            @RequestParam(value = "lat", required = false) Double lat,
            @RequestParam(value = "date", required = false) String[] date) {
        {
            return repository.updatedEvent(id, nom, description, arrondissement, nomLieu, lng, lat, date);
        }
    }

    @RequestMapping(value = "/activites-375e", method = RequestMethod.POST)
    public Activites createdEvent(
            @RequestParam(value = "id", required = true) int id,
            @RequestParam(value = "nom", required = true) String nom,
            @RequestParam(value = "description", required = false, defaultValue = "vide") String description,
            @RequestParam(value = "arrondissement", required = true) String arrondissement,
            @RequestParam(value = "nomLieu", required = true) String nomLieu,
            @RequestParam(value = "lng", required = true) Double lng,
            @RequestParam(value = "lat", required = true) Double lat,
            @RequestParam(value = "date", required = true) String[] date) {
        {
            return repository.createdEvent(id, nom, description, arrondissement, nomLieu, lng, lat, date);
        }
    }

    @RequestMapping(value = "/activites-375e/{id}", method = RequestMethod.DELETE)
    public int deleteEvent(@PathVariable("id") int id) {
        return repository.deleteEvent(id);
    }

    @RequestMapping("/activites")
    public List<Activites> findAll() {
        return repository.findAll();
    }

    @RequestMapping("/activites/{id}")
    public Activites findById(@PathVariable("id") int id) {
        return repository.findById(id);
    }

}
