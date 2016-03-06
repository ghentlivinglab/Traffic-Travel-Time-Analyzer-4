
package be.ugent.verkeer4.verkeerdomain.data;

public enum POICategoryEnum {
    
    Unknown(0),
    Construction(1),
    Incident(2),
    TrafficJam(3);
    
    private final int _value;

    POICategoryEnum(int Value) {
        this._value = Value;
    }

    public int getValue() {
            return _value;
    }

    public static POICategoryEnum fromInt(int i) {
        for (POICategoryEnum b : POICategoryEnum.values()) {
            if (b.getValue() == i) { return b; }
        }
        return null;
    }
    
}
