mini.parse();

var form = new mini.Form("form1");

function CloseWindow(action) {

	if (window.CloseOwnerWindow)
		return window.CloseOwnerWindow(action);
	else
		window.close();
}

function onCancel(e) {
	CloseWindow("cancel");
}

function SetData(data) {
	data = mini.clone(data);
	form.setData(data);
}
