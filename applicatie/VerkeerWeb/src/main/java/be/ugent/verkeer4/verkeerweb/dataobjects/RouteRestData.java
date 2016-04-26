package be.ugent.verkeer4.verkeerweb.dataobjects;

import java.util.ArrayList;
import java.util.List;

public class RouteRestData {
    private String name;
    private List<long[]> data;

    public RouteRestData(String name) {
        this.name = name;
        data = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<long[]> getData() {
        return data;
    }

    public void addDataElement(long[] element){
        data.add(element);
    }
}
