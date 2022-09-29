package antifraud.service;

import antifraud.constant.UserAccountStatus;
import antifraud.constant.UserRoleType;
import antifraud.dto.*;
import antifraud.entity.UserDetail;
import antifraud.entity.UserRole;
import antifraud.exception.*;
import antifraud.repository.UserDetailRepository;
import antifraud.repository.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl {

    private final List<UserRoleType> validUpdateRoles = List.of(UserRoleType.ROLE_MERCHANT,
            UserRoleType.ROLE_SUPPORT);

    private final UserDetailRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDetailRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
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
        UserAccountStatus status;
        if (UserRoleType.ROLE_ADMINISTRATOR.equals(userToSave.getRole().getName())) {
            status = UserAccountStatus.UNLOCK;
        } else {
            status = UserAccountStatus.LOCK;
        }
        userToSave.setStatus(status);
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

    public UserDetailResponseDTO updateUserRol(UpdateRoleRequestDTO updateRoleRequestDTO) {
        UserDetail userDetail = userRepository.findByUsernameIgnoreCase(updateRoleRequestDTO.getUsername()).orElseThrow(() -> new UserNotFoundException());

        if (UserRoleType.ROLE_ADMINISTRATOR.equals(userDetail.getRole().getName())) {
            throw new RoleConflictException();
        }

        Optional<UserRole> userRoleOptional = userRoleRepository.findByName(UserRoleType.getUserRoleType(updateRoleRequestDTO.getRole()));
        validateRole(userRoleOptional);
        userDetail.setRole(userRoleOptional.get());
        userDetail = userRepository.save(userDetail);

        return new UserDetailResponseDTO(userDetail);
    }

    private void validateRole(Optional<UserRole> userRoleOptional) {
        if (userRoleOptional.isEmpty() || !validUpdateRoles.contains(userRoleOptional.get().getName())) {
            throw new InvalidRoleException();
        } else if (UserRoleType.ROLE_SUPPORT.equals(userRoleOptional.get().getName())
                && userRepository.findByRole(userRoleOptional.get()).isPresent()) {
            throw new RoleConflictException();
        }
    }

    private void assignUserRol(UserDetail userDetail) {
        UserRoleType userRoleType;

        if (userRepository.findById(1l).isEmpty()) {
            userRoleType = UserRoleType.ROLE_ADMINISTRATOR;
        } else {
            userRoleType = UserRoleType.ROLE_MERCHANT;
        }

        Optional<UserRole> userRoleOptional  = userRoleRepository.findByName(userRoleType);
        userDetail.setRole(userRoleOptional.get());
    }

    public UpdateAccessResponseDTO updateUserAccountAccess(UpdateAccessRequestDTO updateAccessRequestDTO) {
        UserDetail userDetail = userRepository.findByUsernameIgnoreCase(updateAccessRequestDTO.getUsername())
                .orElseThrow(() -> new UserNotFoundException());
        if (UserRoleType.ROLE_ADMINISTRATOR.equals(userDetail.getRole().getName())) {
            throw new InvalidUserOperation();
        }
        UserAccountStatus status = UserAccountStatus.getStatus(updateAccessRequestDTO.getOperation());
        userDetail.setStatus(status);
        UserDetail userSaved = userRepository.save(userDetail);
        return new UpdateAccessResponseDTO(userSaved);

    }
}
