package my.learn.orderservice.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class SimpleController {
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
