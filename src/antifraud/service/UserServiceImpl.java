package antifraud.service;

import antifraud.constant.UserRolType;
import antifraud.dto.DeleteUserResponseDTO;
import antifraud.dto.UserDetailRequestDTO;
import antifraud.dto.UserDetailResponseDTO;
import antifraud.entity.UserDetail;
import antifraud.entity.UserRol;
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

        return modelMapper.map(userDetail, UserDetailResponseDTO.class);
    }

    public List<UserDetailResponseDTO> getUserList() {
        List<UserDetail> users = userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        return users.stream()
                .map(u -> modelMapper.map(u, UserDetailResponseDTO.class))
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
        UserRol userRol = new UserRol();

        if (userRepository.findFirstUser().isEmpty()) {
            userRol.setName(UserRolType.ROLE_ADMINISTRATOR);
        } else {
            userRol.setName(UserRolType.ROLE_MERCHANT);
        }

        userDetail.setRol(userRol);
    }
}
