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
        // 1. Sprawdź czy użytkownik istnieje
        if (userRepository.findByUsername(dto.getUsername()) != null) {
            throw new RuntimeException("Użytkownik o takiej nazwie już istnieje");
        }

        // 2. Stwórz encję User
        User user = new User();
        user.setUsername(dto.getUsername());
        // Kodujemy hasło przed zapisem!
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // 3. Pobierz i przypisz rolę USER
        Role roleUser = roleRepository.findByName("ROLE_USER");
        if (roleUser == null) {
            // Zabezpieczenie gdyby rola nie istniała (choć DataInitializer powinien ją stworzyć)
            roleUser = roleRepository.save(new Role("ROLE_USER"));
        }
        user.setRoles(new HashSet<>(Collections.singletonList(roleUser)));

        // 4. Zapisz
        userRepository.save(user);
    }
}