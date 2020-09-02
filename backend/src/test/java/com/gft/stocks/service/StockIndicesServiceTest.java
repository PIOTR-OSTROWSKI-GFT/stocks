package com.gft.stocks.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.stocks.adapter.StockIndexDataAdapter;
import com.gft.stocks.dto.StockIndex;
import com.gft.stocks.dto.Trend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StockIndicesServiceTest {

    @Autowired
    private StockIndicesService stockIndicesService;

    @MockBean
    private StockIndexDataAdapter stockIndexDataAdapter;

    private ObjectMapper json = new ObjectMapper();

    @Test
    void shouldReturnStockIndicesDTOSerializedToJSON() throws JsonProcessingException {
        when(stockIndexDataAdapter.stockIndicesEvents()).thenReturn(Flux.just(
                new com.gft.stocks.model.StockIndex(
                        "DZIK", BigDecimal.ONE, BigDecimal.ONE, Timestamp.from(Instant.now()))
        ));

        String stockIndexJSON = stockIndicesService.stockIndicesEvents().next().block(Duration.ofMillis(2000));
        StockIndex stockIndex = json.readValue(stockIndexJSON, StockIndex.class);

        assertTrue(stockIndex.getTicker().equals("DZIK"));
    }

    @Test
    void shouldCalculateTrendProperly() throws JsonProcessingException {

        when(stockIndexDataAdapter.stockIndicesEvents()).thenReturn(Flux.just(
                new com.gft.stocks.model.StockIndex(
                        "DZIK", BigDecimal.ONE, BigDecimal.ONE, Timestamp.from(Instant.now()))
        ));

        String stockIndexJSON = stockIndicesService.stockIndicesEvents().next().block(Duration.ofMillis(2000));
        StockIndex stockIndex = json.readValue(stockIndexJSON, StockIndex.class);

        assertTrue(stockIndex.getTrend().equals(Trend.STATUS_QUO));

        when(stockIndexDataAdapter.stockIndicesEvents()).thenReturn(Flux.just(
                new com.gft.stocks.model.StockIndex(
                        "DZIK", BigDecimal.TEN, BigDecimal.TEN, Timestamp.from(Instant.now()))
        ));

        String stockIndexJSON2 = stockIndicesService.stockIndicesEvents().next().block(Duration.ofMillis(2000));
        StockIndex stockIndex2 = json.readValue(stockIndexJSON2, StockIndex.class);

        assertTrue(stockIndex2.getTrend().equals(Trend.UP));

        when(stockIndexDataAdapter.stockIndicesEvents()).thenReturn(Flux.just(
                new com.gft.stocks.model.StockIndex(
                        "DZIK", BigDecimal.ONE, BigDecimal.ONE, Timestamp.from(Instant.now()))
        ));

        String stockIndexJSON3 = stockIndicesService.stockIndicesEvents().next().block(Duration.ofMillis(2000));
        StockIndex stockIndex3 = json.readValue(stockIndexJSON3, StockIndex.class);

        assertTrue(stockIndex3.getTrend().equals(Trend.DOWN));
    }

    @Test
    void shouldCalculateMarketPriceProperly() throws JsonProcessingException {

        when(stockIndexDataAdapter.stockIndicesEvents()).thenReturn(Flux.just(
                new com.gft.stocks.model.StockIndex(
                        "DZIK", BigDecimal.ONE, BigDecimal.TEN, Timestamp.from(Instant.now()))
        ));

        String stockIndexJSON = stockIndicesService.stockIndicesEvents().next().block(Duration.ofMillis(2000));
        StockIndex stockIndex = json.readValue(stockIndexJSON, StockIndex.class);

        assertTrue(stockIndex.getPrice().equals(BigDecimal.valueOf(5.5)));
    }
}
