package ca.uqam.projet.controllers;

import ca.uqam.projet.repositories.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class ApplicationController {

    @Autowired
    ActivitesRepository repository;

    /**
     * Routing l'utilisateur a page index.
     * @param model
     * @return model
     */
    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }

}
