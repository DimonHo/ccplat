tree1.on("nodeselect",function(v){
	
				s.removeClass("hidden");
				
				r.disable();
				e.disable();
				if(!s.hasClass("loading"))
					s.addClass("loading");
				if(v.isLeaf){
					
					$.ajax({
						url  : 'companylistByArea.action',
						data : {areaid:v.node.tid},
						type : 'post',
						success : function(d){
							s.removeClass("loading").html('');
							c(d);
						},
						error: function (jqXHR, textStatus, errorThrown) {
								//alert(jqXHR.responseText);
						}
					});
				}
				
		});


 
//动作
function action(a){
		var title = '',tid='';
		if(a=='add')
		{
			title = '添加水司';
		}
		else if (a=='edir')
		{
			title = '编辑水司';
			tid = $('.hovebut').attr("id");
		}
	
		mini.open({
						url:'ui/demo/CommonLibs/ssWindowFrame.html',
						title:title,width:400,height:400,
						onload:function(){
								var iframe = this.getIFrameEl();
								var data = {action: a, id: tid };
								iframe.contentWindow.SetData(data);
							},
						ondestroy:function(action){
								tree1.reload();
								
							}
				});
}

function addproduc(){
				
			action('add');
				
				
			}

function editproduc(){
			
			action('edir');
		
}

function removeproduc(){
		tid = $('.hovebut').attr("id");
		if(tid)
		{
			$.ajax({
				url:'companyDelete.action',
				data:{tid:tid},
				type:'post',
				success:function(d){
					if(d[0].data)
					 success("册除一条数据成功");
					 
				},
				error: function (jqXHR, textStatus, errorThrown) {
	                    alertClick("删除失败!");
                }
			});
		}
		
}

//调用地区相应的水司
function c(d){
	
				var j = [] , a = '',id = '';
				s.html('');
				for(var i = 0 ; i< d.length ; i++)
				 {
					  a = $('<a class="mini-button mini-button-plain" id='+d[i].tid+' areaid='+d[i].areaid+' href="javascript:void(0)"><span class="mini-button-text  mini-button-icon icon-search">'+d[i].name+'</span></a>').get(0);
					  s.append(a);
					 j.push(a);
				 }
				 
				 for(var i = 0 ; i<j.length;i++)
				 {
					$(j[i]).unbind('click').click(function(){
								$(this).css({'color':'#000'}).addClass('hovebut').siblings().removeClass('hovebut').css({'color':'#ccc'});
								id = $(this).attr("id");
								r.enable();
								e.enable();
								
								tree2.loading("保存中，请稍后......");
								$.ajax({
										url  : 'ui/demo/data/listTree3',
										data : {tid:id},
										type : 'post',
										success : function(d){
												 tree2.loadList(d, "ids", "pids");
											},
										error: function (jqXHR, textStatus, errorThrown) {
												//alert(jqXHR.responseText);
											}
								});
						  });
				 }
				
				
				 
			}

//!--右健菜单-->
  

        function onAddBefore(e) {
            var tree = mini.get("tree1");
            var node = tree.getSelectedNode();

            var newNode = {};
            tree.addNode(newNode, "before", node);
        }
        function onAddAfter(e) {
            var tree = mini.get("tree1");
            var node = tree.getSelectedNode();
            var newNode = {};
            tree.addNode(newNode, "after", node);
        }
        function onAddNode(e) {
            var tree = mini.get("tree1");
            var node = tree.getSelectedNode();

            var newNode = {};
            tree.addNode(newNode, "add", node);
        }
        function onEditNode(e) {
            var tree = mini.get("tree1");
            var node = tree.getSelectedNode();
            
            tree.beginEdit(node);            
        }
        function onRemoveNode(e) {
            var tree = mini.get("tree1");
            var node = tree.getSelectedNode();

            if (node){
                if (confirm("确定删除选中节点?")) {
                    tree.removeNode(node);
                }
            }
        }
      
function onBeforeOpen(e) {
    var menu = e.sender;
    var tree = mini.get("tree1");

    var node = tree.getSelectedNode();
    if (!node) {
        e.cancel = true;
        return;
    }
    if (node && node.text == "Base") {
        e.cancel = true;
        //阻止浏览器默认右键菜单
        e.htmlEvent.preventDefault();
        return;
    }

    ////////////////////////////////
    var editItem = mini.getbyName("edit", menu);
    var removeItem = mini.getbyName("remove", menu);
    editItem.show();
    removeItem.enable();

    if (node.pids == "forms") {
        editItem.hide();
    }
    if (node.pids == "lists") {
        removeItem.disable();
    }
}
   