package com.framework.file.controller.User;

import com.framework.file.pojo.OperationType;
import com.framework.file.pojo.user.User;
import com.framework.file.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.security.util.Password;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/checkUserExists")
    public ResponseEntity checkUserExists(User user){
        Long num = userService.checkUserExists(user);
        if(num > 0){
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.ok(false);
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(User user, HttpServletRequest request,HttpServletResponse response){
        User u = userService.login(user);
        if(u == null){
            return ResponseEntity.ok(false);
        }else{
            request.getSession().setAttribute("user", u);
            Cookie c = new Cookie("token", "一个加密的token用来鉴权");
            response.addCookie(c);
            return ResponseEntity.ok(true);
        }
    }

    @GetMapping("/findlost")
    public ModelAndView toFindlost(){
        ModelAndView modelAndView = new ModelAndView("findlost");
        return modelAndView;
    }

    @GetMapping("/sendValidEmail")
    public ResponseEntity sendValidEmail(User user, HttpServletRequest request){

        String code = userService.sendValidEmail(user);
        if(!StringUtils.isEmpty(code)){
            request.getSession().setAttribute("code", code);
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/validCode")
    public ResponseEntity validCode(String code, HttpServletRequest request){
        String signCode = (String) request.getSession().getAttribute("code");
        if(StringUtils.isEmpty(signCode) || StringUtils.isEmpty(code)){
            return ResponseEntity.ok(false);
        }else if(!StringUtils.isEmpty(signCode) && signCode.equals(code)){
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/toRegister")
    public ModelAndView toRegister(){
        return new ModelAndView("register");
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity register(User user, HttpServletRequest request){
        boolean flag = false;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        switch (OperationType.OPERATIONTYPE.valueOf(user.getOperationType())){
            case GET:
                break;
            case SAVE:
                flag = userService.register(user);
                request.getSession().setAttribute("user", user);
                break;
            case UPDATE:
                flag = userService.updateUser(user);
                break;
            case DEL:
                flag = userService.delUser(user);
                break;
            default:
                break;
        }
        return ResponseEntity.ok(flag);

    }

    @GetMapping("/profile")
    public ModelAndView profile(ModelAndView modelAndView){
        modelAndView = new ModelAndView();
        modelAndView.setViewName("user/profile");
        return modelAndView;
    }

    @GetMapping("/contacts")
    public ModelAndView contacts(HttpServletRequest request){
        User u = (User) request.getSession().getAttribute("user");
        List<User> contacts = userService.getContactList(u);
        ModelAndView modelAndView = new ModelAndView("user/contacts");
        modelAndView.addObject(contacts);
        return modelAndView;
    }

    @GetMapping("/mailbox")
    public ModelAndView mailbox(){
        return new ModelAndView("user/mailbox");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request){
        request.getSession().invalidate();
        return new ModelAndView("login");
    }

}
