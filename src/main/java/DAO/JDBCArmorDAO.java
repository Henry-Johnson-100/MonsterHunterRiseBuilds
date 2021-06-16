package DAO;

import Model.Armor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JDBCArmorDAO implements ArmorDAO {
    private final JdbcTemplate jdbcTemplate;

    public JDBCArmorDAO(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Armor getArmorFromId(Long armorId) {
        String sql = "SELECT * FROM armor WHERE armor_id = ?;";
        SqlRowSet result = this.jdbcTemplate.queryForRowSet(sql,armorId);
        if (result.next()) {
            return mapRowToArmor(result);
        }
        return null;
    }

    @Override
    public Armor getArmorFromName(String armorName) {
        String sql = "SELECT * FROM armor WHERE armor_name LIKE ?;";
        SqlRowSet result = this.jdbcTemplate.queryForRowSet(sql, "%" + armorName + "%");
        if (result.next()) {
            return mapRowToArmor(result);
        }
        return null;
    }

    private Armor mapRowToArmor(SqlRowSet row) { //will have to add support to get skills somewhere here or elsewhere
        List<Integer> tempDecoSlots = Arrays.asList(row.getInt("lv1_slots"),row.getInt("lv2_slots"),row.getInt("lv3_slots"));
        List<Integer> tempResistances = Arrays.asList(row.getInt("fire_res"),row.getInt("water_res"),row.getInt("thunder_res"),row.getInt("ice_res"),row.getInt("dragon_res"));
        return new Armor(row.getLong("armor_id"),row.getString("armor_name"),row.getInt("base_defense"),tempDecoSlots,tempResistances);
    }
}
