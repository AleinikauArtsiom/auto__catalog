package com.auto_catalog.auto__catalog.api.services;


import com.auto_catalog.auto__catalog.api.dto.UserDto;
import com.auto_catalog.auto__catalog.store.model.User;
import com.auto_catalog.auto__catalog.store.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    private final UserRepository userRepository;
    private final User user;

    @Autowired
    public UserService(UserRepository userRepository, User user) {
        this.userRepository = userRepository;
        this.user = user;
    }
   public List<User> getAllUsers(){
        return userRepository.findAll();
   }
   public Optional<User> getUserById(Long id ){
        return userRepository.findById(id);
   }
   public  Boolean deleteUserById(Long id ){
         userRepository.deleteById(id);
       return getUserById(id).isEmpty();
   }
    //ниже проверочка на то... существует ли заданный айди для удаления
    public boolean isUserExists(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.isPresent();
    }
   public Boolean createUser(@Valid UserDto userDto){
        User user = new User();
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setLogin(userDto.getLogin());
        user.setFirstName(userDto.getFirstName());
        user.setEmail(userDto.getEmail());
        User createdUser = userRepository.save(user);
        return getUserById(createdUser.getUserId()).isPresent();
   }
    public Boolean updateUser(User user) {
        Optional<User> userFromDBOptional = (userRepository.findById(user.getUserId()));
        if (userFromDBOptional.isPresent()) {
            User userFromDB = userFromDBOptional.get();
            if (user.getEmail() != null) {
                userFromDB.setEmail(user.getEmail());
            }
            if (user.getPassword() != null) {
                userFromDB.setPassword(user.getPassword());
            }
            if (user.getFirstName() != null) {
                userFromDB.setFirstName(user.getFirstName());
            }
            if(user.getLastName()!= null){
                userFromDB.setLastName(user.getLastName());
            }
            if(user.getLogin()!= null){
                userFromDB.setLogin(user.getLogin());
            }
            User updatedUser = userRepository.saveAndFlush(userFromDB);
            return getUserById(updatedUser.getUserId()).isPresent();
        }
        return false;
    }

}
