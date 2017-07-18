package ca.uqam.projet.controllers;

import java.util.*;

import ca.uqam.projet.repositories.*;
import ca.uqam.projet.resources.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActivitesController {

  @Autowired ActivitesRepository repository;

  @RequestMapping("/activites")
  public List<Activites> findAll() {
    return repository.findAll();
  }
/*
  @RequestMapping("/citations/contenu")
  public List<Citation> findByContenu(@RequestParam("term") String[] tsterms) {
    return (tsterms.length == 0) ? repository.findAll() : repository.findByContenu(tsterms);
  }
  */

  @RequestMapping("/ActivitesRepository/{id}")
  public Activites findById(@PathVariable("id") int id) {
    return repository.findById(id);
  }
}
