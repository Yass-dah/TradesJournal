package fx.tradesjournal.services;

import fx.tradesjournal.model.Trade;
import java.util.Map;


public class CalculationService {
    private static final Map<String, Double> EUR_RATES = Map.of(
            "EUR", 1.0,       // Base
            "USD", 1.08,      // 1 EUR = 1.08 USD
            "GBP", 0.85,      // 1 EUR = 0.85 GBP
            "CHF", 0.95,      // 1 EUR = 0.95 CHF
            "CAD", 1.48,      // 1 EUR = 1.48 CAD
            "AUD", 1.65,      // 1 EUR = 1.65 AUD
            "NZD", 1.78,      // 1 EUR = 1.78 NZD
            "JPY", 168.0,     // 1 EUR = 168.0 JPY
            "ZAR", 20.0       // 1 EUR = 20.0 ZAR
    );

    public record PositionSizeResult(
            double positionSize,    // Size calcolata (Lotti, Quote, Monete, Contratti)
            double requiredMargin,  // Margine nella valuta del CONTO
            String unitLabel,       // Etichetta ("Lotti", "Quote", "BTC", ecc.)
            String quoteCurrency,   // Valuta con cui è quotato lo strumento
            double riskAmountAccountCurrency // Rischio monetario effettivo nella valuta del conto
    ) {}

    public record SymbolInfo(
            String symbol,
            String category,      // "FOREX", "STOCKS", "ETFs", "COMMODITIES", "INDICES", "CRYPTO"
            String quoteCurrency  // "USD", "EUR", "GBP", "JPY"
    ){}

    // Profit/Loss calculation methods
    private static double getProfitLossValue(Double value) {
        return value == null ? 0.0 : value;
    }

    private static double getTradeNet(Trade trade) {
        return getProfitLossValue(trade.getProfitLoss()) - trade.getFees();
    }

    public static double addedTrade(double capital, Trade trade) {
        return capital + getTradeNet(trade);
    }

    public static double editedTrade(double capital, Trade oldTrade, Trade newTrade) {
        return addedTrade(deletedTrade(capital, oldTrade), newTrade);
    }

    public static double deletedTrade(double capital, Trade trade) {
        return capital - getTradeNet(trade);
    }

    // Capital currency converter
    private static double convertAccountToTargetCurrency(
            double amount,
            String accountCurrency,
            String targetCurrency) {

        if (accountCurrency == null || targetCurrency == null) return amount;

        String from = accountCurrency.toUpperCase().trim();
        String to = targetCurrency.toUpperCase().trim();

        if (from.equals(to)) return amount;

        double rateFrom = EUR_RATES.getOrDefault(from, 1.0);
        double rateTo = EUR_RATES.getOrDefault(to, 1.0);

        // Converti prima da 'from' ad EUR, poi da EUR a 'to'
        return (amount / rateFrom) * rateTo;
    }

    // Calculators
    public static PositionSizeResult calculateForex(
            double balance, double leverage, SymbolInfo symbolInfo,
            double price, double riskPercent, double slPips, String accountCurrency) {

        double riskInAccountCurrency = balance * (riskPercent / 100.0);
        double riskInQuoteCurrency = convertAccountToTargetCurrency(riskInAccountCurrency, accountCurrency, symbolInfo.quoteCurrency());

        // Determinazione del Pip Value per 1.0 Lotto Standard (100.000 unità)
        double pipValue = symbolInfo.quoteCurrency().equalsIgnoreCase("JPY") ? 1000.0 : 10.0;; // Default per XXX/USD

        // Lot Size = Rischio Monetario / (SL Pips * Valore del Pip)
        double lotSize = riskInQuoteCurrency / (slPips * pipValue);

        // Margine = (Lotti * 100.000 * Prezzo) / Leva
        double marginInQuoteCurrency = (lotSize * 100_000.0 * price) / leverage;
        double marginInAccountCurrency = convertAccountToTargetCurrency(marginInQuoteCurrency, symbolInfo.quoteCurrency(), accountCurrency);

        return new PositionSizeResult(lotSize, marginInAccountCurrency, "Lotti", symbolInfo.quoteCurrency(), riskInAccountCurrency);
    }

    public static PositionSizeResult calculateStocksAndETFs(
            double balance, double leverage, SymbolInfo symbolInfo,
            double price, double riskPercent, double slPips, String accountCurrency) {

        double riskInAccountCurrency = balance * (riskPercent / 100.0);
        double riskInQuoteCurrency = convertAccountToTargetCurrency(riskInAccountCurrency, accountCurrency, symbolInfo.quoteCurrency());

        // Per le azioni slPips indica la distanza di prezzo in valuta (es. SL a $5 di distanza)
        double shares = riskInQuoteCurrency / slPips;

        // Margine = (Numero Quote * Prezzo) / Leva
        double marginInQuoteCurrency = (shares * price) / leverage;
        double marginInAccountCurrency = convertAccountToTargetCurrency(marginInQuoteCurrency, symbolInfo.quoteCurrency(), accountCurrency);

        return new PositionSizeResult(shares, marginInAccountCurrency, "Quote", symbolInfo.quoteCurrency(), riskInAccountCurrency);
    }

    public static PositionSizeResult calculateCrypto(
            double balance, double leverage, SymbolInfo symbolInfo,
            double price, double riskPercent, double slPips, String accountCurrency) {

        double riskInAccountCurrency = balance * (riskPercent / 100.0);
        double riskInQuoteCurrency = convertAccountToTargetCurrency(riskInAccountCurrency, accountCurrency, symbolInfo.quoteCurrency());

        // slPips = Distanza di prezzo in $ (es. BTC Entry 60000, SL 58000 -> slPips = 2000)
        double coins = riskInQuoteCurrency / slPips;

        String unitLabel = symbolInfo.symbol().split("/")[0].replace("($)", "");
        double marginInQuoteCurrency = (coins * price) / leverage;
        double marginInAccountCurrency = convertAccountToTargetCurrency(marginInQuoteCurrency, symbolInfo.quoteCurrency(), accountCurrency);

        return new PositionSizeResult(coins, marginInAccountCurrency, unitLabel, symbolInfo.quoteCurrency(), riskInAccountCurrency);
    }

    public static PositionSizeResult calculateCommodities(
            double balance, double leverage, SymbolInfo symbolInfo,
            double price, double riskPercent, double slPips, String accountCurrency) {

        double riskInAccountCurrency = balance * (riskPercent / 100.0);
        double riskInQuoteCurrency = convertAccountToTargetCurrency(riskInAccountCurrency, accountCurrency, symbolInfo.quoteCurrency());

        double contracts;
        String label = "Contratti";
        double multiplier = 1.0;

        String sym = symbolInfo.symbol().toUpperCase();
        if (sym.contains("XAU") || sym.contains("GOLD")) {
            label = "Lotti (100 oz)";
            // Oro: 1 Pip (0.10$) = $10 per lotto
            contracts = riskInQuoteCurrency / (slPips * 10.0);
            multiplier = 100.0;
        } else if (sym.contains("XAG") || sym.contains("SILVER")) {
            label = "Lotti (5000 oz)";
            // Argento: 1 Pip (0.01$) = $50 per lotto
            contracts = riskInQuoteCurrency / (slPips * 50.0);
            multiplier = 5000.0;
        } else {
            // Petrolio (WTI/BRENT), Gas Naturale
            contracts = riskInQuoteCurrency / slPips;
        }

        double marginInQuoteCurrency = (contracts * multiplier * price) / leverage;
        double marginInAccountCurrency = convertAccountToTargetCurrency(marginInQuoteCurrency, symbolInfo.quoteCurrency(), accountCurrency);

        return new PositionSizeResult(contracts, marginInAccountCurrency, label, symbolInfo.quoteCurrency(), riskInAccountCurrency);
    }

    public static PositionSizeResult calculateIndices(
            double balance, double leverage, SymbolInfo symbolInfo,
            double price, double riskPercent, double slPips, String accountCurrency) {

        double riskInAccountCurrency = balance * (riskPercent / 100.0);
        double riskInQuoteCurrency = convertAccountToTargetCurrency(riskInAccountCurrency, accountCurrency, symbolInfo.quoteCurrency());

        // slPips rappresenta i Punti Indice
        double contracts = riskInQuoteCurrency / slPips;

        double marginInQuoteCurrency = (contracts * price) / leverage;
        double marginInAccountCurrency = convertAccountToTargetCurrency(marginInQuoteCurrency, symbolInfo.quoteCurrency(), accountCurrency);

        return new PositionSizeResult(contracts, marginInAccountCurrency, "Contratti", symbolInfo.quoteCurrency(), riskInAccountCurrency);
    }

    // Calculators handler
    public static PositionSizeResult calculateSize(
            String accountCurrency,
            double balance,
            double riskPercent,
            double slPips,
            SymbolInfo symbolInfo,
            double price,
            double leverage) {

        if (balance <= 0 || leverage <= 0 || price <= 0 || riskPercent <= 0 || slPips <= 0 || symbolInfo == null)
            return new PositionSizeResult(0.0, 0.0, "-", "USD", 0.0);

        return switch (symbolInfo.category().toUpperCase()) {
            case "FOREX"       -> calculateForex(balance, leverage, symbolInfo, price, riskPercent, slPips, accountCurrency);
            case "STOCKS", "ETFS" -> calculateStocksAndETFs(balance, leverage, symbolInfo, price, riskPercent, slPips, accountCurrency);
            case "CRYPTO"      -> calculateCrypto(balance, leverage, symbolInfo, price, riskPercent, slPips, accountCurrency);
            case "COMMODITIES" -> calculateCommodities(balance, leverage, symbolInfo, price, riskPercent, slPips, accountCurrency);
            case "INDICES"     -> calculateIndices(balance, leverage, symbolInfo, price, riskPercent, slPips, accountCurrency);
            default            -> calculateStocksAndETFs(balance, leverage, symbolInfo, price, riskPercent, slPips, accountCurrency);
        };
    }
}
