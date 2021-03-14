package isp.lab7.safehome;

import javafx.scene.chart.ScatterChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SafeHome {

    public static void main(String[] args) throws Exception {
        ControllerInterface ctrl;
        DoorLockController controller;
        Map<Tenant, AccessKey> validAcces = new HashMap<>();
        List<AccessLog> accessLog = new ArrayList<>();
        DoorStatus doorStatus = DoorStatus.OPEN;
        Door door = new Door(doorStatus);
        controller = new DoorLockController(validAcces, accessLog, door);

        try {
            controller.addTenant("123", "Name1");
            controller.addTenant("123", "Name1");

        } catch (Exception e) {
            System.out.println("Tenant exists");
        }

        controller.clearMap();

        try {
            controller.removeTenant("Name1");
            controller.removeTenant("Name1");
        } catch (Exception e) {
            System.out.println("Tenant not found");
        }

        controller.addTenant("1234", "Tenant Name");

        for (int i = 0; i <= 2; i++) {
            try {
                controller.enterPin("1235");
            } catch (Exception e) {
                System.out.println("Pin is wrong");
            }
        }

        try {
            controller.enterPin("1235");
        } catch (Exception e) {
            System.out.println("Too many attempts");
        }

        controller.enterPin(ControllerInterface.MASTER_KEY);
    }
}
