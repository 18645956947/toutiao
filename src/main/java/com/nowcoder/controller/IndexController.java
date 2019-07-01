package com.nowcoder.controller;

import com.nowcoder.model.User;
import com.nowcoder.service.ToutiaoService;
import org.apache.commons.lang.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.lang.String;

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    ToutiaoService toutiaoService;

    /*@RequestMapping(path = {"/", "/index"})
    @ResponseBody
    public String index(HttpSession session){

        logger.info("visit index");
        return "hello" + session.getAttribute("msg") + toutiaoService.say();
    }*/

    @RequestMapping(value = {"/profile/{groupId}/{userId}"})
    @ResponseBody
        public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") int userId,
                          @RequestParam(value = "type", defaultValue = "1") int type,
                          @RequestParam(value = "key", defaultValue = "nowcoder") String key) {

        return String.format("GID{%s},UID{%d},TYPE{%d},KEY{%s}", groupId, userId, type, key);
    }

    @RequestMapping(value = {"/vm"})
    public String news(Model model) {
        model.addAttribute("value1", "vv1");
        java.lang.String[] str = {"red", "green", "yellow"};
        List<java.lang.String> colors = Arrays.asList(str);
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            map.put(String.valueOf(i), String.valueOf(i*i));

        }
        model.addAttribute("colors", colors);
        model.addAttribute("map", map);
        /*for( Map.Entry<String, String> entry : map.entrySet()){
            System.out.println("key:"+entry.getKey()+"value:"+entry.getValue());
        }*/

        model.addAttribute("user", new User("JIM"));

        return "news";
    }

    @RequestMapping("request")
    @ResponseBody
    public String requet(HttpServletRequest request,
                         HttpServletResponse response,
                         HttpSession session){
        StrBuilder sb = new StrBuilder();
        Enumeration<String> headerName = request.getHeaderNames();
        while (headerName.hasMoreElements()){
            String name = headerName.nextElement();
            sb.append(name+":"+request.getHeader(name)+"<br>");
        }
        for (Cookie cookie : request.getCookies()) {
            sb.append("Cookie:");
            sb.append(cookie.getName());
            sb.append(":");
            sb.append(cookie.getValue());
            sb.append("<br>");
        }
        sb.append("getMethod,"+request.getMethod()+"<br>");
        sb.append("getPathInfo,"+request.getPathInfo()+"<br>");
        sb.append("getQueryString,"+request.getQueryString()+"<br>");
        sb.append("getRequestURI,"+request.getRequestURI()+"<br>");
        return sb.toString();

    }

    @RequestMapping("response")
    @ResponseBody
    public String respomse(@CookieValue(value = "nowcoderid", defaultValue = "a") String nowcoderId,
                           @RequestParam(value = "key", defaultValue = "key") String key,
                               @RequestParam(value = "value", defaultValue = "value") String value,
                           HttpServletResponse response){

        response.addCookie(new Cookie(key, value));
        response.addHeader(key, value);
        return "NowcoderId From Cookie:" + nowcoderId;
    }

    //跳转
    @RequestMapping("/redirect/{code}")
    public RedirectView redirect(@PathVariable("code") int code){
        RedirectView red = new RedirectView("/", true);
        if(code == 301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }
    @RequestMapping("/redirect1/{code}")
    public String redirect(@PathVariable("code") int code,
                           HttpSession session){

        session.setAttribute("msg", "JUMP from ");
        return "redirect:/";
    }

    @RequestMapping("/admin")
    @ResponseBody
    public String admin(@RequestParam(value = "key", required = false)String key){
        if("admin".equals(key)){
            return "hello admin";
        }
        throw new IllegalArgumentException("key error");
    }

    //统一错误处理
    @ExceptionHandler
    @ResponseBody
    public String error(Exception e){
        return "error:" + e.getMessage();
    }

    @RequestMapping(value = "/rw/", produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String respomse1(){
        return "你好是阿瑟东sss";
    }

}
