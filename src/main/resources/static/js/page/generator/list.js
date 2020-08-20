$(document).ready(function(){
   
	
	var GeneratorFunctionObj = {
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

			//删除按钮事件
			$("tbody").find(".btn-danger").on("click", function () {
				var recordIteamId = $(this).val();
				$.get("deleteById",
					{"id":recordIteamId},
					function(result){
						var info = result.info;
						if(info == "1"){
							alert("删除成功!");
							window.location.reload();
						}
					});

			});

			//查看按钮事件
			$("tbody").find(".btn-success").on("click", function () {
				var tableName = $(this).val();
				self.showTableDetail(tableName)

			});

			//生成代码按钮事件
			$("table").find(".btn-info").on("click", function () {
				var tableName = $(this).val();
				self.toGeneratorAndDownload(tableName)
			});


			$("#toGeneratorCode").on("click", function(){
				var checkedEL = $("input:checked");
				var tableArr = "";
				for(var i=0; i<checkedEL.length; i++){
					var tableName =  $(checkedEL[i]).val()
					if(i > 0){
						tableArr += "," + tableName;
					}else {
						tableArr += tableName;
					}
				}
				if(tableArr.length == 0){
					alert("请选择一条数据生成！")
					return
				}
				self.toGeneratorAndDownload(tableArr)
			});

            $("#confirmConfigButto").click(function (){
                $.ajax({
                    type: "POST",//方法类型
                    dataType: "json",//预期服务器返回的数据类型
                    url: "saveConfig" ,//url
                    data: $('#confirmConfigForm').serialize(),
                    success: function (result) {
                        alert(result.data)
                    }
                });
            })

		},
		showTableDetail : function(_tableName){
			$(window).attr('location','tableInfoDetail?tableName='+_tableName);
		},
		toGeneratorAndDownload: function(_tableName){

			var url = "toGeneratorAndDownload";
			// 构造隐藏的form表单
			var $form = $("<form id='generatorAndDownload' style='display: none' method='post'></form>");
			$form.attr("action",url);
			$("body").prepend($form);
			// 添加提交参数
			var $input = $("<input name='tableNameArr' type='text' />");
			$input.attr("value",_tableName);
			$("#generatorAndDownload").append($input);
			// 提交表单
			$form.submit();

		}
			
	}

	GeneratorFunctionObj.inital();
    
	
});