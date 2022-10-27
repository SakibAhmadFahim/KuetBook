package com.example.kuet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump2 {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();

        List<String>vehicle = new ArrayList<>();
        vehicle.add("❯   Login to Vehicle Requisition System");


        List<String> onlinepay = new ArrayList<>();
        onlinepay.add("❯   Pay your Fees");


        List<String> hall = new ArrayList<>();
        hall.add("❯   Fazlul Haque Hall");
        hall.add("❯   Lalan Shah Hall");
        hall.add("❯   Khan Jahan Ali Hall");
        hall.add("❯   Dr. M.A Rahsid Hall");
        hall.add("❯   Rokeya Hall");
        hall.add("❯   Amar Ekushey Hall");
        hall.add("❯   Bangabandhu Shiekh Mujibur Rahman Hall");

        List<String> academic = new ArrayList<>();
        academic.add("❯   Academic System (UG)");
        academic.add("❯   Academic System (PG)");
        academic.add("❯   Graduate Portal For Clearance");
        academic.add("❯   Central Library");

        List<String> alumni = new ArrayList<>();
        alumni.add("❯   Alumni Registration");


        expandableListDetail.put("Vehicle Requisition System", vehicle);
        expandableListDetail.put("Online Payment Service", onlinepay);
        expandableListDetail.put("Academic Automation", academic);
        expandableListDetail.put("Hall Automation", hall);
        expandableListDetail.put("Alumni Association", alumni);
        return expandableListDetail;
    }
}