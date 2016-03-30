<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<style>

</style>

<?php
//echo'<pre>';
//print_r($tasks);
//echo'</pre>';
//echo '<div class="">';

echo' <button id="newRow" type="button" class="btn btn-success fixed">Dodaj kolejny wiersz</button>';
//echo '</div>';
?>
<script>
    var active,unactive;
    var focusId=new Array(null,null);

    function ajaks(id){
        var wor = getUrlParameter('row');
        console.log(id);
        $.ajax({
            type     : "POST",
            url      : "../project/delRows",
            data     : {
                row2 : id,
                row : wor
            },
            success : function(msg) {
            },
            complete : function(r) {
            }
        });
    }

    /*=================POP UP ========================================================*/
    $(".usunwiersz").click(function(){
        if(typeof unactive === 'undefined')
        {alert('Najpierw zaznacz wiersz.');}
        else if(confirm("Jesteś pewny że chcesz usunąć to zadanie, razem z jego dziećmi (o ile istnieją)?")){

            if(($('.'+active).next().get('0')) && ($('.'+active).next().get('0').localName==='ul')){


                var wor = getAllId(active+'_0');
                wor.push(focusId[1]);
console.log('usuwa: '+wor);
                ajaks(wor);

                $('.'+active).remove();
                $('.'+active+'_0').remove();
            }else{
                console.log('usuwa: '+focusId[1]);
                ajaks(focusId[1]);
                $('.'+active).remove();
            }

            aktualizacja('#tasks .0');
        }
        else{
            console.log('NIE zgodzil sie');
            return false;
        }
    });
    /*===============END==POP UP =====================================================*/



    function getAllId($id){
        var tab=[];
        for(var i=0;i<$('.'+$id).find('li').length;i++)
            tab[i] =$('.'+$id).find('li').get(i).getAttribute( "key" );
        return tab;
    }

    function saveAll(id,classes,depend){
        var zmienne= {
            'dependence': depend,
            'title':$('.'+classes).find('input').val(),
            'description':$('.'+classes).find('input').next().val(),
            'data_start':$('.'+classes).find('input').next().next().val(),
            'data_stop':$('.'+classes).find('input').next().next().next().val(),
            'user_id':parseInt($('.'+classes).find('select').find(":selected").attr('value'))
        };
        console.log(zmienne);



            var wor = getUrlParameter('row');

            $.ajax({
                type: "POST",
                url: "../project/updateTask",
                data: {
                    row: wor,
                    ajdi: id,
                    dejta: zmienne
                },
                success: function (msg) {
                },
                complete: function (r) {
                }


            });


    }

    $(".zapiszwszystko").click(function(){
        var key=[], id=[], depend=[], len;
        len = $('.0').find('li').length;
        console.log(len);
        for(var i=0;i<len;i++){
            key[i]=$('.0').find('li').get(i).getAttribute( "key" );
            depend[i]=$('.0').find('li').get(i).getAttribute( "depend" );
            id[i]=$('.0').find('li').get(i).getAttribute( "class" );
            console.log('dla key: '+ key[i]+' klasa: '+id[i]+' depend: '+depend[i]);
            saveAll(key[i],id[i],depend[i]);
        }
    });

    $(document).on("click","#tasks li",function(){
        $('.'+active).removeClass("active");

        var onlunumber=$(this).attr( "class" );
        var separator = onlunumber.split(" ");

        if(typeof unactive === 'undefined')
            unactive=separator[0];
        else
            unactive=active;
        active=separator[0];

        if(!focusId[0])
            focusId[0]=$(this).attr( "key" );
        else
            focusId[0]=focusId[1];

        focusId[1]=$(this).attr( "key" );
        //console.log(focusId[0]+'---'+focusId[1]+' |||| '+unactive+'---'+active);
        $(this).addClass("active");

    });

    $(document).on("click",".down",function(){


        var prevKey = $(this).parent().prev().attr('key');  //pobierz keya wczesniejszego
        var actualKey=$(this).parent().attr('key');         //pobierz keya aktualnego

        console.log('key="' + actualKey + '" depend="' + prevKey + '"');
//        var prevdepend = $(this).parent().prev().attr('depend');


        $(this).removeClass( "down" ).addClass( "up" ).html('<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>');
        var newClass=$(this).parent().attr('class');
        var separator = newClass.split(" ");
        var stare = $(this).parent().html();
        if($('.'+separator[0]).next().get('0') && ($('.'+separator[0]).next().get('0').localName==='ul') ) {
            console.log('warunek1');
           // prevKey = $(this).parent().prev().attr('key');  //pobierz keya wczesniejszego
            $('.' + separator[0] + '_0').children().first().before('<li class="' + newClass + '_1" key="' + actualKey + '" depend="' + prevKey + '">' + stare + '</li>');
            $(this).parent().remove();
        }else if($('.'+separator[0]).prev().get('0') && ($('.'+separator[0]).prev().get('0').localName==='ul') ){
            console.log('warunek2');
            prevKey = $(this).parent().prev().children().last().attr('depend');  //pobierz keya wczesniejszego
            rem2 = $('.'+separator[0]).prev().attr('class');
            $('.' + rem2).children().last().after('<li class="' + newClass + '_1" key="' + actualKey + '" depend="' + prevKey + '">' + stare + '</li>');
            $(this).parent().remove();
        }else{
            console.log('warunek3');
            $(this).parent().attr('depend',prevKey);
            $(this).parent().wrapAll('<ul class="'+separator[0]+'_0">');
        }
        console.log('key="' + actualKey + '" depend="' + prevKey + '"');
        saveAll(actualKey,newClass+'_1',prevKey);
        aktualizacja('#tasks .0');

    });

    $(document).on("click",".up",function(){
        $(this).removeClass( "up" ).addClass( "down" ).html('<span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span>');
        var newClass=$(this).parent().parent().attr('class');
        var parent = $(this).parent().attr('class');
        var separator = newClass.split(" ");        //wyznaczenie rodzica w którym ul się znajduje
        var stare = $(this).parent().html();
        var wyzszyDep, actualKey;
        console.log('klasa: '+newClass+' -> '+parent);
        console.log('Poprzednik: '+$('.'+parent).prev().attr('class'));
        console.log('Następnik: '+$('.'+parent).next().attr('class'));


        if((!$('.'+parent).prev().attr('class'))&&(!$('.'+parent).next().attr('class'))){

            console.log('Jestem jedyny');
            actualKey=$(this).parent().attr('key');
            wyzszyDep = $('.'+newClass).prev().attr('depend');
            $('.'+newClass).after('<li class="active" key="' + actualKey + '" depend="' + wyzszyDep + '">' + stare + '</li>');
            $(this).parent().parent().remove();
        }
        else if((!$('.'+parent).prev().attr('class'))&&($('.'+parent).next().attr('class'))){
            console.log('Jestem pierwszy');
            actualKey=$(this).parent().attr('key');
            wyzszyDep = $('.'+newClass).prev().attr('depend');
            $('.'+newClass).before('<li class="active" key="' + actualKey + '" depend="' + wyzszyDep + '">' + stare + '</li>');
            $(this).parent().remove();
        }
        else if(($('.'+parent).prev().attr('class'))&&(!$('.'+parent).next().attr('class'))){
            console.log('Nie ma nic pod spodem');
            actualKey=$(this).parent().attr('key');
            wyzszyDep = $('.'+newClass).prev().attr('depend');
            $('.'+newClass).after('<li class="active" key="' + actualKey + '" depend="' + wyzszyDep + '">' + stare + '</li>');
            $(this).parent().remove();
        }
        else {
            console.log('Jestem w środku');
            actualKey=$(this).parent().attr('key');
            wyzszyDep = $('.'+newClass).prev().attr('depend');
            //$('.'+parent).before('');
            $('.'+newClass).after('<li class="active" key="' + actualKey + '" depend="' + wyzszyDep + '">' + stare + '</li>');
            $(this).parent().remove();
        }




        aktualizacja('#tasks .0');

    });

    function aktualizacja(klasa) {
        //console.log(klasa);
        var i = 1;
         (klasa==='#tasks .0')?(iklasa=klasa):(iklasa='.'+klasa);
        $(iklasa).children().each(function(){

            if($(this).get(0).localName!=='ul') {   //
                var klasssa = $(this).attr('class');
                //=============================================================
                separator2 = klasssa.split(" ");
//              console.log(klasssa);
                separator = separator2[0].split("_");

                separator.pop();
                separator.push(i);
                //separator.join('_');

               // separator = separator + ' ' + separator2[1];
                //=============================================================

//                rem2 = $(this).attr('class');
//                rem3 = rem2.split(" ");
//                rem = rem3[0];

                //console.log($(this).localName);
                $(this).removeClass(klasssa).addClass(separator.join('_')+' '+separator2[1]).children('span').html(separator.join('.'));  //usuwa tą klasę i dodaje nową ORAZ do wartości span dodaje wartość (tą z klasy)

                i++;
            }else{                                  //..a jeśli napotka to...
                rem = $(this).attr('class');
                rem2 = $(this).prev().attr('class');            //pobiera klasę z ul
                //console.log('prev id: '+rem);

                $(this).removeClass(rem).addClass(rem2+'_0');       //usuwa tą klasę i dodaje nową z zero na koncu
                 aktualizacja(rem2+'_0');        //no i wejdź do tego ul i zaktualizuj te li któe się w nim znajdują
            }

        });

    }




    function getUrlParameter(sParam) {
        var sPageURL = decodeURIComponent(window.location.search.substring(1)),
            sURLVariables = sPageURL.split('&'),
            sParameterName,
            i;

        for (i = 0; i < sURLVariables.length; i++) {
            sParameterName = sURLVariables[i].split('=');

            if (sParameterName[0] === sParam) {
                return sParameterName[1] === undefined ? true : sParameterName[1];
            }
        }
    };

    $(document).on("click","#newRow",function(){
//        var rodzic =  $('.'+active ).parent().attr('class');
        var rodzic =  $('.'+active ).parent().prev().attr('key');
        var mojKey =  $('.'+active ).parent().attr('key');
            (rodzic>0)?rodzic:rodzic=0;
        console.log('Rodzic:'+rodzic);
        var classlast = $('.'+active ).attr('class');
        var separator = classlast.split(" ");
        var wor = getUrlParameter('row');
        separator = separator[0].split("_");
        separator[(separator.length)-1]++;
        var i= 1, idMax=0;
        var separator2='', uzytkow='';
        $.ajax({
            type     : "POST",
            url      : "../project/showMe",
            data     : {
                row : wor,
                mojKey: mojKey,
                rodzic: rodzic
            },
            success : function(msg) {
                if (msg!==false){
                    separator2 = msg;
                }
            },
            complete : function(r) {

                console.log(separator2);
                separator2 = separator2.split("|||||");
                uzytkow = separator2[0];
                idMax = separator2[1];
                console.log(idMax);
                if((typeof $('.'+active).next().get('0') !== 'undefined') && ($('.'+active).next().get('0').localName==='ul') ) {
                    //jeśli następne jest ul to robi nowe li po tym ul
                    $('.'+active).next('ul').after('<li class="'+separator.join('_')+'" key="'+idMax+'" depend="'+rodzic+'"> <span>'+separator.join('.')+'. </span>  <input type="text" name="2." class="form-control" placeholder="Tytuł zadania"><input name="2." class="form-control" placeholder="Opis" ><input name="2." type="date" class="form-control" placeholder="Data rozpoczęcia"><input name="2." type="date" class="form-control" placeholder="Data zakończenia"><select name="2." class="form-control" name="nazwa">'+uzytkow+'</select><a href="#" class="up"><span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span></a></li>');
                }else{
                    //robi nowe li po aktywnym li
                    $('.'+active).after('<li class="'+separator.join('_')+'" key="'+idMax+'" depend="'+rodzic+'"> <span>'+separator.join('.')+'. </span>            <input type="text" name="2." class="form-control" placeholder="Tytuł zadania"><input name="2." class="form-control" placeholder="Opis" ><input name="2." type="date" class="form-control" placeholder="Data rozpoczęcia"><input name="2." type="date" class="form-control" placeholder="Data zakończenia"><select name="2." class="form-control" name="nazwa">'+uzytkow+'</select><a href="# "class="down"><span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span></a></li>');
                }
                aktualizacja('#tasks .0');
            }


        });




    });

    $(document).on("blur","#tasks",function(){
        var wor = getUrlParameter('row');
       var zmienne= {
            'title':$('.'+unactive).find('input').val(),
            'description':$('.'+unactive).find('input').next().val(),
            'data_start':$('.'+unactive).find('input').next().next().val(),
            'data_stop':$('.'+unactive).find('input').next().next().next().val(),
            'user_id':parseInt($('.'+unactive).find('select').find(":selected").attr('value'))
        };
        console.log(zmienne);
        var data_start = new Date($('.'+unactive).find('input').next().next().val());
        var data_end   = new Date($('.'+unactive).find('input').next().next().next().val());

        console.log(data_start.getTime()+' -> '+data_start);
        console.log(data_end.getTime()+' -> '+data_end);

        if ((data_start.getTime()>0) && (data_end.getTime()>0)){

        console.log('weszlo1');
            if (data_start <= data_end) {

                    $.ajax({
                        type     : "POST",
                        url      : "../project/updateTask",
                        data     : {
                            row : wor,
                            ajdi: focusId[0],
                            dejta: zmienne
                        },
                        success : function(msg) {
                        },
                        complete : function(r) {
                        }


                    });
            }else {
                $('ul#tasks').before('<div class="alert alert-danger displ2 msgg2" role="alert"><strong>Nieprawidłowo podano daty.</strong></div>');
                confirm("Daty zostały nieprawidłowo podane");
                $('.'+unactive).find('input').next().next().val('');
                $('.'+unactive).find('input').next().next().next().val('');
                console.log('weszlo2');
                return false;
            }
        }else console.log('nie wykonalo warunku');

    });

    $(document).on("click",".glyphicon-thumbs-up",function(){
        var rodzic =  $(this).parent().parent().attr('key');
        //console.log(rodzic);
        var obok = $(this).parent().next().css('display','none');
        $(this).parent().parent().removeClass('redBg').addClass('greenBg');
        var wor = getUrlParameter('row');


        $.ajax({
            type     : "POST",
            url      : "../project/updateCheck",
            data     : {
                'row':wor,
                'task':rodzic,
                'wart':1
            },
            success : function(msg) {
            },
            complete : function(r) {
            }
        });


    });

    $(document).on("click",".glyphicon-thumbs-down",function(){
        var rodzic =  $(this).parent().parent().attr('key');
        //console.log(rodzic);
        var wor = getUrlParameter('row');
//        var obok = $(this).parent().prev().css('display','none');
        $(this).parent().parent().addClass('redBg');

        $.ajax({
            type     : "POST",
            url      : "../project/updateCheck",
            data     : {
                'row':wor,
                'task':rodzic,
                'wart':-1
            },
            success : function(msg) {
            },
            complete : function(r) {
            }
        });


    });


</script>