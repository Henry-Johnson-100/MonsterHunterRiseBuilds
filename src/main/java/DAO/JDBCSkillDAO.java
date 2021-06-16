package DAO;

import Model.Skill;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.List;

public class JDBCSkillDAO implements SkillDAO{

    private JdbcTemplate jdbcTemplate;

    public JDBCSkillDAO (DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Skill getSkillFromId(Long skillId) {
        String sql = "SELECT * FROM skill WHERE skill_id = ?;";
        SqlRowSet result = this.jdbcTemplate.queryForRowSet(sql,skillId);
        if (result.next()) {
            return mapRowToSkill(result);
        }
        return null;
    }

    @Override
    public Skill getSkillFromName(String skillName) {
        String sql = "SELECT * FROM skill WHERE skill_name LIKE ?;";
        SqlRowSet result = this.jdbcTemplate.queryForRowSet(sql, "%" + skillName + "%");
        if (result.next()) {
            return mapRowToSkill(result);
        }
        return null;
    }

    @Override
    public List<Skill> getSkillsFromArmorId(Long armorId) {
        return null;
    }

    @Override
    public List<Skill> getSkillsFromArmorName(String armorName) {
        return null;
    }

    public Skill mapRowToSkill(SqlRowSet row) {
        return new Skill(row.getLong("skill_id"),row.getString("skill_name"),row.getString("skill_description"),row.getInt("max_level"));
    }
}
