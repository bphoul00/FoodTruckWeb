package ca.uqam.projet.repositories;

import ca.uqam.projet.resources.*;
import java.sql.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;


@Component
public class DatesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String FIND_BY_ID_STMT
            = " select"
            + "     *"
            + " from"
            + "   dates"
            + " where"
            + "   id = ?";

    /**
     * Chercher un Date avec id du BD et retourne le Date
     * @param id
     * @return Date
     */
    public Dates findById(int id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_STMT, new Object[]{id}, new DatesRowMapper());
    }

    private String getFIND_BY_ACTIVITES_ID_STMT(int id) {
        return " select"
                + "     *"
                + " from"
                + "   dates"
                + " where"
                + "   activitesID = " + id;
    }

    /**
     * Chercher un Date qui a un relation avec un specifique activite dans BD et retourne la liste de Date
     * @param id
     * @return liste de Date
     */
    public List<Dates> findByActivitesId(int id) {
        return jdbcTemplate.query(getFIND_BY_ACTIVITES_ID_STMT(id), new DatesRowMapper());
    }

    private String getINSERT_STMT(Dates dates) {
        return " insert into dates (id, activitesID, dates, schedule)"
                + " values (?, ?, ?, (DATE '" + dates.getMonth() + "-" + dates.getMonth() + "-" + dates.getDay() + "' ) )"
                + " on conflict do nothing";
    }

    /**
     * Create un nouvelle Date dans la BD
     * @param dates
     * @return nombre de changement performer
     */
    public int insert(Dates dates) {
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(getINSERT_STMT(dates));
            ps.setInt(1, dates.getId());
            ps.setInt(2, dates.getActivitesID());
            ps.setString(3, dates.getDates());
            return ps;
        });
    }

    private String getUPDATE_STMT(Dates dates) {
        return " UPDATE dates "
                + " SET dates = ?, "
                + " schedule = (DATE '" + dates.getMonth() + "-" + dates.getMonth() + "-" + dates.getDay() + "' ) )"
                + " WHERE id = ? "
                + " AND activitesID = ? ";
    }

    /**
     * Mise a jouer un Date dans la BD
     * @param dates
     * @return nombre de changement performer
     */
    public int update(Dates dates) {
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(getUPDATE_STMT(dates));
            ps.setString(1, dates.getDates());
            ps.setInt(2, dates.getId());
            ps.setInt(3, dates.getActivitesID());
            return ps;
        });
    }

    private static final String CLEAR_STMT
            = " DELETE from dates";

    /**
     * Vider le BD tableau Date
     * @return  nombre de changement performer
     */
    public int clear() {
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(CLEAR_STMT);
            return ps;
        });
    }

    private static final String DELETE_BY_ACTIVITES_ID_STMT
            = " DELETE FROM dates "
            + "WHERE activitesID = ?";

    /**
     * Supprimer un Date avec id du BD.
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

class DatesRowMapper implements RowMapper<Dates> {

          /**
     * Transforme le date du BD in java object date
     * @param rs
     * @return java object Dates
     */
    public Dates mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Dates(
                rs.getInt("id"),
                rs.getInt("activitesID"),
                rs.getString("dates")
        );
    }

}
