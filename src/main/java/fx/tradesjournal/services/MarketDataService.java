package fx.tradesjournal.services;

import com.google.gson.*;
import fx.tradesjournal.model.DefaultSymbols;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class MarketDataService {
    private final HttpClient httpClient;
    private final Gson gson = new Gson();

    public MarketDataService() {
        httpClient = HttpClient.newHttpClient();
    }

    // Fetchers
    private Map<String, Double> fetchForexRatesByCurrency(String currency) throws Exception {
        String url = "https://api.frankfurter.dev/v2/rates?base=" + currency;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonArray jsonArray = gson.fromJson(response.body(), JsonArray.class);
        Map<String, Double> ratesMap = new HashMap<>();
        for (JsonElement element : jsonArray) {
            JsonObject obj = element.getAsJsonObject();
            String quote = currency + "/" + obj.get("quote").getAsString();
            double rate = obj.get("rate").getAsDouble();
            ratesMap.put(quote, rate);
        }
        return ratesMap;
    }

    public Map<String, Double> fetchForexRates() {
        try {
            for(String currency: DefaultSymbols.getSections().get("FOREX"))
                System.out.println(fetchForexRatesByCurrency(currency));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
