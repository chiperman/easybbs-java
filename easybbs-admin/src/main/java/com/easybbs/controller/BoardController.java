package com.easybbs.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
public class BoardController {
    @RequestMapping("/loadBoard")
    public String loadBoard() {
        return "hello";
    }
}
