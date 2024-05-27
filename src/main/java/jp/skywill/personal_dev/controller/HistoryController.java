package jp.skywill.personal_dev.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.skywill.personal_dev.model.Score;
import jp.skywill.personal_dev.service.ScoreService;
import jp.skywill.personal_dev.service.UserService;

@SessionAttributes("userId")
@Controller
public class HistoryController {
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private UserService userService;

    @ModelAttribute("userId")
    public String setUpUserId() {
        return "";
    }

    @RequestMapping("/history")
    public String history(Model model, @ModelAttribute("userId") String userId,
            @RequestParam(name = "ele_num", defaultValue = "0") String eleNum) {
        if (userId == null || userId.isEmpty()) {
            return "redirect:/login";
        }
        model.addAttribute("title", "MyProject");
        model.addAttribute("subtitle", "Myゲーム結果履歴");
        model.addAttribute("name", userService.findById(Integer.parseInt(userId)).get().getName());
        List<Score> datas;
        switch (eleNum) {
            case "0":
                datas = scoreService.getAllByUserId(Integer.parseInt(userId));
                model.addAttribute("datas", datas);
                break;
            default:
                datas = scoreService.getAllByUserIdAndEleNum(Integer.parseInt(userId), Integer.parseInt(eleNum));
                model.addAttribute("datas", datas);
                break;
        }
        List<Integer> eleNumList = scoreService.getListEleNum(Integer.parseInt(userId));
        model.addAttribute("ele_nums", eleNumList);
        return "history";
    }
    @PostMapping("/delete")
    public String delete(@RequestParam Integer id){
        scoreService.delete(id);
        return "redirect:/history";
    }
}
