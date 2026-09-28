package fx.tradesjournal.model;

import java.util.List;
import java.util.Map;

public class DefaultSymbols {
    public static Map<String, List<String>> getSections() {
        return Map.of(
                "FOREX", List.of("EUR", "USD", "GBP", "JPY", "CHF", "AUD", "CAD", "NZD"),
                "STOCKS", List.of("AAPL", "MSFT", "NVDA", "AMZN", "GOOGL", "META", "TSLA", "BRK.B", "UNH", "JNJ"),
                "ETFs", List.of("SPY", "QQQ", "IWM", "VTI", "VOO", "EEM", "TLT", "GLD"),
                "COMMODITIES", List.of("XAU/USD", "XAG/USD", "WTI", "BRENT", "NG", "COPPER"),
                "INDICES", List.of("US500", "NAS100", "US30", "GER40", "UK100", "JP225"),
                "CRYPTO", List.of("BTC/USD", "ETH/USD", "SOL/USD", "XRP/USD", "BNB/USD")
        );
    }
}