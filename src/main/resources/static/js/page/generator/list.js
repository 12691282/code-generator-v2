$(document).ready(function(){
   
	
	var GeneratorFunctionObj = {
		    isBaseModel : $("#isBaseModelVal").val(),
			
			
		inital : function(){
			
			 this.setWindowsObj();
			
			 this.bindEvent();

		},
		setWindowsObj : function(){

			window.setToPageCss = function(_val){
				$("#pageCss").val(_val);
				return false;
			}
			if(this.isBaseModel === 'Y'){
				$("#checkBaseModel").attr("checked", true);
				$("[name=isBaseModel]").val(true)
				$("#baseModelPathDiv").show();
			}
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
			
			$(".btn-success").on("click", function(){
				 var tableName = $(this).val();
				 self.showTableDetail(tableName)
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

			$("table").find(".btn-info").on("click", function () {
				var tableName = $(this).val();
				self.toGeneratorAndDownload(tableName)
			});

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