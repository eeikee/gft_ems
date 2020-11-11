$('#alocacaoFuncionario').on('show.bs.modal',function(event){
	var button = $(event.relatedTarget);
	var funcionario_id = button.data('id');
	var nomeFuncionario = button.data('nome');
	var cargoFuncionario = button.data('cargo');
	var matriculaFuncionario = button.data('matricula');
	var inicioWA = button.data('inicio_wa');
	
	
	var modal = $(this);
	
	modal.find('.table .nomeFuncionario').html(nomeFuncionario);
	modal.find('.table .matriculaFuncionario').html(matriculaFuncionario);
	modal.find('.table .cargoFuncionario').html(cargoFuncionario);
	 
	 var splitDate = inicioWA.split("-");
	 inicioWA = splitDate[2] + "/" + splitDate[1] + "/" + splitDate[0];
	 
	 
	 document.getElementById("nomeFuncionario").value = nomeFuncionario; 
	 document.getElementById("inicioWA").value = inicioWA;
	 document.getElementById("funcionario_id").value = funcionario_id; 

});	