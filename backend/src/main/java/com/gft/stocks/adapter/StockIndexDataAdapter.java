package com.gft.stocks.adapter;

import com.gft.stocks.model.StockIndex;
import reactor.core.publisher.Flux;


public interface StockIndexDataAdapter {
    public Flux<StockIndex> stockIndicesEvents();
}
