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
    /*
  private static final String FIND_BY_CONTENU_STMT =
      " select"
    + "     id"
    + "   , ts_headline(contenu, q, 'HighlightAll = true') as contenu"
    + "   , auteur"
    + " from"
    + "     citations"
    + "   , to_tsquery(?) as q"
    + " where"
    + "   contenu @@ q"
    + " order by"
    + "   ts_rank_cd(to_tsvector(contenu), q) desc"
    ;

  public List<Citation> findByContenu(String... tsterms) {
    String tsquery = Arrays.stream(tsterms).collect(Collectors.joining(" & "));
    return jdbcTemplate.query(FIND_BY_CONTENU_STMT, new Object[]{tsquery}, new LieuRowMapper());
  }
     */
    private static final String INSERT_STMT
            = " insert into dates (id, dates)"
            + " values (?, ?)"
            + " on conflict do nothing";

    public int insert(Dates dates) {
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(INSERT_STMT);
            ps.setInt(1, dates.getId());
            ps.setString(2, dates.getDates());
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
                rs.getInt("datesid"),
                rs.getString("dates")
        );
    }

}
