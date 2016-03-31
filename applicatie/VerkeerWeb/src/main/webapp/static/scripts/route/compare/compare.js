(function (compare, routeChart, $) {

    const chartId = "container";

    routeChart.setDefaultTitle("Travel time comparison");
    routeChart.setToggleTitle("Delay comparison");
    $(document).ready(function () {
        routeChart.buildChart(chartId);
    });

}(window.verkeer.compare = window.verkeer.compare || {}, verkeer.routeChart, jQuery));