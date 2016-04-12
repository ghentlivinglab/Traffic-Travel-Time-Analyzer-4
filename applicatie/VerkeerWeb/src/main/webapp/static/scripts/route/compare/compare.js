(function (compare, routeChart, $) {

    const chartId = "container";
    var datePickerBegin;
    var datePickerEnd;

    routeChart.setDefaultTitle("Travel time comparison");
    routeChart.setToggleTitle("Delay comparison");
    $(document).ready(function () {
        datePickerBegin = $("#datetimepicker-begin");
        datePickerEnd = $("#datetimepicker-end");
        datePickerBegin.datetimepicker({
            format: "DD/MM/YYYY HH:mm",
            showTodayButton: true,
            showClear: true,
            defaultDate: moment().startOf("day")
        });
        datePickerEnd.datetimepicker({
            format: "DD/MM/YYYY HH:mm",
            showTodayButton: true,
            showClear: true,
            defaultDate: moment().endOf("day")
        });

        routeChart.buildChart(chartId);
        compare.getRouteData();
    });

    compare.getRouteData = function () {
        var providers = [0,1,2];
        $.ajax({
            method: "GET",
            url: "comparedata",
            data: {
                routeId1: 11,
                routeId2: 12,
                startDate: new Date(2016, 2, 21, 0, 0, 0),
                endDate: new Date(2016, 2, 21, 23, 59, 59)
            },
            success: function (data) {
                var series = [];
                series.push({name: "Route 1", data: data.route1TravelTime});
                series.push({name: "Route 2", data: data.route2TravelTime});
                routeChart.setChartData(series);
            }
        });
    };

}(window.verkeer.compare = window.verkeer.compare || {}, verkeer.routeChart, jQuery));