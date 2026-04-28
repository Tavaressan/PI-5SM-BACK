package dsm.api.pi.DTO.User;

import dsm.api.pi.Enum.UserRoles;

public record UserRegisterDTO(String userName, String senha, UserRoles role) {
}
