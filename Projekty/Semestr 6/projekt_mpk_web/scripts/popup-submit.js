$(document).ready(function() {
	$('input').magnificPopup({
		type: 'inline',
		items: {src: '#modal'},
		preloader: false,
		modal: true
	});
	$(document).on('click', '.popup-modal-dismiss', function (e) {
		e.preventDefault();
		$.magnificPopup.close();
        $('form')[0].reset();
	});
});