mini.parse();
//获取行业树对象
var industryTree = mini.get("industryTree");

var agreeurl='ccindustryinfo/agree.action';

//标准方法接口定义
function loadData() {
	industryTree.load({flowid:flowno});
}


