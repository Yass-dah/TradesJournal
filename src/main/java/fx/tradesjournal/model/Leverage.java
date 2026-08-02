package fx.tradesjournal.model;

public enum Leverage {
    L1_1("1:1 (No Leverage)", 1.0),
    L1_10("1:10", 10.0),
    L1_20("1:20", 20.0),
    L1_30("1:30", 30.0),
    L1_50("1:50", 50.0),
    L1_100("1:100", 100.0),
    L1_200("1:200", 200.0),
    L1_500("1:500", 500.0);

    private final String label;
    private final double value;

    Leverage(String label, double value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() { return label; }
    public double getValue() { return value; }

    @Override
    public String toString() {
        return label;
    }
}