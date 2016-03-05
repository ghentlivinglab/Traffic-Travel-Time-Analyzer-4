$(function () {
    "use strict";

    var routeChart;
    var combinedTravelTimes = [];
    var combinedDelays = [];
    var showingDelayChart = false;
    const travelTimeTitle = "Reistijden per provider";
    const delayTitle = "Vertraging per provider";

    $(document).ready(function () {
        markExtremeProviders();
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

        $("#update-btn").click(getRouteData);
        $("#toggle-btn").click(toggleChart);
        buildChart();
        // Fill table with the default filters
        getRouteData();
    });

    var getRouteData = function getRouteData() {
        $.ajax({
                method: "GET",
                url: "routedata",
                data: {
                    id: getUrlParameter('id'),
                    startDate: $("#datetimepicker-begin").data("DateTimePicker").date().toDate(),
                    endDate: $("#datetimepicker-end").data("DateTimePicker").date().toDate()
                }
            })
            .done(function (routeData) {
                showingDelayChart = false;
                routeChart.setTitle({text: travelTimeTitle});
                setTableData("#data-table", "#data-table-body", routeData);
                combineRouteData(routeData, "travelTime", combinedTravelTimes);
                combineRouteData(routeData, "delay", combinedDelays);
                setChartData(combinedTravelTimes);
            });
    };

    var setTableData = function setTableData(tableId, tableBodyId, routeData) {
        $(tableBodyId).empty();
        routeData.forEach(function (ele) {
            var date = new Date(ele.timestamp);
            var delayLevel = getDelayLevel(ele.delay);
            var label = getBootstrapLabelDelay(delayLevel);
            var row = "<tr>"
                + "<td>" + moment(date).format("D/MM/YYYY") + "</td>"
                + "<td>" + moment(date).format("HH:mm:ss") + "</td>"
                + "<td>" + ele.provider + "</td>"
                + "<td>" + secondsToText(ele.travelTime) + "</td>"
                + "<td><span class='" + label + "'>" + secondsToText(ele.delay) + "</span></td>"
                + "</tr>";
            $(tableBodyId).append(row);
        });
        // Make sure to refresh sort again
        $(tableId).trigger("update");
        // set sorting column and direction
        var sorting = [[0, 0], [1, 0], [2, 0]];
        $(tableId).trigger("sorton", [sorting]);
    };

    var buildChart = function buildChart() {
        $('#container').highcharts({
            chart: {
                zoomType: 'x'
            },
            title: {
                text: travelTimeTitle
            },
            subtitle: {
                text: document.ontouchstart === undefined ?
                    'Klik en sleep op de grafiek om te zoomen' : 'Pinch op de grafiek om te zoomen'
            },
            xAxis: {
                title: {
                    text: 'Tijdstip'
                },
                type: "datetime"
            },
            yAxis: {
                title: {
                    text: 'Duur (s)'
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

    var clearAllChartData = function clearAllChartData() {
        if (!routeChart || !routeChart.series) return;

        while (routeChart.series.length > 0) {
            // todo bug? Repaints with false...
            routeChart.series[0].remove(false);
        }
        routeChart.redraw();
    };

    var setChartData = function setChartData(series) {
        clearAllChartData();

        if (series && series.length > 0) {
            // Put new data on chart
            series.forEach(function (serie) {
                routeChart.addSeries(serie, false);
            });
            routeChart.redraw();
        }
    };

    var combineRouteData = function combineRouteData(routeData, xAxisProperty, container) {
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

    var toggleChart = function toggleChart() {
        if (showingDelayChart) {
            setChartData(combinedTravelTimes);
            routeChart.setTitle({text: travelTimeTitle});
        } else {
            setChartData(combinedDelays);
            routeChart.setTitle({text: delayTitle});
        }
        showingDelayChart = !showingDelayChart;
    };

    var getSecondsFromSummaryRow = function getSecondsFromSummaryRow(row) {
        var td = row.children[1]; // second td, the traveltime
        var span = td.children[0]; // the span
        return parseInt(span.getAttribute("data-time"));
    };

    var markExtremeProviders = function markExtremeProviders() {
        var table = document.getElementById("summary-table-body");

        var travelTimes = [];
        var rowLength = table.rows.length;
        for (var i = 0; i < rowLength; i += 1) {
            var row = table.rows[i];
            travelTimes.push(getSecondsFromSummaryRow(row));
        }

        var m = mean(travelTimes);
        var vrnc = variance(m, travelTimes);
        var stdev = standardDeviation(vrnc);

        for (i = 0; i < rowLength; i += 1) {
            row = table.rows[i];
            if (!withinStd(travelTimes[i], m, stdev, 1)) {
                row.className += " danger";
            }
        }
    };
});