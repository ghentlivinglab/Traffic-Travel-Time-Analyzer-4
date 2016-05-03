(function (log, $) {

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
            url: "logdata",
            data: {
                category: categorySelection.id,
                startDate: datePickerBegin.data("DateTimePicker").date().toDate(),
                endDate: datePickerEnd.data("DateTimePicker").date().toDate(),
            },
            success: function (logData) {
                log.clearTable();
                log.showLogs(logData);
            }
        });
    };
    
    log.clearTable = function(){
        //tabel wissen
        for(var i = document.getElementById("logtable").rows.length; i > 1;i--)
        {
            document.getElementById("logtable").deleteRow(i -1);
        }
    }
    
    log.showLogs = function(data) {
        //overloop de logs en maak tabelrijen aan.
        // Find a <table> element with id="myTable":
        var table = document.getElementById("logtable");
        
        var array = data.logEntries;
        var length = array.length;
        for (var i = 0; i < length; i++) {
            var row = table.insertRow(-1);
            var typeCel = row.insertCell(-1);
            var dateCel = row.insertCell(-1);
            var timeCel = row.insertCell(-1);
            var messageCel = row.insertCell(-1);

            // Add some text to the new cells:
            typeCel.innerHTML = array[i].type;
            dateCel.innerHTML = array[i].date;
            timeCel.innerHTML = array[i].time;
            messageCel.innerHTML = array[i].message;
        }
    };

}({}, jQuery));