/**
 * 删除产品节点事件
 * @param {Object} e 触发事件对象
 * @return {TypeName} 
 */
function onRemoveNode(e) {
	var tips='确定删除选中节点?';
	var tree = mini.get("productTree");
	//当前选中的节点
	var node = tree.getSelectedNode();
	var arr = tree.getAllChildNodes(node);
	if (arr != null && arr.length > 0) {
		tips='存在下级内容，确定删除选中节点?';
	}
	if (node) {
		
		mini.confirm(tips,"提示",function(action){
			if(action=="ok"){
				tree.removeNode(node);
				//保存历史记录
				var content={};
				var type="删除";
				content.type=type;
				var data={};
				data.code=node.code;
				data.name=node.name;
				data.remark=node.remark;
				if(node.open!=null){
			    	data.url=node.url;
			    	data.open=node.open;
			    }
				content.data=data;
				if(node._level==0){
					if(historyData[node.code]==null){
							historyData[node.code]=[];
					}
					historyData[node.code].push(content);
					deleteCode.push(node.code);
				}else{
					productTree.bubbleParent(node,function(e){
						if(e._level==0){
							if(historyData[e.code]==null){
								historyData[e.code]=[];
							}
							historyData[e.code].push(content);
						}
					});
				}
				saveBtn.enable();
			}
		});
		
//		if (confirm(tips)) {
//			tree.removeNode(node);
//			//保存历史记录
//			var content={};
//			var type="删除";
//			content.type=type;
//			var data={};
//			data.code=node.code;
//			data.name=node.name;
//			data.remark=node.remark;
//			if(node.open!=null){
//		    	data.url=node.url;
//		    	data.open=node.open;
//		    }
//			content.data=data;
//			if(node._level==0){
//				if(historyData[node.code]==null){
//						historyData[node.code]=[];
//				}
//				historyData[node.code].push(content);
//				deleteCode.push(node.code);
//			}else{
//				productTree.bubbleParent(node,function(e){
//					if(e._level==0){
//						if(historyData[e.code]==null){
//							historyData[e.code]=[];
//						}
//						historyData[e.code].push(content);
//					}
//				});
//			}
//			saveBtn.enable();
//		}
	}
}