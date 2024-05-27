package jp.skywill.personal_dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.skywill.personal_dev.model.User;
import jp.skywill.personal_dev.service.UserService;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showForm(Model model, @ModelAttribute("user") User user, @RequestParam(name = "err", defaultValue = "none") String err) {
        model.addAttribute("title", "MyProject");
        model.addAttribute("subtitle", "登録画面");
        switch (err) {
            case "name_pass":
                model.addAttribute("err", "UserNameかPasswordを変更してください");
                break;
            default:
                model.addAttribute("err", "");
                break;
        }
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(@ModelAttribute User user) {
        if (userService.existsByNameAndPass(user.getName(), user.getPass())) {
            return "redirect:/register?err=name_pass";
        } else {
            userService.insertuser(user);
            return "redirect:/index";
        }
    }
}
