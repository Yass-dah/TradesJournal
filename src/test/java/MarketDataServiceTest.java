import fx.tradesjournal.services.MarketDataService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MarketDataServiceTest {
    static MarketDataService marketDataService;

    @BeforeAll
    static void setUpService() throws Exception {
        marketDataService = new MarketDataService();
    }

    @Test
    @DisplayName("Forex API reachable and returns data")
    void forexApiReachable() {
        Map<String, Double> data = marketDataService.fetchForexRates();
        assertNotNull(data);
        assertFalse(data.isEmpty());
    }

    @Test
    @DisplayName("Stocks API reachable and returns data")
    void stocksApiReachable() {
        Map<String, Double> data = marketDataService.fetchStocksPrices();
        assertNotNull(data);
        assertFalse(data.isEmpty());
    }

    @Test
    @DisplayName("ETFs API reachable and returns data")
    void ETFsApiReachable() {
        Map<String, Double> data = marketDataService.fetchETFsPrices();
        assertNotNull(data);
        assertFalse(data.isEmpty());
    }

    @Test
    @DisplayName("Commodities API reachable and returns data")
    void commoditiesApiReachable() {
        Map<String, Double> data = marketDataService.fetchCommoditiesPrices();
        assertNotNull(data);
        assertFalse(data.isEmpty());
    }

    @Test
    @DisplayName("Indeces API reachable and returns data")
    void indicesApiReachable() {
        Map<String, Double> data = marketDataService.fetchIndicesPrices();
        assertNotNull(data);
        assertFalse(data.isEmpty());
    }

    @Test
    @DisplayName("Crypto API reachable and returns data")
    void cryptoApiReachable() {
        Map<String, Double> data = marketDataService.fetchCryptoPrices();
        assertNotNull(data);
        assertFalse(data.isEmpty());
    }
}
