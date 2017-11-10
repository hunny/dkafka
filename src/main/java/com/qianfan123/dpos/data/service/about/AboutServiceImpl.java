package com.qianfan123.dpos.data.service.about;

import org.springframework.stereotype.Service;

@Service
public class AboutServiceImpl implements AboutService {

  @Override
  public String about() {
    return "DPOS data to kafka.";
  }

}
