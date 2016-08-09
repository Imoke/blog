function addDynamicClass() {

    $(window).on('scroll', function() {
        var st = $(this).scrollTop();
        var wh = $(document).height();
        var perc = (st*100)/wh ;
				if( perc >= 8) {
				    $("header").removeClass("headSkin") ;
				}if( perc >= 25) {
				    $(".monitorBlock").addClass("showContent") ;
				}
				if( perc >= 10) {
				    console.log(" st :"+st) ;
				    $("header").addClass("headSkin") ;
				}
				if( perc >= 45) {
				    $(".analyzeBlock").addClass("showContent") ;
				}
				if( perc >= 60) {
				    $(".publishBlock").addClass("showContent") ;
				}
		});
}

function GetURLParameter(sParam)
    {
        var sPageURL = window.location.search.substring(1);
        var sURLVariables = sPageURL.split('&');
        for (var i = 0; i < sURLVariables.length; i++)
        {
            var sParameterName = sURLVariables[i].split('=');
            if (sParameterName[0] == sParam)
            {
                return sParameterName[1];
            }
        }
    }




function loadFeaturesScript() {
    $(window).scroll(function (event) {
        var scroll = $(window).scrollTop();
        var headerHeight = $(".headerDark").height() ;
        if( scroll > 64 ) {
            $(".menuFea").addClass("fixedTop") ;
        } else {
            $(".menuFea").removeClass("fixedTop") ;
        }
    });
}

