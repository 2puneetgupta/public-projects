import java.util.Map;
import java.util.Objects;

public class AccountPojo
{
    private String CompanyNumber;
    private String AccountDate;
    private Map<String,String> TURNOVER;
    private Map<String,String> CURRENT_ASSETS;
    private Map<String,String>  SHAREHOLDERS_FUNDS;
    private Map<String,String>  FIXED_ASSETS;
    private Map<String,String>  CREDITORS;
    private Map<String,String>  CALLED_UP_SHARE_CAPITAL;
    private Map<String,String>  DEBTORS;
    private Map<String,String> NET_ASSETS;
    private Map<String,String> CASH;
    private Map<String,String> RETAINED_EARNINGS;
    private Map<String,String> STOCKS;

    public String getCompanyNumber() {
        return CompanyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        CompanyNumber = companyNumber;
    }

    public String getAccountDate() {
        return AccountDate;
    }

    public void setAccountDate(String accountDate) {
        AccountDate = accountDate;
    }

    public Map<String, String> getTURNOVER() {
        return TURNOVER;
    }

    public void setTURNOVER(Map<String, String> TURNOVER) {
        this.TURNOVER = TURNOVER;
    }

    public Map<String, String> getCURRENT_ASSETS() {
        return CURRENT_ASSETS;
    }

    public void setCURRENT_ASSETS(Map<String, String> CURRENT_ASSETS) {
        this.CURRENT_ASSETS = CURRENT_ASSETS;
    }

    public Map<String, String> getSHAREHOLDERS_FUNDS() {
        return SHAREHOLDERS_FUNDS;
    }

    public void setSHAREHOLDERS_FUNDS(Map<String, String> SHAREHOLDERS_FUNDS) {
        this.SHAREHOLDERS_FUNDS = SHAREHOLDERS_FUNDS;
    }

    public Map<String, String> getFIXED_ASSETS() {
        return FIXED_ASSETS;
    }

    public void setFIXED_ASSETS(Map<String, String> FIXED_ASSETS) {
        this.FIXED_ASSETS = FIXED_ASSETS;
    }

    public Map<String, String> getCREDITORS() {
        return CREDITORS;
    }

    public void setCREDITORS(Map<String, String> CREDITORS) {
        this.CREDITORS = CREDITORS;
    }

    public Map<String, String> getCALLED_UP_SHARE_CAPITAL() {
        return CALLED_UP_SHARE_CAPITAL;
    }

    public void setCALLED_UP_SHARE_CAPITAL(Map<String, String> CALLED_UP_SHARE_CAPITAL) {
        this.CALLED_UP_SHARE_CAPITAL = CALLED_UP_SHARE_CAPITAL;
    }

    public Map<String, String> getDEBTORS() {
        return DEBTORS;
    }

    public void setDEBTORS(Map<String, String> DEBTORS) {
        this.DEBTORS = DEBTORS;
    }

    public Map<String, String> getNET_ASSETS() {
        return NET_ASSETS;
    }

    public void setNET_ASSETS(Map<String, String> NET_ASSETS) {
        this.NET_ASSETS = NET_ASSETS;
    }

    public Map<String, String> getCASH() {
        return CASH;
    }

    public void setCASH(Map<String, String> CASH) {
        this.CASH = CASH;
    }

    public Map<String, String> getRETAINED_EARNINGS() {
        return RETAINED_EARNINGS;
    }

    public void setRETAINED_EARNINGS(Map<String, String> RETAINED_EARNINGS) {
        this.RETAINED_EARNINGS = RETAINED_EARNINGS;
    }

    public Map<String, String> getSTOCKS() {
        return STOCKS;
    }

    public void setSTOCKS(Map<String, String> STOCKS) {
        this.STOCKS = STOCKS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountPojo that = (AccountPojo) o;
        return Objects.equals(CompanyNumber, that.CompanyNumber) &&
                Objects.equals(AccountDate, that.AccountDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CompanyNumber, AccountDate);
    }

    @Override
    public String toString() {
        return "AccountPojo{" +
                "CompanyNumber='" + CompanyNumber + '\'' +
                ", AccountDate='" + AccountDate + '\'' +
                ", TURNOVER=" + TURNOVER +
                ", CURRENT_ASSETS=" + CURRENT_ASSETS +
                ", SHAREHOLDERS_FUNDS=" + SHAREHOLDERS_FUNDS +
                ", FIXED_ASSETS=" + FIXED_ASSETS +
                ", CREDITORS=" + CREDITORS +
                ", CALLED_UP_SHARE_CAPITAL=" + CALLED_UP_SHARE_CAPITAL +
                ", DEBTORS=" + DEBTORS +
                ", NET_ASSETS=" + NET_ASSETS +
                ", CASH=" + CASH +
                ", RETAINED_EARNINGS=" + RETAINED_EARNINGS +
                ", STOCKS=" + STOCKS +
                '}';
    }
}
