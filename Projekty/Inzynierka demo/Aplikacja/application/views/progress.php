<!DOCTYPE HTML>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Ajax</title>
<script>
function getXMLHttpRequestObject()
{
  try{
    return new XMLHttpRequest();
  }
  catch(e){
    try{
      return new ActiveXObject("Microsoft.XMLHTTP");
    }
    catch(e){
      return false;
    }
  }
}

function startGETRequest(url, onComplete, onEnd)
{
  var XMLHttpRequestObject = getXMLHttpRequestObject();
  if(XMLHttpRequestObject){
    XMLHttpRequestObject.open("GET", url);
    XMLHttpRequestObject.onreadystatechange = function()
    {
      if (XMLHttpRequestObject.readyState == 4){
        if(XMLHttpRequestObject.status == 200){
          var responseXML = XMLHttpRequestObject.responseXML;
          var responseText = XMLHttpRequestObject.responseText;
          onComplete(responseText, responseXML);
        }
        delete XMLHttpRequestObject;
        onEnd();
      }
    }
    XMLHttpRequestObject.send(null);
  }
}

function startPOSTRequest(url, params, onComplete, onEnd)
{
  var XMLHttpRequestObject = getXMLHttpRequestObject();
  if(XMLHttpRequestObject){
    XMLHttpRequestObject.open("POST", url);
    XMLHttpRequestObject.setRequestHeader(
      'Content-Type', 'application/x-www-form-urlencoded');
    XMLHttpRequestObject.onreadystatechange = function()
    {
      if (XMLHttpRequestObject.readyState == 4){
        if(XMLHttpRequestObject.status == 200){
          var responseXML = XMLHttpRequestObject.responseXML;
          var responseText = XMLHttpRequestObject.responseText;
          onComplete(responseText, responseXML);
        }
        delete XMLHttpRequestObject;
        onEnd();
      }
    }
    XMLHttpRequestObject.send(params);
  }
}
//========================================================================

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


</script>
<style>
.mainDiv
{
  color: #333333;
  position: relative;
  background-color: #EFEFEF;
  border: 1px solid #000000;
  margin: 10px 10px 10px 10px;
  padding: 14px 14px 14px 14px;
  width: 420px;
  visibility: visible;
}

.dataDiv
{
  color: #333333;
  position: relative;
  background-color: #ffffff;
  border: 1px solid #000000;
  margin: 10px 10px 10px 10px;
  padding: 14px 14px 14px 14px;
  width: 420px;
  height:300px;
  visibility: visible;
}

.myButton
{
  font-family:helvetica,sans-serif;
  font-size:84%;
  font-weight:bold;
  border:1px solid;
  border-top-color:#696;
  border-left-color:#696;
  border-right-color:#363;
  border-bottom-color:#363;
  width:120px;
  margin:5px;
}
</style>

</head>
<body>
<div id="selectorsDiv" class="mainDiv">
<input type="button" class="myButton" value="Pierwszy artykuł"
       onclick="loadArticle(1)" id="btn1">
<input type="button" class="myButton" value="Drugi artykuł"
       onclick="loadArticle(2)" id="btn2">
<input type="button" class="myButton" value="Trzeci artykuł"
       onclick="loadArticle(3)" id="btn3">
</div>
<div id="dataDiv" class="dataDiv">
</div>

</body>
</html>
