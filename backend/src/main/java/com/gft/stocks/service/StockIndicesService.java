package com.gft.stocks.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.stocks.adapter.StockIndexDataAdapter;
import com.gft.stocks.dto.StockIndex;
import com.gft.stocks.dto.Trend;
import com.gft.stocks.repository.StockIndexRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
@Slf4j
public class StockIndicesService {

    @Autowired
    private StockIndexRepository stockIndexRepository;

    @Autowired
    private StockIndexDataAdapter stockIndexDataAdapter;

    private static final ObjectMapper json = new ObjectMapper();

    public Flux<String> stockIndicesEvents() {
        return stockIndexDataAdapter.stockIndicesEvents().map(this::modelToDTOString);
    }

    private String modelToDTOString(com.gft.stocks.model.StockIndex stockIndex) {

        BigDecimal newMarketPrice = calculateMarketPrice(stockIndex.getAskPrice(), stockIndex.getBidPrice());
        Trend newTrend = calculateTrend(stockIndex.getTicker(), newMarketPrice);

        stockIndexRepository.add(stockIndex);

        try {
            return json.writeValueAsString(StockIndex.builder()
                    .ticker(stockIndex.getTicker())
                    .price(newMarketPrice)
                    .trend(newTrend)
                    .build());
        } catch (JsonProcessingException e) {
            log.error("Couldn't serialize StockIndex to JSON", e);
            throw new IllegalStateException("Couldn't serialize StockIndex to JSON", e);
        }
    }

    private Trend calculateTrend(String ticker, BigDecimal newMarketPrice) {
        Trend trend;

        if (stockIndexRepository.get(ticker).isPresent()) {
            var previousStockIndexRecord = stockIndexRepository.get(ticker).get();

            switch (calculateMarketPrice(previousStockIndexRecord.getAskPrice(), previousStockIndexRecord.getBidPrice())
                    .compareTo(newMarketPrice)) {
                case 1:
                    trend = Trend.DOWN;
                    break;
                case -1:
                    trend = Trend.UP;
                    break;
                case 0:
                default:
                    trend = Trend.STATUS_QUO;
                    break;
            }
        } else {
            trend = Trend.STATUS_QUO;
        }

        return trend;
    }

    private BigDecimal calculateMarketPrice(BigDecimal askPrice, BigDecimal bidPrice) {
        return askPrice.add(bidPrice).divide(BigDecimal.valueOf(2));
    }
}
