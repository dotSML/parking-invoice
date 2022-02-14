package ee.sml.parking.dao;

import ee.sml.parking.model.Customer;
import ee.sml.parking.model.CustomerTier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerRepository implements DAO<Customer> {
    private JdbcTemplate jdbcTemplate;

    RowMapper<Customer> rowMapper = (rs, i) -> {
        Customer customer = new Customer();
        customer.setId(rs.getLong("id"));
        customer.setFirstName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setEmail(rs.getString("email"));
        customer.setTier(CustomerTier.valueOf(rs.getString("tier")));

        return customer;
    };

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> list() {
        String sql = "SELECT id, first_name, last_name, email, tier FROM customer";

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void create(Customer customer) {
        String sql = "INSERT INTO customer(first_name, last_name, email, tier) VALUES(?, ?, ?, ?)";
        int updateStatus = jdbcTemplate.update(sql, customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getTier().name());
        if (updateStatus == 1) {
            System.out.print(customer);
        }
    }

    @Override
    public Optional<Customer> get(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Customer customer, int id) {

    }

    @Override
    public void delete(int id) {

    }
}
