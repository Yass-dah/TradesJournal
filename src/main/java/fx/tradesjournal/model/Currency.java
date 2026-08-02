package fx.tradesjournal.model;

public enum Currency {
    EUR("EUR (€)", "€"),
    USD("USD ($)", "$"),
    GBP("GBP (£)", "£"),
    CHF("CHF (CHF)", "CHF"),
    JPY("JPY (¥)", "¥"),
    AUD("AUD ($)", "$"),
    CAD("CAD ($)", "$"),
    NZD("NZD ($)", "$"),
    ZAR("ZAR (R)", "R");

    private final String label;
    private final String symbol;

    Currency(String label, String symbol) {
        this.label = label;
        this.symbol = symbol;
    }

    public String getLabel() { return label; }
    public String getSymbol() { return symbol; }

    @Override
    public String toString() {
        return label;
    }
}