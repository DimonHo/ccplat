var tree=null;
var actno="";
var submit;
var actname;
var ccno;
var rule;
	
mini.parse();
	
//弹出框默认调用方法
function SetData(data) {
    
  	data = mini.clone(data);
  	actno = data.actno;
  	actname=mini.get("actname");
  	actname.setValue(data.actname);
  	ccno=data.ccno;
	rule=mini.get("rule");
		
	submit=mini.get("submit");
	submit.load(basePath+"audit/personList.action?actno="+actno+"&sub="+actno+"&type=2");
	rule.load(basePath+"audit/getRule.action?actno="+actno);
	
	getInitData(ccno,actno);
	}
	
function getInitData(ccno,actno){
	
	$.ajax( {
		url : basePath+"audit/getPersonRuleInfo.action",
		type : 'post',
		data : {
			actno : actno,
			ccno:ccno
		},
		cache : false,
		success : function(text) {
			var data=mini.decode(text);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			mini.alert(jqXHR.responseText);
			CloseWindow();
		}
	});
}
    
function changeRuleName(e){
    
  var ruleStr=rule.getValue();
  var strAry=ruleStr.split(",");
    	
  mini.get("ruleType").setValue(strAry[1]);
  mini.get("rulestart").setValue(strAry[2]);
  mini.get("ruleend").setValue(strAry[3]);
}
    

function onNodeDblClick(e) {
    onOk();
}

//////////////////////////////////
function CloseWindow(action) {
  if(window.CloseOwnerWindow) 
       return window.CloseOwnerWindow(action);
       else window.close();
}

function onOk() {
     $.ajax({
		url : basePath+"audit/saveRuleToUserAction.action",
		type : 'post',
		data : {
			actno : actno,
			ccno:ccno,
			ruletype : mini.get("ruleType").getValue(),
			rulestart : mini.get("rulestart").getValue(),
			ruleend : mini.get("ruleend").getValue(),
			submit:mini.get("submit").getValue()
		},
		cache : false,
		success : function(text) {
			var data=mini.decode(text);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			mini.alert(jqXHR.responseText);
			CloseWindow();
		}
	 });   
    }

function onCancel() {
    CloseWindow("cancel");
}