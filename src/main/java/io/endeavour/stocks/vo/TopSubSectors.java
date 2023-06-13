package io.endeavour.stocks.vo;

import java.util.List;

public class TopSubSectors {
    private String sectorName;
    private List<TopSubSectorsByAvgMarketCap> topSubSectorsByAvgMarketCaps;

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public List<TopSubSectorsByAvgMarketCap> getTopSubSectorsByAvgMarketCaps() {
        return topSubSectorsByAvgMarketCaps;
    }

    public void setTopSubSectorsByAvgMarketCaps(List<TopSubSectorsByAvgMarketCap> topSubSectorsByAvgMarketCaps) {
        this.topSubSectorsByAvgMarketCaps = topSubSectorsByAvgMarketCaps;
    }
}
