package com.gft.stocks.repository;

import com.gft.stocks.model.StockIndex;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StockIndexRepositoryTest {

    @Autowired
    private StockIndexRepository stockIndexRepository;

    @Test
    void shouldStoreAndRetrieveStockIndexFromRepository() {
        StockIndex stockIndex =
                new StockIndex("DZIKERINO", BigDecimal.ONE, BigDecimal.TEN, Timestamp.from(Instant.now()));

        assertFalse(stockIndexRepository.get("DZIKERINO").isPresent());

        stockIndexRepository.add(stockIndex);

        assertTrue(stockIndexRepository.get("DZIKERINO").isPresent());
    }

    @Test
    void shouldUpdateEntryWithTheSameTicker() {
        StockIndex stockIndex =
                new StockIndex("DZIK", BigDecimal.ONE, BigDecimal.TEN, Timestamp.from(Instant.now()));

        StockIndex stockIndexTwo =
                new StockIndex("DZIK", BigDecimal.TEN, BigDecimal.TEN, Timestamp.from(Instant.now()));

        stockIndexRepository.add(stockIndex);

        assertTrue(stockIndexRepository.get("DZIK").isPresent());
        assertEquals(stockIndexRepository.get("DZIK").get().getBidPrice(), BigDecimal.ONE);

        stockIndexRepository.add(stockIndexTwo);

        assertEquals(stockIndexRepository.get("DZIK").get().getBidPrice(), BigDecimal.TEN);
    }
}
