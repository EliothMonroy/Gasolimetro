import java.util.List;
public class PlaceFeed {
    private List<Object> htmlAttributions = null;
    private ResultPlace resultPlace;
    private String status;
    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }
    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }
    public ResultPlace getResultPlace() {
        return resultPlace;
    }
    public void setResultPlace(ResultPlace result) {
        this.resultPlace = result;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
