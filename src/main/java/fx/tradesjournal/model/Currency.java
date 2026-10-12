package fx.tradesjournal.model;

public enum Currency {
    EUR("EUR", "€"),
    USD("USD", "$"),
    GBP("GBP", "£"),
    CHF("CHF", "CHF"),
    JPY("JPY", "¥"),
    AUD("AUD", "A$"),
    CAD("CAD", "C$"),
    NZD("NZD", "NZ$"),
    ZAR("ZAR", "R");

    private final String label;
    private final String symbol;

    // Constructor
    Currency(String label, String symbol) {
        this.label = label;
        this.symbol = symbol;
    }

    // Getters
    public String getLabel() { return label; }
    public String getSymbol() { return symbol; }

    @Override
    public String toString() {
        return label;
    }
}