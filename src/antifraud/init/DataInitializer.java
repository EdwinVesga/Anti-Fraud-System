package antifraud.init;

import antifraud.constant.UserRoleType;
import antifraud.entity.UserRole;
import antifraud.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private UserRoleRepository userRoleRepository;

    @Autowired
    public DataInitializer(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        UserRole administrator = new UserRole();
        administrator.setName(UserRoleType.ROLE_ADMINISTRATOR);
        UserRole merchant = new UserRole();
        merchant.setName(UserRoleType.ROLE_MERCHANT);
        UserRole support = new UserRole();
        support.setName(UserRoleType.ROLE_SUPPORT);

        userRoleRepository.saveAll(List.of(administrator, merchant, support));
    }
}
