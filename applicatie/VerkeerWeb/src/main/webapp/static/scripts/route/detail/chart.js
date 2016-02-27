$(function () {
    "use strict";
    $('#container').highcharts({
        title: {
            text: 'Historiek per provider',
            x: -20 //center
        },

        xAxis: {
            title: {
                text: 'Tijd (uur)'
            }
        },
        yAxis: {
            title: {
                text: 'Reistijd (minuten)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: 's'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: 'TomTom',
            data: [100, 190, 65, 145, 182, 215, 252, 265, 233, 183, 139, 96]
        }, {
            name: 'Waze',
            data: [100, 108, 57, 113, 170, 220, 248, 241, 201, 141, 86, 25]
        }, {
            name: 'Google Maps',
            data: [110, 106, 35, 84, 135, 170, 186, 179, 143, 90, 39, 10]
        }, {
            name: 'Coyote',
            data: [120, 106, 35, 84, 135, 170, 186, 179, 143, 90, 39, 100]
        }, {
            name: 'Here',
            data: [103, 142, 57, 85, 119, 152, 170, 166, 142, 103, 66, 48]
        }]
    });
});