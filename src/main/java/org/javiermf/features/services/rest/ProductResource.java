package org.javiermf.features.services.rest;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductResource {

    @RequestMapping("/products/")
    public String home() {
        return "Hello World!";
    }


}
