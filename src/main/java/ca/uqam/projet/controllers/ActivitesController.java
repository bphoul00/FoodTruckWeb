package ca.uqam.projet.controllers;

import ca.uqam.projet.repositories.*;
import ca.uqam.projet.resources.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ActivitesController {

    @Autowired
    ActivitesRepository repository;

    /**
     * Rechercher liste activites par distance du location, temp ou les deux 
     * @param du
     * @param au
     * @param lng
     * @param lat
     * @param rayon
     * @return ResponseEntity<>
     */
    @RequestMapping(value = "/activites-375e", method = RequestMethod.GET)
    public ResponseEntity<List<Activites>> findByDistanceLocationAndTime(
            @RequestParam(value = "du", required = false) String du,
            @RequestParam(value = "au", required = false) String au,
            @RequestParam(value = "lng", required = false) Double lng,
            @RequestParam(value = "lat", required = false) Double lat,
            @RequestParam(value = "rayon", required = false) Double rayon) {
        if (du != null && au != null && lng != null && lat != null && rayon != 0) {
            List<Activites> ListA = repository.findByDistanceLocationAndTime(du, au, lng, lat, rayon);
            if (ListA == null) {
                return new ResponseEntity<>(ListA, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(ListA, HttpStatus.OK);
        } else if (du != null && au != null) {
            List<Activites> ListA = repository.findByTime(du, au);
            if (ListA == null) {
                return new ResponseEntity<>(ListA, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(ListA, HttpStatus.OK);
        } else if (lng != null && lat != null && rayon != null) {
            List<Activites> ListA = repository.findByDistanceLocation(lng, lat, rayon);
            if (ListA == null) {
                return new ResponseEntity<>(ListA, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(ListA, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Mise a jour un activites 
     * @param id
     * @param nom
     * @param description
     * @param arrondissement
     * @param nomLieu
     * @param lng
     * @param lat
     * @param date
     * @return ResponseEntity<>
     */
    @RequestMapping(value = "/activites-375e", method = RequestMethod.PUT)
    public ResponseEntity<Activites> updateEvent(
            @RequestParam(value = "id", required = true) int id,
            @RequestParam(value = "nom", required = false) String nom,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "arrondissement", required = false) String arrondissement,
            @RequestParam(value = "nomLieu", required = false) String nomLieu,
            @RequestParam(value = "lng", required = false) Double lng,
            @RequestParam(value = "lat", required = false) Double lat,
            @RequestParam(value = "date", required = false) String[] date) {
        {
            Activites returnAct = repository.updatedEvent(id, nom, description, arrondissement, nomLieu, lng, lat, date);
            if (returnAct == null) {
                return new ResponseEntity<>(returnAct, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(returnAct, HttpStatus.ACCEPTED);
        }
    }

    /**
     * Creer et ajouter un activites 
     * @param id
     * @param nom
     * @param description
     * @param arrondissement
     * @param nomLieu
     * @param lng
     * @param lat
     * @param date
     * @return ResponseEntity
     */
    @RequestMapping(value = "/activites-375e", method = RequestMethod.POST)
    public ResponseEntity<Activites> createdEvent(
            @RequestParam(value = "id", required = true) int id,
            @RequestParam(value = "nom", required = true) String nom,
            @RequestParam(value = "description", required = false, defaultValue = "") String description,
            @RequestParam(value = "arrondissement", required = false, defaultValue = "") String arrondissement,
            @RequestParam(value = "nomLieu", required = false, defaultValue = "") String nomLieu,
            @RequestParam(value = "lng", required = true) Double lng,
            @RequestParam(value = "lat", required = true) Double lat,
            @RequestParam(value = "date", required = true) String[] date) {
        {
            Activites returnAct = repository.createdEvent(id, nom, description, arrondissement, nomLieu, lng, lat, date);
            if (returnAct == null) {
                return new ResponseEntity<>(returnAct, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(returnAct, HttpStatus.CREATED);
        }
    }

    /**
     * Supprimer un activites 
     * @param id
     * @return ResponseEntity 
     */
    @RequestMapping(value = "/activites-375e/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Integer> deleteEvent(@PathVariable("id") int id) {
        int rowDeleted = repository.deleteEvent(id);
        if (rowDeleted <= 0) {
            return new ResponseEntity<>(rowDeleted, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(rowDeleted, HttpStatus.ACCEPTED);
    }

    /**
     * Rechercher tout  le liste activites.
     * @return ResponseEntity
     */
    @RequestMapping("/activites")
    public List<Activites> findAll() {
        return repository.findAll();
    }

    /**
     * Rechercher un activite pas id.
     * @param id
     * @return ResponseEntity
     */
    @RequestMapping("/activites/{id}")
    public Activites findById(@PathVariable("id") int id) {
        return repository.findById(id);
    }

}
