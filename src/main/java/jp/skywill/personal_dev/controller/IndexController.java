package jp.skywill.personal_dev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jp.skywill.personal_dev.model.UserDTO;
import jp.skywill.personal_dev.model.UserScoreDTO;
import jp.skywill.personal_dev.service.ScoreService;
import jp.skywill.personal_dev.service.UserService;

@SessionAttributes("userId")
@Controller
public class IndexController {
    @Autowired
    private UserService userService;
    @Autowired
    private ScoreService scoreService;

    @ModelAttribute("userId")
    public String setUpUserId() {
        return "";
    }

    @RequestMapping("/")
    public String redirectindex(@ModelAttribute("userId") String userId) {
        if (userId != null && !userId.isEmpty()) {
            return "redirect:/index";
        }
        return "redirect:/login";
    }

    @GetMapping("/change")
    public String change(Model model, @ModelAttribute("userId") String userId,
            @ModelAttribute("userform") UserDTO userDTO) {
        if (userId == null || userId.isEmpty()) {
            return "redirect:/login";
        }
        model.addAttribute("title", "MyProject");
        model.addAttribute("subtitle", "My情報");
        model.addAttribute("original_name", userService.findById(Integer.parseInt(userId)).get().getName());
        model.addAttribute("original_age", userService.findById(Integer.parseInt(userId)).get().getAge());
        model.addAttribute("original_gender", userService.findById(Integer.parseInt(userId)).get().getGender());
        return "change";
    }

    @PostMapping("/change")
    public String change(
            @ModelAttribute("userId") String userId,
            @ModelAttribute("userform") UserDTO userDTO) {
        userService.updateById(Integer.parseInt(userId), userDTO.getName(), userDTO.getAge(), userDTO.getGender());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/login";
    }

    @RequestMapping("/index")
    public String showPage(Model model,
            @ModelAttribute("userId") String userId,
            @RequestParam(name = "ele_num", defaultValue = "0") String eleNum) {
        if (userId == null || userId.isEmpty()) {
            return "redirect:/login";
        }

        model.addAttribute("title", "MyProject");
        model.addAttribute("subtitle", "ホーム画面");
        model.addAttribute("name", userService.findById(Integer.parseInt(userId)).get().getName());
        List<UserScoreDTO> datas;
        switch (eleNum) {
            case "0":
                datas = scoreService.getAllScoreOrderByDate();
                model.addAttribute("legend", "全体の直近の記録");
                model.addAttribute("datas", datas);
                System.out.println(datas.size());
                break;
            default:
                datas = scoreService.getAllScoreByEleNum(Integer.parseInt(eleNum));
                model.addAttribute("legend", "全体のランキングTOP10");
                model.addAttribute("datas", datas);
                break;
        }
        List<Integer> eleNumList = scoreService.getAllListEleNum();
        model.addAttribute("ele_nums", eleNumList);
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model,
            @RequestParam(name = "err", defaultValue = "") String err) {
        model.addAttribute("title", "MyProject");
        model.addAttribute("subtitle", "ログイン画面");
        switch (err) {
            case "none":
                model.addAttribute("err", "アカウントが登録されていないかUserNameかパスワード（合言葉）が違います。");
                break;
            case "login":
                model.addAttribute("err", "ログインしてください");
                break;
        }
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(Model model,
            @RequestParam(name = "name", defaultValue = "") String name,
            @RequestParam(name = "pass", defaultValue = "") String pass) {
        if (name != "" && pass != "") {
            if (userService.existsByNameAndPass(name, pass)) {
                model.addAttribute("userId", userService.findid(name, pass));
                return "redirect:/index";
            } else {
                return "redirect:/login?err=none";
            }
        }
        return "redirect:/login?err=none";
    }
}
