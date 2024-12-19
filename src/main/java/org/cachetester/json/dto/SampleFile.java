package org.cachetester.json.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class SampleFile implements Cacheable {
    private String type;
    private int processingState;
    private String namespace;
    private String localId;
    private String regionCode;
    private Date registeredFrom;
    private String sector;
    private Date registeredTo;
    private Date validFrom;
    private Date validTo;
    private int status;
    private String buildingNumber;
    private String houseId;
    private String propertyId;

    public SampleFile() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getProcessingState() {
        return processingState;
    }

    public void setProcessingState(int processingState) {
        this.processingState = processingState;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public Date getRegisteredFrom() {
        return registeredFrom;
    }

    public void setRegisteredFrom(Date registeredFrom) {
        this.registeredFrom = registeredFrom;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Date getRegisteredTo() {
        return registeredTo;
    }

    public void setRegisteredTo(Date registeredTo) {
        this.registeredTo = registeredTo;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    @JsonIgnore
    @Override
    public String getId() {
        return getLocalId();
    }

    @JsonIgnore
    @Override
    public String getCollectionName() {
        return getSector();
    }
}
