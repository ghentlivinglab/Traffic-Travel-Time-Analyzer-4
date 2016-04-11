(function (trajectDetail, verkeer, $, moment, Highcharts) {

    // chart and data variabels
    var routeChart;
    var combinedTravelTimes = [];
    var combinedDelays = [];
    var showingDelayChart = false;
    var travelTimeTitle = "Travel time per provider";
    var delayTitle = "Delay per provider";
    var providerChartSettings = {
        Coyote: {color: "#7cb5ec", symbol: "circle"},
        BeMobile: {color: "#434348", symbol: "diamond"},
        ViaMichelin: {color: "#90ed7d", symbol: "square"},
        HereMaps: {color: "#f7a35c", symbol: "triangle"},
        Bing: {color: "#8085e9", symbol: "triangle-down"},
        Waze: {color: "#f15c80", symbol: "circle"},
        TomTom: {color: "#e4d354", symbol: "diamond"},
        GoogleMaps: {color: "#2b908f", symbol: "square"}
    };
    var weatherConditions = {
        1 : {discription: "Light Drizzle"},
        2 : {discription: "Heavy Drizzle"},
        3 : {discription: "Light Rain"},
        4 : {discription: "Heavy Rain"},
        5 : {discription: "Light Snow"},
        6 : {discription: "Heavy Snow"},
        7 : {discription: "Light Snow Grains"},
        8 : {discription: "Heavy Snow Grains"},
        9 : {discription: "Light Ice Crystals"},
        10: {discription: "Heavy Ice Crystals"},
        11: {discription: "Light Ice Pellets"},
        12: {discription: "Heavy Ice Pellets"},
        13: {discription: "Light Hail"},
        14: {discription: "Heavy Hail"},
        15: {discription: "Light Mist"},
        16: {discription: "Heavy Mist"},
        17: {discription: "Light Fog"},
        18: {discription: "Heavy Fog"},
        19: {discription: "Light Fog Patches"},
        20: {discription: "Heavy Fog Patches"},
        21: {discription: "Light Smoke"},
        22: {discription: "Heavy Smoke"},
        23: {discription: "Light Volcanic Ash"},
        24: {discription: "Heavy Volcanic Ash"},
        25: {discription: "Light Widespread Dust"},
        26: {discription: "Heavy Widespread Dust"},
        27: {discription: "Light Sand"},
        28: {discription: "Heavy Sand"},
        29: {discription: "Light Haze"},
        30: {discription: "Heavy Haze"},
        31: {discription: "Light Spray"},
        32: {discription: "Heavy Spray"},
        33: {discription: "Light Dust Whirls"},
        34: {discription: "Heavy Dust Whirls"},
        35: {discription: "Light Sandstorm"},
        36: {discription: "Heavy Sandstorm"},
        37: {discription: "Light Low Drifting Snow"},
        38: {discription: "Heavy Low Drifting Snow"},
        39: {discription: "Light Low Drifting Widespread Dust"},
        40: {discription: "Heavy Low Drifting Widespread Dust"},
        41: {discription: "Light Low Drifting Sand"},
        42: {discription: "Heavy Low Drifting Sand"},
        43: {discription: "Light Blowing Snow"},
        44: {discription: "Heavy Blowing Snow"},
        45: {discription: "Light Blowing Widespread Dust"},
        46: {discription: "Heavy Blowing Widespread Dust"},
        47: {discription: "Light Blowing Sand"},
        48: {discription: "Heavy Blowing Sand"},
        49: {discription: "Light Rain Mist"},
        50: {discription: "Heavy Rain Mist"},
        51: {discription: "Light Rain Showers"},
        52: {discription: "Heavy Rain Showers"},
        53: {discription: "Light Snow Showers"},
        54: {discription: "Heavy Snow Showers"},
        55: {discription: "Light Snow Blowing Snow Mist"},
        56: {discription: "Heavy Snow Blowing Snow Mist"},
        57: {discription: "Light Ice Pellet Showers"},
        58: {discription: "Heavy Ice Pellet Showers"},
        59: {discription: "Light Hail Showers"},
        60: {discription: "Heavy Hail Showers"},
        61: {discription: "Light Small Hail Showers"},
        62: {discription: "Heavy Small Hail Showers"},
        63: {discription: "Light Thunderstorm"},
        64: {discription: "Heavy Thunderstorm"},
        65: {discription: "Light Thunderstorms and Rain"},
        66: {discription: "Heavy Thunderstorms and Rain"},
        67: {discription: "Light Thunderstorms and Snow"},
        68: {discription: "Heavy Thunderstorms and Snow"},
        69: {discription: "Light Thunderstorms and Ice Pellets"},
        70: {discription: "Heavy Thunderstorms and Ice Pellets"},
        71: {discription: "Light Thunderstorms with Hail"},
        72: {discription: "Heavy Thunderstorms with Hail"},
        73: {discription: "Light Thunderstorms with Small Hail"},
        74: {discription: "Heavy Thunderstorms with Small Hail"},
        75: {discription: "Light Freezing Drizzle"},
        76: {discription: "Heavy Freezing Drizzle"},
        77: {discription: "Light Freezing Rain"},
        78: {discription: "Heavy Freezing Rain"},
        79: {discription: "Light Freezing Fog"},
        80: {discription: "Heavy Freezing Fog"},
        81: {discription: "Patchesof Fog"},
        82: {discription: "Shallow Fog"},
        83: {discription: "Partial Fog"},
        84: {discription: "Overcast"},
        85: {discription: "Clear"},
        86: {discription: "Partly Cloudy"},
        87: {discription: "Mostly Cloudy"},
        88: {discription: "Scattered Clouds"},
        89: {discription: "Small Hail"},
        90: {discription: "Squalls"},
        91: {discription: "Funnel Cloud"},
        92: {discription: "Unknown Precipitation"},
        93: {discription: "Unknown"}
    };

    // view only variables
    var refreshIcon;
    var updateBtn;
    var toggleBtn;
    var datePickerBegin;
    var datePickerEnd;

    $(document).ready(function () {
        refreshIcon = document.getElementById("refresh-icon");
        updateBtn = document.getElementById("update-btn");
        toggleBtn = document.getElementById("toggle-btn");
        datePickerBegin = $("#datetimepicker-begin");
        datePickerEnd = $("#datetimepicker-end");
        trajectDetail.markExtremeProviders();
        datePickerBegin.datetimepicker({
            format: "DD/MM/YYYY HH:mm",
            showTodayButton: true,
            showClear: true,
            defaultDate: moment().startOf("day")
        });

        datePickerEnd.datetimepicker(
            {
                format: "DD/MM/YYYY HH:mm",
                showTodayButton: true,
                showClear: true,
                defaultDate: moment().endOf("day")
            }
        );
        updateBtn.addEventListener("click", trajectDetail.getRouteData);
        toggleBtn.addEventListener("click", trajectDetail.toggleChart);
        trajectDetail.buildChart();
        trajectDetail.getRouteData();
    });

    trajectDetail.getRouteData = function () {
        // spin the update button
        refreshIcon.classList.add("spinning");
        $.ajax({
            method: "GET",
            url: "../routedata",
            data: {
                id: $("#routeId").val(),
                startDate: datePickerBegin.data("DateTimePicker").date().toDate(),
                endDate: datePickerEnd.data("DateTimePicker").date().toDate()
            },
            success: function (data) {
                showingDelayChart = false;
                routeChart.setTitle({text: travelTimeTitle});
                trajectDetail.combineRouteData(data.values, "travelTime", combinedTravelTimes);
                trajectDetail.combineRouteData(data.values, "delay", combinedDelays);
                trajectDetail.setChartData(combinedTravelTimes);
                trajectDetail.buildTrafficJamTable(data.jams);
            },
            complete: function(){
                // spin the update button
                refreshIcon.classList.remove("spinning");
            }
        });
    };

    trajectDetail.buildChart = function () {
        routeChart = new Highcharts.Chart({
            chart: {
                zoomType: "x",
                renderTo: "container"
            },
            title: {
                text: travelTimeTitle
            },
            subtitle: {
                text: document.ontouchstart === undefined ?
                    "Click and drag in the plot area to zoom in" : "Pinch the chart to zoom in"
            },
            xAxis: {
                title: {
                    text: "Timestamp"
                },
                type: "datetime"
            },
            yAxis: {
                title: {
                    text: "Duration"
                },
                labels: {
                    formatter: function () {
                        return verkeer.secondsToText(this.value);
                    }
                }
            },
            tooltip: {
                formatter: function () {
                    var date = moment(this.x).format("dddd, MMMM Do, HH:mm:ss");
                    return date + "<br/>" + "<span style='color:" + this.point.series.color + "'>" + trajectDetail.getPointSymbol(this.point)
                        + "</span> " + this.series.name + ": <b>" + verkeer.secondsToText(this.point.y) + "</b>";
                }
            },
            legend: {
                layout: "vertical",
                align: "right",
                verticalAlign: "middle",
                borderWidth: 0
            }
        });
    };

    trajectDetail.getPointSymbol = function (point) {
        var symbol = "";
        if (point.series && point.series.symbol) {
            switch (point.series.symbol) {
                case "circle":
                    symbol = "●";
                    break;
                case "diamond":
                    symbol = "♦";
                    break;
                case "square":
                    symbol = "■";
                    break;
                case "triangle":
                    symbol = "▲";
                    break;
                case "triangle-down":
                    symbol = "▼";
                    break;
                default:
                    symbol = "";
                    break;
            }
        }
        return symbol;
    };

    trajectDetail.clearAllChartData = function () {
        if (!routeChart || !routeChart.series)
            return;

        while (routeChart.series.length > 0) {
            // todo bug? Repaints with false...
            routeChart.series[0].remove(false);
        }
        routeChart.redraw();
    };

    trajectDetail.setChartData = function (series) {
        trajectDetail.clearAllChartData();

        if (series && series.length > 0) {
            // Put new data on chart
            series.forEach(function (serie) {
                routeChart.addSeries(serie, false);
            });
            routeChart.redraw();
        }
    };

    trajectDetail.combineRouteData = function (routeData, xAxisProperty, container) {
        var dict = {}; // <provider name, provider object>

        // combine all data in one object per provider
        routeData.forEach(function (ele) {
            var provider = dict[ele.provider];
            if (!provider) {
                var providerSetting = providerChartSettings[ele.provider];
                provider = {
                    name: ele.provider,
                    color: providerSetting ? providerSetting.color : null,
                    marker: {
                        symbol: providerSetting ? providerSetting.symbol : null
                    },
                    data: []
                };
                dict[ele.provider] = provider;
            }
            provider.data.push([ele.timestamp, ele[xAxisProperty]]);
        });

        // empty container
        container.length = 0;
        // fill container
        for (var providerKey in dict) {
            container.push(dict[providerKey]);
        }
        // sort the providers by name
        container.sort(function (a, b) {
            return (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0);
        });
    };

    trajectDetail.toggleChart = function () {
        if (showingDelayChart) {
            trajectDetail.setChartData(combinedTravelTimes);
            routeChart.setTitle({text: travelTimeTitle});
        } else {
            trajectDetail.setChartData(combinedDelays);
            routeChart.setTitle({text: delayTitle});
        }
        showingDelayChart = !showingDelayChart;
    };

    trajectDetail.getSecondsFromSummaryRow = function (row) {
        var td = row.children[1]; // second td, the traveltime
        var span = td.children[0]; // the span
        return parseInt(span.getAttribute("data-time"));
    };

    trajectDetail.markExtremeProviders = function () {
        var table = document.getElementById("summary-table-body");

        var travelTimes = [];
        var rowLength = table.rows.length;
        for (var i = 0; i < rowLength; i += 1) {
            var row = table.rows[i];
            travelTimes.push(trajectDetail.getSecondsFromSummaryRow(row));
        }

        var mean = verkeer.mean(travelTimes);
        var variance = verkeer.variance(mean, travelTimes);
        var stdev = verkeer.standardDeviation(variance);

        for (i = 0; i < rowLength; i += 1) {
            row = table.rows[i];
            if (!verkeer.withinStd(travelTimes[i], mean, stdev, 1)) {
                row.className += " danger";
            }
        }
    };

    trajectDetail.buildTrafficJamTable = function (jams) {
        var table = document.getElementById("tblJamsBody");

        var html = "";
        for (var i = 0; i < jams.length; i++) {
            var jam = jams[i];
            var jamRow = "<tr>";

            jamRow += "<td>" + moment(jam.trafficJam.from).format("DD/MM/YYYY HH:mm:ss") + "</td>";
            jamRow += "<td>" + moment(jam.trafficJam.to).format("DD/MM/YYYY HH:mm:ss") + "</td>";

            var duration = moment.utc(moment(jam.trafficJam.to).diff(moment(jam.trafficJam.from))).format("HH:mm:ss");
            jamRow += "<td>" + duration + "</td>";

            jamRow += "<td>" + verkeer.secondsToText(jam.trafficJam.avgDelay) + "</td>";
            jamRow += "<td>" + verkeer.secondsToText(jam.trafficJam.maxDelay) + "</td>";

            // TODO possible causes
            jamRow += "<td>";

            if (jam.causes !== null) {
                var causes = jam.causes.sort(function (a, b) {
                    return b.avgProbability - a.avgProbability;
                });
                for (var j = 0; j < causes.length; j++) {
                    var icon = trajectDetail.getIconForJamCause(causes[j]);
                    if (icon !== "") {
                        jamRow += "<img src='" + icon + "'/>";
                    }
                    
                    if(jam.causes[j].description === null && jam.causes[j].category === "Weather") {
                        jamRow += weatherConditions[jam.causes[j].subCategory].discription;
                    }
                    else {
                        jamRow += jam.causes[j].description;
                    }
                    
                    jamRow += " " + (causes[j].avgProbability * 100).toFixed(2) + "%";
                    if (j !== jam.causes.length - 1)
                        jamRow += "<br/>";
                }
            }

            jamRow += "</td>";

            jamRow += "</tr>";
            html += jamRow;
        }

        html += "";

        table.innerHTML = html;
    };

    trajectDetail.getIconForJamCause = function (cause) {
        var iconUrl = "";
        if (cause.category === "POI") { // POI
            iconUrl = verkeer.MAIN_ROOT + "/static/images/poi_";
            switch (cause.subCategory) {
                case 0: //Unknown(0),
                    iconUrl += "unknown";
                    break;
                case 1: //Construction(1),
                    iconUrl += "construction";
                    break;
                case 2: //Incident(2),
                    iconUrl += "incident";
                    break;
                case 3: //TrafficJam(3),
                    iconUrl += "jam";
                    break;
                case 4: //LaneClosed(4),
                    iconUrl += "laneclosed";
                    break;
                case 5: //RoadClosed(5),
                    iconUrl += "roadclosed";
                    break;
                case 6: //PoliceTrap(6),
                    iconUrl += "police";
                    break;
                case 7: //Hazard(7),
                    iconUrl += "hazard";
                    break;
                case 8: //Accident(8);
                    iconUrl += "accident";
                    break;
            }
        } else {
            iconUrl = verkeer.MAIN_ROOT + "/static/images/";
            switch (cause.subCategory) {
                case 4,13,14,49,50:
                    iconUrl += "rain";
                    break;
                case 5-12,43,44:
                    iconUrl += "snow";
                    break;
                case 15-20:
                    iconUrl += "fog";
                    break;
                case 63-68,85:
                    iconUrl += "tstorms";
                    break;                
            }
        }
        
        return iconUrl += ".png";
    };
}(window.verkeer.trajectDetail = window.verkeer.trajectDetail || {}, verkeer, jQuery, moment, Highcharts));
