package fx.tradesjournal.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class MarketDataService {
    private HttpClient httpClient;

    public MarketDataService() {
        httpClient = HttpClient.newHttpClient();
    }

    // Fetchers
    public Map<String, Double> fetchForexRates() throws Exception {
        String url = "https://api.frankfurter.dev/v2/rates?base=eur";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        return null;
    }

    public Map<String, Double> fetchStocksPrices(){
        return null;
    }

    public Map<String, Double> fetchETFsPrices(){
        return null;
    }

    public Map<String, Double> fetchCommoditiesPrices(){
        return null;
    }

    public Map<String, Double> fetchIndicesPrices(){
        return null;
    }

    public Map<String, Double> fetchMarketPrices(){
        return null;
    }
}
