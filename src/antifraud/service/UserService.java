package antifraud.service;

import antifraud.dto.*;

import java.util.List;

public interface UserService {

    UserDetailResponseDTO saveUser(UserDetailRequestDTO userDetailRequestDTO);

    List<UserDetailResponseDTO> getUserList();

    DeleteUserResponseDTO deleteUser(String username);

    UserDetailResponseDTO updateUserRol(UpdateRoleRequestDTO updateRoleRequestDTO);

    UpdateAccessResponseDTO updateUserAccountAccess(UpdateAccessRequestDTO updateAccessRequestDTO);
}
