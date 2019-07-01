package com.nowcoder.controller;


import com.nowcoder.service.UserService;
import com.nowcoder.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;


    @RequestMapping(path = {"/reg/"}, produces = { "application/json;charset=UTF-8" }, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String reg(Model model, @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "rember", defaultValue = "0") int rememberme){

        try{
            Map<String, Object> map = userService.register(username, password);
            if(map.isEmpty()){
                return ToutiaoUtil.getJSONString(0, "注册成功");
            }else{
                return ToutiaoUtil.getJSONString(1, map);
            }

        }catch (Exception e){
            logger.error("注册异常" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "注册异常");
        }

    }


}
