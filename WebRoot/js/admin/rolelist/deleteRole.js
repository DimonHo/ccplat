/**
 * 删除岗位节点事件
 * @param {Object} e 触发事件对象
 * @return {TypeName} 
 */
function onRemoveNode(e) {
	var tips='确定删除选中节点?';
	var tree = mini.get("treegrid");
	//当前选中的节点
//	var currentnode = tree.getSelectedNode();
	
	var node = tree.getSelectedNode();
	var arr = tree.getAllChildNodes(node);
	if (arr != null && arr.length > 0) {
		tips='存在下级内容，确定删除选中节点?';
	}
//	if (node) {
//		if (confirm(tips)) {
//			//获取选中树节点uuid
//			var uuid=node.uuid;
//			//获取当前树节点在第几层
//			var level=node._level;
//			if(level=='1'){
//				node=tree.getParentNode(node);
//			}
//			//获取模版节点
//			var rootuuid=node.uuid;
//			$.ajax( {
//				cache : false,
//				url : 'ccrolelist/deleteRole.action',
//				type : 'post',
//				datatype : 'json',
//				data : {
//					uuid : uuid,rootuuid: rootuuid
//				},
//				success : function(okstr) {
//					if (okstr == "1") {
//						tree.removeNode(currentnode);
//						alert('删除成功');
//					}else{
//						alert('删除失败');
//					}
//				},
//				error : function(e) {
//					alert('请求异常');
//				}
//			});
//		}
//	}
	if (node) {
		mini.confirm(tips,"提示",function(action){
			if(action=="ok"){
				if(node._level==0){
					deleteCode.push(node.uuid);
				}
				tree.removeNode(node);
				//保存历史记录
				var content={};
				var type="删除";
				content.type=type;
				var data={};
				data.uuid=node.uuid;
				data.name=node.name;
				data.remark=node.remark;
				if(node._level!=0){
					data.lev=node.lev;
				}
				content.data=data;
				//当删除模版时，保存模版id
				if(node._level==0){
					if(historyData[node.uuid]==null){
							historyData[node.uuid]=[];
					}
					historyData[node.uuid].push(content);
					deleteCode.push(node.uuid);
				}else{
					//将历史记录保存到根节点对应的数组中
					roleGrid.bubbleParent(node,function(e){
						if(e._level==0){
							if(historyData[e.uuid]==null){
								historyData[e.uuid]=[];
							}
							historyData[e.uuid].push(content);
						}
					});
				}
				saveBtn.enable();
			}
		});
//		if (confirm(tips)) {
//			if(node._level==0){
//				deleteCode.push(node.uuid);
//			}
//			tree.removeNode(node);
//			//保存历史记录
//			var content={};
//			var type="删除";
//			content.type=type;
//			var data={};
//			data.uuid=node.uuid;
//			data.name=node.name;
//			data.remark=node.remark;
//			if(node._level!=0){
//				data.lev=node.lev;
//			}
//			content.data=data;
//			//当删除模版时，保存模版id
//			if(node._level==0){
//				if(historyData[node.uuid]==null){
//						historyData[node.uuid]=[];
//				}
//				historyData[node.uuid].push(content);
//				deleteCode.push(node.uuid);
//			}else{
//				//将历史记录保存到根节点对应的数组中
//				roleGrid.bubbleParent(node,function(e){
//					if(e._level==0){
//						if(historyData[e.uuid]==null){
//							historyData[e.uuid]=[];
//						}
//						historyData[e.uuid].push(content);
//					}
//				});
//			}
//			saveBtn.enable();
//		}
	}
}