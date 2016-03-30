<?php include_once('header.php') ?>

	<?php include_once('nav_icon.php') ?>
<div id="div1" class="dataDiv size_small_l modal-body">
    <h4 class="modal-header">Zmień dane</h4>
    <div>
      <input type="text" class="form-control displ" id="name" name="name" placeholder="Imię" required value="<?php echo $dane["name"];?>"/><span>*</span>

      <input type="text" class="form-control displ" id="surname" name="surname" placeholder="Nazwisko" required value="<?php echo $dane["surname"];?>"/><span>*</span>

      <input type="email" class="form-control displ" id="email" name="email" placeholder="E-mail" required value="<?php echo $dane["email"];?>"/><span>*</span>

      <input type="tel" pattern="\d{9}" title="Zapisz same cyfry, bez dodatkowych znaków" class="form-control displ" id="telephone" placeholder="Nr. telefonu" name="telephone" required value="<?php echo $dane["telephone"];?>"/><span>*</span>

      <input type="text" class="form-control displ" id="status" placeholder="Firma" name="status" required value="<?php echo $dane["status"];?>"/><span>*</span>

	  <input type="text" class="form-control displ" id="address" placeholder="Adres" name="address"  value="<?php echo $dane["address"]; ?>"/>

	<input type="submit" class="btn btn-success" id="zmienDane" role="button" value="Zmień"/>
    </div>
</div>

<div id="div1" class="dataDiv size_small_r modal-body">
<h4 class="modal-header">Zmień hasło</h4>


    <div>
      <input type="password" class="form-control displ" id="oldpass" placeholder="Aktualne hasło" name="oldpass"  required/><span>*</span>
      <input type="password" class="form-control displ" id="newpass" placeholder="Nowe hasło"  name="newpass" required/><span>*</span>
      <input type="password" class="form-control displ" id="repass" placeholder="Powtórz nowe hasło"  name="repass" required/><span>*</span>
	<input type="submit" class="btn btn-success" id="zmienHaslo" role="button" value="Zmień"/>
    </div>
</div>
	
	
<?php include_once('footer.php') ?>	
