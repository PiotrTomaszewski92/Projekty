
<!DOCTYPE HTML>
<html>
<head>
    <title>Zintegrowany system zarządzania projektem informatycznym</title>
    <link rel="stylesheet" href="<?php echo base_url('css/bootstrap.css');?>" >
    <link rel="stylesheet" href="<?php echo base_url('css/style.css');?>" >
    <link href='http://fonts.googleapis.com/css?family=Anton&subset=latin,latin-ext' rel='stylesheet' type='text/css'>
    <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <script>
        $(document).on("click","#sendReg",function(){
            if (($('#rejestr1').val())&&($('#rejestr2').val())&&($('#rejestr3').val())&&($('#rejestr4').val())&&($('#rejestr5').val())&&($('#rejestr6').val())) {
                $.ajax({
                    type: "POST",
                    url: "login/registration",
                    data: {
                        username: $('#rejestr4').val(),
                        password: $('#rejestr5').val(),
                        status: $('#rejestr6').val(),
                        name: $('#rejestr1').val(),
                        surname: $('#rejestr2').val(),
                        email: $('#rejestr4').val(),
                        telephone: $('#rejestr3').val(),
                        address: $('#rejestr7').val()

                    },
                    success: function (msg) {
                        console.log(msg);


                    }, complete: function (r) {
                        console.log(r);
                    }

                });
            }
        });

        $(document).on("keyup","#rejestr4",function(){
            $.ajax({
                type: "POST",
                url: "login/ismail",
                data: {
                    email: $('#rejestr4').val()

                },
                success: function (msg) {
                    console.log(msg);
                        if (msg==1){
                            $('#rejestr1').before( '<div class="alert alert-danger displ2" role="alert"><strong>Adres e-mail istnieje w bazie</strong></div>' );
                            $('#sendReg').attr("disabled", true);
                        } else{
                            $('.displ2').css("display","none");
                            $('#sendReg').attr("disabled", false);
                        }


                }, complete: function (r) {
                    //console.log(r);
                }

            });
        });

        $(document).on("click",".remind",function(){
            console.log($('#exampleInputEmail3').val());
            $('.alert-danger').html('Adres e-mail jest niezgodny.<br><a href="#" class="alert-link remind">Przypomnij hasło</a>');

                $.ajax({
                    type: "POST",
                    url: "login/ismail",
                    data: {
                        email: $('#exampleInputEmail3').val()

                    },
                    success: function (msg) {
                        if (msg==1){
                            $.ajax({
                                type: "POST",
                                url: "login/remind",
                                data: {
                                    email: $('#exampleInputEmail3').val()

                                },
                                success: function (msg) {
                                    if (msg==1){
                                        $('.alert-danger').addClass('alert-success').removeClass('alert-danger').html('Nowe hasło wysłano na podany adres e-mail.');
                                    } else{
                                        $('.alert-danger').html('Problemy z przypomnieniem hasła.');
                                    }
                                }, complete: function (r) {

                                },error: function(err) {

                                }


                            });
                        } else{
                            $('.alert-danger').html('Adres e-mail jest niezgodny.<br><a href="#" class="alert-link remind">Przypomnij hasło</a>');
                        }
                    }, complete: function (r) {

                    },error: function(err) {

                    }


                });

//            if ($('#exampleInputEmail3').val()){
//
//
//            }else{
//
//            }

//            $.ajax({
//                type: "POST",
//                url: "login/ismail",
//                data: {
//                    email: $('#rejestr4').val()
//
//                },
//                success: function (msg) {
//                    console.log(msg);
//                    if (msg==1){
//                        $('#rejestr1').before( '<div class="alert alert-danger displ2" role="alert"><strong>Adres e-mail istnieje w bazie</strong></div>' );
//                        $('#sendReg').attr("disabled", true);
//                    } else{
//                        $('.displ2').css("display","none");
//                        $('#sendReg').attr("disabled", false);
//                    }
//
//
//                }, complete: function (r) {
//                    //console.log(r);
//                }
//
//            });
        });

        $(document).on("keyup","#captcha",function(){
            if(($('#captcha').val()==6)||($('#captcha').val()=='')){
                $('.displ3').css("display","none");
                $('#sendReg').attr("disabled", false);
            }else{
                console.log($('#captcha').val());
                $('#rejestr1').before( '<div class="alert alert-danger displ3" role="alert"><strong>Zły wynik działania</strong></div>' );
                $('#sendReg').attr("disabled", true);
            }
        });


        $( document ).ready(function() {
            if(($('.log .infoWstep+p').text()!=='')&&($('.log .infoWstep+p+p').text()!=='')){
                $('.log .infoWstep+p').css("display","block");
                $('.log .infoWstep+p+p').css("display","block");
            }  else if(($('.log .infoWstep+p').text()!=='')&&($('.log .infoWstep+p+p').text()==='')) {
                $('.log .infoWstep+p').css("display","block");
                $('.log .infoWstep+p+p').css("display","none");
            } else if(($('.log .infoWstep+p').text()==='')&&($('.log .infoWstep+p+p').text()!=='')) {
                $('.log .infoWstep+p').css("display","none");
                $('.log .infoWstep+p+p').css("display","block");
            }else{
                $('.log .infoWstep+p').css("display","none");
                $('.log .infoWstep+p+p').css("display","none");
            }
        });

    </script>



</head>

<body>

    <header class="row" id="header">
        <div class="szer70">
            <div class="span12 hjeden">
                <div class="well">
                    <h1>Zintegrowany system zarządzania projektem informatycznym</h1>
                </div>
            </div>
            <div class="log form-group">
                <p class="infoWstep">Zarządzaj w szybszy sposób, swoim projektem, z każdego miejsca na ziemi. Jednym kliknięciem możesz zobaczyć, zaprojektować lub koordynować jednocześnie wiele projektów.</p>

                <?php if (validation_errors()!=null) echo validation_errors(); else echo ''; ?>
                <?php echo form_open('verifylogin'); ?>
                <input type="email" class="form-control" id="exampleInputEmail3" placeholder="E-mail" name="username"/>
                <br/>
                <input type="password" class="form-control" id="exampleInputPassword3" placeholder="Hasło" name="password"/>
                <br/>
                <input type="submit" class="btn btn-success" id="loguj"  required value="Zaloguj"/>
                </form>
            </div>
            <button type="button" class="btn btn-info rejestr" data-toggle="modal" data-target="#myModal">Albo zarejestruj się - ZA DARMO!</button>


            <!--================================================================================ Button trigger modal -->


            <!-- Modal -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" >
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">Rejestracja</h4>
                        </div>
                        <div class="modal-body">
                            <form method="POST" accept-charset="utf-8" >

                                <input type="name" class="form-control displ" id="rejestr1" placeholder="Imię" name="name" required/><span>*</span>
                                <input type="surname" class="form-control displ" id="rejestr2" placeholder="Nazwisko" name="surname" required/><span>*</span>
                                <input type="tel" class="form-control displ" id="rejestr3" pattern="\d{9}" title="Zapisz same cyfry, bez dodatkowych znaków" placeholder="Nr. telefonu" name="telephone" required /><span>*</span>

                                <input type="email" class="form-control displ" autocomplete="off" id="rejestr4" placeholder="Adres e-mail" name="email" required/><span>*</span>
                                <input type="password" class="form-control displ" autocomplete="off" id="rejestr5" placeholder="Hasło" name="password" required/><span>*</span>

                                <input type="status" class="form-control displ" id="rejestr6" placeholder="Firma" name="status" required/><span>*</span>
                                <input type="address" class="form-control displ" id="rejestr7" placeholder="Adres firmy" name="address" />

                                <div><p>2+2*2 =</p> <input type="captcha" class="form-control displ" id="captcha" placeholder="Podaj wynik" name="captcha" required /></div>

                                <input type="close" class="btn btn-default" style="width:120px;" data-dismiss="modal" role="button" value="Zamknij"/>
                                <input type="submit" id="sendReg" class="btn btn-primary" style="width:120px; margin-right: 17px; float:right;" role="button" value="Wyślij"/>

                            </form>

                        </div>
                    </div>
                </div>
            </div>


            <!-- ================================================================================================ -->
        </div>
    </header>



</body>
</html
	
