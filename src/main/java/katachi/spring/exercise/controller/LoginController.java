package katachi.spring.exercise.controller;


import org.springframework.security.core.AuthenticationException;
import javax.servlet.http.HttpSession;

import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
    		Model model, HttpSession session) {
    	if (error != null) {
    		AuthenticationException ex = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    		if (ex != null) {
    			model.addAttribute("showErrorMsg", true);
    			model.addAttribute("errorMsg", "IDかパスワードが違います");
    		}
    	}
        return "login";
    }
}
