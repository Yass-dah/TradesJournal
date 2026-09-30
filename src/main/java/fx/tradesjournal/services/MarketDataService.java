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
    // API URLs
    private static final String FX_API_URL = "https://api.frankfurter.dev/v2/rates?base=";
    private static final String YAHOO_API_URL = "https://query1.finance.yahoo.com/v8/finance/chart/";
    private static final String CRYPTO_API_URL = "https://api.binance.com/api/v3/ticker/price?symbol=";

    private final HttpClient httpClient;
    private final Gson gson = new Gson();

    // Constructor
    public MarketDataService() {
        httpClient = HttpClient.newHttpClient();
    }

    // Fetchers
    private Double fetchForexRateByPair(String pair) throws Exception {
        String url = FX_API_URL + DefaultSymbols.getCleanFxParameters(pair);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200)
            throw new RuntimeException("Error retrieving live data from Forex API: HTTP " + response.statusCode());
        JsonArray jsonArray = gson.fromJson(response.body(), JsonArray.class);
        JsonObject json = jsonArray.get(0).getAsJsonObject();
        return json.get("rate").getAsDouble();
    }

    public Map<String, Double> fetchForexRates() {
        Map<String, Double> rates = new HashMap<>();
        try {
            for(String pair: DefaultSymbols.getSections().get("FOREX"))
                rates.putIfAbsent(pair, fetchForexRateByPair(pair));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rates;
    }

    private Double fetchStockPriceByName(String stock) throws Exception {
        String url = YAHOO_API_URL + stock;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("User-Agent", "Mozilla/5.0").GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200)
            throw new RuntimeException("Error retrieving live data from Stocks API: HTTP " + response.statusCode());
        JsonObject obj = gson.fromJson(response.body(), JsonObject.class);
        return obj.getAsJsonObject("chart")
                .getAsJsonArray("result")
                .get(0).getAsJsonObject()
                .getAsJsonObject("meta")
                .get("regularMarketPrice").getAsDouble();
    }

    public Map<String, Double> fetchStocksPrices(){
        Map<String, Double> prices = new HashMap<>();
        try {
            for(String stock: DefaultSymbols.getSections().get("STOCKS"))
                prices.putIfAbsent(stock, fetchStockPriceByName(stock));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return prices;
    }

    private Double fetchETFPriceByName(String ETF) throws Exception {
        String url = YAHOO_API_URL + ETF;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("User-Agent", "Mozilla/5.0").GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200)
            throw new RuntimeException("Error retrieving live data from ETFs API: HTTP " + response.statusCode());
        JsonObject obj = gson.fromJson(response.body(), JsonObject.class);
        return obj.getAsJsonObject("chart")
                .getAsJsonArray("result")
                .get(0).getAsJsonObject()
                .getAsJsonObject("meta")
                .get("regularMarketPrice").getAsDouble();
    }

    public Map<String, Double> fetchETFsPrices(){
        Map<String, Double> prices = new HashMap<>();
        try {
            for(String stock: DefaultSymbols.getSections().get("ETFs"))
                prices.putIfAbsent(stock, fetchETFPriceByName(stock));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return prices;
    }

    private Double fetchCommodityPriceByName(String commodity) throws Exception {
        String url = YAHOO_API_URL + DefaultSymbols.getCleanCommodity(commodity);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("User-Agent", "Mozilla/5.0").GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200)
            throw new RuntimeException("Error retrieving live data from Commodities API: HTTP " + response.statusCode());
        JsonObject obj = gson.fromJson(response.body(), JsonObject.class);
        return obj.getAsJsonObject("chart")
                .getAsJsonArray("result")
                .get(0).getAsJsonObject()
                .getAsJsonObject("meta")
                .get("regularMarketPrice").getAsDouble();
    }

    public Map<String, Double> fetchCommoditiesPrices(){
        Map<String, Double> prices = new HashMap<>();
        try {
            for(String commodity: DefaultSymbols.getSections().get("COMMODITIES"))
                prices.putIfAbsent(commodity, fetchCommodityPriceByName(commodity));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return prices;
    }

    private Double fetchIndexPriceByName(String index) throws Exception {
        String url = YAHOO_API_URL + DefaultSymbols.getCleanIndex(index);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("User-Agent", "Mozilla/5.0").GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200)
            throw new RuntimeException("Error retrieving live data from Indices API: HTTP " + response.statusCode());
        JsonObject obj = gson.fromJson(response.body(), JsonObject.class);
        return obj.getAsJsonObject("chart")
                .getAsJsonArray("result")
                .get(0).getAsJsonObject()
                .getAsJsonObject("meta")
                .get("regularMarketPrice").getAsDouble();
    }

    public Map<String, Double> fetchIndicesPrices(){
        Map<String, Double> prices = new HashMap<>();
        try {
            for(String index: DefaultSymbols.getSections().get("INDICES"))
                prices.putIfAbsent(index, fetchIndexPriceByName(index));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return prices;
    }

    private Double fetchCryptoPriceByName(String crypto) throws Exception {
        String url = CRYPTO_API_URL + DefaultSymbols.getCleanCrypto(crypto);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200)
            throw new RuntimeException("Error retrieving live data from Cryptos API: HTTP " + response.statusCode());
        JsonObject json = gson.fromJson(response.body(), JsonObject.class);
        return json.get("price").getAsDouble();
    }

    public Map<String, Double> fetchCryptoPrices(){
        Map<String, Double> prices = new HashMap<>();
        try {
            for(String crypto: DefaultSymbols.getSections().get("CRYPTO"))
                prices.putIfAbsent(crypto, fetchCryptoPriceByName(crypto));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return prices;
    }
}
