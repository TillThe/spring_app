package hello;

/**
 * Created by Кирилл on 19.12.2016.
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
    @RequestMapping("/greetings")
    public String greetings(@RequestParam(value="value", required=false, defaultValue="Worlds") String value,
                            @RequestParam(value="valuer", required=false, defaultValue="Worlds") String valuer, Model model) {
        model.addAttribute("valuer", valuer);
        model.addAttribute("value", value);
        return "greetings";
    }
}
