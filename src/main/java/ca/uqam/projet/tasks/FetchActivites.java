package ca.uqam.projet.tasks;

import ca.uqam.projet.resources.*;

import java.util.*;

import ca.uqam.projet.resources.*;
import ca.uqam.projet.repositories.*;

import java.util.*;
import java.util.stream.*;

import com.fasterxml.jackson.annotation.*;
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

  @Autowired private ActivitesRepository repository;

  @Scheduled(cron="0 */1 * * * ?") // à toutes les 2 secondes.
  // Actuellement désactivé.
  public void execute() {
    Arrays.asList(new RestTemplate().getForObject(URL, ListActivites[].class)).stream()
      .map(this::asActivites)
      .peek(activite -> log.info(activite.toString()))
      .forEach(repository::insert)
      ;
  }

  private Activites asActivites(ListActivites q) {
    return new Activites(q.id, q.nom, q.description, q.arrondissement/*, q.dates, q.lieuNom, q.lag, q.lng*/);
  }
}

class ListActivites {
  @JsonProperty("id") int id;
  @JsonProperty("nom") String nom;
  @JsonProperty("description") String description;
  @JsonProperty("arrondissement") String arrondissement;
 // @JsonProperty("dates") String dates;
 // @JsonProperty("lieuNom") String lieuNom;
  //@JsonProperty("lag") int lag;
  //@JsonProperty("lng") int lng;
}
