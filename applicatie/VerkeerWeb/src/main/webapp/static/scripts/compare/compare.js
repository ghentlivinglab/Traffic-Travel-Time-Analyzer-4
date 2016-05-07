(function (compare, routeChart) {

    // view
    const chartId = "container";

    compare.initChart = function () {
        routeChart.setDefaultTitle("Travel time comparison");
        routeChart.setToggleTitle("Delay comparison");
        routeChart.buildChart(chartId);
    };

    compare.showRoutesOnChart = function (route1Data, route2Data) {
        var series = [];
        series.push({name: compare.routeInfo1.name, data: route1Data, color: "#F4D03F", marker: {symbol: "square"}});
        series.push({name: compare.routeInfo2.name, data: route2Data, color: "#1B4F72", marker: {symbol: "triangle"}});
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

    compare.getSelectedRoute = function (optionsId) {
        var routeOptions = document.getElementById(optionsId);
        var id = routeOptions.options[routeOptions.selectedIndex].value;
        var name = routeOptions.options[routeOptions.selectedIndex].text;
        return {id: id, name: name};
    };

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