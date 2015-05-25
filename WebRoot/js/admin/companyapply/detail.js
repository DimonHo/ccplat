  mini.parse();
        var form = new mini.Form("detail");
  function SetData(data) {
                row = mini.clone(data);
                var flowid="";
                if(data.flowno!=undefined){
                	 flowid=data.flowno;
				}
                if(data.flowid!=undefined){
                	flowid=data.flowid;
                }
                $.ajax({
                    url: "companyapply/query.action", 
                    dataType:"json",
                    cache: false,
                    data:{id:row.id,
                		flowid:flowid,
                		data:row
                		},
                    success: function (text) {
                    	
                        var test = mini.encode(text);
                        test=test.toString().substring(1,test.toString().length-1);
                        var o = mini.decode(test); 
                        form.setData(o);                       

                    }
                });
            setDataCommon(data);
        }