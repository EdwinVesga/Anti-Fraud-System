package antifraud.service;

import antifraud.dto.DeleteUserResponseDTO;
import antifraud.dto.UserDetailRequestDTO;
import antifraud.dto.UserDetailResponseDTO;
import antifraud.entity.UserDetail;
import antifraud.exception.UserAlreadyExistException;
import antifraud.exception.UserNotFoundException;
import antifraud.repository.UserDetailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        if (!userRepository.findByUsername(userDetailRequestDTO.getUsername()).isEmpty()) {
            throw new UserAlreadyExistException();
        }

        userDetailRequestDTO.setPassword(passwordEncoder.encode(userDetailRequestDTO.getPassword()));
        UserDetail userDetail = userRepository.save(modelMapper.map(userDetailRequestDTO, UserDetail.class));

        return modelMapper.map(userDetail, UserDetailResponseDTO.class);
    }

    public List<UserDetailResponseDTO> getUserList() {
        List<UserDetail> users = userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        return users.stream()
                .map(u -> modelMapper.map(u, UserDetailResponseDTO.class))
                .collect(Collectors.toList());
    }

    public DeleteUserResponseDTO deleteUser(String username) {
        UserDetail userDetail = userRepository.deleteByUsername(username).orElseThrow(() -> new UserNotFoundException());

        return new DeleteUserResponseDTO(userDetail.getUsername());
    }
}
