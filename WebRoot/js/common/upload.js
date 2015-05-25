function ajaxFileUpload(id) {
	var fileType = $("#uploadType" + id).attr("value");
	$("#loading" + id).ajaxStart(function() {
		$(this).show();
	}).ajaxComplete(function() {
		$(this).hide();
	});

	$.ajaxFileUpload({
		url : 'file/fileUpload.action',
		secureuri : false,
		fileElementId : 'fileToUpload' + id,
		dataType : 'json',
		type : "post",
		data : {
			uploadType : fileType
		},
		success : function(data, status) {
			calBack(data, id);
		},
		error : function(data, status, e) {
			alert("error");
		}
	});
	return false;
}

function calBack(data, id) {
	// 赋值给隐藏字段和展示图片的span
	var textId = "#" + $("#textId" + id).attr("value");
	//var text = $("#textId" + id).attr("value");
	//mini.get(text).setValue(data);
	$(textId).val(data);

}