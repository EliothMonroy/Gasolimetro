
package com.example;

import java.util.List;

public class PlaceFeed {

    private List<Object> htmlAttributions = null;
    private ResultPlace result;
    private String status;

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public ResultPlace getResult() {
        return result;
    }

    public void setResult(ResultPlacet result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
