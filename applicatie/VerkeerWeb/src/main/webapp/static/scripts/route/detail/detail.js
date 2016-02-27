$(function () {
    "use strict";

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
                sideBySide: true,
                defaultDate: moment().endOf('day')
            }
        );

        $("#update-btn").click(getRouteData);
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
                setTableData("#data-table-body", routeData)
            });
    }

    function setTableData(tableBodyId, routeData) {
        $(tableBodyId).empty();
        routeData.forEach(function (ele) {
            var date = new Date(ele.timestamp);
            var row = "<tr>"
                + "<td>" + moment(date).format("D/MM/YYYY") + "</td>"
                + "<td>" + moment(date).format("HH:mm:ss") + "</td>"
                + "<td>" + ele.provider + "</td>"
                + "<td>" + secondsToText(ele.travelTime) + "</td>"
                + "<td><span class='label label-warning'>" + secondsToText(ele.delay) + "</span></td>"
                + "</tr>"
            $(tableBodyId).append(row);
        });
    }
});

