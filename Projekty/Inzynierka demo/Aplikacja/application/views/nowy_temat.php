<!DOCTYPE html>
<html>
	<head>
		<title>No cześć</title>
	</head>
	<body>
		<?php echo form_open();?>
		
		<input type="text" name="temat" placeholder="Temat">
		<br>
		<textarea type="text" name="tresc" placeholder="Treść"></textarea>
		<br>
		<input type="text" name="email" placeholder="Email">
		<br>
		<input type="submit" value="Nowy">
		
		
		<?php echo form_close();?>
	</body>
</html>