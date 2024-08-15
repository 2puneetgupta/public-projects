import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProcessRule {
    public static void main(String args[]){
        List<Stock> csvdata = new ReadStockData().processInputFile("C:\\Users\\Lenovo\\Downloads\\RELIANCE.NS.csv");
        System.out.println(csvdata.stream().findFirst());

        //scenario 1 : buy at 5% down and sell at 5% up
        // print positions at last wiht profit and loss
        double lastBuyPrice=0;
        double lastSellPrice=0;
        double profit=0;
        double prevClose=0;
        TreeMap<LocalDate,Stock> trades = new TreeMap<>();
        for(Stock s : csvdata){
            if(lastBuyPrice ==0 || lastBuyPrice > s.getClose() + 5 || lastSellPrice > s.getClose() + 5){
                lastBuyPrice = s.getClose();
                s.setBuySellInd("B");
                trades.put(s.getDate(),s);
            }

            if(  ( lastSellPrice !=0 && lastSellPrice + 5 < s.getClose() )|| lastBuyPrice +5 < s.getClose()  ){
                lastSellPrice = s.getClose();
                s.setBuySellInd("S");
                trades.put(s.getDate(),s);
            }
        }
        calculateProfits(trades);

      //  System.out.println(trades);
        System.out.println("lastBuyPrice: "+lastBuyPrice);
        System.out.println("lastSellPrice: "+lastSellPrice);
    }

    public static void calculateProfits(TreeMap<LocalDate, Stock> trades){
        double boughtValue=0;int boughtShares=0;int totalboughtShares=0;
        double soldValue=0;int soldShares=0;
        for(Map.Entry<LocalDate, Stock> t : trades.entrySet()){
            Stock value = t.getValue();
            if("B".equalsIgnoreCase(value.getBuySellInd())){
                boughtShares ++;
                boughtValue = boughtValue + value.getClose();
                totalboughtShares ++;
            }

            if(boughtShares > 0 && "S".equalsIgnoreCase(value.getBuySellInd())){
                soldShares = soldShares + boughtShares ;
                soldValue = soldValue + boughtShares * value.getClose();
                boughtShares =0;
            }
        }

        System.out.println("totalboughtShares: "+totalboughtShares);
        System.out.println("boughtValue: "+boughtValue);
        System.out.println("soldShares: "+soldShares);
        System.out.println("soldValue: "+soldValue);
        System.out.println("Remaining boughtShares: "+boughtShares);

        System.out.println("Profit : "+ (soldValue - boughtValue));
    }

}
