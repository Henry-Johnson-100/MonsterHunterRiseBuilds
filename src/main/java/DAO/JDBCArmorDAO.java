package DAO;

import Model.Armor;
import Model.Skill;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.*;

public class JDBCArmorDAO implements ArmorDAO {
    private final JdbcTemplate jdbcTemplate;

    public JDBCArmorDAO(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Armor getArmorFromId(Long armorId) {
        String sql = "SELECT * FROM armor ar JOIN has_piece hp ON ar.armor_id = hp.armor_id WHERE ar.armor_id = ?;";
        SqlRowSet result = this.jdbcTemplate.queryForRowSet(sql, armorId);
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
        sql.append(") AS oar JOIN armor ar ON oar.armor_id = ar.armor_id");
        boolean anyExclude = false;
        StringBuilder whereSubBuilder = new StringBuilder(" WHERE oar.armor_piece_type NOT IN (");
        List<String> pieceTypeToSplit = new ArrayList<>();
        for (int i = 0; i < excludePieceTypes.length; i++) {
            if (excludePieceTypes[i].length() == 0 || excludePieceTypes[i].isEmpty()) {
                continue;
            } else {
                pieceTypeToSplit.add("'" + excludePieceTypes[i] + "'");
                anyExclude = true;
            }
        }
        if (anyExclude) {
            whereSubBuilder.append(String.join(",",pieceTypeToSplit));
            sql.append(whereSubBuilder);
            sql.append(");");
        } else {
            sql.append(";");
        }
        return sql.toString();
    }

    private SqlRowSet optimalArmorVarArgs(String sql, List<Skill> searchSkills) { //makes me want to throw up
        return switch (searchSkills.size()) {
            case 1 -> this.jdbcTemplate.queryForRowSet(sql, searchSkills.get(0).getSkillName());
            case 2 -> this.jdbcTemplate.queryForRowSet(sql, searchSkills.get(0).getSkillName(), searchSkills.get(1).getSkillName());
            case 3 -> this.jdbcTemplate.queryForRowSet(sql, searchSkills.get(0).getSkillName(), searchSkills.get(1).getSkillName(), searchSkills.get(2).getSkillName());
            case 4 -> this.jdbcTemplate.queryForRowSet(sql, searchSkills.get(0).getSkillName(), searchSkills.get(1).getSkillName(), searchSkills.get(2).getSkillName(),
                    searchSkills.get(3).getSkillName());
            case 5 -> this.jdbcTemplate.queryForRowSet(sql, searchSkills.get(0).getSkillName(), searchSkills.get(1).getSkillName(), searchSkills.get(2).getSkillName(),
                    searchSkills.get(3).getSkillName(), searchSkills.get(4).getSkillName());
            case 6 -> this.jdbcTemplate.queryForRowSet(sql, searchSkills.get(0).getSkillName(), searchSkills.get(1).getSkillName(), searchSkills.get(2).getSkillName(),
                    searchSkills.get(3).getSkillName(), searchSkills.get(4).getSkillName(), searchSkills.get(5).getSkillName());
            case 7 -> this.jdbcTemplate.queryForRowSet(sql, searchSkills.get(0).getSkillName(), searchSkills.get(1).getSkillName(), searchSkills.get(2).getSkillName(),
                    searchSkills.get(3).getSkillName(), searchSkills.get(4).getSkillName(), searchSkills.get(5).getSkillName(), searchSkills.get(6).getSkillName());
            default -> this.jdbcTemplate.queryForRowSet("SELECT NULL"); //TODO think of something better to put here
        };
    }

    @Override
    public Armor getOptimalArmorFromSkills(List<Skill> searchSkills, String[] excludePieceTypes) {//TODO ensure this method is working
        String sql = getOptimalArmorQuery(searchSkills, excludePieceTypes);
        SqlRowSet result = optimalArmorVarArgs(sql, searchSkills);
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
