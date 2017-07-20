package ca.uqam.projet.repositories;

import java.util.*;
import java.util.stream.*;
import java.sql.*;

import ca.uqam.projet.resources.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import org.springframework.stereotype.*;

@Component
public class LieuRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String FIND_ALL_STMT
            = " select"
            + "     *"
            + " from"
            + "   lieu";

    public List<Lieu> findAll() {
        return jdbcTemplate.query(FIND_ALL_STMT, new LieuRowMapper());
    }

    private static final String FIND_BY_ID_STMT
            = " select"
            + "     *"
            + " from"
            + "   lieu"
            + " where"
            + "   id = ?";

    public Lieu findById(int id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_STMT, new Object[]{id}, new LieuRowMapper());
    }


    private String getINSERT_STMT(Lieu lieu){
        return " insert into lieu (id ,activitesID , nom, point)"
            + " values (?, ?, ?, ST_GeomFromText('POINT("+ lieu.getLng()+" "+lieu.getLat()  + ")', 4326) )"
            + " on conflict do nothing";
    }

    public int insert(Lieu lieu) {
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(getINSERT_STMT(lieu));
            ps.setInt(1, lieu.getId());
            ps.setInt(2, lieu.getActivitesID());
            ps.setString(3, lieu.getNom());
            return ps;
        });
    }

    private static final String CLEAR_STMT
            = " delete from lieu";

    public int clear() {
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(CLEAR_STMT);
            return ps;
        });
    }

}

class LieuRowMapper implements RowMapper<Lieu> {

    public Lieu mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Lieu(
                rs.getString("nom"),
                rs.getDouble("lat"),
                rs.getDouble("lng"));
    }

}
