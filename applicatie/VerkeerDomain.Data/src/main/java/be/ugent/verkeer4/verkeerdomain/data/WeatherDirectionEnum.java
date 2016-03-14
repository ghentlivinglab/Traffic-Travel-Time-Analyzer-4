/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain.data;

/**
 *
 * @author Niels
 */
public enum WeatherDirectionEnum {
    
    East(0),	
    ENE(1),     //East-northeast
    ESE(2),     //East-southeast
    NE(3),      //Northeast
    NNE(4),     //North-northeast
    NNW(5),     //North-northwest
    North(6),	
    NW(7),      //Northwest
    SE(8),      //Southeast
    South(9),	
    SSE(10),     //South-southeast
    SSW(11),     //South-southwest
    SW(12),      //Southwest
    West(13),	
    WNW(14),     //West-northwest
    WSW(15);     //West-southwest
    
    private final int _value;

    WeatherDirectionEnum(int Value) {
        this._value = Value;
    }

    public int getValue() {
            return _value;
    }

    public static WeatherDirectionEnum fromInt(int i) {
        for (WeatherDirectionEnum w : WeatherDirectionEnum.values()) {
            if (w.getValue() == i) { return w; }
        }
        return null;
    }
    
}
