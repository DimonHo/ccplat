<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>


<div class="mini-fit" style="width:100%;"  borderStyle="border:1px solid #c8c7c7;border-top:0px;">
     <div class="mini-toolbar" borderStyle="border-left:0px;border-right:0px;"  >                
   	  <span style="padding-left:5px;">选择区域</span>
 	</div>
     <ul id="tree1"  class="mini-tree" url="ccarea/list.action" style="width:100%;"
         showTreeIcon="true" textField="name" expandOnLoad="true"   allowDrag="true" allowDrop="true" 
         allowLeafDropIn="true"  idField="id" parentField="pid" resultAsTree="false"   contextMenu="#treeMenu">        
     </ul>
      <!--右健菜单-->
           <ul id="treeMenu" class="mini-contextmenu"  onbeforeopen="onBeforeOpen">        
             <li iconCls="icon-add" onclick="onAddNode1">新增节点</li>
             <li name="edit" iconCls="icon-edit" onclick="onEditNode1">编辑节点</li>
             <li name="remove" iconCls="icon-remove" onclick="onRemoveNode1">删除节点</li>        
          </ul>
 </div>
  
 <script src="js/ui/scripts/boot.js" type="text/javascript"></script> 
 <script src="js/admin/area/list.js" type="text/javascript"></script> 
