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
        //compare.getRouteData();
    });

    compare.getRouteData = function () {
        $.ajax({
            method: "GET",
            url: "routedata",
            data: {
                id: 11,
                startDate: new Date(2016, 2, 22, 0, 0, 0),
                endDate: new Date(2016, 2, 23, 0, 0, 0)
            },
            success: function (data) {
                // todo not finished! Needs to be server side!
                return;

                if (!data || !data.values || data.values.length === 0) {
                    return;
                }

                var provider = {
                    name: "test",
                    data: []
                };

                var routeData = data.values;
                var start = routeData[0].timestamp;
                var results = [];
                while (routeData.length >= 0) {
                    var baseTime = 0;
                    var amount = 0;
                    while (routeData.length >= 0) {
                        if (routeData[0] && routeData[0].timestamp <= (start + 60)) {
                            baseTime += routeData[0].baseTime;
                            amount++;
                        }
                        routeData.shift();
                    }
                    if (amount > 0) {
                        var avg = baseTime / amount;
                        results.push([start, avg]);
                    }
                    start += 60;
                }

                provider.data = results;
                routeChart.setChartData(provider);
            }
        });
    };

}(window.verkeer.compare = window.verkeer.compare || {}, verkeer.routeChart, jQuery));