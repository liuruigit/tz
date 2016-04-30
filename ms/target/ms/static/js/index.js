// item selection
$('li').click(function () {
  var imgSrc = $(this).find('img').attr('src');
  $(this).toggleClass('selected');
  if ($('li.selected').length == 0){
    temp.remove(imgSrc);
    $('.select').removeClass('selected');
  }else{
    if(temp.indexOf(imgSrc) == -1){
      temp.push(imgSrc);
    }
    $('.select').addClass('selected');
  }

  counter();
});

// all item selection
$('.select').click(function () {
  if ($('li.selected').length == 0) {
    $('li').addClass('selected');
    $('.select').addClass('selected');
  }
  else {
    $('li').removeClass('selected');
    $('.select').removeClass('selected');
  }
  counter();
});

// number of selected items
function counter() {
  if ($('li.selected').length > 0)
    $('.send').addClass('selected');
  else
    $('.send').removeClass('selected');
  $('.send').attr('data-counter',$('li.selected').length);
}


