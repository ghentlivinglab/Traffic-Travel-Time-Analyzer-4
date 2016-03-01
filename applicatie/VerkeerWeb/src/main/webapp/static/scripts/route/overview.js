/// <reference path="typings/jquery/jquery.d.ts"/>
// kolom filteren
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
