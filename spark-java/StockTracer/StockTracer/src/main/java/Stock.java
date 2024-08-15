import java.time.LocalDate;
import java.util.Objects;

public class Stock {
    //Date	Open	High	Low	Close	Adj Close	Volume

    private String symbol;
    private String name;
    private LocalDate date;
    private double open;
    private double high;
    private double low;
    private double close;
    private double adjClose;
    private long volume;
    private String buySellInd;
    public String getBuySellInd() {
        return buySellInd;
    }

    public void setBuySellInd(String buySellInd) {
        this.buySellInd = buySellInd;
    }



    public Stock(String symbol,String name,String allValues[]) {
        this.symbol = symbol;
        this.name = name;
        this.date = LocalDate.parse(allValues[0]);
        this.open = Double.parseDouble(allValues[1]);
        this.high = Double.parseDouble(allValues[2]);
        this.low = Double.parseDouble(allValues[3]);
        this.close = Double.parseDouble(allValues[4]);
        this.adjClose = Double.parseDouble(allValues[5]);
        this.volume = Long.parseLong(allValues[6]);
    }

    public Stock(String symbol, String name, LocalDate date, double open, double high, double low, double close, double adjClose, long volume) {
        this.symbol = symbol;
        this.name = name;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.adjClose = adjClose;
        this.volume = volume;
    }


    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getAdjClose() {
        return adjClose;
    }

    public void setAdjClose(double adjClose) {
        this.adjClose = adjClose;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return symbol.equals(stock.symbol) && name.equals(stock.name) && date.equals(stock.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, name, date);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", adjClose=" + adjClose +
                ", volume=" + volume +
                ", buySellInd=" + buySellInd +
                '}';
    }


}
