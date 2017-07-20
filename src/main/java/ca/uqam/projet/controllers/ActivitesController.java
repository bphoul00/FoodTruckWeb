package ca.uqam.projet.controllers;

import java.util.*;

import ca.uqam.projet.repositories.*;
import ca.uqam.projet.resources.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActivitesController {

    @Autowired
    ActivitesRepository repository;
  
    @RequestMapping(value = "/activites-375e", method=RequestMethod.GET)
    public Activites findByDistanceLocationAndTime(@RequestParam(value = "du", required = false) String du 
                                                 , @RequestParam(value = "au", required = false) String au
                                                 , @RequestParam(value = "rayon", required = false) int rayon
                                                 , @RequestParam(value = "lat", required = false) Double lat
                                                 , @RequestParam(value = "lng", required = false) Double lng) {
        return repository.findByDistanceLocationAndTime(du,au,rayon,lat,lng);
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

