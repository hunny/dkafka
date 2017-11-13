package com.qianfan123.dpos.data.service.sale;

import com.hd123.dpos.api.sale.Sale;

public class KafkaSale extends Sale {

  private static final long serialVersionUID = 4797950368221033570L;

  private String _dcbustype;

  public String get_dcbustype() {
    return _dcbustype;
  }

  public void set_dcbustype(String _dcbustype) {
    this._dcbustype = _dcbustype;
  }

}
