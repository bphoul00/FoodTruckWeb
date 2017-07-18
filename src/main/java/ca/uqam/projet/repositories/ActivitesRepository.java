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
public class ActivitesRepository {

  @Autowired private JdbcTemplate jdbcTemplate;

  private static final String FIND_ALL_STMT =
      " select"
    + "     *"
    + " from"
    + "   activites"
    ;

  public List<Activites> findAll() {
    return jdbcTemplate.query(FIND_ALL_STMT, new ActivitesRowMapper());
  }

  private static final String FIND_BY_ID_STMT =
      " select"
    + "     *"
    + " from"
    + "   activites"
    + " where"
    + "   id = ?"
    ;

  public Activites findById(int id) {
    return jdbcTemplate.queryForObject(FIND_BY_ID_STMT, new Object[]{id}, new ActivitesRowMapper());
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
    return jdbcTemplate.query(FIND_BY_CONTENU_STMT, new Object[]{tsquery}, new ActivitesRowMapper());
  }
*/
  private static final String INSERT_STMT =
      " insert into activites (id, nom, description, arrondissement)"
    + " values (?, ?, ?, ?)"
    + " on conflict do nothing"
    ;
  
  /*
    private static final String INSERT_STMT =
      " insert into activites (id, nom, description, arrondissement, dates, lieuNom, lag, lng)"
    + " values (?, ?, ?, ?, ?, ?, ?)"
    + " on conflict do nothing"
    ;
  */

  public int insert(Activites activite) {
    return jdbcTemplate.update(conn -> {
      PreparedStatement ps = conn.prepareStatement(INSERT_STMT);
      ps.setInt(1, activite.getId());
      ps.setString(2, activite.getNom());
      ps.setString(3, activite.getDescription());
      ps.setString(4, activite.getArrondissement());
      //ps.setString(5, activite.getDates());
     // ps.setString(6, activite.getLieuNom());
     // ps.setInt(7, activite.getLag());
     // ps.setInt(8, activite.getLng());
      return ps;
    });
  }
  
}

class ActivitesRowMapper implements RowMapper<Activites> {
  public Activites mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new Activites(
        rs.getInt("id")
      , rs.getString("nom")
      , rs.getString("description")
      , rs.getString("arrondissement")
      //, rs.getString("dates")
      //, rs.getString("lieuNom")
     // , rs.getInt("lag")
      //, rs.getInt("lng")
    );
  }
  
}
