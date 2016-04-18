(function (routeChart, verkeer, $, moment, Highcharts) {

    var chart;
    var travelTimes = [];
    var delays = [];
    var toggled = false;
    var defaultTitle = "Travel time per provider";
    var toggleTitle = "Delay per provider";
    var providerSettings = {
        Coyote: {color: "#7cb5ec", symbol: "circle"},
        BeMobile: {color: "#434348", symbol: "diamond"},
        ViaMichelin: {color: "#90ed7d", symbol: "square"},
        HereMaps: {color: "#f7a35c", symbol: "triangle"},
        Bing: {color: "#8085e9", symbol: "triangle-down"},
        Waze: {color: "#f15c80", symbol: "circle"},
        TomTom: {color: "#e4d354", symbol: "diamond"},
        GoogleMaps: {color: "#2b908f", symbol: "square"}
    };

    routeChart.setDefaultTitle = function (title) {
        defaultTitle = title;
    };

    routeChart.setToggleTitle = function (title) {
        toggleTitle = title;
    };

    routeChart.showDefaultTitle = function () {
        chart.setTitle({text: defaultTitle});
    };

    routeChart.showToggleTitle = function () {
        chart.setTitle({text: toggleTitle});
    };

    // todo weg
    routeChart.getRouteData = function (options) {
        $.ajax({
            method: "GET",
            url: options.url,
            data: {
                id: options.id,
                startDate: options.beginDate,
                endDate: options.endDate
            },
            success: function (data) {
                toggled = false;
                chart.setTitle({text: defaultTitle});
                routeChart.combineRouteData(data.values, "travelTime", travelTimes);
                routeChart.combineRouteData(data.values, "delay", delays);
                routeChart.setChartData(travelTimes);
                if(options.onSuccess) options.onSuccess(data);
            },
            complete: function () {
                if(options.onComplete) options.onComplete();
            }
        });
    };

    routeChart.buildChart = function (chartId) {
        chart = new Highcharts.Chart({
            chart: {
                zoomType: "x",
                renderTo: chartId
            },
            title: {
                text: defaultTitle
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
                    return date + "<br/>" + "<span style='color:" + this.point.series.color + "'>" + routeChart.getPointSymbol(this.point)
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

    routeChart.getPointSymbol = function (point) {
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

    routeChart.clearAllChartData = function () {
        if (!chart || !chart.series)
            return;

        while (chart.series.length > 0) {
            // todo bug? Repaints with false...
            chart.series[0].remove(false);
        }
        chart.redraw();
    };

    routeChart.setChartData = function (series) {
        routeChart.clearAllChartData();

        if (series && series.length > 0) {
            // Put new data on chart
            series.forEach(function (serie) {
                chart.addSeries(serie, false);
            });
            chart.redraw();
        }
    };

    routeChart.combineRouteData = function (routeData, xAxisProperty, container) {
        var dict = {}; // <provider name, provider object>

        // combine all data in one object per provider
        routeData.forEach(function (ele) {
            var provider = dict[ele.provider];
            if (!provider) {
                var providerSetting = providerSettings[ele.provider];
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

    routeChart.toggleChart = function () {
        if (toggled) {
            routeChart.setChartData(travelTimes);
            chart.setTitle({text: defaultTitle});
        } else {
            routeChart.setChartData(delays);
            chart.setTitle({text: toggleTitle});
        }
        toggled = !toggled;
    };
}(window.verkeer.routeChart = window.verkeer.routeChart || {}, verkeer, jQuery, moment, Highcharts));