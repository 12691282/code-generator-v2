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

			
		}
			
			
	}

	FunctionObj.inital();

});