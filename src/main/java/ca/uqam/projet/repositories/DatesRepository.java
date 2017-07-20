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
public class DatesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String FIND_ALL_STMT
            = " select"
            + "     *"
            + " from"
            + "   dates";

    public List<Dates> findAll() {
        return jdbcTemplate.query(FIND_ALL_STMT, new DatesRowMapper());
    }

    private static final String FIND_BY_ID_STMT
            = " select"
            + "     *"
            + " from"
            + "   dates"
            + " where"
            + "   id = ?";

    public Dates findById(int id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_STMT, new Object[]{id}, new DatesRowMapper());
    }


        private String getINSERT_STMT(Dates dates){
        return " insert into dates (id, activitesID, dates, schedule)"
            + " values (?, ?, ?, (DATE '"+dates.getMonth()+"-"+dates.getMonth()+"-"+dates.getDay()+"' ) )"
            + " on conflict do nothing";
    }
    
    public int insert(Dates dates) {
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(getINSERT_STMT(dates));
            ps.setInt(1, dates.getId());
            ps.setInt(2, dates.getActivitesID());
            ps.setString(3, dates.getDates());
            return ps;
        });
    }

    private static final String CLEAR_STMT
            = " delete from dates";

    public int clear() {
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(CLEAR_STMT);
            return ps;
        });
    }

}

class DatesRowMapper implements RowMapper<Dates> {

    public Dates mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Dates(
                rs.getInt("id"),
                rs.getInt("activitesID"),
                rs.getString("dates")
        );
    }

}
