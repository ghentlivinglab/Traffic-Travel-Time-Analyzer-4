/// <reference path="typings/jquery/jquery.d.ts"/>
/// <reference path="jquery.tablesorter.js"/>

(function (verkeer, $) {

    verkeer.MAIN_ROOT = "/VerkeerWeb";

    "use strict";
    $(document).ready(function () {
        
        $(".sortable").tablesorter({
            theme: 'bootstrap'
        });

        verkeer.labelDelays();
        verkeer.formatTimes();
        verkeer.humanizeSeconds();
    });

    verkeer.formatTimes = function () {
        var times = $(".time");
        for (var i = 0; i < times.length; i++) {
            var seconds = parseInt($(times[i]).attr("data-time"));
            var text = verkeer.secondsToText(seconds);
            $(times[i]).text(text);
        }
    };

    verkeer.secondsToText = function (seconds) {
        var min = Math.floor(seconds / 60) + "";
        if (min.length == 1)
            min = "0" + min;
        var sec = (seconds % 60) + "";
        if (sec.length == 1)
            sec = "0" + sec;
        return min + "' " + sec + "''";
    };

    /*
     Searches for tags with the "humanize" class and converts seconds into readable time
     */
    verkeer.humanizeSeconds = function () {
        var tag = $(".humanize");
        for (var i = 0; i < tag.length; i++) {
            var milliSec = parseInt($(tag[i]).attr("data-time")) * 1000; //works with millisec only
            var humanTime = humanizeDuration(milliSec, {language: 'nl'});
            $(tag[i]).text(humanTime);
        }
    };

    /*
     Pass the delay in seconds and it returns a level that represents the delay:
     0: no delay (e.g. green)
     1: medium delay (e.g. orange)
     2: large delay (e.g. red)
     */
    verkeer.getDelayLevel = function (delay) {
        var level;

        if (delay <= 60) {
            level = 0;
        } else if (delay <= 420) { // 7 minutes
            level = 1;
        } else {
            level = 2;
        }

        return level;
    };

    verkeer.labelDelays = function () {
        var times = $(".label-delay");
        for (var i = 0; i < times.length; i++) {
            var seconds = parseInt($(times[i]).attr("data-time"));
            var delayLevel = verkeer.getDelayLevel(seconds);
            $(times[i]).addClass(verkeer.getBootstrapLabelDelay(delayLevel));
        }
    };

    verkeer.getBootstrapLabelDelay = function (delayLevel) {
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
    };

    verkeer.mean = function (data) {
        var sum = 0;
        data.forEach(function (ele) {
            sum += ele;
        });

        return sum / data.length;
    };

    verkeer.variance = function (mean, data) {
        var sum = 0;
        data.forEach(function (ele) {
            sum += Math.pow((ele - mean), 2);
        });
        return sum / data.length;
    };

    verkeer.standardDeviation = function (variance) {
        return Math.sqrt(variance);
    };

    verkeer.withinStd = function (value, mean, stdev, stdevCount) {
        var low = mean - (stdev * stdevCount);
        var high = mean + (stdev * stdevCount);
        return (value > low) && (value < high);
    };
}(window.verkeer = window.verkeer || {}, jQuery));
