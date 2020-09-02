package com.gft.stocks.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.stocks.dto.StockIndex;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static junit.framework.TestCase.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class StocksWebSocketTest {
    @LocalServerPort
    private Integer port;

    private WebSocketClient webSocketClient;

    private CompletableFuture<String> completableFuture;

    private ObjectMapper json = new ObjectMapper();

    @BeforeEach
    public void setup() {
        webSocketClient = new ReactorNettyWebSocketClient();
        completableFuture = new CompletableFuture<>();
    }

    @Test
    void shouldReceiveStockIndexThroughWebSocket() throws InterruptedException, JsonProcessingException, TimeoutException, ExecutionException {

        webSocketClient.execute(URI.create(getWsPath()), session -> session
                .receive()
                .map(WebSocketMessage::getPayloadAsText)
                .doOnNext(x -> completableFuture.complete(x)).then()).subscribe();

        StockIndex stockIndex = json.readValue(completableFuture.get(2, TimeUnit.SECONDS), StockIndex.class);

        assertTrue(stockIndex.getTicker().contains("DZIK"));
    }

    private String getWsPath() {
        return String.format("ws://localhost:%d/stock_indices", port);
    }
}
