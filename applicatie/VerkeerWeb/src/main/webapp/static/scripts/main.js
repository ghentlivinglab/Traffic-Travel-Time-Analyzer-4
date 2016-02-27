/// <reference path="typings/jquery/jquery.d.ts"/>
/// <reference path="jquery.tablesorter.js"/>
$(document).ready(function() {

    $(".sortable").tablesorter({
            theme : 'bootstrap'
    });

    formatTimes();
    humanizeSeconds();
});

function formatTimes() {
    var times = $(".time");
    for (var i = 0; i < times.length; i++) {
        var seconds = parseInt($(times[i]).attr("data-time"));
        var text = secondsToText(seconds);
        $(times[i]).text(text);
    }
}

function secondsToText(seconds){
    var min = Math.floor(seconds / 60) + "";
    if(min.length == 1) min = "0" + min;
    var sec = (seconds % 60) + "";
    if(sec.length == 1) sec = "0" + sec;
    return min + "' " + sec + "''";
}

/*
 Searches for tags with the "humanize" class and converts seconds into readable time
 */
function humanizeSeconds() {
    var tag = $(".humanize")
    for (var i = 0; i < tag.length; i++) {
        var milliSec = parseInt($(tag[i]).attr("data-time")) * 100; //works with millisec only
        var humanTime = humanizeDuration(milliSec, {language: 'nl'});
        $(tag[i]).text(humanTime);
    }
}

// Source: https://stackoverflow.com/questions/19491336/get-url-parameter-jquery-or-how-to-get-query-string-values-in-js
var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};