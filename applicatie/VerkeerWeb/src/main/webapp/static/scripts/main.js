/// <reference path="typings/jquery/jquery.d.ts"/>
/// <reference path="jquery.tablesorter.js"/>

(function (verkeer, $) {

    verkeer.MAIN_ROOT = "";

    "use strict";
    $(document).ready(function () {

        $(".sortable").tablesorter({
            theme: 'bootstrap'
        });

        $(".nodefault").click(function (e) {
            e.preventDefault();
            return false;
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
            var humanTime = humanizeDuration(milliSec);
            $(tag[i]).text(humanTime);
        }
    };


    verkeer.delayClasses = [60, 420];
    /*
     Pass the delay in seconds and it returns a level that represents the delay:
     0: no delay (e.g. green)
     1: medium delay (e.g. orange)
     2: large delay (e.g. red)
     */
    verkeer.getDelayLevel = function (delay) {
        var level;

        if (delay <= verkeer.delayClasses[0]) {
            level = 0;
        } else if (delay <= verkeer.delayClasses[1]) { // 7 minutes
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

    verkeer.getDelayGradient = function (delay) {
        var r1 = 92;
        var g1 = 184;
        var b1 = 92;
        
        var r2 = 240;
        var g2 = 173;
        var b2 = 74;
        
        var r3 = 217;
        var g3 = 83;
        var b3 = 79;
        
        if (delay <= verkeer.delayClasses[0]) {
            var alpha = (delay - 0) / (verkeer.delayClasses[0] - 0);
            var r = Math.floor(r1 * (1-alpha) + r2 * alpha);
            var g = Math.floor(g1 * (1-alpha) + g2 * alpha);
            var b = Math.floor(b1 * (1-alpha) + b2 * alpha);
            
            return "rgb(" + r + ", " + g + "," + b + ")";
        } else if (delay <= verkeer.delayClasses[1]) { // 7 minutes
            var alpha = (delay - verkeer.delayClasses[0]) / (verkeer.delayClasses[1] - verkeer.delayClasses[0]);
            var r = Math.floor(r3 * alpha + r2 * (1-alpha));
            var g = Math.floor(g3 * alpha + g2 * (1-alpha));
            var b = Math.floor(b3 * alpha + b2 * (1-alpha));
            
            return "rgb(" + r + ", " + g + "," + b + ")";
        } else {
            return "rgb(" + r3 + ", " + g3 + "," + b3 + ")";
        }
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
