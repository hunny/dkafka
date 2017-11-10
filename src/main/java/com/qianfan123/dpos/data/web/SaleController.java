package com.qianfan123.dpos.data.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaleController {

  @RequestMapping(value = {"/get"}, method = RequestMethod.GET)
  public String get() {
    return "sale info";
  }
  
}
