/**
 * This code is used by both compare pages, it has all basic features for a graph with two series
 */
(function (compare, routeChart) {

    const chartId = "container";

    /**
     * Sets chart titles and builds empty chart
     */
    compare.initChart = function () {
        routeChart.setDefaultTitle("Travel time comparison");
        routeChart.setToggleTitle("Delay comparison");
        routeChart.buildChart(chartId);
    };

    /**
     * Shows the data on the graph
     * @param route1Data data route 1
     * @param route2Data data route 2
     */
    compare.showRoutesOnChart = function (route1Data, route2Data) {
        var series = [];
        series.push({name: compare.routeInfo1.name, data: route1Data, color: "#F4D03F", marker: {symbol: "square"}});
        series.push({name: compare.routeInfo2.name, data: route2Data, color: "#1B4F72", marker: {symbol: "triangle"}});
        routeChart.setChartData(series);
    };

    /**
     * Returns the provider names that are checked
     * @returns {Array}
     */
    compare.getCheckedProviders = function () {
        var providers = [];
        var checkboxes = document.querySelectorAll("input[name=providers]:checked");
        for (var i = 0; i < checkboxes.length; i++) {
            providers.push(checkboxes[i].value);
        }
        return providers;
    };

    /**
     * Returns the selected route by name and id
     * @param optionsId id of the form
     * @returns {{id: *, name: *}}
     */
    compare.getSelectedRoute = function (optionsId) {
        var routeOptions = document.getElementById(optionsId);
        var id = routeOptions.options[routeOptions.selectedIndex].value;
        var name = routeOptions.options[routeOptions.selectedIndex].text;
        return {id: id, name: name};
    };

    /**
     * Toggles the chart between delay and traveltime
     */
    compare.toggleChart = function () {
        if (compare.toggled) {
            routeChart.showDefaultTitle();
            compare.showRoutesOnChart(compare.data.route1TravelTime, compare.data.route2TravelTime);
        } else {
            routeChart.showToggleTitle();
            compare.showRoutesOnChart(compare.data.route1Delay, compare.data.route2Delay);
        }
        compare.toggled = !compare.toggled;
    };

}(window.verkeer.compare = window.verkeer.compare || {}, verkeer.routeChart));