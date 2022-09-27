package antifraud.security.service;

import antifraud.entity.UserDetail;
import antifraud.repository.UserDetailRepository;
import antifraud.security.model.AuthUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailRepository userRepository;


    @Autowired
    public UserDetailsServiceImpl(UserDetailRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDetail> userDetailOptional = userRepository.findByUsername(username);

        UserDetail userDetail = userDetailOptional.orElseThrow(() -> new UsernameNotFoundException("User not found."));

        return new AuthUserDetail(userDetail);
    }
}
