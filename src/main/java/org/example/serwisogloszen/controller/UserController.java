package org.example.serwisogloszen.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.serwisogloszen.model.dto.CategoryDTO;
import org.example.serwisogloszen.model.dto.UserDTO;
import org.example.serwisogloszen.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, null, auth);
        }

        return "redirect:/";
    }
    @GetMapping("/register")
    public String addUserForm(Model model){
        model.addAttribute("user", new UserDTO());
        return "user/register";
    }


    @PostMapping("/register")
    public String addUser(@Valid @ModelAttribute("user") UserDTO dto,
                              BindingResult bindingResult,
                              Model model) {
        if(bindingResult.hasErrors()) {
            return "user/register";
        }
        userService.addUser(dto);
        return "redirect:/login";
    }
}
