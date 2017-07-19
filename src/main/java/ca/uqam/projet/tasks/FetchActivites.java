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

    @Scheduled(cron = "*/10 * * * * ?") // à toutes les 2 secondes.
    // Actuellement désactivé.
    public void execute() {

        ObjectMapper mapper = new ObjectMapper();
        try {
            URL jsonUrl = new URL(URL);
            ArrayList<Activites> myObjects = mapper.readValue(jsonUrl, new TypeReference<List<Activites>>() {
            });
            //Clear
            repository.clear();
            lieuRepository.clear();
            datesRepository.clear();
            for (Activites activite : myObjects) {
                int newID = IDMaker.createID();

                //set foreign LieuID
                activite.setLieuId(newID);
                Lieu newlieu = activite.getLieu();
                newlieu.setId(newID);
                activite.setLieu(newlieu);

                //set foreign DatesID
                activite.setDatesId(newID);
                for (String datesString : activite.getDates()) {
                    Dates newDates = new Dates(newID, datesString);
                    datesRepository.insert(newDates);
                }

                log.info(activite.toString());
                System.out.println(activite.getLieu().toString() + " " + activite.getLieu().getId());
                lieuRepository.insert(activite.getLieu());
                repository.insert(activite);
            }

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FetchActivites.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /*
  private Activites asActivites(ListActivites q) {
    return new Activites(q.id, q.nom, q.description, q.arrondissement, q.lieu, q.dates);
  }
     */

}
/*
class ListActivites {
  @JsonProperty("id") int id;
  @JsonProperty("nom") String nom;
  @JsonProperty("description") String description;
  @JsonProperty("arrondissement") String arrondissement;
  @JsonProperty("lieu") Lieu lieu;
  @JsonProperty("dates") ArrayList<String> dates;
 // @JsonProperty("dates") String dates;
 // @JsonProperty("lieuNom") String lieuNom;
  //@JsonProperty("lag") int lag;
  //@JsonProperty("lng") int lng;
}
 */
