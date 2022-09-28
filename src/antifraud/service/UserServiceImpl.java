package antifraud.service;

import antifraud.constant.UserRoleType;
import antifraud.dto.DeleteUserResponseDTO;
import antifraud.dto.UserDetailRequestDTO;
import antifraud.dto.UserDetailResponseDTO;
import antifraud.entity.UserDetail;
import antifraud.entity.UserRole;
import antifraud.exception.UserAlreadyExistException;
import antifraud.exception.UserNotFoundException;
import antifraud.repository.UserDetailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl {

    private final UserDetailRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDetailRepository userRepository,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetailResponseDTO saveUser(UserDetailRequestDTO userDetailRequestDTO) {

        if (!userRepository.findByUsernameIgnoreCase(userDetailRequestDTO.getUsername()).isEmpty()) {
            throw new UserAlreadyExistException();
        }

        userDetailRequestDTO.setPassword(passwordEncoder.encode(userDetailRequestDTO.getPassword()));
        UserDetail userToSave = modelMapper.map(userDetailRequestDTO, UserDetail.class);
        assignUserRol(userToSave);
        UserDetail userDetail = userRepository.save(userToSave);

        return new UserDetailResponseDTO(userDetail);
    }

    public List<UserDetailResponseDTO> getUserList() {
        List<UserDetail> users = userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        return users.stream()
                .map(u -> new UserDetailResponseDTO(u))
                .collect(Collectors.toList());
    }

    @Transactional
    public DeleteUserResponseDTO deleteUser(String username) {
        UserDetail userDetail = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UserNotFoundException());
        userRepository.deleteByUsernameIgnoreCase(username);
        return new DeleteUserResponseDTO(userDetail.getUsername());
    }

    private void assignUserRol(UserDetail userDetail) {
        UserRole userRole = new UserRole();

        if (userRepository.findFirstUser().isEmpty()) {
            userRole.setName(UserRoleType.ROLE_ADMINISTRATOR);
        } else {
            userRole.setName(UserRoleType.ROLE_MERCHANT);
        }

        userDetail.setRole(userRole);
    }
}
