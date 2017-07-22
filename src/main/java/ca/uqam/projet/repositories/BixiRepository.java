package ca.uqam.projet.repositories;

import ca.uqam.projet.resources.*;
import java.sql.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

@Component
public class BixiRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String FIND_ALL_STMT
            = " SELECT"
            + " * "
            + " FROM"
            + " bixi";

    public List<Bixi> findAll() {
        return jdbcTemplate.query(FIND_ALL_STMT, new BixiRowMapper());
    }

    private static final String FIND_BY_ID_STMT
            = " SELECT"
            + " * "
            + " FROM"
            + " bixi"
            + " WHERE "
            + " id = ?";

    public Bixi findById(int id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_STMT, new Object[]{id}, new BixiRowMapper());
    }

    private String getINSERT_STMT(Bixi bixi) {
        return " INSERT INTO bixi ( id, nom, identifiantTerminale, etat, "
                + "bloquee, suspendue, horService, lu, lc, "
                + "bk, bl, latitude, longitude, nombreBornesDisponible, "
                + "nombreBornesIndisponible, nombreVelosDisponible, "
                + "nombreVelosIndisponible, point )"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,"
                + "ST_GeomFromText('POINT(" + bixi.getLo() + " " + bixi.getLa() + ")', 4326))"
                + " ON CONFLICT DO NOTHING";
    }

    public int insert(Bixi bixi) {
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(getINSERT_STMT(bixi));
            ps.setInt(1, bixi.getId());
            ps.setString(2, bixi.getS());
            ps.setInt(3, bixi.getN());
            ps.setInt(4, bixi.getSt());
            ps.setBoolean(5, bixi.isB());
            ps.setBoolean(6, bixi.isSu());
            ps.setBoolean(7, bixi.isM());
            ps.setLong(8, bixi.getLu());
            ps.setLong(9, bixi.getLc());
            ps.setBoolean(10, bixi.isBk());
            ps.setBoolean(11, bixi.isBl());
            ps.setDouble(12, bixi.getLa());
            ps.setDouble(13, bixi.getLo());
            ps.setInt(14, bixi.getDa());
            ps.setInt(15, bixi.getDx());
            ps.setInt(16, bixi.getBa());
            ps.setInt(17, bixi.getBx());
            return ps;
        });
    }

    private String getFIND_BY_DISTANCE_LOCATION_NUMBER_BIXI_STMT(double lng, double lat, double rayon, int numberBixiAvailable) {
        return " SELECT * FROM bixi "
                + " WHERE nombreVelosDisponible >= " + numberBixiAvailable
                + " AND ST_DISTANCE_SPHERE( point, "
                + " ST_GeomFromText('POINT (" + lng + " " + lat + ")',4326)) <= "
                + rayon;
    }

    public List<Bixi> findByDistanceLocationAndNumberBixi(double lat, double lng, double rayon, int numberBixiAvailable) {
        List<Bixi> listBixiStation = jdbcTemplate.query(getFIND_BY_DISTANCE_LOCATION_NUMBER_BIXI_STMT(lat, lng, rayon, numberBixiAvailable), new BixiRowMapper());
        return listBixiStation;

    }
    private static final String CLEAR_STMT
            = " DELETE FROM bixi";

    public int clear() {
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(CLEAR_STMT);
            return ps;
        });
    }

}

class BixiRowMapper implements RowMapper<Bixi> {

    public Bixi mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Bixi(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getInt("identifiantTerminale"),
                rs.getInt("etat"),
                rs.getBoolean("bloquee"),
                rs.getBoolean("suspendue"),
                rs.getBoolean("horService"),
                rs.getLong("lu"),
                rs.getLong("lc"),
                rs.getBoolean("bk"),
                rs.getBoolean("bl"),
                rs.getDouble("latitude"),
                rs.getDouble("longitude"),
                rs.getInt("nombreBornesDisponible"),
                rs.getInt("nombreBornesIndisponible"),
                rs.getInt("nombreVelosDisponible"),
                rs.getInt("nombreVelosIndisponible")
        );
    }

}
