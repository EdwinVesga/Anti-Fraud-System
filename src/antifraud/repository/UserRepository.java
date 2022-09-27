package antifraud.repository;

import antifraud.entity.User;
import antifraud.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;

@Repository
public class UserRepository {

    private static String SELECT_USER_BY_USERNAME = "SELECT * FROM user_detail WHERE username = LOWER(?)";

    private static String INSERT_USER = "INSERT INTO user_detail(name, username, password) VALUES (?,LOWER(?),?)";

    private static String DELETE_USER = "DELETE FROM user_detail WHERE username = LOWER(?)";

    private static String FIND_ALL_USERS = "SELECT * FROM user_detail";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getUserByUsername(String username) {
        return jdbcTemplate.queryForObject(
                SELECT_USER_BY_USERNAME,
                new UserMapper(),
                new Object[] {username.toLowerCase(Locale.ROOT)}
        );
    }

    public int deleteUser(String username) {
        return jdbcTemplate.update(DELETE_USER, username);
    }

    public int addUser(User user) {
        return jdbcTemplate.update(INSERT_USER, user.getName(), user.getUsername(), user.getPassword());
    }

    public List<User> getAllUsers() {
        return jdbcTemplate.query(FIND_ALL_USERS, new UserMapper());
    }
}
