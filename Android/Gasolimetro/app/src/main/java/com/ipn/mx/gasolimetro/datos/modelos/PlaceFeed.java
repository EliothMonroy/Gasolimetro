
package com.ipn.mx.gasolimetro.datos.modelos;

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

    public ResultPlace getResultPlace() {
        return result;
    }

    public void setResultPlace(ResultPlace result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
