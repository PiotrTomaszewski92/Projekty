<!DOCTYPE HTML>
<html>
	<head>
		<title>Zintegrowany system zarządzania projektem informatycznym</title>
		<link rel="stylesheet" href="<?php echo base_url('css/bootstrap.css');?>" >
		<link rel="stylesheet" href="<?php echo base_url('css/style.css');?>" >
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
		<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

		<script>


			$(document).on("click","#sendAddProj",function(){
				console.log($('#rejestr1').val());
				console.log($('#rejestr2').val());
				var d1 = new Date($('#rejestr2').val()).getTime();
				console.log($('#rejestr3').val());
				var d2 = new Date($('#rejestr3').val()).getTime();

				if (($('#rejestr1').val())&&($('#rejestr2').val())&&($('#rejestr3').val())) {

					if (d1 < d2){
						$.ajax({
							type: "POST",
							url: "addproj/add",
							data: {
								name: $('#rejestr1').val(),
								date_start: $('#rejestr2').val(),
								date_end: $('#rejestr3').val()

							},
							success: function (msg) {
								console.log(msg);
							}, complete: function (r) {
								console.log(r);
							},error: function(err) {
								console.log(err)
							}

						});
					}else{
						$('#rejestr1').before('<div class="alert alert-danger displ2 msgg2" role="alert"><strong>Nieprawidłowo podano daty.</strong></div>');
						return false;
					}

				}
			});

			$(document).on("click","#zmienDane",function(){
				$('.msgg2').css('display','none');
				if (($('#name').val())&&($('#surname').val())&&($('#email').val())&&($('#telephone').val())&&($('#status').val())) {
					$.ajax({
						type: "POST",
						url: "config/update",
						data: {
							status: $('#status').val(),
							name: $('#name').val(),
							surname: $('#surname').val(),
							email: $('#email').val(),
							telephone: $('#telephone').val(),
							address: $('#address').val()

						},
						success: function (msg) {
							console.log(msg);

						}, complete: function (r) {
							$('#name').before( '<div class="alert alert-success displ2 msgg2" role="alert"><strong>Zaktualizowano dane</strong></div>' );
						},error: function(err) {
							console.log(err);
						}


					});
				}
			});

			$(document).on("click","#zmienHaslo",function(){
				$('.msgg').css('display','none');
				if (($('#oldpass').val())&&($('#newpass').val())&&($('#repass').val())) {
					$.ajax({
						type: "POST",
						url: "config/password",
						data: {
							oldpass: $('#oldpass').val(),
							newpass: $('#newpass').val(),
							repass: $('#repass').val()

						},
						success: function (msg) {
							$('#oldpass').before( msg );
						}, complete: function (r) {
							$('#oldpass').before( r );
						},error: function(err) {
							$('#oldpass').before( err );
						}


					});
				}
			});
		

		function loadArticle(id){
                switch(id){
					case 1 : 
						document.getElementById('div1').style.display="block";
						document.getElementById('div2').style.display=document.getElementById('div3').style.display="none";
						document.getElementById('btn1').className = "active";
						document.getElementById('btn2').className = document.getElementById('btn3').className = "";
						break;
					case 2 : 
						document.getElementById('div1').style.display=document.getElementById('div3').style.display="none";
						document.getElementById('div2').style.display="block";
						document.getElementById('btn2').className = "active";
						document.getElementById('btn1').className = document.getElementById('btn3').className = "";
						break;
					case 3 : 
						document.getElementById('div1').style.display=document.getElementById('div2').style.display="none";
						document.getElementById('div3').style.display="block";
						document.getElementById('btn3').className = "active";
						document.getElementById('btn1').className = document.getElementById('btn2').className = "";
						break;
					
				}
				
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




		$(document).on("click",".tab-proj tbody tr",function(){
			var id = $(this).children().first().attr('class');
			var zakladka = $(this).children().first().attr('name');
			if(zakladka==1){
				window.location.replace("work?row=" +id);
			}else if(zakladka==2){
				window.location.replace("see?row=" +id);
			}else
				window.location.replace("project?row=" +id);
		});



			$(document).on("click",".table-tasks tbody tr",function(){
				var id = $(this).children().first().attr('class');
				var wor = getUrlParameter('row');
				$.ajax({
					type     : "POST",
					url      : "work/done",
					data     : {
						idd : id,
						wor: wor
					},
					success : function(msg) {
						$('.alert').css('display','none');
						$('.table-tasks').before( msg );
					}, complete : function(r) {
						$('.table-tasks').before( r );
					}
				});
			});

			$(document).on("click",".table-see tbody tr td a .glyphicon-thumbs-up",function(){
				var id = $(this).parent().parent().parent().children().first().attr('class');
				var wor = getUrlParameter('row');
				console.log(id+' - '+$(this).attr('class'));
				$.ajax({
					type     : "POST",
					url      : "see/good",
					data     : {
						idd : id,
						wor: wor
					},
					success : function(msg) {
						$('.alert').css('display','none');
						$('.table-see').before( msg );
					}, complete : function(r) {
						$('.table-see').before( r );
//						$('.'+id+' a span+span').css('display','none');
						$('.'+id+'>a:first-child').before('<span class="glyphicon glyphicon-ok" style="color:green;" aria-hidden="true"></span>');
						$('.'+id+' a').css('display','none');
//						$('.'+id+'+a+a').css('display','none');

					}
				});
			});



			$(document).on("click","#sendCorrectDescript",function(){
				var id = $(this).attr('key');
				var wor = getUrlParameter('row');
				var text;
				if ($('#correctDescript'+id).val()){
					text = $('#correctDescript'+id).val();
				}else{
					text = 'pusto';
				}
				console.log(id+' - '+wor+' - '+text);
				$.ajax({
					type     : "POST",
					url      : "see/bad",
					data     : {
						zad : id,
						messag: text,
						proj: wor
					},
					success : function(msg) {
						$('.alert').css('display','none');
						$('.table-see').before( msg );
					}, complete : function(r) {
						$('.table-see').before( r );

						$('.'+id+' a span').before('<span class="glyphicon glyphicon-remove" style="color:red; margin-right: 15px;" aria-hidden="true"></span>');
						$('.'+id+' a+a span').css('display','none');
					}
				});
			});





		$(document).on("keyup","#programmer",function(){
			var vartosc = $("#programmer").val();
			$.ajax({
				type     : "POST",
				url      : "project/searchemail",
				data     : {
					mail : vartosc
				},
				success : function(msg) {
					console.log(msg);
					if(($("#programmer").val())&&(msg==='1')){
						$('.form-control:focus').css("border-color","#008000 ").css("box-shadow","inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(0, 205, 0,.6)"); //green
						$('#alertprog').addClass('alert-success').removeClass('alert-danger').show("fast").html('Osoba o takim adresie e-mail <strong>istnieje</strong> u nas. <br> Zaproś ją!');
						$("#programmer ~ .btn").attr('disabled', false);
					}else if (($("#programmer").val())&&(msg==='0')){
						$('.form-control:focus').css("border-color","#ff3333").css("box-shadow","inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(255, 51, 51,.6)"); //red
						$('#alertprog').addClass('alert-danger').removeClass('alert-success').show("fast").html('Osoba o takim adresie e-mail jeszcze u nas <strong>nie istnieje</strong>. <br> Ale to nic! Zaproś ją to współpracy!');
						$("#programmer ~ .btn").attr('disabled', false);
					}else if (!$("#programmer").val()){
						$('.form-control:focus').css("border-color","#66afe9").css("box-shadow","inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6)");  //blue
						$('#alertprog').removeClass('alert-danger').removeClass('alert-success').hide();
						$("#programmer ~ .btn").attr('disabled', true);
					}
				}, complete : function(r) {
					console.log(r);
				}

			});

		});

		$(document).on("keyup","#boss",function(){
			var vartosc = $("#boss").val();

			$.ajax({
				type     : "POST",
				url      : "project/searchemail",
				data     : {
					mail : vartosc
				},
				success : function(msg) {
					if(($("#boss").val())&&(msg==='1')){
						$('.form-control:focus').css("border-color","#008000 ").css("box-shadow","inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(0, 205, 0,.6)"); //green
						$('#alertboss').addClass('alert-success').removeClass('alert-danger').show("fast").html('Osoba o takim adresie e-mail <strong>istnieje</strong> u nas. <br> Zaproś ją!');
						$("#boss ~ .btn").attr('disabled', false);
					}else if (($("#boss").val())&&(msg==='0')){
						$('.form-control:focus').css("border-color","#ff3333").css("box-shadow","inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(255, 51, 51,.6)"); //red
						$('#alertboss').addClass('alert-danger').removeClass('alert-success').show("fast").html('Osoba o takim adresie e-mail jeszcze u nas <strong>nie istnieje</strong>. <br> Ale to nic! Zaproś ją to współpracy!');
						$("#boss ~ .btn").attr('disabled', false);
					}else if (!$("#boss").val()){
						$('.form-control:focus').css("border-color","#66afe9").css("box-shadow","inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6)");  //blue
						$('#alertboss').removeClass('alert-danger').removeClass('alert-success').hide();
						$("#boss ~ .btn").attr('disabled', true);
					}
				}, complete : function(r) {
				}

			});

		});

		
		
		
		
		
	</script>
	
	</head>
	
	<body id="header_grey" onload="loadArticle(3)">
		<div class="szer90">

	


