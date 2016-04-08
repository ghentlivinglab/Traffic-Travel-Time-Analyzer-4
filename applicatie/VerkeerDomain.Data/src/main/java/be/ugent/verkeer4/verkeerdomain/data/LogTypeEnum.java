
package be.ugent.verkeer4.verkeerdomain.data;

public enum LogTypeEnum {

    Info(0),
    Warning(1),
    Error(2);

    
    private final int _value;

    LogTypeEnum(int Value) {
        this._value = Value;
    }

    public int getValue() {
            return _value;
    }

    public static LogTypeEnum fromInt(int i) {
        for (LogTypeEnum b : LogTypeEnum.values()) {
            if (b.getValue() == i) { return b; }
        }
        return null;
    }   
}
