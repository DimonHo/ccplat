        mini.parse();
        var form = new mini.Form("form1");
        
        function SaveData() {
            var o = form.getData();            
            form.validate();
            if (form.isValid() == false) return;

            var json = mini.encode(o);
            $.ajax({
                url: "companyapply/edit.action",
				type: 'post',
                data: { data: json },
                cache: false,
                success: function (text) {
                	mini.alert("保存成功");
                   CloseWindow("save");

                },
                error: function (jqXHR, textStatus, errorThrown) {
                	mini.alert(jqXHR.responseText);
                   // CloseWindow();
                }
            });
        }
			function CloseWindow(action) {            
	            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
			
			 function onOk(e) {
            SaveData();
        }
        function onCancel(e) {
            CloseWindow("cancel");
        }
        
        
          function SetData(data) {
            if (data.action == "edit") {
                //跨页面传递的数据对象，克隆后才可以安全使用
                row = mini.clone(data);  
                $.ajax({
                    url: "companyapply/query.action", 
                    dataType:"json",
                    cache: false,
                    data:{id:row.id},
                    success: function (text) {
                    	
                        var test = mini.encode(text);
                        test=test.toString().substring(1,test.toString().length-1);
                        var o = mini.decode(test); 
                        form.setData(o);
                        var myDate = new Date();
                        myDate=mini.formatDate (myDate,"yyyy-MM-dd");
                        mini.getbyName("createtime").setValue(myDate);


                    }
                });
            }
            
        }
          
     //创建时间
function onCreatetime(){
    var record = form.getData();            
	var createtime = record.createtime.$date;
	return mini.formatDate (createtime,"yyyy-MM-dd");

}