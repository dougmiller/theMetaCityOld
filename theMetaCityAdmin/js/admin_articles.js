window.setTimeout(function() {
    $.getJSON("/admin/json/getArticleTitleUsed.jsp?",
        function(data){
            data++;
        });
}, 1000);


$(document).ready(function(){




});