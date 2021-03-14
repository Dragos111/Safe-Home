package isp.lab7.safehome;

import java.util.Objects;

import static isp.lab7.safehome.DoorStatus.CLOSE;
import static isp.lab7.safehome.DoorStatus.OPEN;

public class Door {
    private DoorStatus doorStatus;

    public Door(DoorStatus doorStatus) {
        this.doorStatus = doorStatus;
    }

    public void lockDoor() {
        this.doorStatus = CLOSE;
    }

    public void unlockDoor() {
        this.doorStatus = OPEN;
    }

    public DoorStatus getDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(DoorStatus doorStatus) {
        this.doorStatus = doorStatus;
    }

}
