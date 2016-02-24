
package be.ugent.verkeer4.verkeerdomain.data;

public enum ProviderEnum {

    TomTom(0),
    HereMaps(1),
    GoogleMaps(2),
    Coyote(3),
    BeMobile(4),
    Waze(5),
    Bing(6),
    ViaMichelin(7);
    
    private final int _value;

    ProviderEnum(int Value) {
        this._value = Value;
    }

    public int getValue() {
            return _value;
    }

    public static ProviderEnum fromInt(int i) {
        for (ProviderEnum b : ProviderEnum.values()) {
            if (b.getValue() == i) { return b; }
        }
        return null;
    }
}
