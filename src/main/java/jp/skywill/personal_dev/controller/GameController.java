package jp.skywill.personal_dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.skywill.personal_dev.service.UserService;

@SessionAttributes("userId")
@Controller
public class GameController {

    @Autowired
    private UserService userService;

    @ModelAttribute("userId")
    public String setUpUserId() {
        return "";
    }

    @RequestMapping("/game")
    public String game(@ModelAttribute("userId") String userId,
            @RequestParam(name = "ele_num", defaultValue = "3") int ele_num, Model model) {
        if (userId == null || userId.isEmpty()) {
            return "redirect:/login?err=login";
        }
        model.addAttribute("title", "MyProject");
        model.addAttribute("subtitle", "ゲーム画面");
        model.addAttribute("name", userService.findById(Integer.parseInt(userId)).get().getName());
        model.addAttribute("element_num", ele_num);
        return "/game";
    }

}
