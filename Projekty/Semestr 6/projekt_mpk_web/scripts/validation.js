
 function validation()
 {
    
	var contactname=document.enq.name.value;
	var name_exp=/^[A-Za-z\s]+$/;
	if(contactname=='')
	{
		alert("Pole z imieniem i nazwiskiem nie powinno być puste!");
		document.enq.name.focus();
		return false;
	}
	else if(!contactname.match(name_exp))
	{
		alert("Niepoprawne wprowadzone dane!");
		document.enq.name.focus();
		return false;
	}
	
	var email=document.enq.email.value;
	var email_exp=/^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	if(email=='')
	{
		alert("Pole mailowe nie powinno być puste!");
		document.enq.email.focus();
		return false;
	}
	else if(!email.match(email_exp))
	{
		alert("Niepoprawny format adresu email!");
		document.enq.email.focus();
		return false;
	}
	
	
	var message=document.enq.message.value;
	if(message=='')
	{
		alert("Wpisz jakąś treść!");
		document.enq.message.focus();
		return false;
	}
    return true;
 }
