package com.qianfan123.dpos.data.service.sale;

import com.hd123.dpos.api.sale.Sale;
import com.hd123.rumba.commons.util.converter.Converter;
import com.hd123.rumba.commons.util.converter.ConverterBuilder;

public class KafkaSale extends Sale {

  private static final long serialVersionUID = 4797950368221033570L;

  public static final Converter<Sale, KafkaSale> TO = ConverterBuilder//
      .newBuilder(Sale.class, KafkaSale.class)//
      .build();

  private String _dcbustype;

  public String get_dcbustype() {
    return _dcbustype;
  }

  public void set_dcbustype(String _dcbustype) {
    this._dcbustype = _dcbustype;
  }

}
