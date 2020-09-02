package com.gft.stocks.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockIndex {
    private String ticker;

    private BigDecimal bidPrice;

    private BigDecimal askPrice;

    private Timestamp eventTime;
}