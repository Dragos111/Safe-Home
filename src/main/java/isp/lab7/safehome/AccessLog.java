package isp.lab7.safehome;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AccessLog implements Serializable {
    private String tenantName;
    private LocalDateTime localDateTime;
    private String operation;
    private DoorStatus doorStatus;
    private String errorMessage;

    public AccessLog(String tenantName, LocalDateTime localDateTime, String operation, DoorStatus doorStatus, String errorMessage) {
        this.tenantName = tenantName;
        this.localDateTime = localDateTime;
        this.operation = operation;
        this.doorStatus = doorStatus;
        this.errorMessage = errorMessage;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public DoorStatus getDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(DoorStatus doorStatus) {
        this.doorStatus = doorStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    @Override
    public String toString() {
        return "AccessLog{" +
                "tenantName='" + tenantName + '\'' +
                ", localDateTime=" + localDateTime +
                ", operation='" + operation + '\'' +
                ", doorStatus=" + doorStatus +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
