package com.mir.newgift.controllers;

import com.mir.newgift.dao.GiftSetDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.sql.SQLException;

@Controller
public class MainController {

    @Autowired
    private GiftSetDAO giftSetDAO;

    @GetMapping("/gifts")
    public String hello(Model model) throws SQLException {
        giftSetDAO.createTemporaryViews();
        giftSetDAO.createGiftSets();
        giftSetDAO.delTemporaryViews();
        model.addAttribute("giftSets", giftSetDAO.getGiftSets());
        return "result";
    }
}
