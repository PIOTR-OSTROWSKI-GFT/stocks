package com.gft.stocks.adapter;

import com.gft.stocks.model.StockIndex;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;

@Repository
public class SimulatedStockIndexDataAdapter implements StockIndexDataAdapter {

    private final Long EVENTS_INTERVAL = 1000L;
    private final String INDEX_PREFIX = "DZIK-";

    private Random random = new Random();

    @Override
    public Flux<StockIndex> stockIndicesEvents() {
        Flux<com.gft.stocks.model.StockIndex> eventFlux = Flux.generate(sink ->
                sink.next(new com.gft.stocks.model.StockIndex(
                        INDEX_PREFIX + (random.nextInt() & Integer.MAX_VALUE) % 10,
                        BigDecimal.valueOf(random.nextDouble() * 20),
                        BigDecimal.valueOf(random.nextDouble() * 20),
                        Timestamp.from(Instant.now()))
                )
        );

        return Flux.interval(Duration.ofMillis(EVENTS_INTERVAL))
                .zipWith(eventFlux, (time, event) -> event);
    }
}
