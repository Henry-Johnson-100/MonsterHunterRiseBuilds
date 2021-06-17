package DAO;

import Model.Armor;
import Model.Skill;
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
        String sql = "SELECT * FROM armor ar JOIN has_piece hp ON ar.armor_id = hp.armor_id WHERE ar.armor_id = ?;";
        SqlRowSet result = this.jdbcTemplate.queryForRowSet(sql,armorId);
        if (result.next()) {
            return mapRowToArmor(result);
        }
        return null;
    }

    @Override
    public Armor getArmorFromName(String armorName) {
        String sql = "SELECT * JOIN has_piece hp ON ar.armor_id = hp.armor_id WHERE ar.armor_name LIKE ?;";
        SqlRowSet result = this.jdbcTemplate.queryForRowSet(sql, "%" + armorName + "%");
        if (result.next()) {
            return mapRowToArmor(result);
        }
        return null;
    }

    private String getOptimalArmorQuery(List<Skill> searchSkills, String[] excludePieceTypes) {
        StringBuilder sql = new StringBuilder("SELECT oar.armor_id, ar.armor_name FROM optimal_armor(");
        for (int i = 0; i < searchSkills.size(); i++) {
            if (i == searchSkills.size() - 1) {
                sql.append("?");
                continue; //should break out of the loop anyways
            }
            sql.append("?,");
        }
        sql.append(") AS oar JOIN armor ar ON aor.armor_id = ar.armor_id WHERE oar.armor_piece_type NOT IN (");
        for (int i = 0; i < excludePieceTypes.length; i++) {
            sql.append(excludePieceTypes[i]);
            if (i < excludePieceTypes.length - 1) {
                sql.append(",");
            }
        }
        sql.append(");");
        return sql.toString();
    }

    @Override
    public Armor getOptimalArmorFromSkills(String[] excludePieceTypes, Skill... searchSkills) {//TODO ensure this method is working
        String sql = getOptimalArmorQuery(Arrays.asList(searchSkills),excludePieceTypes);
        SqlRowSet result = this.jdbcTemplate.queryForRowSet(sql, searchSkills); //TODO fix this to appropriately parameterize searchSkills
        if (result.next()) {
            return getArmorFromId(result.getLong("armor_id"));
        }
        return null; //two queries here, not sure about this, may want change it later
    }

    private Armor mapRowToArmor(SqlRowSet row) { //will have to add support to get skills somewhere here or elsewhere
        List<Integer> tempDecoSlots = Arrays.asList(
                row.getInt("lv1_slots"),
                row.getInt("lv2_slots"),
                row.getInt("lv3_slots")
        );
        List<Integer> tempResistances = Arrays.asList(
                row.getInt("fire_res"),
                row.getInt("water_res"),
                row.getInt("thunder_res"),
                row.getInt("ice_res"),
                row.getInt("dragon_res")
        );
        return new Armor(
                row.getLong("armor_id"),
                row.getString("armor_name"),
                row.getString("piece_type"),
                row.getInt("base_defense"),
                tempDecoSlots,
                tempResistances
        );
    }
}
