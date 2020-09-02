package com.gft.stocks.repository;

import com.gft.stocks.model.StockIndex;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class StockIndexRepository {
    private final Map<String, StockIndex> entities = new ConcurrentHashMap<>();

    public void add(StockIndex stockIndex) {
        entities.put(stockIndex.getTicker(), stockIndex);
    }

    public Optional<StockIndex> get(String ticker) {
        return entities.values().stream().filter(i -> i.getTicker().equals(ticker)).findFirst();
    }
}
