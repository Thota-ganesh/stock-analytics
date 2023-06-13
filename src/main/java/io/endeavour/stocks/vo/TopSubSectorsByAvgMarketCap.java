package io.endeavour.stocks.vo;

import java.math.BigDecimal;

public class TopSubSectorsByAvgMarketCap {
    private String SubSectorName;
    private BigDecimal AvgMarketCap;

    public String getSubSectorName() {
        return SubSectorName;
    }

    public void setSubSectorName(String subSectorName) {
        SubSectorName = subSectorName;
    }

    public BigDecimal getAvgMarketCap() {
        return AvgMarketCap;
    }

    public void setAvgMarketCap(BigDecimal avgMarketCap) {
        AvgMarketCap = avgMarketCap;
    }
}
