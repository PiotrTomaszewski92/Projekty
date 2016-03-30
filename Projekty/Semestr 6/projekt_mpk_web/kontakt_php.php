<?php
 if(isset($_POST['submit']))
 {
    $name = $_POST['name'];
	$email = $_POST['email'];
	$message = $_POST['message'];
	$email_from = $name.'<'.$email.'>';
	if(($name=="")||($email=="")||($message==""))echo '<p class="alert">Uzupełnij wszystkie pola. </p>';

 $to="piotrekha15@gmail.com";
 $subject="Formularz kontaktowy";
 $headers  = 'MIME-Version: 1.0' . "\r\n";
 $headers .= 'Content-type: text/html; charset=utf-8' . "\r\n";
 $headers .= "From: ".$email_from."\r\n";
 $message="	  
 	   
 		 Name:
		 $name 	   
         
 		 Email-Id:
		 $email 	   
         
 		 Message:
		 $message 	   
      
   ";
	if(mail($to,$subject,$message,$headers))
		header("Location:kontakt.php?msg=Wiadomość wysłana!");
	else
		header("Location:kontakt.php?msg=Błąd przy wysyłaniu wiadomości!");
		//contact:-your-email@your-domain.com
 }
?>
