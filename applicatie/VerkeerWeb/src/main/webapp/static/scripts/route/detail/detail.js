(function (trajectDetail, $) {
    "use strict";

    var routeChart;
    var combinedTravelTimes = [];
    var combinedDelays = [];
    var showingDelayChart = false;
    const travelTimeTitle = "Travel time per provider";
    const delayTitle = "Delay per provider";

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

    trajectDetail.getRouteData = function() {
        $.ajax({
                method: "GET",
                url: "../routedata",
                data: {
                    id: $("#routeId").val(),
                    startDate: $("#datetimepicker-begin").data("DateTimePicker").date().toDate(),
                    endDate: $("#datetimepicker-end").data("DateTimePicker").date().toDate()
                }
            })
            .done(function (routeData) {
                showingDelayChart = false;
                routeChart.setTitle({text: travelTimeTitle});
                trajectDetail.setTableData("#data-table", "#data-table-body", routeData);
                trajectDetail.combineRouteData(routeData, "travelTime", combinedTravelTimes);
                trajectDetail.combineRouteData(routeData, "delay", combinedDelays);
                trajectDetail.setChartData(combinedTravelTimes);
            });
    };

    trajectDetail.setTableData = function(tableId, tableBodyId, routeData) {
        $(tableBodyId).empty();
        routeData.forEach(function (ele) {
            var date = new Date(ele.timestamp);
            var delayLevel = verkeer.getDelayLevel(ele.delay);
            var label = verkeer.getBootstrapLabelDelay(delayLevel);
            var row = "<tr>"
                + "<td>" + moment(date).format("D/MM/YYYY") + "</td>"
                + "<td>" + moment(date).format("HH:mm:ss") + "</td>"
                + "<td>" + ele.provider + "</td>"
                + "<td>" + verkeer.secondsToText(ele.travelTime) + "</td>"
                + "<td><span class='" + label + "'>" + verkeer.secondsToText(ele.delay) + "</span></td>"
                + "</tr>";
            $(tableBodyId).append(row);
        });
        // Make sure to refresh sort again
        $(tableId).trigger("update");
        // set sorting column and direction
        var sorting = [[0, 0], [1, 0], [2, 0]];
        $(tableId).trigger("sorton", [sorting]);
    };

    trajectDetail.buildChart = function() {
        $('#container').highcharts({
            chart: {
                zoomType: 'x'
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
                    text: 'Duration (s)'
                }
            },
            tooltip: {
                valueSuffix: 's'
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            }
        });
        routeChart = $('#container').highcharts();
    };

    trajectDetail.clearAllChartData = function() {
        if (!routeChart || !routeChart.series) return;

        while (routeChart.series.length > 0) {
            // todo bug? Repaints with false...
            routeChart.series[0].remove(false);
        }
        routeChart.redraw();
    };

    trajectDetail.setChartData = function(series) {
        trajectDetail.clearAllChartData();

        if (series && series.length > 0) {
            // Put new data on chart
            series.forEach(function (serie) {
                routeChart.addSeries(serie, false);
            });
            routeChart.redraw();
        }
    };

    trajectDetail.combineRouteData = function(routeData, xAxisProperty, container) {
        var dict = {}; // <provider name, provider object>

        // combine all data in one object per provider
        routeData.forEach(function (ele) {
            var provider = dict[ele.provider];
            if (!provider) {
                provider = {
                    name: ele.provider,
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
    };

    trajectDetail.toggleChart = function() {
        if (showingDelayChart) {
            trajectDetail.setChartData(combinedTravelTimes);
            routeChart.setTitle({text: travelTimeTitle});
        } else {
            trajectDetail.setChartData(combinedDelays);
            routeChart.setTitle({text: delayTitle});
        }
        showingDelayChart = !showingDelayChart;
    };

    trajectDetail.getSecondsFromSummaryRow = function(row) {
        var td = row.children[1]; // second td, the traveltime
        var span = td.children[0]; // the span
        return parseInt(span.getAttribute("data-time"));
    };

    trajectDetail.markExtremeProviders = function() {
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
}(window.verkeer.trajectDetail = window.verkeer.trajectDetail || {}, jQuery));