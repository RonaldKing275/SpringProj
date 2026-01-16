package pl.rsz.springproj.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.rsz.springproj.domain.UserRegistrationDto;
import pl.rsz.springproj.service.UserService;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(UserRegistrationDto userDto, Model model) {
        try {
            if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
                throw new RuntimeException("Hasła nie są identyczne");
            }
            userService.registerUser(userDto);
            return "redirect:/login?registered"; // Przekierowanie po sukcesie
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("user", userDto); // Żeby nie czyściło formularza
            return "register";
        }
    }
}