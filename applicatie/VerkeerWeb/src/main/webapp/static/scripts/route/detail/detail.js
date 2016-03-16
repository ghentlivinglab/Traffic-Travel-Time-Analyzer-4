(function (trajectDetail, $) {
    "use strict";

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

    $(document).ready(function () {
        trajectDetail.markExtremeProviders();
        $("#datetimepicker-begin").datetimepicker({
            format: 'DD/MM/YYYY HH:mm',
            showTodayButton: true,
            showClear: true,
            defaultDate: moment().startOf('day')
        });

        $("#datetimepicker-end").datetimepicker(
                {
                    format: 'DD/MM/YYYY HH:mm',
                    showTodayButton: true,
                    showClear: true,
                    defaultDate: moment().endOf('day')
                }
        );

        $("#update-btn").click(trajectDetail.getRouteData);
        $("#toggle-btn").click(trajectDetail.toggleChart);
        trajectDetail.buildChart();
        // Fill table with the default filters
        trajectDetail.getRouteData();
    });

    trajectDetail.getRouteData = function () {
        $.ajax({
            method: "GET",
            url: "../routedata",
            data: {
                id: $("#routeId").val(),
                startDate: $("#datetimepicker-begin").data("DateTimePicker").date().toDate(),
                endDate: $("#datetimepicker-end").data("DateTimePicker").date().toDate()
            }
        })
                .done(function (data) {
                    showingDelayChart = false;
                    routeChart.setTitle({text: travelTimeTitle});
                    trajectDetail.combineRouteData(data.values, "travelTime", combinedTravelTimes);
                    trajectDetail.combineRouteData(data.values, "delay", combinedDelays);
                    trajectDetail.setChartData(combinedTravelTimes);
                    trajectDetail.buildTrafficJamTable(data.jams);
                });
    };

    trajectDetail.buildChart = function () {
        routeChart = new Highcharts.Chart({
            chart: {
                zoomType: 'x',
                renderTo: 'container'
            },
            title: {
                text: travelTimeTitle
            },
            subtitle: {
                text: document.ontouchstart === undefined ?
                        'Click and drag in the plot area to zoom in' : 'Pinch the chart to zoom in'
            },
            xAxis: {
                title: {
                    text: 'Timestamp'
                },
                type: "datetime"
            },
            yAxis: {
                title: {
                    text: 'Duration'
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
                    return date + "<br/>" + '<span style="color:' + this.point.series.color + '">' + trajectDetail.getPointSymbol(this.point)
                            + '</span> ' + this.series.name + ': <b>' + verkeer.secondsToText(this.point.y) + "</b>";
                }
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            }
        });
    };

    trajectDetail.getPointSymbol = function (point) {
        var symbol = "";
        if (point.series && point.series.symbol) {
            switch (point.series.symbol) {
                case 'circle':
                    symbol = '●';
                    break;
                case 'diamond':
                    symbol = '♦';
                    break;
                case 'square':
                    symbol = '■';
                    break;
                case 'triangle':
                    symbol = '▲';
                    break;
                case 'triangle-down':
                    symbol = '▼';
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
            
            if(jam.causes !== null) {
                for (var j = 0; j < jam.causes.length; j++) {                     
                    jamRow += "Cause: C:" + jam.causes[j].category  + ", subcat: " + jam.causes[j].subCategory + ": " + (jam.causes[j].probability * 100).toFixed(2) + "%";
                    if(j !== jam.causes.length - 1)
                        jamRow += "<br/>"
                }
            }
                    
            jamRow += "</td>";

            jamRow += "</tr>";
            html += jamRow;
        }

        html += "";

        table.innerHTML = html;
    };

}(window.verkeer.trajectDetail = window.verkeer.trajectDetail || {}, jQuery));