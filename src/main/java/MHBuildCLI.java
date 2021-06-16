import org.apache.commons.dbcp2.BasicDataSource;

public class MHBuildCLI {
    public static void main(String[] args) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/Gear");
        dataSource.setUsername(System.getenv("DB_USERNAME"));
        dataSource.setPassword(System.getenv("DB_PASSWORD"));
    }
}
