var Plano = Plano || {}

Plano.EditorTexto = (function(){
	
	function EditorTexto(){
		this.btnSubmit = $('.js-btn-submit');
		this.fieldReferencia = $('input[name=referenciaBibliograficas]');
		this.fieldReferenciaHtml = $('input[name=referenciaBibliograficasHtml]');
		this.fieldObjetivo = $('input[name=objetivosAprendizagem]');
		this.fieldObjetivoHtml = $('input[name=objetivosAprendizagemHtml]');
		this.fieldObjetos = $('input[name=objetosConhecimento]');
		this.fieldObjetosHtml = $('input[name=objetosConhecimentoHtml]');
		this.theme = 'snow';
		this.editorObjetivo = '.objetivo';
		this.editorReferencia = '.referencia';
		this.editorObjeto = '.objeto';
		this.optionsFullTollbar = [
	        [{ 'font': [] }, { 'size': [] }],
	        [ 'bold', 'italic', 'underline', 'strike' ],
	        [{ 'color': [] }, { 'background': [] }],
	        [{ 'script': 'super' }, { 'script': 'sub' }],
	        [{ 'header': '1' }, { 'header': '2' }, 'blockquote', 'code-block' ],
	        [{ 'list': 'ordered' }, { 'list': 'bullet'}, { 'indent': '-1' }, { 'indent': '+1' }],
	        [ {'direction': 'rtl'}, { 'align': [] }],
	        [ 'link', 'image', 'video', 'formula' ],
	        [ 'clean' ]
	      ];
		
		this.optionsTollbar = [
	        [{ 'font': [] }, { 'size': [] }],
	        [ 'bold', 'italic', 'underline', 'strike' ],
	        [{ 'color': [] }, { 'background': [] }],
	        [{ 'script': 'super' }, { 'script': 'sub' }],
	        [{ 'header': '1' }, { 'header': '2' }, 'blockquote'],
	        [{ 'list': 'ordered' }, { 'list': 'bullet'}, { 'indent': '-1' }, { 'indent': '+1' }],
	        [ {'direction': 'rtl'}, { 'align': [] }],
	        [ 'link'],
	        [ 'clean' ]
	      ];
	}
	
	EditorTexto.prototype.enable = function(){
		var fullEditor = new Quill(this.editorObjetivo, {
		    bounds: this.editorObjetivo,
		    modules: {
		      syntax: true,
		      toolbar: this.optionsTollbar,
		    },
		    theme: this.theme
		  });
		
		var fullEditor2 = new Quill(this.editorReferencia, {
		    bounds: this.editorReferencia,
		    modules: {
		      syntax: true,
		      toolbar: this.optionsTollbar,
		    },
		    theme: this.theme
		  });
		
		var fullEditor3 = new Quill(this.editorObjeto, {
		    bounds: this.editorObjeto,
		    modules: {
		      syntax: true,
		      toolbar: this.optionsTollbar,
		    },
		    theme: this.theme
		  });
		
		if(this.fieldObjetivo.val()){
			fullEditor.setContents(JSON.parse(this.fieldObjetivo.val()), 'api');
		}
		
		if(this.fieldReferencia.val()){
			fullEditor2.setContents(JSON.parse(this.fieldReferencia.val()), 'api');
		}
		
		if(this.fieldObjetos.val()){
			fullEditor3.setContents(JSON.parse(this.fieldObjetos.val()), 'api');
		}
		
		this.btnSubmit.on('click', putText.bind(this, fullEditor, fullEditor2, fullEditor3));
	}
	
	function putText(text, text2, text3){
		
		this.fieldObjetivo.val(JSON.stringify(text.getContents()));
		this.fieldReferencia.val(JSON.stringify(text2.getContents()));
		this.fieldObjetos.val(JSON.stringify(text3.getContents()));
		
		this.fieldObjetivoHtml.val(text.container.firstChild.innerHTML);
		this.fieldReferenciaHtml.val(text2.container.firstChild.innerHTML);
		this.fieldObjetosHtml.val(text3.container.firstChild.innerHTML);
		
	}
	
	return EditorTexto;
}());

$(function(){
	
	var editorTexto = new Plano.EditorTexto();
	editorTexto.enable();
	
});