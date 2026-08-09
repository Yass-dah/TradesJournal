package fx.tradesjournal.model;

import java.util.List;
import java.util.Arrays;

public class DefaultSymbols {
    public static List<String> getAllSymbols() {
        return Arrays.asList(
                // Forex
                "EUR/USD", "GBP/USD", "USD/JPY", "USD/CHF", "AUD/USD", "USD/CAD", "NZD/USD",
                "EUR/GBP", "EUR/JPY", "GBP/JPY", "EUR/AUD", "GBP/CAD", "AUD/JPY", "EUR/CAD",

                // Stocks
                "AAPL", "MSFT", "NVDA", "AMZN", "GOOGL", "META", "TSLA", "BRK.B", "UNH", "JNJ",
                "JPM", "V", "PG", "XOM", "MA", "HD", "CVX", "MRK", "ABBV", "COST",
                "AMD", "NFLX", "INTC", "DIS", "BA", "NKE", "PEP", "KO", "BAC", "WMT",

                // ETFs
                "SPY", "QQQ", "IWM", "VTI", "VOO", "EEM", "TLT", "GLD", "SLV", "XLF", "XLK", "XLE", "ARKK",

                // Commodities
                "XAU/USD", "XAG/USD", "WTI", "BRENT", "NG", "COPPER", "CORN", "SOYBEAN", "WHEAT",

                // Indices
                "US500", "NAS100", "US30", "GER40", "UK100", "JP225",

                // Crypto
                "BTC/USD", "ETH/USD", "SOL/USD", "XRP/USD", "BNB/USD", "ADA/USD", "AVAX/USD", "DOGE/USD"
        );
    }
}