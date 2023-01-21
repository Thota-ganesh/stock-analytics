package io.endeavour.stocks.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "SUBSECTOR_LOOKUP", schema = "ENDEAVOUR")
public class SubSectorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUBSECTOR_ID")
    private Integer subSectorId;
    @Column(name = "SUBSECTOR_NAME")
    private String subSectorName;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "SECTOR_ID")
    private SectorEntity sectorEntity;

    public SectorEntity getSectorEntity() {
        return sectorEntity;
    }

    public void setSectorEntity(SectorEntity sectorEntity) {
        this.sectorEntity = sectorEntity;
    }

    public Integer getSubSectorId() {
        return subSectorId;
    }

    public void setSubSectorId(Integer subSectorId) {
        this.subSectorId = subSectorId;
    }

    public String getSubSectorName() {
        return subSectorName;
    }

    public void setSubSectorName(String subSectorName) {
        this.subSectorName = subSectorName;
    }
}
