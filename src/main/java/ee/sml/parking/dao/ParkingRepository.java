package ee.sml.parking.dao;

import ee.sml.parking.model.Parking;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class ParkingRepository implements DAO<Parking> {
    private JdbcTemplate jdbcTemplate;

    public ParkingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Parking> rowMapper = (rs, i) -> {
        Parking Parking = new Parking();
        Parking.setId(rs.getLong("id"));
        Parking.setStart(rs.getObject("start_ts", Instant.class));
        Parking.setEnd(rs.getObject("end_ts", Instant.class));
        Parking.setCustomerId(rs.getString("customer_id"));
        Parking.setPropertyId(rs.getString("property_id"));

        return Parking;
    };
    
    @Override
    public List<Parking> list() {
        String sql = "SELECT id, start_ts, end_ts, customer_id, property_id FROM parking";
        System.out.println("LIST");

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void create(Parking parking) {
        String sql = "INSERT INTO parking(start_ts, end_ts, customer_id, property_id) VALUES(?, ?, ?, ?)";
        int updateStatus = jdbcTemplate.update(sql, parking.getStart(), parking.getEnd(), parking.getCustomerId(), parking.getPropertyId());
        if (updateStatus == 1) {
            System.out.print(parking);
            System.out.println(parking.getBillableUnits());
        }
    }

    @Override
    public Optional<Parking> get(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Parking parking, int id) {

    }

    @Override
    public void delete(int id) {

    }
}
