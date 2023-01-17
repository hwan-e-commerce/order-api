package co.hwan.order.app.item.stock.service;

import co.hwan.order.app.item.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class StockService {
    private final StockRepository stockRepository;

}
