package main.controller;


import main.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SaleController {
    @Autowired
    private User user;
}
