package com.qianfan123.dpos.data.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(value = "/sale", //
produces = {
    MediaType.APPLICATION_JSON_UTF8_VALUE })
public class SaleController {

  @RequestMapping(value = {"/get/{shop}"}, method = RequestMethod.GET)
  public String get(@PathVariable String shop, //
      @RequestParam("uuid") String uuid) {
    return "sale info";
  }
  
}
