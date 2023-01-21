package io.endeavour.stocks.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SECTOR_LOOKUP", schema = "ENDEAVOUR")
public class SectorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SECTOR_ID")
    private Integer sectorId;
    @Column(name = "SECTOR_NAME")
    private String sectorName;
    @OneToMany(mappedBy = "sectorEntity")
    private List<SubSectorEntity> subSectorEntities;

    public List<SubSectorEntity> getSubSectorEntities() {
        return subSectorEntities;
    }

    public void setSubSectorEntities(List<SubSectorEntity> subSectorEntities) {
        this.subSectorEntities = subSectorEntities;
    }

    public Integer getSectorId() {
        return sectorId;
    }

    public void setSectorId(Integer sectorId) {
        this.sectorId = sectorId;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }
}
