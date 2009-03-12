$(document).ready(function() {
  $('.workdetails').hide();

  $('.workdetailsdiv > h3').click(function(){
    $(this).next('.workdetails').toggle('fast');
  });
});
