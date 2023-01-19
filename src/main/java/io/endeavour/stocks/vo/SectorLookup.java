package io.endeavour.stocks.vo;

public class SectorLookup implements Comparable<SectorLookup> {
    private int sectorId;
    private String sectorName;

    public int getSectorId() {
        return sectorId;
    }

    public void setSectorId(int sectorId) {
        this.sectorId = sectorId;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    @Override
    public String toString() {
        return "SectorLookup{" +
                "sectorId=" + sectorId +
                ", sectorName='" + sectorName + '\'' +
                '}' + "\n";
    }

    @Override
    public int compareTo(SectorLookup that) {
        return this.sectorName.compareTo(that.sectorName);
//        if (this.sectorId > that.sectorId) {
//            return -1;
//        } else if (this.sectorId < that.sectorId) {
//            return 1;
//        } else {
//            return 0;
//        }
    }
}
