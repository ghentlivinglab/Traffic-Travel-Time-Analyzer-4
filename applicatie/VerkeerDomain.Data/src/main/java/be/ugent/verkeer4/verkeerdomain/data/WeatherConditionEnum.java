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
public enum WeatherConditionEnum {
    
    LightDrizzle(0),
    HeavyDrizzle(1),
    LightRain(3),
    HeavyRain(4),
    LightSnow(5),
    HeavySnow(6),
    LightSnowGrains(7),
    HeavySnowGrains(9),
    LightIceCrystals(10),
    HeavyIceCrystals(11),
    LightIcePellets(12),
    HeavyIcePellets(13),
    LightHail(14),
    HeavyHail(15),
    LightMist(16),
    HeavyMist(17),
    LightFog(18),
    HeavyFog(19),
    LightFogPatches(20),
    HeavyFogPatches(21),
    LightSmoke(22),
    HeavySmoke(23),
    LightVolcanicAsh(24),
    HeavyVolcanicAsh(25),
    LightWidespreadDust(26),
    HeavyWidespreadDust(26),
    LightSand(27),
    HeavySand(28),
    LightHaze(29),
    HeavyHaze(30),
    LightSpray(31),
    HeavySpray(32),
    LightDustWhirls(33),
    HeavyDustWhirls(34),
    LightSandstorm(35),
    HeavySandstorm(36),
    LightLowDriftingSnow(37),
    HeavyLowDriftingSnow(38),
    LightLowDriftingWidespreadDust(39),
    HeavyLowDriftingWidespreadDust(40),
    LightLowDriftingSand(41),
    HeavyLowDriftingSand(42),
    LightBlowingSnow(43),
    HeavyBlowingSnow(44),
    LightBlowingWidespreadDust(45),
    HeavyBlowingWidespreadDust(46),
    LightBlowingSand(47),
    HeavyBlowingSand(48),
    LightRainMist(49),
    HeavyRainMist(50),
    LightRainShowers(51),
    HeavyRainShowers(52),
    LightSnowShowers(53),
    HeavySnowShowers(54),
    LightSnowBlowingSnowMist(55),
    HeavySnowBlowingSnowMist(56),
    LightIcePelletShowers(57),
    HeavyIcePelletShowers(58),
    LightHailShowers(59),
    HeavyHailShowers(60),
    LightSmallHailShowers(61),
    HeavySmallHailShowers(62),
    LightThunderstorm(63),
    HeavyThunderstorm(64),
    LightThunderstormsandRain(65),
    HeavyThunderstormsandRain(66),
    LightThunderstormsandSnow(67),
    HeavyThunderstormsandSnow(68),
    LightThunderstormsandIcePellets(69),
    HeavyThunderstormsandIcePellets(70),
    LightThunderstormswithHail(71),
    HeavyThunderstormswithHail(72),
    LightThunderstormswithSmallHail(73),
    HeavyThunderstormswithSmallHail(74),
    LightFreezingDrizzle(75),
    HeavyFreezingDrizzle(76),
    LightFreezingRain(77),
    HeavyFreezingRain(78),
    LightFreezingFog(79),
    HeavyFreezingFog(80),
    PatchesofFog(81),
    ShallowFog(82),
    PartialFog(83),
    Overcast(84),
    Clear(85),
    PartlyCloudy(86),
    MostlyCloudy(87),
    ScatteredClouds(88),
    SmallHail(89),
    Squalls(90),
    FunnelCloud(91),
    UnknownPrecipitation(92),
    Unknown(93);
    
    private final int _value;

    WeatherConditionEnum(int Value) {
        this._value = Value;
    }

    public int getValue() {
            return _value;
    }

    public static WeatherConditionEnum fromInt(int i) {
        for (WeatherConditionEnum w : WeatherConditionEnum.values()) {
            if (w.getValue() == i) { return w; }
        }
        return null;
    }

}
