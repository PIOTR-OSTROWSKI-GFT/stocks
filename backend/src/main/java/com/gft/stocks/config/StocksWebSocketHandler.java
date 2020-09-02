package com.gft.stocks.config;

import com.gft.stocks.service.StockIndicesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Component("StocksWebSocketHandler")
@Slf4j
public class StocksWebSocketHandler implements WebSocketHandler {

    @Autowired
    private StockIndicesService stockIndicesService;


    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        return webSocketSession.send(stockIndicesService.stockIndicesEvents()
                .map(webSocketSession::textMessage))
                .and(webSocketSession.receive()
                        .map(WebSocketMessage::getPayloadAsText).log());
    }
}