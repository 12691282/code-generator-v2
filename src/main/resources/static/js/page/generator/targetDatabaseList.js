$(document).ready(function(){
   
	
	var FunctionObj = {
		inital : function(){
			
			 this.bindEvent();

		},
		bindEvent : function(){
			
			var self = this;
			
			 //循环每个tr行，添加click事件
		    $("table").find("tr").on("click", function () {
				//标题行不作任何操作
				if (this.rowIndex == 0) return false;
				var checkBox = $(this).find("input[type=checkbox]");
				checkBox.prop("checked",!checkBox[0].checked);
		    });


			$("#importTableInfo").on("click", function(){

				var checkboxArr = $("table tbody tr").find("input[type=checkbox]:checked");
				if(checkboxArr.length == 0){
					alert("请选择一条数据导入！")
					return
				}
				var tableNameStr = '';
				checkboxArr.each(function (index,ele) {
					if(index > 0){
						tableNameStr =   tableNameStr + ',' + ele.value;
					}else {
						tableNameStr =  ele.value;
					}
				});
				var url = $("#dataBaseUrl").val();
				var driver = $("#dataBaseDriver").val();
				var username = $("#dataBaseUsername").val();
				var password = $("#dataBasePassword").val();
				$.post("importTableInfo",
					{"url":url, "username": username, "password":password,"driver":driver, "tableNameStr":tableNameStr},
					function(result){
						var info = result.info;
						if(info == "1"){
							alert("导入成功!");
							window.location.reload();
						}
					});

			});
			
		}
			
			
	}

	FunctionObj.inital();

});