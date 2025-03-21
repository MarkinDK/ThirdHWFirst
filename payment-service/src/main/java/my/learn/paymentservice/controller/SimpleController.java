package my.learn.paymentservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class SimpleController {
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
