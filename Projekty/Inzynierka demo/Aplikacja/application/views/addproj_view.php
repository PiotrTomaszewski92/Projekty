<h2>Edit data</h2>
    <?php echo form_open('addproj/add'); ?>
      <label for="title">Nazwa projektu</label>
      <input type="text" size="20" id="title" name="title"/>
      <br/>
      <label for="date_start">Data rozpoczęcia projektu</label>
      <input type="date" size="20" id="date_start" name="date_start"/>
      <br/>
	  <label for="date_end">Data zakończenia projektu</label>
      <input type="date" size="20" id="date_end" name="date_end"/>
      <br/>
	<input type="submit" value="Dodaj projekty"/>
    </form>
	