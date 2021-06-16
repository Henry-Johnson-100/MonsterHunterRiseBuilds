package DAO;

import Model.Decoration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;

public class JDBCDecorationDAO implements DecorationDAO {
    private final JdbcTemplate jdbcTemplate;

    public JDBCDecorationDAO(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Decoration getDecorationFromId(Long decoId) {
        String sql = "SELECT * FROM decoration WHERE deco_id = ?;";
        SqlRowSet result = this.jdbcTemplate.queryForRowSet(sql,decoId);
        if (result.next()) {
            return mapRowToDecoration(result);
        }
        return null;
    }

    @Override
    public Decoration getDecorationFromDecoName(String decoName) {
        String sql = "SELECT * FROM decoration WHERE deco_name LIKE ?;";
        SqlRowSet result = this.jdbcTemplate.queryForRowSet(sql,"%" + decoName + "%");
        if (result.next()) {
            return mapRowToDecoration(result);
        }
        return null;
    }

    @Override
    public Decoration getDecorationFromSkillName(String skillName) {
        String sql = "SELECT d.* FROM decoration d JOIN skill s " +
                "ON d.skill_id = s.skill_id WHERE s.skill_name = ?;";
        SqlRowSet result = this.jdbcTemplate.queryForRowSet(sql,skillName);
        if (result.next()) {
            return mapRowToDecoration(result);
        }
        return null;
    }

    private Decoration mapRowToDecoration(SqlRowSet row) {
        return new Decoration(row.getLong("deco_id"),row.getLong("skill_id"),row.getString("deco_name"),row.getInt("slot_level"));
    }
}
