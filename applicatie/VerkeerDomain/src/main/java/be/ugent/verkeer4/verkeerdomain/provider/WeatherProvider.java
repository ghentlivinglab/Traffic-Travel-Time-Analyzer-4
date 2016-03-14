/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.data.WeatherConditionEnum;
import be.ugent.verkeer4.verkeerdomain.data.WeatherData;
import be.ugent.verkeer4.verkeerdomain.data.WeatherDirectionEnum;
import be.ugent.verkeer4.verkeerdomain.weather.CurrentObservation;
import be.ugent.verkeer4.verkeerdomain.weather.WeatherDataClient;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Niels
 */
public class WeatherProvider implements IWeatherProvider {
    
    static HashMap<String,WeatherConditionEnum> conditionMap = new HashMap<>();
    static HashMap<String,WeatherDirectionEnum> directionMap = new HashMap<>();
    
    public WeatherProvider()
    {
        init();
    }
    
    @Override
    public WeatherData poll(double lat, double lng)
    {
        try {
            
            WeatherData data = new WeatherData();   
            CurrentObservation current = WeatherDataClient.GetWeather(lat, lng);
            
            data.setLatitude(lat);
            data.setLongitude(lng);
            data.setTemperature(current.getTempC());
            data.setWindSpeed(current.getWindKph());
            data.setWindDirection(directionMap.get(current.getWindDir()).getValue());
            data.setCondition(conditionMap.get(current.getWeather()).getValue());
            
            Date d = new Date(Long.parseLong(current.getObservationEpoch())*1000);      //Omvormen van unix naar miliseconden
            data.setTimestamp(d);
                      
            return data;
                  
        } catch (IOException ex) {
            Logger.getLogger(WeatherProvider.class.getName()).log(Level.SEVERE, null, ex);          
            return null;
        }
        
    }
    
    private void init()
    {
        conditionMap.put("Light Drizzle", WeatherConditionEnum.LightDrizzle);
        conditionMap.put("Heavy Drizzle", WeatherConditionEnum.HeavyDrizzle);
        conditionMap.put("Light Rain", WeatherConditionEnum.LightRain);
        conditionMap.put("Heavy Rain", WeatherConditionEnum.HeavyRain);
        conditionMap.put("Light Snow", WeatherConditionEnum.LightSnow);
        conditionMap.put("Heavy Snow", WeatherConditionEnum.HeavySnow);
        conditionMap.put("Light Snow Grains", WeatherConditionEnum.LightSnowGrains);
        conditionMap.put("Heavy Snow Grains", WeatherConditionEnum.HeavySnowGrains);
        conditionMap.put("Light Ice Crystals", WeatherConditionEnum.LightIceCrystals);
        conditionMap.put("Heavy Ice Crystals", WeatherConditionEnum.HeavyIceCrystals);
        conditionMap.put("Light Ice Pellets", WeatherConditionEnum.LightIcePellets);
        conditionMap.put("Heavy Ice Pellets", WeatherConditionEnum.HeavyIcePellets);
        conditionMap.put("Light Hail", WeatherConditionEnum.LightHail);
        conditionMap.put("Heavy Hail", WeatherConditionEnum.HeavyHail);
        conditionMap.put("Light Mist", WeatherConditionEnum.LightMist);
        conditionMap.put("Heavy Mist", WeatherConditionEnum.HeavyMist);
        conditionMap.put("Light Fog", WeatherConditionEnum.LightFog);
        conditionMap.put("Heavy Fog", WeatherConditionEnum.HeavyFog);
        conditionMap.put("Light Fog Patches", WeatherConditionEnum.LightFogPatches);
        conditionMap.put("Heavy Fog Patches", WeatherConditionEnum.HeavyFogPatches);
        conditionMap.put("Light Smoke", WeatherConditionEnum.LightSmoke);
        conditionMap.put("Heavy Smoke", WeatherConditionEnum.HeavySmoke);
        conditionMap.put("Light Volcanic Ash", WeatherConditionEnum.LightVolcanicAsh);
        conditionMap.put("Heavy Volcanic Ash", WeatherConditionEnum.HeavyVolcanicAsh);
        conditionMap.put("Light Widespread Dust", WeatherConditionEnum.LightWidespreadDust);
        conditionMap.put("Heavy Widespread Dust", WeatherConditionEnum.HeavyWidespreadDust);
        conditionMap.put("Light Sand", WeatherConditionEnum.LightSand);
        conditionMap.put("Heavy Sand", WeatherConditionEnum.HeavySand);
        conditionMap.put("Light Haze", WeatherConditionEnum.LightHaze);
        conditionMap.put("Heavy Haze", WeatherConditionEnum.HeavyHaze);
        conditionMap.put("Light Spray", WeatherConditionEnum.LightSpray);
        conditionMap.put("Heavy Spray", WeatherConditionEnum.HeavySpray);
        conditionMap.put("Light Dust Whirls", WeatherConditionEnum.LightDustWhirls);
        conditionMap.put("Heavy Dust Whirls", WeatherConditionEnum.HeavyDustWhirls);
        conditionMap.put("Light Sandstorm", WeatherConditionEnum.LightSandstorm);
        conditionMap.put("Heavy Sandstorm", WeatherConditionEnum.HeavySandstorm);
        conditionMap.put("Light Low Drifting Snow", WeatherConditionEnum.LightLowDriftingSnow);
        conditionMap.put("Heavy Low Drifting Snow", WeatherConditionEnum.HeavyLowDriftingSnow);
        conditionMap.put("Light Low Drifting Widespread Dust", WeatherConditionEnum.LightLowDriftingWidespreadDust);
        conditionMap.put("Heavy Low Drifting Widespread Dust", WeatherConditionEnum.HeavyLowDriftingWidespreadDust);
        conditionMap.put("Light Low Drifting Sand", WeatherConditionEnum.LightLowDriftingSand);
        conditionMap.put("Heavy Low Drifting Sand", WeatherConditionEnum.HeavyLowDriftingSand);
        conditionMap.put("Light Blowing Snow", WeatherConditionEnum.LightBlowingSnow);
        conditionMap.put("Heavy Blowing Snow", WeatherConditionEnum.HeavyBlowingSnow);
        conditionMap.put("Light Blowing Widespread Dust", WeatherConditionEnum.LightBlowingWidespreadDust);
        conditionMap.put("Heavy Blowing Widespread Dust", WeatherConditionEnum.HeavyBlowingWidespreadDust);
        conditionMap.put("Light Blowing Sand", WeatherConditionEnum.LightBlowingSand);
        conditionMap.put("Heavy Blowing Sand", WeatherConditionEnum.HeavyBlowingSand);
        conditionMap.put("Light Rain Mist", WeatherConditionEnum.LightRainMist);
        conditionMap.put("Heavy Rain Mist", WeatherConditionEnum.HeavyRainMist);
        conditionMap.put("Light Rain Showers", WeatherConditionEnum.LightRainShowers);
        conditionMap.put("Heavy Rain Showers", WeatherConditionEnum.HeavyRainShowers);
        conditionMap.put("Light Snow Showers", WeatherConditionEnum.LightSnowShowers);
        conditionMap.put("Heavy Snow Showers", WeatherConditionEnum.HeavySnowShowers);
        conditionMap.put("Light Snow Blowing Snow Mist", WeatherConditionEnum.LightSnowBlowingSnowMist);
        conditionMap.put("Heavy Snow Blowing Snow Mist", WeatherConditionEnum.HeavySnowBlowingSnowMist);
        conditionMap.put("Light Ice Pellet Showers", WeatherConditionEnum.LightIcePelletShowers);
        conditionMap.put("Heavy Ice Pellet Showers", WeatherConditionEnum.HeavyIcePelletShowers);
        conditionMap.put("Light Hail Showers", WeatherConditionEnum.LightHailShowers);
        conditionMap.put("Heavy Hail Showers", WeatherConditionEnum.HeavyHailShowers);
        conditionMap.put("Light Small Hail Showers", WeatherConditionEnum.LightSmallHailShowers);
        conditionMap.put("Heavy Small Hail Showers", WeatherConditionEnum.HeavySmallHailShowers);
        conditionMap.put("Light Thunderstorm", WeatherConditionEnum.LightThunderstorm);
        conditionMap.put("Heavy Thunderstorm", WeatherConditionEnum.HeavyThunderstorm);
        conditionMap.put("Light Thunderstorms and Rain", WeatherConditionEnum.LightThunderstormsandRain);
        conditionMap.put("Heavy Thunderstorms and Rain", WeatherConditionEnum.HeavyThunderstormsandRain);
        conditionMap.put("Light Thunderstorms and Snow", WeatherConditionEnum.LightThunderstormsandSnow);
        conditionMap.put("Heavy Thunderstorms and Snow", WeatherConditionEnum.HeavyThunderstormsandSnow);
        conditionMap.put("Light Thunderstorms and Ice Pellets", WeatherConditionEnum.LightThunderstormsandIcePellets);
        conditionMap.put("Heavy Thunderstorms and Ice Pellets", WeatherConditionEnum.HeavyThunderstormsandIcePellets);
        conditionMap.put("Light Thunderstorms with Hail", WeatherConditionEnum.LightThunderstormswithHail);
        conditionMap.put("Heavy Thunderstorms with Hail", WeatherConditionEnum.HeavyThunderstormswithHail);
        conditionMap.put("Light Thunderstorms with Small Hail", WeatherConditionEnum.LightThunderstormswithSmallHail);
        conditionMap.put("Heavy Thunderstorms with Small Hail", WeatherConditionEnum.HeavyThunderstormswithSmallHail);
        conditionMap.put("Light Freezing Drizzle", WeatherConditionEnum.LightFreezingDrizzle);
        conditionMap.put("Heavy Freezing Drizzle", WeatherConditionEnum.HeavyFreezingDrizzle);
        conditionMap.put("Light Freezing Rain", WeatherConditionEnum.LightFreezingRain);
        conditionMap.put("Heavy Freezing Rain", WeatherConditionEnum.HeavyFreezingRain);
        conditionMap.put("Light Freezing Fog", WeatherConditionEnum.LightFreezingFog);
        conditionMap.put("Heavy Freezing Fog", WeatherConditionEnum.HeavyFreezingFog);
        conditionMap.put("Patches of Fog", WeatherConditionEnum.PatchesofFog);
        conditionMap.put("Shallow Fog", WeatherConditionEnum.ShallowFog);
        conditionMap.put("Partial Fog", WeatherConditionEnum.PartialFog);
        conditionMap.put("Overcast", WeatherConditionEnum.Overcast);
        conditionMap.put("Clear", WeatherConditionEnum.Clear);
        conditionMap.put("Partly Cloudy", WeatherConditionEnum.PartlyCloudy);
        conditionMap.put("Mostly Cloudy", WeatherConditionEnum.MostlyCloudy);
        conditionMap.put("Scattered Clouds", WeatherConditionEnum.ScatteredClouds);
        conditionMap.put("Small Hail", WeatherConditionEnum.SmallHail);
        conditionMap.put("Squalls", WeatherConditionEnum.Squalls);
        conditionMap.put("Funnel Cloud", WeatherConditionEnum.FunnelCloud);
        conditionMap.put("Unknown Precipitation", WeatherConditionEnum.UnknownPrecipitation);
        conditionMap.put("Unknown", WeatherConditionEnum.Unknown);
        
        directionMap.put("East", WeatherDirectionEnum.East	);
        directionMap.put("ENE", WeatherDirectionEnum.ENE);
        directionMap.put("ESE", WeatherDirectionEnum.ESE);
        directionMap.put("NE", WeatherDirectionEnum.NE);
        directionMap.put("NNE", WeatherDirectionEnum.NNE);
        directionMap.put("NNW", WeatherDirectionEnum.NNW);
        directionMap.put("North", WeatherDirectionEnum.North	);
        directionMap.put("NW", WeatherDirectionEnum.NW);
        directionMap.put("SE", WeatherDirectionEnum.SE);
        directionMap.put("South", WeatherDirectionEnum.South	);
        directionMap.put("SSE", WeatherDirectionEnum.SSE);
        directionMap.put("SSW", WeatherDirectionEnum.SSW);
        directionMap.put("SW", WeatherDirectionEnum.SW);
        directionMap.put("West", WeatherDirectionEnum.West	);
        directionMap.put("WNW", WeatherDirectionEnum.WNW);
        directionMap.put("WSW", WeatherDirectionEnum.WSW);
    }
}
