package com.example.employeemanager.convert;
import com.example.employeemanager.dto.RoleDTO;
import com.example.employeemanager.dto.UserDTO;
import com.example.employeemanager.entity.Role;
import com.example.employeemanager.entity.User;
import com.example.employeemanager.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Component
public class UserConvert {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    public User toEntity(UserDTO dto, User old) {

        if (dto.getUsername() != null)
            old.setUsername(dto.getUsername());
        if (dto.getFullName() != null)
            old.setFullName(dto.getFullName());
        if (dto.getEmail() != null)
            old.setEmail(dto.getEmail());
        if (dto.getCode() != 0)
            old.setCode(dto.getCode());
        if (dto.getRoles() != null) {
            Set<Role> roles = new HashSet<Role>();
            if (old.getRoles() != null)
                roles = old.getRoles();
            for (RoleDTO role : dto.getRoles()) {
                roles.add(modelMapper.map(role, Role.class));
            }
            old.setRoles(roles);
        }

        return old;
    }
    public List<UserDTO> toDto(List<User> list){
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : list) {
            UserDTO userDTO=new UserDTO();
            userDTO.setUsername(user.getUsername());
            userDTO.setFullName(user.getFullName());
            userDTO.setEmail(user.getEmail());
            userDTO.setCode((user.getCode()));
//            Set<RoleDTO> roleDTOS= new HashSet<>();
//            for(Role role: user.getRoles()){
////                RoleDTO dto= modelMapper.map(role,RoleDTO.class );
//                RoleDTO roleDTO = new RoleDTO();
//                roleDTO.setId(role.getId());
//                roleDTO.setName(role.getName());
//                roleDTOS.add(roleDTO);
//            }
//            userDTO.setRoles(roleDTOS);
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }
}
