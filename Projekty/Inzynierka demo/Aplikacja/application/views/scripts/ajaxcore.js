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