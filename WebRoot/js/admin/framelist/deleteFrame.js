/**
 * 删除组织架构节点事件
 * @param {Object} e 触发事件对象
 * @return {TypeName} 
 */
function onRemoveNode(e) {
	var tips='确定删除选中节点?';
	var tree = mini.get("frameTree");
	//当前选中节点
	var node = tree.getSelectedNode();
	var arr = tree.getAllChildNodes(node);
	if (arr != null && arr.length > 0) {
		tips='存在下级内容，确定删除选中节点?';
	}
//	if (node) {
//		if (confirm(tips)) {
//			//获取选中树节点uuid
//			var uuid=node.uuid;
//			//获取子节点所处的位置
//			var key = '';
//			//获取下标
//			var index = '';
//			//获取当前树节点在第几层
//			var level=node._level;
//			//根据层级获取所有下标位置
//			for(var i=level;i>0;i--){
//				if(i==level){
//					index=tree.indexOfNode(node);
//				}else{
//					index=(tree.indexOfNode(node))+','+index;
//				}
//				key='children'+','+key;
//				node=tree.getParentNode(node);
//			}
//			//树根节点的uuid
//			var rootuuid=node.uuid;
//			$.ajax( {
//				cache : false,
//				url : 'ccframelist/deleteFrame.action',
//				type : 'post',
//				datatype : 'json',
//				data : {
//					uuid : uuid,key: key,index: index,rootuuid: rootuuid
//				},
//				success : function(okstr) {
//					if (okstr == "1") {
//						tree.removeNode(currentNode);
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
				tree.removeNode(node);
			
				//保存历史记录
				var content={};
				var type="删除";
				content.type=type;
				var data={};
				data.uuid=node.uuid;
				data.name=node.name;
				data.remark=node.remark;
				content.data=data;
				if(node._level==0){
					if(historyData[node.uuid]==null){
							historyData[node.uuid]=[];
					}
					historyData[node.uuid].push(content);
					deleteCode.push(node.uuid);
				}else{
					frameTree.bubbleParent(node,function(e){
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
//			tree.removeNode(node);
//			
//			//保存历史记录
//			var content={};
//			var type="删除";
//			content.type=type;
//			var data={};
//			data.uuid=node.uuid;
//			data.name=node.name;
//			data.remark=node.remark;
//			content.data=data;
//			if(node._level==0){
//				if(historyData[node.uuid]==null){
//						historyData[node.uuid]=[];
//				}
//				historyData[node.uuid].push(content);
//				deleteCode.push(node.uuid);
//			}else{
//				frameTree.bubbleParent(node,function(e){
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