/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain.data.composite;

import be.ugent.verkeer4.verkeerdomain.data.WeatherData;

/**
 *
 * @author Niels
 */
public class WeatherWithDistanceToRoute extends WeatherData {
    
    private double distance;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
