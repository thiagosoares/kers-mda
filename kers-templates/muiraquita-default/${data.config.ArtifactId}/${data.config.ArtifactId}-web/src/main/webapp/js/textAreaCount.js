function contar_chars(evento, tamMax, render) {
	var campo, valor, tam;
	
	var progresso = document.getElementById(render);
	
	if (document.all) // Internet Explorer
		campo = evento.srcElement;
	else
		// Nestcape, Mozzila
		campo = evento.target;

	valor = campo.value;
	tam = valor.length;
	
	progresso.innerHTML = "(" + tam + " / " + tamMax + ")";
}