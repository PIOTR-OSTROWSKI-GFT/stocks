package com.gft.stocks.adapter;

import com.gft.stocks.model.StockIndex;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimulatedStockIndexDataAdapterTest {

    @Autowired
    private StockIndexDataAdapter stockIndexDataAdapter;

    @Test
    void shouldGenerateEventsWithBidAndAskPriceWithinRange() {

        StockIndex stockIndex = stockIndexDataAdapter.stockIndicesEvents().next().block(Duration.ofMillis(2000));

        assertTrue(stockIndex.getBidPrice().compareTo(BigDecimal.valueOf(20.0)) == -1
                && stockIndex.getBidPrice().compareTo(BigDecimal.valueOf(0.0)) == 1);

        assertTrue(stockIndex.getAskPrice().compareTo(BigDecimal.valueOf(20.0)) == -1
                && stockIndex.getAskPrice().compareTo(BigDecimal.valueOf(0.0)) == 1);
    }
}
