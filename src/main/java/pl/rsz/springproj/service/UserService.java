package pl.rsz.springproj.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.rsz.springproj.domain.Role;
import pl.rsz.springproj.domain.User;
import pl.rsz.springproj.domain.UserRegistrationDto;
import pl.rsz.springproj.repositories.RoleRepository;
import pl.rsz.springproj.repositories.UserRepository;

import java.util.Collections;
import java.util.HashSet;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerUser(UserRegistrationDto dto) {
        if (userRepository.findByUsername(dto.getUsername()) != null) {
            throw new RuntimeException("Użytkownik o takiej nazwie już istnieje");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        Role roleUser = roleRepository.findByName("ROLE_USER");
        if (roleUser == null) {
            roleUser = roleRepository.save(new Role("ROLE_USER"));
        }
        user.setRoles(new HashSet<>(Collections.singletonList(roleUser)));

        userRepository.save(user);
    }
}