<div class="row">
		<ul class="nav">
			<li><a href="home"><span class="glyphicon glyphicon-home" aria-hidden="true"></span></a></li>
			<li><a href="#" data-toggle="modal" data-target="#kontaktForm"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span></a></li>
			<li><a href="config"><span class="glyphicon glyphicon-cog" aria-hidden="true"></span></a></li>
			<li><a href="home/logout"><span class="glyphicon glyphicon-off" aria-hidden="true"></span></a></li>
		</ul>
</div>

<div class="modal fade" id="kontaktForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="myModalLabel">Kontakt z administratorem</h4>
			</div>
			<div class="modal-body">
				<form action="config/contact" method="POST" accept-charset="utf-8" >

					<input type="text" class="form-control displ" id="titleproblem" placeholder="Temat wiadomości" name="titleproblem" required/><span>*</span>
					<input type="email" class="form-control displ" id="emailproblem" placeholder="Adres e-mail" name="emailproblem" required/><span>*</span>
					<textarea  class="form-control displ" rows="3" id="textproblem" placeholder="Opis problemu" name="textproblem"></textarea><span>*</span>

					<input type="close" class="btn btn-default" style="width:120px; margin-top:10px;" data-dismiss="modal" role="button" value="Zamknij"/>
					<input type="submit" id="sendproblem"  class="btn btn-primary" style="width:120px; margin-top:10px; float:right;" role="button" value="Wyślij"/>

				</form>
			</div>
		</div>
	</div>
</div>
