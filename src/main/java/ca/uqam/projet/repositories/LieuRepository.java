package ca.uqam.projet.repositories;

import ca.uqam.projet.resources.*;
import java.sql.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

@Component
public class LieuRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private static final String FIND_BY_ID_STMT
            = " select"
            + "     *"
            + " from"
            + "   lieu"
            + " where"
            + "   id = ?";

    /**
     * Chercher un Lieu avec id du BD et retourne le Lieu
     * @param id
     * @return Lieu
     */
    public Lieu findById(int id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_STMT, new Object[]{id}, new LieuRowMapper());
    }

    private static final String FIND_BY_ACTIVITES_ID_STMT
            = " SELECT"
            + " nom, ST_X(point), ST_Y(point) , id, activitesid"
            + " FROM"
            + " lieu "
            + " WHERE "
            + " activitesID = ?";

    /**
     * Chercher un Lieu qui a un relation avec un specifique activite dans BD et retourne le Lieu
     * @param id
     * @return Lieu
     */
    public Lieu findByActivitesId(int id) {
        return jdbcTemplate.queryForObject(FIND_BY_ACTIVITES_ID_STMT, new Object[]{id}, new LieuRowMapper());
    }

    private String getINSERT_STMT(Lieu lieu) {
        return " insert into lieu (id ,activitesID , nom, point)"
                + " values (?, ?, ?, ST_GeomFromText('POINT(" + lieu.getLng() + " " + lieu.getLat() + ")', 4326) )"
                + " on conflict do nothing";
    }

    /**
     * Create un nouvelle lieu dans la BD
     * @param lieu
     * @return nombre de changement performer
     */
    public int insert(Lieu lieu) {
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(getINSERT_STMT(lieu));
            ps.setInt(1, lieu.getId());
            ps.setInt(2, lieu.getActivitesID());
            ps.setString(3, lieu.getNom());
            return ps;
        });
    }

    private String getUPDATE_STMT(Lieu lieu) {
        return " UPDATE lieu "
                + " SET nom = ? , "
                + " point = ST_GeomFromText('POINT(" + lieu.getLng() + " " + lieu.getLat() + ")', 4326) "
                + " WHERE id = ?"
                + " AND activitesID = ?";
    }

    /**
     * Mise a jouer un lieu dans la BD
     * @param lieu
     * @return nombre de changement performer
     */
    public int update(Lieu lieu) {
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(getUPDATE_STMT(lieu));
            ps.setString(1, lieu.getNom());
            ps.setInt(2, lieu.getId());
            ps.setInt(3, lieu.getActivitesID());
            return ps;
        });
    }

    private static final String CLEAR_STMT
            = " delete from lieu";

    /**
     *
     * @return nombre de changement performer
     */
    public int clear() {
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(CLEAR_STMT);
            return ps;
        });
    }

    private static final String DELETE_BY_ACTIVITES_ID_STMT
            = " DELETE FROM lieu "
            + "WHERE activitesID = ?";

    /**
     * Vider le BD tableau Date
     * @param id
     * @return nombre de changement performer
     */
    public int deleteByActivitesID(int id) {
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(DELETE_BY_ACTIVITES_ID_STMT);
            ps.setInt(1, id);
            return ps;
        });
    }

}

class LieuRowMapper implements RowMapper<Lieu> {

      /**
     * Transforme le lieu du BD in java object date
     * @param rs
     * @return java object Dates
     */
    public Lieu mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Lieu(
                rs.getString("nom"),
                rs.getDouble("st_x"),
                rs.getDouble("st_y"),
                rs.getInt("id"),
                rs.getInt("activitesID"));
    }

}
