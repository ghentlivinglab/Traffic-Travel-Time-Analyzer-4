(function (routes, compareBase, routeChart, $) {

    var updateBtn;
    var toggleBtn;
    var datePickerBegin;
    var datePickerEnd;

    $(document).ready(function () {
        // find buttons
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
        updateBtn.addEventListener("click", routes.updateChart);
        toggleBtn.addEventListener("click", compareBase.toggleChart);

        // init chart
        compareBase.initChart();
    });

    /**
     * Get form data and fetch the correct info
     */
    routes.updateChart = function () {
        compareBase.routeInfo1 = compareBase.getSelectedRoute("route1");
        compareBase.routeInfo2 = compareBase.getSelectedRoute("route2");
        routes.fetchRouteData();
    };

    /**
     * Downloads the route data from the server
     */
    routes.fetchRouteData = function () {
        var providers = compareBase.getCheckedProviders();

        $.ajax({
            method: "GET",
            url: "routesREST",
            data: {
                routeId1: compareBase.routeInfo1.id,
                routeId2: compareBase.routeInfo2.id,
                startDate: datePickerBegin.data("DateTimePicker").date().toDate(),
                endDate: datePickerEnd.data("DateTimePicker").date().toDate(),
                providers: providers.toString()
            },
            success: function (routeData) {
                compareBase.data = routeData;
                compareBase.toggled = false;
                routeChart.showDefaultTitle();
                compareBase.showRoutesOnChart(compareBase.data.route1TravelTime, compareBase.data.route2TravelTime);
            }
        });
    };

}(window.verkeer.compare.routes = window.verkeer.compare.routes || {}, verkeer.compare, verkeer.routeChart, jQuery));