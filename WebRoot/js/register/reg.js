function regInfo() {
	params = {
		name : "原动力有限责任有限公司",
		contact : "李茂山",
		phone : "13865232536",
		remark: "备注"
	};
	$.ajax({
		url : "reg/check.action",
		type : "post",
		data : {
			param : mini.encode(params)
		},
		success : function(text) {
			mini.alert(text);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			mini.alert("ERROR_____");
		}
	});
}