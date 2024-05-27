package jp.skywill.personal_dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.skywill.personal_dev.model.Score;
import jp.skywill.personal_dev.service.ScoreService;
import jp.skywill.personal_dev.service.UserService;

@SessionAttributes("userId")
@Controller
public class ResultController {
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private UserService userService;

    @ModelAttribute("userId")
    public String setUpUserId() {
        return "";
    }
    // @RequestParam(name = "id") String user_id, @RequestParam(name = "ele_num") String ele_num, @RequestParam(name = "time") String time, @RequestParam(name = "name") String name
    @PostMapping("/result")
    public String showresult(Model model, @ModelAttribute("userId") String userId, @ModelAttribute Score score, @RequestParam(name = "name") String name) {
        if (userId == null || userId.isEmpty()) {
            return "redirect:/login?err=login";
        }
        if (score.getTime() == null || score.getEleNum() == null) {
            return "redirect:/index";
        }
        model.addAttribute("title", "MyProject");
        model.addAttribute("subtitle", "ゲーム結果");
        score.setUser(userService.findById(Integer.parseInt(userId)).orElseThrow());
        scoreService.insert(score);
        model.addAttribute("name", name);
        model.addAttribute("time", score.getTime());
        model.addAttribute("ele_num", score.getEleNum());

        // TODO 結果の表示と保存をする
        // TODO 結果の履歴のページを作る
        // TODO アカウントの削除ページを作る
        return "result";
    }
}
