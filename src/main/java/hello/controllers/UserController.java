package hello.controllers;

import hello.jdbc.UserJDBC;
import hello.model.JsonResponse;
import hello.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Кирилл on 05.01.2017.
 */

@SessionAttributes(value = "user")
@Controller
//@SessionAttributes(types = User.class)
public class UserController {

    private final UserJDBC userJDBC;
    private static User currentUser;

    @Autowired
    public UserController(UserJDBC userJDBC) {
        this.userJDBC = userJDBC;
    }

    public ModelAndView test(User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user1", user);
        return modelAndView;
    }

    @RequestMapping(value = "/sign_in",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public JsonResponse signInFormPost(@RequestBody User user, @ModelAttribute("user") User userSess, HttpSession httpSession, SessionStatus status) throws SQLException {
        if (userJDBC.checkAuth(user) == 1) {
            System.out.println(userSess);
            System.out.println("\naaaaaaaaaaaaa\n" + httpSession.getId());
            System.out.println("\nneponyal" + user + "\n");
//            httpSession.removeAttribute("user");
//            httpSession.getAttribute("user").setLogin(user.getLogin());
//            httpSession.setAttribute("user", user );
//            status.setComplete();
            test(user);
//            httpSession.setAttribute("userSess", user );
//            httpSession.getAttribute()
//            httpSession.setAttribute("user", user );

            return new JsonResponse("OK", "game");
        } else {
            return new JsonResponse("FAIL", "Неправильный логин или пароль");
        }
    }
    @ModelAttribute("user")
    public User addUserToSession() {
       return new User("creator", "creatorPass");
   }



    @GetMapping("/game")
    public ModelAndView game(@ModelAttribute("user") User user,@ModelAttribute("user1") User user1, @ModelAttribute("userSess") User userSess, HttpSession httpSession) {
//        System.out.println(res);
        System.out.println( httpSession.getId());
        System.out.println("begin\n");
        Enumeration e = httpSession.getAttributeNames();
        while (e.hasMoreElements()) {
            String name = (String) e.nextElement();
            System.out.println(name + ": " + httpSession.getAttribute(name) + "\n");
        }
        System.out.println("end\n");
        System.out.println("game: " + user);
        System.out.println("kk: " + user1);
        ModelAndView modelAndView = new ModelAndView();
        httpSession.setAttribute("user", new User("ebat", "ura"));
//        HttpSession httpSession = req.getSession(true);
//        User user = (User)httpSession.getAttribute("user" );
        modelAndView.addObject("user", user);
        if (user.getAuth() == 0) {
            modelAndView.setViewName("sign_in");
        } else {
            modelAndView.setViewName("game");
        }
        return modelAndView;
    }
//    @RequestMapping
//    @ModelAttribute("userSession")
//    public String redirect(User user) {
//        return "game";
//    }

    @RequestMapping(value = "/sign_up",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public JsonResponse signUpFormPost(@RequestBody User user) throws SQLException {
        if (userJDBC.insert(user) != 0) {
//            System.out.println("Registered: " + user);
            user.setAuth(1);
            return new JsonResponse("OK", "game");
        } else {
//            System.out.println("Not registered: " + user);
            return new JsonResponse("FAIL", "Этот логин уже занят");
        }
    }

    @GetMapping("/stats")
    public ModelAndView stats(@ModelAttribute("user") User user) throws SQLException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("stats");
        List<User> data = userJDBC.selectAll();
//        System.out.println(data);
        Collections.sort(data, new Comparator<User>() {
            public int compare(User o1, User o2) {
                return Math.toIntExact(Math.round(o2.getPoints()) - Math.round(o1.getPoints()));
            }
        });
        modelAndView.addObject("data", data);
        return modelAndView;
    }

    @GetMapping( "/stats1")
    public ModelAndView stats1(@ModelAttribute("user") User user) throws SQLException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("stats1");
        List<User> data = userJDBC.selectAll();
//        System.out.println(data);
        Collections.sort(data, new Comparator<User>() {
            public int compare(User o1, User o2) {
                return Math.toIntExact(Math.round(o2.getPoints()) - Math.round(o1.getPoints()));
            }
        });
        modelAndView.addObject("data", data);
        return modelAndView;
    }

    @ModelAttribute("user")
    public User createUser(){
        return new User();
    }

    @GetMapping("/exit")
    public String exit(@ModelAttribute("userSession") User user, SessionStatus sessionStatus) throws SQLException {
        sessionStatus.setComplete();
        return "/";
    }

//    @PostMapping("/sign_up")
//    public String signUpFormPost(@ModelAttribute("userSession") User user, Model model) throws SQLException {
//        model.addAttribute("user", user);
//        System.out.println(user);
//        if (checkFields(user)) {
//            if (userJDBC.checkLogin(user) == 0)
//                System.out.println(userJDBC.insert(user));
//        }
//
//        return "result1";
//    }

    public boolean checkFields (User user) {
        return (user.getLogin().trim().length() != 0 && user.getPassword().trim().length() != 0 && checkPass(user));
    }
    public boolean checkPass (User user) {
        return user.getPassword() == null ? user.getCheckPassword() == null : user.getPassword().equals(user.getCheckPassword());
    }

}