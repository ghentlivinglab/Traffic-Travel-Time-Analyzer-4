(function (log, routeChart, $) {

    // view
    const chartId = "container";
    var updateBtn;  //calender
    var datePickerBegin;
    var datePickerEnd;

    // data
    var data;

    $(document).ready(function () {
        // find buttons
        updateBtn = document.getElementById("update-btn");
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
        updateBtn.addEventListener("click", log.updateLogs);
    });
    
    log.updateLogs = function () {
        categorySelection = log.getSelectedCategory("category");
        log.fetchLogData();
    };
    
    
    log.getSelectedCategory = function (optionsId) {
        var categoryOptions = document.getElementById(optionsId);
        var id = categoryOptions.options[categoryOptions.selectedIndex].value;
        var name = categoryOptions.options[categoryOptions.selectedIndex].text;
        console.log("De geselecteerde categorie is: "+id);
        return {id: id, name: name};
    };

    log.fetchLogData = function () {
        
        $.ajax({
            method: "GET",
            url: "comparedata",
            data: {
                category: categorySelection.id,
                startDate: datePickerBegin.data("DateTimePicker").date().toDate(),
                endDate: datePickerEnd.data("DateTimePicker").date().toDate(),
            },
            success: function (routeData) {
                data = routeData;
                toggled = false;
                routeChart.showDefaultTitle();
                log.showRoutesOnChart(data.route1TravelTime, data.route2TravelTime);
            }
        });
    };

}(window.verkeer.compare = window.verkeer.compare || {}, verkeer.routeChart, jQuery));