package isp.lab7.safehome;

import isp.lab7.safehome.exceptions.InvalidPinException;
import isp.lab7.safehome.exceptions.TenantAlreadyExistsException;
import isp.lab7.safehome.exceptions.TenantNotFoundException;
import isp.lab7.safehome.exceptions.TooManyAttemptsException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoorLockController implements ControllerInterface {
    private Map<Tenant, AccessKey> validAccess;
    private List<AccessLog> accessLogs;
    private Door door = new Door(DoorStatus.CLOSE);
    private final static String ADD_TENANT_OPERATION = "addTenant";
    private final static String REMOVE_TENANT_OPERATION = "removeTenant";
    private int enterPinAttempts = 0;
    private String MASTER_KEY;
    private String MASTER_TENANT_NAME;

    public DoorLockController() {
        this.validAccess = new HashMap<>();
        this.accessLogs = new ArrayList<>();
    }

    public DoorLockController(Map<Tenant, AccessKey> validAccess, List<AccessLog> accessLogs, Door door) {
        this.validAccess = validAccess;
        this.accessLogs = accessLogs;
        this.door = door;
        this.MASTER_KEY = ControllerInterface.MASTER_KEY;
        this.MASTER_TENANT_NAME = ControllerInterface.MASTER_TENANT_NAME;
    }

    public Map<Tenant, AccessKey> getValidAccess() {
        return validAccess;
    }

    public void setValidAccess(Map<Tenant, AccessKey> validAccess) {
        this.validAccess = validAccess;
    }

    public List<AccessLog> getAccessLogs() {
        return accessLogs;
    }

    public void setAccessLogs(List<AccessLog> accessLogs) {
        this.accessLogs = accessLogs;
    }

    public Door getDoor() {
        return door;
    }

    public void setDoor(Door door) {
        this.door = door;
    }


    @Override
    public DoorStatus enterPin(String pin) throws Exception {
        AccessKey accessKey = new AccessKey(pin);

        if (accessKey.getPin() == null) {
            return null;
        }

        if (enterPinAttempts != 2) {
            if (validAccess.containsValue(new AccessKey(pin))) {
                this.enterPinAttempts = 0;
                if (this.door.getDoorStatus().equals(DoorStatus.CLOSE)) {
                    this.door.unlockDoor();
                    System.out.println("Door unlocked");
                    return this.door.getDoorStatus();
                } else {
                    System.out.println("Door locked");
                    this.door.lockDoor();
                    return door.getDoorStatus();
                }
            }

            enterPinAttempts++;
            throw new InvalidPinException("InvalidPinException");
        }

        if (pin.equals(MASTER_KEY)) {
            enterPinAttempts = 0;
            this.door.unlockDoor();
            System.out.println("Master pin entered, door unlocked");
            return door.getDoorStatus();
        }

        door.lockDoor();
        throw new TooManyAttemptsException("Too many attempts");
    }

    @Override
    public void addTenant(String pin, String tenantName) throws Exception {
        if (validAccess != null) {
            if (validAccess.containsKey(new Tenant(tenantName))) {
                accessLogs.add(new AccessLog(tenantName, LocalDateTime.now(), ADD_TENANT_OPERATION, door.getDoorStatus(), "Tenant " + tenantName + " already exists"));
                throw new TenantAlreadyExistsException("Tenant already exists");
            }

            accessLogs.add(new AccessLog(tenantName, LocalDateTime.now(), ADD_TENANT_OPERATION, door.getDoorStatus(), "No error"));
            System.out.println("Tenant " + tenantName + " with pin " + pin + " added");
            this.door.lockDoor();
            validAccess.put(new Tenant(tenantName), new AccessKey(pin));
        } else {
            System.out.println("Please create a validAccess Map");
        }
    }

    @Override
    public void removeTenant(String name) throws Exception {
        if (validAccess.isEmpty()) {
            accessLogs.add(new AccessLog(name, LocalDateTime.now(), REMOVE_TENANT_OPERATION, door.getDoorStatus(), "validAcces is empty"));
            System.out.println("No tenants to remove");
            throw new TenantNotFoundException("TenantNotFoundException");
        } else {
            if (this.validAccess.containsKey(new Tenant(name))) {
                this.accessLogs.add(new AccessLog(name, LocalDateTime.now(), REMOVE_TENANT_OPERATION, door.getDoorStatus(), "No error"));
                this.validAccess.remove(new Tenant(name));
                this.door.lockDoor();
                System.out.println("Tenant removed");
            } else {

                accessLogs.add(new AccessLog(name, LocalDateTime.now(), REMOVE_TENANT_OPERATION, door.getDoorStatus(), "Tenant not found"));
                throw new TenantNotFoundException("TenantNotFoundException");
            }
        }

    }

    public void clearMap() {
        if (validAccess != null) {
            validAccess.clear();
        }
    }

}
