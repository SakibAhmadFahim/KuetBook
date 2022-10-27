package com.example.kuet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();

        List<String>civil = new ArrayList<>();
        civil.add(">   Civil Engineering");
        civil.add(">   Urban and Regional Planning");
        civil.add(">   Building Engineering & Construction Management");
        civil.add(">   Architecture");

        List<String> eee = new ArrayList<>();
        eee.add(">   Electrical and Electronic Engineering");
        eee.add(">   Computer Science & Engineering");
        eee.add(">   Electronics and Communication Engineering");
        eee.add(">   Biomedical Engineering");
        eee.add(">   Materials Science and Engineering");

        List<String> me = new ArrayList<>();
        me.add(">   Mechanical Engineering");
        me.add(">   Industrial Engineering and Management");
        me.add(">   Leather Engineering");
        me.add(">   Textile Engineering");
        me.add(">   Energy  Science and Engineering");
        me.add(">   Chemical Engineering");
        me.add(">   Mechatronics Engineering");

        expandableListDetail.put("Faculty of Civil Engineering", civil);
        expandableListDetail.put("Faculty of Electrical and Electronic Engineering", eee);
        expandableListDetail.put("Faculty of Mechanical Engineering", me);
        return expandableListDetail;
    }
}