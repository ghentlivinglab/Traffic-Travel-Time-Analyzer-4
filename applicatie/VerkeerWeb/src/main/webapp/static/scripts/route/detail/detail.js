$(function () {
    "use strict";
    $(document).ready(function () {
        $('#datetimepicker1').datetimepicker({
            format: 'DD/MM/YYYY HH:mm',
            showTodayButton: true,
            sideBySide: true,
            defaultDate: moment().startOf('day')
        });
        $('#datetimepicker2').datetimepicker(
            {
                format: 'DD/MM/YYYY HH:mm',
                showTodayButton: true,
                sideBySide: true,
                defaultDate: moment().endOf('day')
            }
        );
    });
});