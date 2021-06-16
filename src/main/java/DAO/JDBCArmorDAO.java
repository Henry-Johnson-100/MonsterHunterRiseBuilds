package DAO;

import Model.Armor;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JDBCArmorDAO implements ArmorDAO {
    private final JdbcTemplate jdbcTemplate;

    public JDBCArmorDAO(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Armor getArmorFromId(Long armor_id) {
        return null;
    }

    @Override
    public Armor getArmorFromName(String armor_name) {
        return null;
    }
}
