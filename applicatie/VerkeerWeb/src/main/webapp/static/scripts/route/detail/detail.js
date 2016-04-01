(function (trajectDetail, verkeer, routeChart, $, moment) {

    // view only variables
    var refreshIcon;
    var updateBtn;
    var toggleBtn;
    var datePickerBegin;
    var datePickerEnd;

    var id;
    const url = "../routedata";
    const chartId = "container";

    $(document).ready(function () {
        refreshIcon = document.getElementById("refresh-icon");
        updateBtn = document.getElementById("update-btn");
        toggleBtn = document.getElementById("toggle-btn");
        datePickerBegin = $("#datetimepicker-begin");
        datePickerEnd = $("#datetimepicker-end");
        id = $("#routeId").val();
        datePickerBegin.datetimepicker({
            format: "DD/MM/YYYY HH:mm",
            showTodayButton: true,
            showClear: true,
            defaultDate: moment().startOf("day")
        });
        datePickerEnd.datetimepicker(
            {
                format: "DD/MM/YYYY HH:mm",
                showTodayButton: true,
                showClear: true,
                defaultDate: moment().endOf("day")
            }
        );
        updateBtn.addEventListener("click", trajectDetail.getRouteData);
        toggleBtn.addEventListener("click", routeChart.toggleChart);
        trajectDetail.markExtremeProviders();
        routeChart.buildChart(chartId);
        trajectDetail.getRouteData();
    });

    trajectDetail.getRouteData = function () {
        // spin the update button
        refreshIcon.classList.add("spinning");
        var options = {
            url: url,
            id: id,
            beginDate: datePickerBegin.data("DateTimePicker").date().toDate(),
            endDate: datePickerEnd.data("DateTimePicker").date().toDate(),
            onSuccess: function (data) {
                trajectDetail.buildTrafficJamTable(data.jams);
            },
            onComplete: function () {
                // stop the update button spinning
                refreshIcon.classList.remove("spinning");
            }
        };
        routeChart.getRouteData(options);
    };

    trajectDetail.getSecondsFromSummaryRow = function (row) {
        var td = row.children[1]; // second td, the traveltime
        var span = td.children[0]; // the span
        return parseInt(span.getAttribute("data-time"));
    };

    trajectDetail.markExtremeProviders = function () {
        var table = document.getElementById("summary-table-body");

        var travelTimes = [];
        var rowLength = table.rows.length;
        for (var i = 0; i < rowLength; i += 1) {
            var row = table.rows[i];
            travelTimes.push(trajectDetail.getSecondsFromSummaryRow(row));
        }

        var mean = verkeer.mean(travelTimes);
        var variance = verkeer.variance(mean, travelTimes);
        var stdev = verkeer.standardDeviation(variance);

        for (i = 0; i < rowLength; i += 1) {
            row = table.rows[i];
            if (!verkeer.withinStd(travelTimes[i], mean, stdev, 1)) {
                row.className += " danger";
            }
        }
    };

    trajectDetail.buildTrafficJamTable = function (jams) {
        var table = document.getElementById("tblJamsBody");

        var html = "";
        for (var i = 0; i < jams.length; i++) {
            var jam = jams[i];
            var jamRow = "<tr>";

            jamRow += "<td>" + moment(jam.trafficJam.from).format("DD/MM/YYYY HH:mm:ss") + "</td>";
            jamRow += "<td>" + moment(jam.trafficJam.to).format("DD/MM/YYYY HH:mm:ss") + "</td>";

            var duration = moment.utc(moment(jam.trafficJam.to).diff(moment(jam.trafficJam.from))).format("HH:mm:ss");
            jamRow += "<td>" + duration + "</td>";

            jamRow += "<td>" + verkeer.secondsToText(jam.trafficJam.avgDelay) + "</td>";
            jamRow += "<td>" + verkeer.secondsToText(jam.trafficJam.maxDelay) + "</td>";

            // TODO possible causes
            jamRow += "<td>";

            if (jam.causes !== null) {
                var causes = jam.causes.sort(function (a, b) {
                    return b.avgProbability - a.avgProbability;
                });
                for (var j = 0; j < causes.length; j++) {
                    var icon = trajectDetail.getIconForJamCause(causes[j]);
                    if (icon !== "") {
                        jamRow += "<img src='" + icon + "'/>";
                    }
                    //(C:" + causes[j].category + ", subcat: " + causes[j].subCategory + "): "
                    jamRow += jam.causes[j].description + " " + (causes[j].avgProbability * 100).toFixed(2) + "%";
                    if (j !== jam.causes.length - 1)
                        jamRow += "<br/>";
                }
            }

            jamRow += "</td>";

            jamRow += "</tr>";
            html += jamRow;
        }

        html += "";

        table.innerHTML = html;
    };

    trajectDetail.getIconForJamCause = function (cause) {
        var iconUrl = verkeer.MAIN_ROOT + "/static/images/poi_";
        if (cause.category === "POI") { // POI
            switch (cause.subCategory) {
                case 0: //Unknown(0),
                    iconUrl += "unknown";
                    break;
                case 1: //Construction(1),
                    iconUrl += "construction";
                    break;
                case 2: //Incident(2),
                    iconUrl += "incident";
                    break;
                case 3: //TrafficJam(3),
                    iconUrl += "jam";
                    break;
                case 4: //LaneClosed(4),
                    iconUrl += "laneclosed";
                    break;
                case 5: //RoadClosed(5),
                    iconUrl += "roadclosed";
                    break;
                case 6: //PoliceTrap(6),
                    iconUrl += "police";
                    break;
                case 7: //Hazard(7),
                    iconUrl += "hazard";
                    break;
                case 8: //Accident(8);
                    iconUrl += "accident";
                    break;
            }
            iconUrl += ".png";
            return iconUrl;
        } else {
            return "";
        }
    };
}(window.verkeer.trajectDetail = window.verkeer.trajectDetail || {}, verkeer, verkeer.routeChart, jQuery, moment));