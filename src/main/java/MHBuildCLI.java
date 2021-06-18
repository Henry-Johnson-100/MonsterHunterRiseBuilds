import DAO.*;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;

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

        //I will be shocked if this works
        SetBuilder testBuilder = new SetBuilder(armorDAO,skillDAO,decoDAO,Arrays.asList("Rapid Morph","Attack Boost","Evade Extender","Evade Window"));
        //This causes an overlevel TODO fix this
        //new ArrayList<String>(Arrays.asList("Rapid Morph","Attack Boost","Evade Extender","Evade Window")));
        //testBuilder.generateArmorSet();
        //System.out.println(testBuilder.toString());

    }

}
