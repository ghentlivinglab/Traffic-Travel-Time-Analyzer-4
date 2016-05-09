(function (routeCompare, compareBase, routeChart, $) {

    // view vars
    var updateBtn;
    var toggleBtn;
    var datePickerBegin1;
    var datePickerEnd1;
    var datePickerBegin2;
    var datePickerEnd2;

    $(document).ready(function () {
        // find buttons
        updateBtn = document.getElementById("update-btn");
        toggleBtn = document.getElementById("toggle-btn");
        datePickerBegin1 = $("#datetimepicker-begin1");
        datePickerEnd1 = $("#datetimepicker-end1");
        datePickerBegin2 = $("#datetimepicker-begin2");
        datePickerEnd2 = $("#datetimepicker-end2");

        // init datepickers
        datePickerBegin1.datetimepicker({
            format: "DD/MM/YYYY HH:mm",
            showTodayButton: true,
            showClear: true,
            defaultDate: moment().startOf("day")
        });
        datePickerEnd1.datetimepicker({
            format: "DD/MM/YYYY HH:mm",
            showTodayButton: true,
            showClear: true,
            defaultDate: moment().endOf("day")
        });
        datePickerBegin2.datetimepicker({
            format: "DD/MM/YYYY HH:mm",
            showTodayButton: true,
            showClear: true,
            defaultDate: moment().startOf("day")
        });
        datePickerEnd2.datetimepicker({
            format: "DD/MM/YYYY HH:mm",
            showTodayButton: true,
            showClear: true,
            defaultDate: moment().endOf("day")
        });

        // set button actions
        updateBtn.addEventListener("click", routeCompare.updateChart);
        toggleBtn.addEventListener("click", compareBase.toggleChart);

        // init chart
        compareBase.initChart();
    });

    /**
     * Get form data and fetch the correct info
     */
    routeCompare.updateChart = function () {
        compareBase.routeInfo1 = compareBase.getSelectedRoute("route");
        compareBase.routeInfo2 = compareBase.routeInfo1;
        routeCompare.fetchRouteData();
    };

    /**
     * Downloads the route data from the server
     */
    routeCompare.fetchRouteData = function () {
        var providers = compareBase.getCheckedProviders();
        $.ajax({
            method: "GET",
            url: "routeREST",
            data: {
                routeId: compareBase.routeInfo1.id,
                startDate1: datePickerBegin1.data("DateTimePicker").date().toDate(),
                endDate1: datePickerEnd1.data("DateTimePicker").date().toDate(),
                startDate2: datePickerBegin2.data("DateTimePicker").date().toDate(),
                endDate2: datePickerEnd2.data("DateTimePicker").date().toDate(),
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

}(window.verkeer.compare.route = window.verkeer.compare.route || {}, verkeer.compare, verkeer.routeChart, jQuery));