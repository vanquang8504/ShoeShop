package com.example.shopgiayvip.Controller;

import com.example.shopgiayvip.Entity.ChucVu;
import com.example.shopgiayvip.Entity.GioHang;
import com.example.shopgiayvip.Entity.NguoiDung;
import com.example.shopgiayvip.Entity.NguoiDungDetails;
import com.example.shopgiayvip.Repository.GiayCTRepo;
import com.example.shopgiayvip.Repository.NguoiDungRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.ConditionalOnGraphQlSchema;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class ControllerMap {
    @Autowired
    GiayCTRepo giayCTService;

    String error1 = "";
    @GetMapping("/admin")
    public String getAdminPage(){
        return "redirect:/admin/";
    }
    @GetMapping("/checkLogin")
    public String checLogin(Authentication authentication){
        NguoiDungDetails nguoiDungDetails = (NguoiDungDetails) authentication.getPrincipal();
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Admin")) ){
            System.out.println(nguoiDungDetails.getUser().getTrangThai());
            return "redirect:/admin/adminPage";
        } else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("User"))){
            return "redirect:/";
        }
        return "";
    }
    @GetMapping("/admin/adminPage")
    public String RedirectPage(Authentication authentication, Model model){
        NguoiDungDetails userDetails = (NguoiDungDetails) authentication.getPrincipal();
        model.addAttribute("User",userDetails.getUser());
        return "admin/adminPage";
    }
    @GetMapping("/login")
    public String getLoginPage( Model model, @RequestParam(value = "error",required = false) String error,Authentication authentication){
        System.out.println(error1);
        if (error != null) {
            model.addAttribute("error", "Đăng nhập thất bại!");
        }
        return "login";
    }

    @GetMapping("/")
    public String getIndex(Model model, @RequestParam(value = "page",defaultValue = "0") Integer page,Authentication authentication){
        Pageable pageable = PageRequest.of(page, 12);
        model.addAttribute("listCTSP", giayCTService.findAll(pageable));
        if(authentication != null){
            NguoiDungDetails userDetails = (NguoiDungDetails) authentication.getPrincipal();
            model.addAttribute("user",userDetails.getUser());
        }else {
            model.addAttribute("user",new NguoiDung(null,new ChucVu(),"","Welcome","","","",false,new Date(),new Date()));
        }
        return "user/shop";
    }

    @GetMapping("/shop")
    public String getShopPage(Model model, @RequestParam(value = "page",defaultValue = "0") Integer page,Authentication authentication) {
        Pageable pageable = PageRequest.of(page, 12);
        model.addAttribute("listCTSP", giayCTService.findAll(pageable));
        if(authentication != null){
            NguoiDungDetails userDetails = (NguoiDungDetails) authentication.getPrincipal();
            model.addAttribute("user",userDetails.getUser().getEnmail());
        }else {
            model.addAttribute("user","Welcome");
        }
        return "user/shop";
    }
    @GetMapping("/cart")
    public String cartPage(Authentication authentication, Model model){
        if(authentication != null){
            NguoiDungDetails userDetails = (NguoiDungDetails) authentication.getPrincipal();
            model.addAttribute("user",userDetails.getUser());
        }else {
            model.addAttribute("user",new NguoiDung(null,new ChucVu(),"","Welcome","","","",false,new Date(),new Date()));
        }
        return "user/cart";
    }
    @GetMapping("/admin/addSP")
    public String addSP(){
        return "admin/adminAdd";
    }

}
