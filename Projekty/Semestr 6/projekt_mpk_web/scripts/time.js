function change(mins){
		var czas;
			czas=document.getElementById("input-time").value;
			
	var wynik1 = [], napis,minutes ,min_int,hour_int, hours ;
	var j = 1,i;
		  wynik1 = czas.split(":");
		  i=0;
		  min_int=parseInt(wynik1[1]);
		  hour_int=parseInt(wynik1[0]);
		  
	if(mins>0){
		  if(min_int>=50){
				i=1;
			}
			
          minutes = ((parseInt(wynik1[1])+mins )% 60).toString(); 
		  min_int=((parseInt(wynik1[1])+mins ) % 60);
		  
		  
		  
		  hour_int = ((parseInt(wynik1[0])+i ) % 24);
		  hours = ((parseInt(wynik1[0])+i ) % 24).toString();
		if(hours.length === 1){
			hours = "0" + hour_int;
		}
		  
			
	}
	else if(mins<0){
		  
		  if(min_int<10){
				i=-1;
				minutes = (60-(10-(wynik1[1]))).toString(); 
				min_int=(60-(10-(wynik1[1])));
			}
			else{
          minutes = ((parseInt(wynik1[1])+mins )% 60).toString(); 
		  min_int=((parseInt(wynik1[1])+mins ) % 60);
		  }
		  
		  
				hour_int = ((parseInt(wynik1[0])+i ) % 24);
				hours = ((parseInt(wynik1[0])+i ) % 24).toString();
				if(hours.length === 1){
					hours = "0" + hour_int;
				}
				
				
	}
	
	if(min_int==0){
		  minutes="00";
	}
	else if(minutes.length === 1){
        minutes = "0" + minutes;
    }
	
	else if(hour_int<=(-1)){
				hour_int = 23;
				hours = (23).toString();
			}
			
	  var elem = document.getElementById("input-time").value = (hours + ":" + minutes);

    
  }