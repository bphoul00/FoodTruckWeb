package ca.uqam.projet.tasks;

import ca.uqam.projet.resources.*;

import java.util.*;

import ca.uqam.projet.resources.*;
import ca.uqam.projet.repositories.*;

import java.util.*;
import java.util.stream.*;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import org.json.simple.JSONObject;
import org.jsoup.*;
import org.slf4j.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.web.client.*;

@Component
public class FetchActivites {

    private static final Logger log = LoggerFactory.getLogger(FetchActivites.class);
    private static final String URL = "http://guillemette.org/uqam/inf4375-e2017/assets/programmation-parcs.json";
    @Autowired
    LieuRepository lieuRepository;
    @Autowired
    DatesRepository datesRepository;
    @Autowired
    private ActivitesRepository repository;

    @Scheduled(cron = "*/45 * * * * ?") // à toutes les 2 secondes.
    // Actuellement désactivé.
    public void execute() {

        ObjectMapper mapper = new ObjectMapper();
        try {
            URL jsonUrl = new URL(URL);
            ArrayList<Activites> myObjects = mapper.readValue(jsonUrl, new TypeReference<List<Activites>>() {
            });
            //Clear
            lieuRepository.clear();
            datesRepository.clear();
            repository.clear();
            
            for (Activites activite : myObjects) {
                int newLieuID = IDMaker.createID();

                //set foreign LieuID
                Lieu bufferLieu = activite.getLieu();
                bufferLieu.setId(newLieuID);
                bufferLieu.setActivitesID(activite.getId());
                activite.setLieu(bufferLieu);
                
                log.info(activite.toString());
                repository.insert(activite);
                lieuRepository.insert(activite.getLieu());

                //set foreign DatesID
                for (String datesString : activite.getDates()) {
                    int newDatesID = IDMaker.createID();
                    Dates newDates = new Dates(newDatesID,activite.getId(), datesString);
                    datesRepository.insert(newDates);
                }

            }

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FetchActivites.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    

}

