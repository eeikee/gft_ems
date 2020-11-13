$('#alocacaoFuncionario').on('show.bs.modal',function(event){
	var button = $(event.relatedTarget);
	var funcionario_id = button.data('id');
	var nomeFuncionario = button.data('nome');
	var cargoFuncionario = button.data('cargo');
	var localFuncionario = button.data('local');
	var tecnologiaFuncionario = button.data('tecnologia');
	var matriculaFuncionario = button.data('matricula');
	var inicioWA = button.data('inicio_wa');
	
	var modal = $(this);
	
	modal.find('.table .nomeFuncionario').html("Nome: " + nomeFuncionario);
	modal.find('.table .matriculaFuncionario').html("Matrícula: " + matriculaFuncionario);
	modal.find('.table .cargoFuncionario').html("Cargo: " + cargoFuncionario);
	modal.find('.table .localFuncionario').html("Local: " + localFuncionario);
	modal.find('.table .TecnologiaFuncionario').html("Tecnologias: " + tecnologiaFuncionario);
	 
	 var splitDate = inicioWA.split("-");
	 inicioWA = splitDate[2] + "/" + splitDate[1] + "/" + splitDate[0];
	 
	 
	 document.getElementById("nomeFuncionario").value = nomeFuncionario; 
	 document.getElementById("inicioWA").value = inicioWA;
	 document.getElementById("funcionario_id").value = funcionario_id; 

});	

$('#confirmacaoExclusaoModal').on('show.bs.modal',function(event){
	var button = $(event.relatedTarget);
	var vaga_id = button.data('vaga');
	var vaga_codigo = button.data('codigo');
	var funcionario_id = button.data('funcionario');
	var funcionario_nome = button.data('nome');

	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	
	if (!action.endsWith('/')) {
		action += '/';
	}
	
	var urlHide = funcionario_id === undefined ? action + "vagas/hide/" + vaga_id : action + "funcionarios/hide/" + funcionario_id;
	form.attr('action', urlHide);
	var confirm = funcionario_id === undefined ? ' a vaga <strong>' + vaga_codigo  + '</strong>': ' o funcionario <strong>' + funcionario_nome  + '</strong>'; 
	modal.find('.modal-body span').html('Tem certeza que você deseja excluir'+ confirm);

});	