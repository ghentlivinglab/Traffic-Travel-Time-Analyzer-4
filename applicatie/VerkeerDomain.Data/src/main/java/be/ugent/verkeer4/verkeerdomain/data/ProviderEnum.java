
package be.ugent.verkeer4.verkeerdomain.data;

import java.util.Arrays;

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

    public static String[] getProviderNamesSorted(){
        ProviderEnum[] providersEnum = ProviderEnum.values();
        String[] providers = new String[providersEnum.length];
        for (int i = 0; i < providers.length; i++) {
            providers[i] = providersEnum[i].name();
        }
        Arrays.sort(providers);
        return providers;
    }
}
