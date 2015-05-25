(function($) {
	$('a[data-reveal-id]').live('click', function(e) {
		e.preventDefault();
		var modalLocation = $(this).attr('data-reveal-id');
		$('#'+modalLocation).reveal($(this).data());
	});

    $.fn.reveal = function(options) {      
        
        var defaults = {  
	    	animation: 'fadeAndPop', //fade, fadeAndPop, none
		    animationspeed: 300, //how fast animtions are
		    closeonbackgroundclick:true, //if you click background will modal close?
		    dismissmodalclass: 'close-reveal-modal' //the class of a button or element that will close an open modal
    	}; 
    	
              var options = $.extend({}, defaults, options); 
	
        return this.each(function() {

        	var modal = $(this),
        		topMeasure  = parseInt(modal.css('top')),
				topOffset = modal.height() + topMeasure,
          		locked = false,
				modalBG = $('.reveal-modal-bg');


			if(modalBG.length == 0) {
				modalBG = $('<div class="reveal-modal-bg" />').insertAfter(modal);
			}		    
     

			//Entrance Animations
			modal.bind('reveal:open', function () {
			  modalBG.unbind('click.modalEvent');
				$('.' + options.dismissmodalclass).unbind('click.modalEvent');
				if(!locked) {
					lockModal();
					if(options.animation == "fadeAndPop") {
						modal.css({'top': $(document).scrollTop()-topOffset, 'opacity' : 0, 'visibility' : 'visible'});
						modalBG.fadeIn(options.animationspeed/2);
						modal.delay(options.animationspeed/2).animate({
							"top": $(document).scrollTop()+topMeasure + 'px',
							"opacity" : 1
						}, options.animationspeed,unlockModal());					
					}
					if(options.animation == "fade") {
						modal.css({'opacity' : 0, 'visibility' : 'visible', 'top': $(document).scrollTop()+topMeasure});
						modalBG.fadeIn(options.animationspeed/2);
						modal.delay(options.animationspeed/2).animate({
							"opacity" : 1
						}, options.animationspeed,unlockModal());					
					} 
					if(options.animation == "none") {
						modal.css({'visibility' : 'visible', 'top':$(document).scrollTop()+topMeasure});
						modalBG.css({"display":"block"});	
						unlockModal()				
					}
				}
				modal.unbind('reveal:open');
			}); 	

			//Closing Animation
			modal.bind('reveal:close', function () {
			  if(!locked) {
					lockModal(); 
						$(".full").css("overflow-y","auto");	
					if(options.animation == "fadeAndPop") { 
						modalBG.delay(options.animationspeed).fadeOut(options.animationspeed); 
						modal.animate({
							"top":  $(document).scrollTop()-topOffset + 'px',
							"opacity" : 0
						}, options.animationspeed/2, function() {
							modal.css({'top':topMeasure, 'opacity' : 1, 'visibility' : 'hidden'});
							unlockModal();
						});		
								
					}  	
					if(options.animation == "fade") { 
						modalBG.delay(options.animationspeed).fadeOut(options.animationspeed);
						modal.animate({
							"opacity" : 0
						}, options.animationspeed, function() {
							modal.css({'opacity' : 1, 'visibility' : 'hidden', 'top' : topMeasure});
							unlockModal();
						});					
					}  	
					if(options.animation == "none") { 
						modal.css({'visibility' : 'hidden', 'top' : topMeasure});
						modalBG.css({'display' : 'none'});	
					}		
				}
				modal.unbind('reveal:close');
			});     
   	

        	//Open Modal Immediately
    	modal.trigger('reveal:open')
			
			//Close Modal Listeners
			var closeButton = $('.' + options.dismissmodalclass).bind('click.modalEvent', function () {
				saveTips(modal);
			  	//modal.trigger('reveal:close')
			});
			
			if(options.closeonbackgroundclick) {
				modalBG.css({"cursor":"pointer"})
				modalBG.bind('click.modalEvent', function () {
					saveTips(modal);
				 // modal.trigger('reveal:close')
				});
			}
			$('body').keyup(function(e) {
        		if(e.which===27){
        			saveTips(modal);
        			//modal.trigger('reveal:close'); 
        		} // 27 is the keycode for the Escape key
			});
			

			function unlockModal() { 
				locked = false;
			}
			function lockModal() {
				locked = true;
			}	
			
        });//each call
    }//orbit plugin call
})(jQuery);

function saveTips(modal){
	var isIE=true;
	//检查是否要保存数据
	var saveBtn=window.frames["frame"].saveBtn;
	if("undefined"==typeof saveBtn){
		isIE=false;
		var contentDoc=window.top.frames["frame"].contentDocument;
		if("undefined"!=typeof contentDoc){
			saveBtn=contentDoc.defaultView.saveBtn;
		}
		if("undefined"==typeof saveBtn){
			modal.trigger('reveal:close')
			return;
		}
	}
	if(saveBtn.enabled&&saveBtn.style!='display: none;'){
		mini.showMessageBox({
		 	title: "提示",    
		    message: "数据已修改，是否保存",
		    buttons: ["ok", "no", "cancel"],    
		    iconCls: "mini-messagebox-question",
		    callback: function(action){
				if(action=="no"){
					modal.trigger('reveal:close')
				}else if(action=="ok"){
					//保存数据
					if(isIE){
						window.frames["frame"].saveResult(modal);
					}else{
						window.top.frames["frame"].contentDocument.defaultView.saveResult(modal);
					}
				}
		    }
		});
	}else{
		modal.trigger('reveal:close')
	}
}
   