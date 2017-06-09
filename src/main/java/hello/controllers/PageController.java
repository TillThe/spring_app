package hello.controllers;

import hello.model.Game;
import hello.model.JsonResponse;
import hello.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Кирилл on 17.01.2017.
 */
@SessionAttributes(value = "user")
@Controller
public class PageController {

    @GetMapping("/")
    public ModelAndView main(HttpSession httpSession, User user) {
        System.out.println("\nBEGINNISHEEEEE\n");
        System.out.println(httpSession.getAttribute("name"));
        System.out.println("\nBEGINNISHEEEEE\n");

        ModelAndView modelAndView = new ModelAndView();
//        httpSession.setAttribute("user",  );
        modelAndView.addObject("user", new User("kek", "passwordex"));
        if (user.getAuth() == 0) {
            modelAndView.setViewName("sign_in");
        } else {
            modelAndView.setViewName("game");
        }
        return modelAndView;
    }

    @GetMapping("/sign_in")
    public String signInFormGet(Model model) {
        model.addAttribute("user", new User());
        return "sign_in";
    }
    @GetMapping("/sign_up")
    public ModelAndView reg(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sign_up");
        modelAndView.addObject("user", user);
        return modelAndView;
    }


    @GetMapping("/gfy")
    public String gfy() {
        return "gfy";
    }

    @GetMapping("/forgot_pass")
    public String forgot_pass(Model model) {
        model.addAttribute("user", new User());
        return "forgot_pass";
    }

    @GetMapping("/test_test")
    public String test_test(Model model) {
        model.addAttribute("user", new User());
        return "test_test";
    }
//
//    @RequestMapping(method=RequestMethod.GET, value="ajaxtest?{id}")
//    @ResponseBody
//    public Set<String> ajaxTest(@PathVariable long id) {
//        Set<String> records = new HashSet<String>();
//        records.add("Record #1");
//        records.add("Record #2");
//        System.out.println("kek");
//        System.out.println(id);
//        System.out.println(records);
////        System.out.println(user_id);
//        return records;
//    }

    @RequestMapping(value = "/ajaxtest",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public JsonResponse ajaxtest(@RequestBody Game game) {
        System.out.println(game);
        return new JsonResponse("OK", "");
    }

}
