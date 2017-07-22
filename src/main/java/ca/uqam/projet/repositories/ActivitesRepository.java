package ca.uqam.projet.repositories;

import ca.uqam.projet.resources.*;
import ca.uqam.projet.tasks.Validation;
import java.sql.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

@Component
public class ActivitesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    LieuRepository lieuRepository;
    @Autowired
    DatesRepository datesRepository;
    Validation validation = new Validation();

    private static final String FIND_ALL_STMT
            = " select"
            + "     *"
            + " from"
            + "   activites";

    public List<Activites> findAll() {
        List<Activites> listAct = jdbcTemplate.query(FIND_ALL_STMT, new ActivitesRowMapper());
        listAct = joinListDatesAndLieu(listAct);
        return listAct;

    }

    private static final String FIND_BY_ID_STMT
            = " SELECT "
            + " * "
            + " FROM "
            + " activites "
            + " WHERE "
            + " id = ?";

    public Activites findById(int id) {
        Activites activite = jdbcTemplate.queryForObject(FIND_BY_ID_STMT, new Object[]{id}, new ActivitesRowMapper());
        activite = joinDatesAndLieu(activite);
        return activite;
    }

    private String getFIND_BY_DISTANCE_LOCATION_TIME(String duStringDate, String auStringDate, double lng, double lat, double rayon) {
        return " SELECT * FROM activites "
                + "INNER JOIN dates ON activites.id = dates.activitesID "
                + "INNER JOIN lieu on lieu.activitesid = activites.id "
                + "WHERE dates.schedule "
                + "BETWEEN (DATE '" + duStringDate + "') AND ('" + auStringDate + "') AND "
                + "ST_DISTANCE_SPHERE(lieu.point, "
                + "ST_GeomFromText('POINT (" + lng + " " + lat + ")',4326)) < "
                + rayon;
    }

    public List<Activites> findByDistanceLocationAndTime(String duString, String auString, double lng, double lat, double rayon) {
        if (!validation.findByDistanceLocationAndTimeValidation(duString, auString, lng, lat, rayon)) {
            return null;
        }
        List<Activites> listAct = jdbcTemplate.query(getFIND_BY_DISTANCE_LOCATION_TIME(duString, auString, lng, lat, rayon), new ActivitesRowMapper());
        listAct = joinListDatesAndLieu(listAct);
        return listAct;

    }

    private String getFIND_BY_TIME(String duStringDate, String auStringDate) {
        return " SELECT * FROM activites "
                + "INNER JOIN dates ON activites.id = dates.activitesID "
                + "WHERE dates.schedule "
                + "BETWEEN (DATE '" + duStringDate + "') AND ('" + auStringDate + "')";
    }

    public List<Activites> findByTime(String duString, String auString) {
        if (!validation.findByTimeValidation(duString, auString)) {
            return null;
        }
        List<Activites> listAct = jdbcTemplate.query(getFIND_BY_TIME(duString, auString), new ActivitesRowMapper());
        List<Activites> nlistAct = joinListDatesAndLieu(listAct);
        return nlistAct;
    }

    private String getFIND_BY_DISTANCE_LOCATION(double lng, double lat, double rayon) {
        return " SELECT * FROM activites "
                + "INNER JOIN lieu on lieu.activitesid = activites.id "
                + "WHERE ST_DISTANCE_SPHERE(lieu.point, "
                + "ST_GeomFromText('POINT (" + lng + " " + lat + ")',4326)) < "
                + rayon;
    }

    public List<Activites> findByDistanceLocation(double lng, double lat, double rayon) {
        if (!validation.findByTimeValidation(lng, lat, rayon)) {
            return null;
        }
        List<Activites> listAct = jdbcTemplate.query(getFIND_BY_DISTANCE_LOCATION(lng, lat, rayon), new ActivitesRowMapper());
        listAct = joinListDatesAndLieu(listAct);
        return listAct;
    }

    private static final String CLEAR_STMT
            = " DELETE FROM activites";

    public int clear() {
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(CLEAR_STMT);
            return ps;
        });
    }

    private static final String INSERT_STMT
            = " INSERT INTO activites (id, nom, description, arrondissement)"
            + " VALUES (?, ?, ?, ? )"
            + " on conflict do nothing";

    public int insert(Activites activite) {
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(INSERT_STMT);
            ps.setInt(1, activite.getId());
            ps.setString(2, activite.getNom());
            ps.setString(3, activite.getDescription());
            ps.setString(4, activite.getArrondissement());
            return ps;
        });
    }

    private static final String getUPDATE_STMT(String nom, String description, String arrondissement, int id) {
        return " UPDATE activites "
                + " SET nom = '" + nom + "', "
                + "description = '" + description + "', "
                + "arrondissement = '" + arrondissement + "' "
                + " WHERE id = " + id;
    }

    public int update(Activites activite) {
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(getUPDATE_STMT(activite.getNom(), activite.getDescription(), activite.getArrondissement(), activite.getId()));
            return ps;
        });
    }

    public Activites createdEvent(int id, String nom, String description, String arrondissement, String nomLieu, double lng, double lat, String[] dates) {
        if (!validation.EventValidation(lng, lat, dates)) {
            return null;
        }
        Activites activite = new Activites(id, nom, description, arrondissement);
        ArrayList<Dates> listDate = new ArrayList<>();
        for (String datesString : dates) {
            listDate.add(new Dates(datesString));
        }
        activite.setDates(listDate);

        int newLieuID = IDMaker.createID();
        Lieu lieu = new Lieu(nomLieu, lat, lng);
        lieu.setId(newLieuID);
        lieu.setActivitesID(activite.getId());
        activite.setLieu(lieu);

        int numberRowCreated = insert(activite);
        if (numberRowCreated <= 0) {
            return null;
        }
        lieuRepository.insert(activite.getLieu());

        //set foreign DatesID
        for (Dates date : activite.getDates()) {
            int newDatesID = IDMaker.createID();
            date.setId(newDatesID);
            date.setActivitesID(activite.getId());
            datesRepository.insert(date);
        }

        return activite;
    }

    public Activites updatedEvent(int id, String nom, String description, String arrondissement, String nomLieu, Double lng, Double lat, String[] dates) {
        if (!validation.EventValidation(lng, lat, dates)) {
            return null;
        }
        Activites currentActivite = findById(id);
        ArrayList<Dates> newListDate = new ArrayList<>();

        if (nom != null) {
            currentActivite.setNom(nom);
            update(currentActivite);
        }
        if (description != null) {
            currentActivite.setDescription(description);
            update(currentActivite);
        }
        if (arrondissement != null) {
            currentActivite.setArrondissement(arrondissement);
            update(currentActivite);
        }
        if (nomLieu != null) {
            currentActivite.getLieu().setNom(nomLieu);
            lieuRepository.update(currentActivite.getLieu());
        }
        if (lng != null) {
            currentActivite.getLieu().setLng(lng);
            lieuRepository.update(currentActivite.getLieu());
        }
        if (lat != null) {
            currentActivite.getLieu().setLat(lat);
            lieuRepository.update(currentActivite.getLieu());
        }
        if (dates != null) {
            for (String dateString : dates) {
                int newDatesID = IDMaker.createID();
                newListDate.add(new Dates(newDatesID, currentActivite.getId(), dateString));
                datesRepository.deleteByActivitesID(currentActivite.getId());
                currentActivite.setDates(newListDate);
            }
            for (Dates date : newListDate) {
                datesRepository.insert(date);
            }
        }
        return currentActivite;
    }

    private static final String DELETE_BY_ACTIVITES_ID_STMT
            = " DELETE FROM activites "
            + "WHERE id = ?";

    public int deleteEvent(int id) {
        datesRepository.deleteByActivitesID(id);
        lieuRepository.deleteByActivitesID(id);
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(DELETE_BY_ACTIVITES_ID_STMT);
            ps.setInt(1, id);
            return ps;
        });
    }

    public int deleteByActivitesID(int id) {
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(DELETE_BY_ACTIVITES_ID_STMT);
            ps.setInt(1, id);
            return ps;
        });
    }

    public Activites joinDatesAndLieu(Activites activite) {
        activite.setDates((ArrayList) datesRepository.findByActivitesId(activite.getId()));
        Lieu newLieu = lieuRepository.findByActivitesId(activite.getId());
        if(newLieu!=null){
        activite.setLieu(newLieu);
        }
        return activite;
    }

    public List<Activites> joinListDatesAndLieu(List<Activites> listActivites) {
        for (Activites activite : listActivites) {
            joinDatesAndLieu(activite);
        }
        return listActivites;
    }

}

class ActivitesRowMapper implements RowMapper<Activites> {

    public Activites mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Activites(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getString("description"),
                rs.getString("arrondissement")
        );
    }

}
