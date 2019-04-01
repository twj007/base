package com.framework.file.controller.User;

import com.framework.file.pojo.OperationType;
import com.framework.file.pojo.ResultBody;
import com.framework.file.pojo.user.SysUser;
import com.framework.file.pojo.user.User;
import com.framework.file.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/checkUserExists")
    @ResponseBody
    public Map checkUserExists(SysUser user){
        Long num = userService.checkUserExists(user);
        Map valid = new HashMap();
        if(num > 0){
            valid.put("valid", true);
        }else{
            valid.put("valid", false);
        }
        return valid;
    }

    @PostMapping("/login")
    public ResponseEntity login(@Validated User user, BindingResult bindingResult, HttpServletRequest request,HttpServletResponse response){
        if(bindingResult.hasErrors()){
            return ResponseEntity.ok(ResultBody.fail("参数验证失败", bindingResult.getAllErrors()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User u = userService.login(user);
        if(u == null){
            return ResponseEntity.ok(ResultBody.fail("用户名或密码错误", null));
        }else{
            request.getSession().setAttribute("user", u);
            Cookie c = new Cookie("token", "一个加密的token用来鉴权");
            response.addCookie(c);
            return ResponseEntity.ok(ResultBody.success("success", null));
        }
    }

    @GetMapping("/main")
    public ModelAndView toMain(){
        return new ModelAndView("user/main");
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
            return ResponseEntity.ok(ResultBody.success("", true));
        }else{
            return ResponseEntity.ok(ResultBody.fail("", false));
        }
    }

    @GetMapping("/validCode")
    public ResponseEntity validCode(String code, HttpServletRequest request){
        String signCode = (String) request.getSession().getAttribute("code");
        if(StringUtils.isEmpty(signCode) || StringUtils.isEmpty(code)){
            return ResponseEntity.ok(ResultBody.fail("验证失败", false));
        }else if(!StringUtils.isEmpty(signCode) && signCode.equals(code)){
            return ResponseEntity.ok(ResultBody.success("验证成功", true));
        }else{
            return ResponseEntity.ok(ResultBody.fail("验证失败", false));
        }
    }

    @GetMapping("/toRegister")
    public ModelAndView toRegister(){
        return new ModelAndView("register");
    }

    @GetMapping("/batchRegister")
    @ResponseBody
    public ResponseEntity<ResultBody> batchRegister(){
        userService.batchRegister();
        return ResponseEntity.ok(ResultBody.success("ok", null));
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity register(@Validated User user, BindingResult bindResult, HttpServletRequest request){
        boolean flag = false;
        if(bindResult.hasErrors()){
//            List<ObjectError> errors = bindResult.getAllErrors();
//            for(ObjectError error : errors){
//                error.getDefaultMessage();
//            }
            return ResponseEntity.ok(bindResult.getAllErrors());
        }
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
        return ResponseEntity.ok(ResultBody.success("", flag));

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
