package antifraud.service;

import antifraud.dto.DeleteUserResponseDTO;
import antifraud.dto.UserDetailRequestDTO;
import antifraud.dto.UserDetailResponseDTO;
import antifraud.entity.User;
import antifraud.exception.UserAlreadyExistException;
import antifraud.exception.UserNotFoundException;
import antifraud.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetailResponseDTO saveUser(UserDetailRequestDTO userDetailRequestDTO) {

        try {
            userDetailRequestDTO.setPassword(passwordEncoder.encode(userDetailRequestDTO.getPassword()));
            userRepository.addUser(modelMapper.map(userDetailRequestDTO, User.class));
            User user = userRepository.getUserByUsername(userDetailRequestDTO.getUsername());
            return new UserDetailResponseDTO(user.getId(), user.getName(), user.getUsername());
        } catch (Exception e) {
            throw new UserAlreadyExistException();
        }
    }

    public List<UserDetailResponseDTO> getUserList() {
        List<User> users = userRepository.getAllUsers();

        return users.stream()
                .map(u -> modelMapper.map(u, UserDetailResponseDTO.class))
                .collect(Collectors.toList());
    }

    public DeleteUserResponseDTO deleteUser(String username) {
        if (userRepository.deleteUser(username) != 0) {
            return new DeleteUserResponseDTO(username);
        }

        throw new UserNotFoundException();
    }
}
