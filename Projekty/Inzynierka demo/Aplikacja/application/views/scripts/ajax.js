disableButtons = false;

function loadArticle(id)
{
  if(disableButtons) return;
  switch(id){
    case 1 : url = "art1.php";break;
    case 2 : url = "art2.php";break;
    case 3 : url = "art1.php";break;
    default : return;
  }
  disableButtons = true;
  startGETRequest(url, onComplete, onEnd);
}

function onComplete(text, xml)
{
  var dataDiv = document.getElementById('dataDiv');
  dataDiv.innerHTML = text;
}

function onEnd(text, xml)
{
  disableButtons = false;
}