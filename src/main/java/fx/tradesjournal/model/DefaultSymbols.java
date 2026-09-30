package fx.tradesjournal.model;

import java.util.List;
import java.util.Map;

public class DefaultSymbols {
    public static String getCleanFxParameters(String pair){
        if(pair == null)
            return "";
        return pair.replace("/", "&quotes=");
    }

    public static String getCleanCommodity(String commodity){
        String cleanCommodity;
        cleanCommodity = switch(commodity) {
            case null -> "";
            case "XAU/USD" -> "GC=F";
            case "XAG/USD" -> "SI=F";
            case "WTI" -> "CL=F";
            case "BRENT" -> "BZ=F";
            case "NG" -> "NG=F";
            case "COPPER" -> "HG=F";
            default -> commodity.trim().toUpperCase();
        };
        return cleanCommodity;
    }

    public static String getCleanIndex(String index){
        String cleanIndex;
        cleanIndex = switch(index) {
            case null -> "";
            case "US500" -> "ES=F";
            case "NAS100" -> "NQ=F";
            case "US30" -> "YM=F";
            case "GER40" -> "%5EGDAXI";
            case "UK100" -> "%5EFTSE";
            case "JP225" -> "%5EN225";
            default -> index.trim().toUpperCase();
        };
        return cleanIndex;
    }

    public static String getCleanCrypto(String crypto){
        return crypto.replace("/", "") + "T";
    }

    public static Map<String, List<String>> getSections() {
        return Map.of(
                "FOREX", List.of("EUR/USD", "EUR/GBP", "EUR/JPY", "EUR/CHF", "EUR/AUD", "EUR/CAD", "EUR/NZD",
                                    "USD/GBP", "USD/JPY", "USD/CHF", "USD/CAD",
                                    "GBP/JPY", "GBP/CHF", "GBP/AUD", "GBP/CAD", "GBP/NZD",
                                    "CHF/JPY",
                                    "AUD/USD", "AUD/JPY", "AUD/CHF", "AUD/CAD", "AUD/NZD",
                                    "CAD/JPY", "CAD/CHF",
                                    "NZD/USD", "NZD/JPY"),
                "STOCKS", List.of("AAPL", "MSFT", "NVDA", "AMZN", "GOOGL", "META", "TSLA", "BRK-B", "UNH", "JNJ"),
                "ETFs", List.of("SPY", "QQQ", "IWM", "VTI", "VOO", "EEM", "TLT", "GLD"),
                "COMMODITIES", List.of("XAU/USD", "XAG/USD", "WTI", "BRENT", "NG", "COPPER"),
                "INDICES", List.of("US500", "NAS100", "US30", "GER40", "UK100", "JP225"),
                "CRYPTO", List.of("BTC/USD", "ETH/USD", "SOL/USD", "XRP/USD", "BNB/USD")
        );
    }
}