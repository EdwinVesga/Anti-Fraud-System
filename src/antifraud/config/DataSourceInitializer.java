package antifraud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataSourceInitializer implements CommandLineRunner {
    
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DataSourceInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {

        //jdbcTemplate.execute("DROP TABLE user_detail IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS user_detail(" +
                " id int not null auto_increment," +
                " name varchar(255) not null," +
                " username varchar(255) not null unique," +
                " password varchar(255) not null," +
                " primary key(id)" +
                ")");


        //jdbcTemplate.update("INSERT INTO user_detail (name, username, password) VALUES (?,?,?)", new Object[]{"ed", "user", "test1"});
    }
}
