/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function() {
    $("#cena").blur(function() {
        var text = $(this).val();
        var reg = /^\d+\.?\d*$/;
        //alert(reg.test(text));
        
        if(reg.test(text)===false)
            {
                $("#komunikat1").remove();
                $("#cena").before("<p id=\"komunikat1\" class=\"bg-danger\">Pole jest niepoprawne. Podaj same liczby z kropką.</p>");
                $("#editbutton").attr("disabled", 'disabled');
            }
            else{
                
                $("#komunikat1").remove();
                $("#editbutton").removeAttr("disabled");
            }
    });
    
    $("#ilosc").blur(function() {
        var text = $(this).val();
        var reg = /^\d+$/;
        //alert(reg.test(text));
        
        if(reg.test(text)===false)
            {
                $("#komunikat2").remove();
                $("#ilosc").before("<p id=\"komunikat2\" class=\"bg-danger\">Pole musi zawierać same cyfry</p>");
                $("#editbutton").attr("disabled", 'disabled');
            }
            else{
                $("#komunikat2").remove();
                $("#editbutton").removeAttr("disabled");
            }
    });
    
    $("#callConfirm").click(function() {
        var retVal = confirm("Czy na pewno chcesz usunąć?");
               if( retVal === true ){
                  //document.write ($(this).attr("event")+" "+$(this).attr("param"));
                  window.location.href = "?event="+$(this).attr("event")+"&param="+$(this).attr("param");
                  return true;
               }else{
                   return false;
               }
    });
   
});

