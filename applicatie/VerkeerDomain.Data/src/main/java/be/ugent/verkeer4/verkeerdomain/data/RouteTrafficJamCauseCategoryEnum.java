package be.ugent.verkeer4.verkeerdomain.data;

public enum RouteTrafficJamCauseCategoryEnum {
    POI(0),
    Weather(1);

    private final int _value;

    RouteTrafficJamCauseCategoryEnum(int Value) {
        this._value = Value;
    }

    public int getValue() {
        return _value;
    }

    public static RouteTrafficJamCauseCategoryEnum fromInt(int i) {
        for (RouteTrafficJamCauseCategoryEnum b : RouteTrafficJamCauseCategoryEnum.values()) {
            if (b.getValue() == i) {
                return b;
            }
        }
        return null;
    }
}
