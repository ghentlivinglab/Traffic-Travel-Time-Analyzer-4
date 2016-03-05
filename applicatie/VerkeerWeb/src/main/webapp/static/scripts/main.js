/// <reference path="typings/jquery/jquery.d.ts"/>
/// <reference path="jquery.tablesorter.js"/>
$(document).ready(function () {

    $(".sortable").tablesorter({
        theme: 'bootstrap'
    });

    labelDelays();
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

function secondsToText(seconds) {
    var min = Math.floor(seconds / 60) + "";
    if (min.length == 1) min = "0" + min;
    var sec = (seconds % 60) + "";
    if (sec.length == 1) sec = "0" + sec;
    return min + "' " + sec + "''";
}

/*
 Searches for tags with the "humanize" class and converts seconds into readable time
 */
function humanizeSeconds() {
    var tag = $(".humanize");
    for (var i = 0; i < tag.length; i++) {
        var milliSec = parseInt($(tag[i]).attr("data-time")) * 1000; //works with millisec only
        var humanTime = humanizeDuration(milliSec, {language: 'nl'});
        $(tag[i]).text(humanTime);
    }
}

/*
 Pass the delay in seconds and it returns a level that represents the delay:
 0: no delay (e.g. green)
 1: medium delay (e.g. orange)
 2: large delay (e.g. red)
 */
function getDelayLevel(delay) {
    var level;

    if (delay <= 60) {
        level = 0;
    } else if (delay <= 420) { // 7 minutes
        level = 1;
    } else {
        level = 2;
    }

    return level;
}

function labelDelays() {
    var times = $(".label-delay");
    for (var i = 0; i < times.length; i++) {
        var seconds = parseInt($(times[i]).attr("data-time"));
        var delayLevel = getDelayLevel(seconds);
        $(times[i]).addClass(getBootstrapLabelDelay(delayLevel));
    }
}

function getBootstrapLabelDelay(delayLevel) {
    var labelClass;
    switch (delayLevel) {
        case 0:
            labelClass = "label label-success";
            break;
        case 1:
            labelClass = "label label-warning";
            break;
        case 2:
            labelClass = "label label-danger";
            break;
        default:
            labelClass = "label label-success";
            break;
    }
    return labelClass;
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