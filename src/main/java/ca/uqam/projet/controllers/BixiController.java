package ca.uqam.projet.controllers;

import ca.uqam.projet.repositories.*;
import ca.uqam.projet.resources.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class BixiController {

    @Autowired
    BixiRepository repository;

    @RequestMapping(value = "/stations-bixi", method = RequestMethod.GET)
    public List<Bixi> findByDistanceLocationAndNumberBixi(
            @RequestParam(value = "min_bixi_dispo", required = false, defaultValue = "0") int min_bixi_dispo,
            @RequestParam(value = "lng", required = false, defaultValue = "-73.569488") double lng,
            @RequestParam(value = "lat", required = false, defaultValue = "45.509803") double lat,
            @RequestParam(value = "rayon", required = false, defaultValue = "1000") double rayon) {
        return repository.findByDistanceLocationAndNumberBixi(lng, lat, rayon, min_bixi_dispo);
    }

    @RequestMapping(value = "/stations-bixi/all", method = RequestMethod.GET)
    public List<Bixi> findAll() {
        return repository.findAll();
    }

    @RequestMapping(value = "/stations-bixi/{id}", method = RequestMethod.GET)
    public Bixi findById(@PathVariable("id") int id) {
        return repository.findById(id);
    }

}
