/// <reference path="typings/jquery/jquery.d.ts"/>
/// <reference path="jquery.tablesorter.js"/>
$(document).ready(function() {
   

    $(".sortable").tablesorter({
            theme : 'bootstrap'
    });
    
    formatTimes();
});

function formatTimes() {
    var times = $(".time");
    for (var i = 0; i < times.length; i++) {
        
        var seconds = parseInt($(times[i]).attr("data-time"));
        
        var min = Math.floor(seconds / 60) + "";
        if(min.length == 1) min = "0" + min;
        var sec = (seconds % 60) + "";
        if(sec.length == 1) sec = "0" + sec;
        
        $(times[i]).text(min + "' " + sec + "''");
    }
}