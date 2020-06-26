$(document).ready(function(){
   
	
	var FunctionObj = {
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
			
			$("table tbody tr").on("click", function(){
				var checkbox = $(this).find("input[type=checkbox]");
				var $checkbox = $(checkbox);
				 checkbox.checked = $checkbox.attr("checked");
			});
			
			 //循环每个tr行，添加click事件
			  $("table").find("tr").on("click", function () {
				    //标题行不作任何操作
				    if (this.rowIndex == 0) return false;
				    var checkBox = $(this).find("input[type=checkbox]");
				    checkBox.prop("checked",!checkBox[0].checked);
			  
			  });
			
			$(".btn-success").on("click", function(){
				 var tableName = $(this).parent().prev().text();
				 self.showTableDetail(tableName)
			});


			$("#checkBaseModel").on("click", function(){
				var checked = $(this).is(':checked');
				if(checked){
					$("[name=isBaseModel]").val(true)
					$("#baseModelPathDiv").show();
				}else {
					$("[name=isBaseModel]").val(false)
					$("#baseModelPathDiv").hide();
				}
			});

			$("#isBaseBean").change(function(){
				var checkBox = $(this);
			    checkBox.val(checkBox[0].checked)
				 
			});
			 
			 $("#isBaseModel").change(function(){
				 var checkBox = $(this);
				    checkBox.val(checkBox[0].checked)
			});
			
			
			$("#generatorToModels").on("click", function(){

				$("#configForm").attr("action", "startToGeneratorModel")
				$("#configForm").submit();
			});
			
			
			$("#confirmDatabaseInfo").on("click", function(){
				var url = $("#databaseUrl").val();
				var username = $("#databaseUsername").val();
				var password = $("#databasePassword").val();
				
				 $.post("connectDatabase",
                         {"url":url, "username": username, "password":password},
                         function(result){
                        	 var info = result.info;
                        	 if(info == "1"){
                        		 alert("数据库连接成功!");
                        		 $(location).attr('href', 'list');
                        	 }
                         });
			});


			
			
		},
		showTableDetail : function(_tableName){
			$(window).attr('location','detail?tableName='+_tableName);
		}
			
			
	}
	
	FunctionObj.inital();
    
	
});