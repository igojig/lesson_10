package com.geekbrains.spring.web.cart.controllers;

import com.geekbrains.spring.web.cart.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
@AllArgsConstructor
public class DemoController {
    private final CartService cartService;
    @GetMapping("/{productId}")
    public void add(@PathVariable Long productId) {
        cartService.addToCart("___", productId);
    }
}
