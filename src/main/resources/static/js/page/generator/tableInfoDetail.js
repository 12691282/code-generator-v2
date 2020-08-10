$(document).ready(function(){
   
	
	var FunctionObj = {
		inital : function(){
			
			 this.bindEvent();

		},
		bindEvent : function(){
			
			var self = this;
			var navEvent =  $(".nav-event-clazz");
			 //循环每个li行，添加click事件
			navEvent.find("li").on("click", function () {
				var liSelf = $(this);
				navEvent.find("li").removeAttr("class")
				$(".content-event-clazz").hide();
				liSelf.addClass("active");
				var targetId = liSelf.attr("value");
				$("#"+targetId).show();
		    });

			$("#confirmTableInfo").click(function (){
				$.ajax({
					type: "POST",//方法类型
				    dataType: "json",//预期服务器返回的数据类型
					url: "updateTableInfoEntity" ,//url
					data: $('#tableInfoEntityForm').serialize(),
					success: function (result) {
					    alert(result.data)
					}
				});
			})

            $(".column-event-clazz").find("button").on("click", function () {
				var but = $(this);
				var params = {
					"columnId": but.val()
				}
				var tdList = but.parent().parent().find(".column-value-clazz");
				for(var i=0; i<tdList.length; i++){
					var tdEl = tdList[i]
					var type = tdEl.type;
					if(type == "checkbox"){
						params[tdEl.name] = $(tdEl).is(":checked") ? "1" : "0";
					}else{
						params[tdEl.name] = tdEl.value;
					}
				}
				$.ajax({
					type: "POST",//方法类型
					dataType: "json",//预期服务器返回的数据类型
					url: "updateTableColumnInfo" ,//url
					data: params,
					success: function (result) {
						alert(result.data)
					}
				});
			});



			
		}
			
			
	}

	FunctionObj.inital();

});