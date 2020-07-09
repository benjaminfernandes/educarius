var Portal = Portal || {};

Portal.TextEditor = (function(){
	
function TextEditor (){
	
	this.btnSubmit = $('.js-btn-submit');
	this.toolbarOptions = [
		  ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
		  ['blockquote', 'code-block'],

		  [{ 'header': 1 }, { 'header': 2 }],               // custom button values
		  [{ 'list': 'ordered'}, { 'list': 'bullet' }],
		  [{ 'script': 'sub'}, { 'script': 'super' }],      // superscript/subscript
		  [{ 'indent': '-1'}, { 'indent': '+1' }],          // outdent/indent
		  [{ 'direction': 'rtl' }],                         // text direction

		  [{ 'size': ['small', false, 'large', 'huge'] }],  // custom dropdown
		  [{ 'header': [1, 2, 3, 4, 5, 6, false] }],

		  [{ 'color': [] }, { 'background': [] }],          // dropdown with defaults from theme
		  [{ 'font': [] }],
		  [{ 'align': [] }],

		  ['clean']                                         // remove formatting button
		];
	
}
	
TextEditor.prototype.iniciar = function(){
	this.btnSubmit.on('click', putText());
}

function putText(){
	var quill = new Quill('#editor', {
		  modules: {
		    toolbar: this.toolbarOptions
		  },
		  theme: 'snow'
		});
	$('input[name=descricao]').val(JSON.stringify(quill.getContents()));
}
 
return TextEditor;
	
})();

$(function(){
	var textEditor = new Portal.TextEditor();
	textEditor.iniciar();
});