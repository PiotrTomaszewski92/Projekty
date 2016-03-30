window.onload = function(){ 
	var submitbutton = document.getElementById("tfq");
	if(submitbutton.addEventListener){
		submitbutton.addEventListener("click", function() {
			if (submitbutton.value == 'Wyszukaj numer linii'){
				submitbutton.value = '';
			}
		});
	}
}