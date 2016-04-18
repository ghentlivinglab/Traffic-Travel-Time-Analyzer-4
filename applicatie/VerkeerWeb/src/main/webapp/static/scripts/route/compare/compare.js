(function (compare, routeChart, $) {

    // view
    const chartId = "container";
    var refreshIcon;
    var updateBtn;
    var toggleBtn;
    var datePickerBegin;
    var datePickerEnd;

    // data
    var data;
    var toggled = false;
    var route1Selection;
    var route2Selection;

    $(document).ready(function () {
        // find buttons
        refreshIcon = document.getElementById("refresh-icon");
        updateBtn = document.getElementById("update-btn");
        toggleBtn = document.getElementById("toggle-btn");
        datePickerBegin = $("#datetimepicker-begin");
        datePickerEnd = $("#datetimepicker-end");

        // init datepickers
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

        // set button actions
        updateBtn.addEventListener("click", compare.updateChart);
        toggleBtn.addEventListener("click", compare.toggleChart);

        // init chart
        routeChart.setDefaultTitle("Travel time comparison");
        routeChart.setToggleTitle("Delay comparison");
        routeChart.buildChart(chartId);
    });

    compare.updateChart = function () {
        route1Selection = compare.getSelectedRoute("route1");
        route2Selection = compare.getSelectedRoute("route2");
        compare.fetchRouteData();
    };

    compare.showRoutesOnChart = function (route1Data, route2Data) {
        var series = [];
        series.push({name: route1Selection.name, data: route1Data});
        series.push({name: route2Selection.name, data: route2Data});
        routeChart.setChartData(series);
    };

    compare.getCheckedProviders = function () {
        var providers = [];
        var checkboxes = document.querySelectorAll("input[name=providers]:checked");
        for (var i = 0; i < checkboxes.length; i++) {
            providers.push(checkboxes[i].value);
        }
        return providers;
    };

    compare.fetchRouteData = function () {
        var providers = compare.getCheckedProviders();

        $.ajax({
            method: "GET",
            url: "comparedata",
            data: {
                routeId1: route1Selection.id,
                routeId2: route2Selection.id,
                startDate: datePickerBegin.data("DateTimePicker").date().toDate(),
                endDate: datePickerEnd.data("DateTimePicker").date().toDate(),
                providers: providers.toString()
            },
            success: function (routeData) {
                data = routeData;
                toggled = false;
                routeChart.showDefaultTitle();
                compare.showRoutesOnChart(data.route1TravelTime, data.route2TravelTime);
            }
        });
    };

    compare.getSelectedRoute = function (optionsId) {
        var routeOptions = document.getElementById(optionsId);
        var id = routeOptions.options[routeOptions.selectedIndex].value;
        var name = routeOptions.options[routeOptions.selectedIndex].text;
        return {id: id, name: name};
    };

    compare.toggleChart = function () {
        if (!toggled) {
            routeChart.showToggleTitle();
            compare.showRoutesOnChart(data.route1Delay, data.route2Delay);
        } else {
            routeChart.showDefaultTitle();
            compare.showRoutesOnChart(data.route1TravelTime, data.route2TravelTime);
        }
        toggled = !toggled;
    };

}(window.verkeer.compare = window.verkeer.compare || {}, verkeer.routeChart, jQuery));