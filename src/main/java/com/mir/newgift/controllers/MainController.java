package com.mir.newgift.controllers;

import com.mir.newgift.dao.GiftSetDAO;
import com.mir.newgift.model.GiftSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class MainController {

    @Autowired
    private GiftSetDAO giftSetDAO;

    @GetMapping("/gifts")
    public String showFilter(){
        return "index";
    }

    @GetMapping("/gifts-search")
    public String searchGiftSets(@RequestParam String gender,
                         @RequestParam String age,
                         HttpServletRequest request, Model model) throws SQLException{
        giftSetDAO.createTemporaryViews();
        giftSetDAO.createGiftSets();
        giftSetDAO.delTemporaryViews();
        ArrayList<GiftSet> gs = giftSetDAO.getGiftSets(gender, age);
        model.addAttribute("giftSets", gs);
        return "result";

    }
}
