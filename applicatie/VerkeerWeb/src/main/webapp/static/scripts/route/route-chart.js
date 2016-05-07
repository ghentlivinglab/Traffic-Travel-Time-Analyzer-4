(function (routeChart, verkeer, $, moment, Highcharts) {

    var chart;
    var defaultTitle = "Travel time per provider";
    var toggleTitle = "Delay per provider";

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
                    var date = moment(this.x).utc().format("dddd, MMMM Do, HH:mm:ss");
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

}(window.verkeer.routeChart = window.verkeer.routeChart || {}, verkeer, jQuery, moment, Highcharts));