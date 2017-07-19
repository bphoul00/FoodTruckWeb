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
            = " insert into lieu (lieuid, nom, lat, lng)"
            + " values (?, ?, ?, ? )"
            + " on conflict do nothing";

    public int insert(Lieu lieu) {
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(INSERT_STMT);
            ps.setInt(1, lieu.getId());
            ps.setString(2, lieu.getNom());
            ps.setDouble(3, lieu.getLat());
            ps.setDouble(4, lieu.getLng());
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
