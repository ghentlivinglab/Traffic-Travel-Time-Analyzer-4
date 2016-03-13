/// <reference path="typings/jquery/jquery.d.ts"/>
// kolom filteren
$(document).ready(function () {
    $(document).on("click", ".chkProviderFilter", function (ev) {
        var provider = $(this).attr("data-provider");
        if ($(this).prop("checked")) {
            $(".provider-cell[data-provider='" + provider + "']").show();
            $(".provider-header[data-provider='" + provider + "']").show();
        }
        else {
            $(".provider-cell[data-provider='" + provider + "']").hide();
            $(".provider-header[data-provider='" + provider + "']").hide();
        }
    });
    $(".delay-column").each(function (i, el) {
        var delay = parseInt($(el).find(".time").attr("data-time"));
        var color = verkeer.getDelayGradient(delay);
        $(el).find(".cell-background").css({
            "backgroundColor": color,
            opacity: 0.6
        });
    });
});
