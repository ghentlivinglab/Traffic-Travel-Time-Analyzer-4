$(function () {
    "use strict";

    var routeChart;
    $(document).ready(function () {

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
        buildChart();
        // Fill table with the default filters
        getRouteData();
    });

    function getRouteData() {
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
                setTableData("#data-table", "#data-table-body", routeData);
                setChartData(routeData);
            });
    }

    function setTableData(tableId, tableBodyId, routeData) {
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
    }

    function buildChart() {
        $('#container').highcharts({
            chart: {
                zoomType: 'x'
            },
            title: {
                text: 'Historiek per provider'
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
                    text: 'Reistijd (s)'
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
    }

    function clearAllChartData(redraw) {
        if (!routeChart || !routeChart.series) return;

        while (routeChart.series.length > 0) {
            // todo bug? Repaints with false...
            routeChart.series[0].remove(false);
        }
        if (redraw) routeChart.redraw();
    }

    function setChartData(routeData) {
        var newData = false;
        if (routeData && routeData.length !== 0) {
            newData = true;
        }
        if (routeChart.series.length > 0) {
            // clear existing data
            clearAllChartData(!newData);
        }
        if (newData) {
            // Put new data on chart
            var providers = combineRouteData(routeData);
            for (var provider in providers) {
                routeChart.addSeries(providers[provider], false);
            }
            routeChart.redraw();
        }
    }

    function combineRouteData(routeData) {
        var providersDict = {};
        routeData.forEach(function (ele) {
            var provider = providersDict[ele.provider];
            if (!provider) {
                provider = {
                    name: ele.provider,
                    data: []
                };
                providersDict[ele.provider] = provider;
            }
            provider.data.push([ele.timestamp, ele.travelTime]);
        });
        var providerArr = [];
        for (var providerKey in providersDict) {
            providerArr.push(providersDict[providerKey]);
        }
        return providerArr;
    }
});