package com.auto_catalog.auto__catalog.api.security.service;

import com.auto_catalog.auto__catalog.api.exception.SameUserInDataBase;
import com.auto_catalog.auto__catalog.api.security.entity.Roles;
import com.auto_catalog.auto__catalog.api.security.entity.UserSecurity;
import com.auto_catalog.auto__catalog.api.security.entity.dto.AuthRequestDto;
import com.auto_catalog.auto__catalog.api.security.entity.dto.UserSecRegistrationDto;
import com.auto_catalog.auto__catalog.api.security.repository.UserSecurityRepository;
import com.auto_catalog.auto__catalog.store.entity.User;
import com.auto_catalog.auto__catalog.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserSecurityService {
    private final PasswordEncoder passwordEncoder;
    private final UserSecurityRepository userSecurityRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    @Autowired
    public UserSecurityService(UserSecurityRepository userSecurityRepository,
                               PasswordEncoder passwordEncoder,UserRepository userRepository,JwtUtils jwtUtils) {
        this.userSecurityRepository = userSecurityRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtUtils=jwtUtils;
    }

    @Transactional(rollbackFor = Exception.class)
    public void registration(UserSecRegistrationDto userSecRegistrationDto){
        Optional<UserSecurity> security = userSecurityRepository.findByUserLogin(userSecRegistrationDto.getLogin());
        if(security.isPresent()){
            throw new SameUserInDataBase(userSecRegistrationDto.getLogin());
        }

        User user = new User();
        user.setEmail(userSecRegistrationDto.getEmail());
        user.setLastName(userSecRegistrationDto.getLastName());
        user.setFirstName(userSecRegistrationDto.getFirstName());
        User savedUser =  userRepository.save(user);

        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setUserLogin(userSecRegistrationDto.getLogin());
        userSecurity.setUserPassword(passwordEncoder.encode(userSecRegistrationDto.getPassword()));
        userSecurity.setRole(Roles.USER);
      //  userSecurity.setId(savedUser.getUserId());
        userSecurity.setIsBlocked(false);
        userSecurity.setUser(savedUser);
        userSecurityRepository.save(userSecurity);
    }

    public Optional<String> generateToken(AuthRequestDto authRequestDto) {
        Optional<UserSecurity> security = userSecurityRepository.findByUserLogin(authRequestDto.getLogin());
        if (security.isPresent()) {
            UserSecurity userSecurity = security.get();
            if (userSecurity.getIsBlocked()) {
                throw new IllegalArgumentException("User is blocked");
            }
            if (passwordEncoder.matches(authRequestDto.getPassword(), userSecurity.getUserPassword())) {
                return Optional.of(jwtUtils.generateJwtToken(authRequestDto.getLogin()));
            }
        }
        return Optional.empty();
    }
    @Transactional
    public void promoteUserToAdmin(Long id) {
        // Поиск пользователя по ID
        UserSecurity user = userSecurityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No user found with ID " + id));
        // Смена роли на ADMIN
        user.setRole(Roles.valueOf("ADMIN"));
        userSecurityRepository.save(user);
        System.out.println("Role of user with ID " + id + " has been changed to ADMIN");

    }
}
