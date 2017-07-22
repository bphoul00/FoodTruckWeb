package ca.uqam.projet.tasks;

import ca.uqam.projet.repositories.*;
import ca.uqam.projet.resources.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

@Component
public class FetchBixi {

    private static final Logger log = LoggerFactory.getLogger(FetchBixi.class);
    private static final String URL = "https://secure.bixi.com/data/stations.json";
    @Autowired
    BixiRepository bixiRepository;

    @Scheduled(cron = "0 */10 * * * ?") // à toutes les 10 minutes.
    public void execute() {

        ObjectMapper mapper = new ObjectMapper();
        try {
            URL jsonUrl = new URL(URL);
            Stations station = mapper.readValue(jsonUrl, Stations.class);
            ArrayList<Bixi> listStationBixi = station.getStations();
            bixiRepository.clear();
            for (Bixi bixi : listStationBixi) {
                bixiRepository.insert(bixi);
                log.info(bixi.toString());
            }
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FetchBixi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
