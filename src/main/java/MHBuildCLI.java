import DAO.*;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class MHBuildCLI {

    private final ArmorDAO armorDAO;
    private final SkillDAO skillDAO;
    private final DecorationDAO decoDAO;


    public static void main(String[] args) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/Gear");
        dataSource.setUsername(System.getenv("DB_USERNAME"));
        dataSource.setPassword(System.getenv("DB_PASSWORD"));
        MHBuildCLI test = new MHBuildCLI(dataSource);

    }

    public MHBuildCLI(DataSource ds) {
        armorDAO = new JDBCArmorDAO(ds);
        skillDAO = new JDBCSkillDAO(ds);
        decoDAO = new JDBCDecorationDAO(ds);
    }

}
