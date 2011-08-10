function foco(id) {
	document.getElementById(id).focus();
}

function corFoco(ref) {
	ref.style.backgroundColor = '#ffff66';
	ref.style.borderColor = '#ffcc00';
}

function corSemFoco(ref) {
	ref.style.backgroundColor = '';
	ref.style.borderColor = '#b6ad84';
}

function corFocoDiv(id) {
	document.getElementById(id).style.backgroundColor = '#ffff66';
	document.getElementById(id).style.borderColor = '#ffffff';
}

function corSemFocoDiv(id) {
	document.getElementById(id).style.backgroundColor = '';
	document.getElementById(id).style.borderColor = '#edeadd';
}