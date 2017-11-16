package com.qianfan123.dpos.data.service.salereturn;

import com.hd123.dpos.api.salereturn.SaleReturn;
import com.hd123.rumba.commons.util.converter.Converter;
import com.hd123.rumba.commons.util.converter.ConverterBuilder;

public class KafkaSaleReturn extends SaleReturn {

  private static final long serialVersionUID = 4797950368221033570L;

  public static final Converter<SaleReturn, KafkaSaleReturn> TO = ConverterBuilder//
      .newBuilder(SaleReturn.class, KafkaSaleReturn.class)//
      .build();

  private String _dcbustype;

  public String get_dcbustype() {
    return _dcbustype;
  }

  public void set_dcbustype(String _dcbustype) {
    this._dcbustype = _dcbustype;
  }

}
