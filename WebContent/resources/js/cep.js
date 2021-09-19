function limpa_formulario_cep() { 
	
	// Fonte: https://viacep.com.br/exemplo/javascript/
	// No caso do JSF, deve-se dar um id ao formulário
	// e adicioná-lo antes dos atributos aqui
	
	//Limpa valores do formulário de cep 
	document.getElementById('form:endereco').value=(""); 
	document.getElementById('form:bairro').value=(""); 
	document.getElementById('form:cidade').value=(""); 
	document.getElementById('form:uf').value=(""); 
}

function meu_callback(conteudo) {
	if (!("erro" in conteudo)) {
		//Atualiza os campos com os valores 
		document.getElementById('form:endereco').value=(conteudo.logradouro); 
		document.getElementById('form:bairro').value=(conteudo.bairro); 
		document.getElementById('form:cidade').value=(conteudo.localidade);
		document.getElementById('form:uf').value=(conteudo.uf); 
	} else {
		//CEP não Encontrado 
		limpa_formulario_cep(); 
		alert("CEP não encontrado.");
	}
}

function pesquisacep(valor) {
	//Nova variável "cep" somente com dígitos 
	var cep = valor.replace(/\D/g, ''); 
	
	//Verifica se campo cep possui valor informado
	if (cep != "") {
		//Expressão regular para validar o CEP 
		var validacep = /^[0-9]{8}$/; 
		//Valida o formato do CEP
		
		if(validacep.test(cep)) {
			//Preenche os campos com "..." enquanto consulta webservice 
			document.getElementById('form:endereco').value="..."; 
			document.getElementById('form:bairro').value="..."; 
			document.getElementById('form:cidade').value="..."; 
			document.getElementById('form:uf').value="...";
			
			//Cria um elemento javascript 
			var script = document.createElement('script'); 
			
			//Sincroniza com o callback 
			script.src = 'https://viacep.com.br/ws/'+ cep + '/json/?callback=meu_callback'; 
			
			//Insere script no documento e carrega o conteúdo 
			document.body.appendChild(script);
		} else {
			//cep é inválido 
			limpa_formulario_cep(); 
			alert("Formato de CEP inválido.");
		}
	} else {
		//cep sem valor, limpa formulário 
		limpa_formulario_cep();
	}
}