/*! jQuery-typing | Author:  Maciej Konieczny | License: public domain */
(function(f){function l(g,h){function d(a){if(!e){e=true;c.start&&c.start(a,b)}}function i(a,j){if(e){clearTimeout(k);k=setTimeout(function(){e=false;c.stop&&c.stop(a,b)},j>=0?j:c.delay)}}var c=f.extend({start:null,stop:null,delay:400},h),b=f(g),e=false,k;b.keypress(d);b.keydown(function(a){if(a.keyCode===8||a.keyCode===46)d(a)});b.keyup(i);b.blur(function(a){i(a,0)})}f.fn.typing=function(g){return this.each(function(h,d){l(d,g)})}})(jQuery);
/*! Ajax Autocomplete for jQuery, version 1.1.5 | (c) 2010 Tomas Kirda, Vytautas Pranskunas | jquery.org/license */
(function($){var reEscape=new RegExp("(\\"+["/",".","*","+","?","|","(",")","[","]","{","}","\\"].join("|\\")+")","g");function fnFormatResult(value,data,currentValue){var pattern="("+currentValue.replace(reEscape,"\\$1")+")";return value.replace(new RegExp(pattern,"gi"),"<strong>$1</strong>")}function Autocomplete(el,options){this.el=$(el);this.el.attr("autocomplete","off");this.suggestions=[];this.data=[];this.badQueries=[];this.selectedIndex=-1;this.currentValue=this.el.val();this.intervalId=0;this.cachedResponse=[];this.onChangeInterval=null;this.onChange=null;this.ignoreValueChange=false;this.serviceUrl=options.serviceUrl;this.isLocal=false;this.options={autoSubmit:false,minChars:1,maxHeight:300,deferRequestBy:0,width:0,highlight:true,params:{},fnFormatResult:fnFormatResult,delimiter:null,zIndex:9999};this.initialize();this.setOptions(options);this.el.data("autocomplete",this)}$.fn.autocomplete=function(options,optionName){var autocompleteControl;if(typeof options=="string"){autocompleteControl=this.data("autocomplete");if(typeof autocompleteControl[options]=="function"){autocompleteControl[options](optionName)}}else{autocompleteControl=new Autocomplete(this.get(0)||$("<input />"),options)}return autocompleteControl};Autocomplete.prototype={killerFn:null,initialize:function(){var me,uid,autocompleteElId;me=this;uid=Math.floor(Math.random()*1048576).toString(16);autocompleteElId="Autocomplete_"+uid;this.killerFn=function(e){if($(e.target).parents(".autocomplete").size()===0){me.killSuggestions();me.disableKillerFn()}};if(!this.options.width){this.options.width=this.el.width()}this.mainContainerId="AutocompleteContainter_"+uid;$('<div id="'+this.mainContainerId+'" style="position:absolute;z-index:9999;"><div class="autocomplete-w1"><div class="autocomplete" id="'+autocompleteElId+'" style="display:none; width:300px;"></div></div></div>').appendTo("body");this.container=$("#"+autocompleteElId);this.fixPosition();if(window.opera){this.el.keypress(function(e){me.onKeyPress(e)})}else{this.el.keydown(function(e){me.onKeyPress(e)})}this.el.keyup(function(e){me.onKeyUp(e)});this.el.blur(function(){me.enableKillerFn()});this.el.focus(function(){me.fixPosition()});this.el.change(function(){me.onValueChanged()})},extendOptions:function(options){$.extend(this.options,options)},setOptions:function(options){var o=this.options;this.extendOptions(options);if(o.lookup||o.isLocal){this.isLocal=true;if($.isArray(o.lookup)){o.lookup={suggestions:o.lookup,data:[]}}}$("#"+this.mainContainerId).css({zIndex:o.zIndex});this.container.css({maxHeight:o.maxHeight+"px",width:o.width})},clearCache:function(){this.cachedResponse=[];this.badQueries=[]},disable:function(){this.disabled=true},enable:function(){this.disabled=false},fixPosition:function(){var offset=this.el.offset();$("#"+this.mainContainerId).css({top:(offset.top+this.el.innerHeight())+"px",left:offset.left+"px"})},enableKillerFn:function(){var me=this;$(document).bind("click",me.killerFn)},disableKillerFn:function(){var me=this;$(document).unbind("click",me.killerFn)},killSuggestions:function(){var me=this;this.stopKillSuggestions();this.intervalId=window.setInterval(function(){me.hide();me.stopKillSuggestions()},300)},stopKillSuggestions:function(){window.clearInterval(this.intervalId)},onValueChanged:function(){this.change(this.selectedIndex)},onKeyPress:function(e){if(this.disabled||!this.enabled){return}switch(e.keyCode){case 27:this.el.val(this.currentValue);this.hide();break;case 9:case 13:if(this.selectedIndex===-1){this.hide();return}this.select(this.selectedIndex);if(e.keyCode===9){return}break;case 38:this.moveUp();break;case 40:this.moveDown();break;default:return}e.stopImmediatePropagation();e.preventDefault()},onKeyUp:function(e){if(this.disabled){return}switch(e.keyCode){case 38:case 40:return}clearInterval(this.onChangeInterval);if(this.currentValue!==this.el.val()){if(this.options.deferRequestBy>0){var me=this;this.onChangeInterval=setInterval(function(){me.onValueChange()},this.options.deferRequestBy)}else{this.onValueChange()}}},onValueChange:function(){clearInterval(this.onChangeInterval);this.currentValue=this.el.val();var q=this.getQuery(this.currentValue);this.selectedIndex=-1;if(this.ignoreValueChange){this.ignoreValueChange=false;return}if(q===""||q.length<this.options.minChars){this.hide()}else{this.getSuggestions(q)}},getQuery:function(val){var d,arr;d=this.options.delimiter;if(!d){return $.trim(val)}arr=val.split(d);return $.trim(arr[arr.length-1])},getSuggestionsLocal:function(q){var ret,arr,len,val,i;arr=this.options.lookup;len=arr.suggestions.length;ret={suggestions:[],data:[]};q=q.toLowerCase();for(i=0;i<len;i++){val=arr.suggestions[i];if(val.toLowerCase().indexOf(q)===0){ret.suggestions.push(val);ret.data.push(arr.data[i])}}return ret},getSuggestions:function(q){var cr,me;cr=this.isLocal?this.getSuggestionsLocal(q):this.cachedResponse[q];if(cr&&$.isArray(cr.suggestions)){this.suggestions=cr.suggestions;this.data=cr.data;this.suggest()}else{if(!this.isBadQuery(q)){me=this;me.options.params.query=q;$.get(this.serviceUrl,me.options.params,function(txt){me.processResponse(txt)},"text")}}},isBadQuery:function(q){var i=this.badQueries.length;while(i--){if(q.indexOf(this.badQueries[i])===0){return true}}return false},hide:function(){this.enabled=false;this.selectedIndex=-1;this.container.hide()},suggest:function(){if(this.suggestions.length===0){this.hide();return}var me,len,div,f,v,i,s,mOver,mClick;me=this;len=this.suggestions.length;f=this.options.fnFormatResult;v=this.getQuery(this.currentValue);mOver=function(xi){return function(){me.activate(xi)}};mClick=function(xi){return function(){me.select(xi)}};this.container.hide().empty();for(i=0;i<len;i++){s=this.suggestions[i];div=$((me.selectedIndex===i?'<div class="selected"':"<div")+' title="'+s+'">'+f(s,this.data[i],v)+"</div>");div.mouseover(mOver(i));div.click(mClick(i));this.container.append(div)}this.enabled=true;this.container.show()},processResponse:function(text){var response;try{response=eval("("+text+")")}catch(err){return}if(!$.isArray(response.data)){response.data=[]}if(!this.options.noCache){this.cachedResponse[response.query]=response;if(response.suggestions.length===0){this.badQueries.push(response.query)}}if(response.query===this.getQuery(this.currentValue)){this.suggestions=response.suggestions;this.data=response.data;this.suggest()}},activate:function(index){var divs,activeItem;divs=this.container.children();if(this.selectedIndex!==-1&&divs.length>this.selectedIndex){$(divs.get(this.selectedIndex)).removeClass()}this.selectedIndex=index;if(this.selectedIndex!==-1&&divs.length>this.selectedIndex){activeItem=divs.get(this.selectedIndex);$(activeItem).addClass("selected")}return activeItem},deactivate:function(div,index){div.className="";if(this.selectedIndex===index){this.selectedIndex=-1}},select:function(i){var selectedValue,f;selectedValue=this.suggestions[i];if(selectedValue){this.el.val(selectedValue);if(this.options.autoSubmit){f=this.el.parents("form");if(f.length>0){f.get(0).submit()}}this.ignoreValueChange=true;this.hide();this.onSelect(i)}},change:function(i){var selectedValue,fn,me;me=this;selectedValue=this.suggestions[i];if(selectedValue){var s,d;s=me.suggestions[i];d=me.data[i];me.el.val(me.getValue(s))}else{s="";d=-1}fn=me.options.onChange;if($.isFunction(fn)){fn(s,d,me.el)}},moveUp:function(){if(this.selectedIndex===-1){return}if(this.selectedIndex===0){this.container.children().get(0).className="";this.selectedIndex=-1;this.el.val(this.currentValue);return}this.adjustScroll(this.selectedIndex-1)},moveDown:function(){if(this.selectedIndex===(this.suggestions.length-1)){return}this.adjustScroll(this.selectedIndex+1)},adjustScroll:function(i){var activeItem,offsetTop,upperBound,lowerBound;activeItem=this.activate(i);offsetTop=activeItem.offsetTop;upperBound=this.container.scrollTop();lowerBound=upperBound+this.options.maxHeight-25;if(offsetTop<upperBound){this.container.scrollTop(offsetTop)}else{if(offsetTop>lowerBound){this.container.scrollTop(offsetTop-this.options.maxHeight+25)}}this.el.val(this.getValue(this.suggestions[i]))},onSelect:function(i){var me,fn,s,d;me=this;fn=me.options.onSelect;s=me.suggestions[i];d=me.data[i];me.el.val(me.getValue(s));if($.isFunction(fn)){fn(s,d,me.el)}},getValue:function(value){var del,currVal,arr,me;me=this;del=me.options.delimiter;if(!del){return value}currVal=me.currentValue;arr=currVal.split(del);if(arr.length===1){return value}return currVal.substr(0,currVal.length-arr[arr.length-1].length)+value}}}(jQuery));
/*jQuery MultiSelect UI Widget 1.13 | Copyright (c) 2012 Eric Hynds | http://www.erichynds.com/jquery/jquery-ui-multiselect-widget/ | Dual licensed under the MIT and GPL licenses: | http://www.opensource.org/licenses/mit-license.php | http://www.gnu.org/licenses/gpl.html */
(function(d){var k=0;d.widget("ech.multiselect",{options:{header:!0,height:175,minWidth:225,classes:"",checkAllText:"Check all",uncheckAllText:"Uncheck all",noneSelectedText:"Select options",selectedText:"# selected",selectedList:0,show:null,hide:null,autoOpen:!1,multiple:!0,position:{}},_create:function(){var a=this.element.hide(),b=this.options;this.speed=d.fx.speeds._default;this._isOpen=!1;a=(this.button=d('<button type="button"><span class="ui-icon ui-icon-triangle-2-n-s"></span></button>')).addClass("ui-multiselect ui-widget ui-state-default ui-corner-all").addClass(b.classes).attr({title:a.attr("title"),"aria-haspopup":!0,tabIndex:a.attr("tabIndex")}).insertAfter(a);(this.buttonlabel=d("<span />")).html(b.noneSelectedText).appendTo(a);var a=(this.menu=d("<div />")).addClass("ui-multiselect-menu ui-widget ui-widget-content ui-corner-all").addClass(b.classes).appendTo(document.body),c=(this.header=d("<div />")).addClass("ui-widget-header ui-corner-all ui-multiselect-header ui-helper-clearfix").appendTo(a);(this.headerLinkContainer=d("<ul />")).addClass("ui-helper-reset").html(function(){return!0===b.header?'<li><a class="ui-multiselect-all" href="#"><span class="ui-icon ui-icon-check"></span><span>'+b.checkAllText+'</span></a></li><li><a class="ui-multiselect-none" href="#"><span class="ui-icon ui-icon-closethick"></span><span>'+b.uncheckAllText+"</span></a></li>":"string"===typeof b.header?"<li>"+b.header+"</li>":""}).append('<li class="ui-multiselect-close"><a href="#" class="ui-multiselect-close"><span class="ui-icon ui-icon-circle-close"></span></a></li>').appendTo(c);(this.checkboxContainer=d("<ul />")).addClass("ui-multiselect-checkboxes ui-helper-reset").appendTo(a);this._bindEvents();this.refresh(!0);b.multiple||a.addClass("ui-multiselect-single")},_init:function(){!1===this.options.header&&this.header.hide();this.options.multiple||this.headerLinkContainer.find(".ui-multiselect-all, .ui-multiselect-none").hide();this.options.autoOpen&&this.open();this.element.is(":disabled")&&this.disable()},refresh:function(a){var b=this.element,c=this.options,f=this.menu,h=this.checkboxContainer,g=[],e="",i=b.attr("id")||k++;b.find("option").each(function(b){d(this);var a=this.parentNode,f=this.innerHTML,h=this.title,k=this.value,b="ui-multiselect-"+(this.id||i+"-option-"+b),l=this.disabled,n=this.selected,m=["ui-corner-all"],o=(l?"ui-multiselect-disabled ":" ")+this.className,j;"OPTGROUP"===a.tagName&&(j=a.getAttribute("label"),-1===d.inArray(j,g)&&(e+='<li class="ui-multiselect-optgroup-label '+a.className+'"><a href="#">'+j+"</a></li>",g.push(j)));l&&m.push("ui-state-disabled");n&&!c.multiple&&m.push("ui-state-active");e+='<li class="'+o+'">';e+='<label for="'+b+'" title="'+h+'" class="'+m.join(" ")+'">';e+='<input id="'+b+'" name="multiselect_'+i+'" type="'+(c.multiple?"checkbox":"radio")+'" value="'+k+'" title="'+f+'"';n&&(e+=' checked="checked"',e+=' aria-selected="true"');l&&(e+=' disabled="disabled"',e+=' aria-disabled="true"');e+=" /><span>"+f+"</span></label></li>"});h.html(e);this.labels=f.find("label");this.inputs=this.labels.children("input");this._setButtonWidth();this._setMenuWidth();this.button[0].defaultValue=this.update();a||this._trigger("refresh")},update:function(){var a=this.options,b=this.inputs,c=b.filter(":checked"),f=c.length,a=0===f?a.noneSelectedText:d.isFunction(a.selectedText)?a.selectedText.call(this,f,b.length,c.get()):/\d/.test(a.selectedList)&&0<a.selectedList&&f<=a.selectedList?c.map(function(){return d(this).next().html()}).get().join(", "):a.selectedText.replace("#",f).replace("#",b.length);this.buttonlabel.html(a);return a},_bindEvents:function(){function a(){b[b._isOpen? "close":"open"]();return!1}var b=this,c=this.button;c.find("span").bind("click.multiselect",a);c.bind({click:a,keypress:function(a){switch(a.which){case 27:case 38:case 37:b.close();break;case 39:case 40:b.open()}},mouseenter:function(){c.hasClass("ui-state-disabled")||d(this).addClass("ui-state-hover")},mouseleave:function(){d(this).removeClass("ui-state-hover")},focus:function(){c.hasClass("ui-state-disabled")||d(this).addClass("ui-state-focus")},blur:function(){d(this).removeClass("ui-state-focus")}});this.header.delegate("a","click.multiselect",function(a){if(d(this).hasClass("ui-multiselect-close"))b.close();else b[d(this).hasClass("ui-multiselect-all")?"checkAll":"uncheckAll"]();a.preventDefault()});this.menu.delegate("li.ui-multiselect-optgroup-label a","click.multiselect",function(a){a.preventDefault();var c=d(this),g=c.parent().nextUntil("li.ui-multiselect-optgroup-label").find("input:visible:not(:disabled)"),e=g.get(),c=c.parent().text();!1!==b._trigger("beforeoptgrouptoggle",a,{inputs:e,label:c})&&(b._toggleChecked(g.filter(":checked").length!==g.length,g),b._trigger("optgrouptoggle",a,{inputs:e,label:c,checked:e[0].checked}))}).delegate("label","mouseenter.multiselect",function(){d(this).hasClass("ui-state-disabled")||(b.labels.removeClass("ui-state-hover"),d(this).addClass("ui-state-hover").find("input").focus())}).delegate("label","keydown.multiselect",function(a){a.preventDefault();switch(a.which){case 9:case 27:b.close();break;case 38:case 40:case 37:case 39:b._traverse(a.which,this);break;case 13:d(this).find("input")[0].click()}}).delegate('input[type="checkbox"], input[type="radio"]',"click.multiselect",function(a){var c=d(this),g=this.value,e=this.checked,i=b.element.find("option");this.disabled||!1===b._trigger("click",a,{value:g,text:this.title,checked:e})?a.preventDefault():(c.focus(),c.attr("aria-selected",e),i.each(function(){this.value===g?this.selected=e:b.options.multiple||(this.selected=!1)}),b.options.multiple||(b.labels.removeClass("ui-state-active"),c.closest("label").toggleClass("ui-state-active",e),b.close()),b.element.trigger("change"),setTimeout(d.proxy(b.update,b),10))});d(document).bind("mousedown.multiselect",function(a){b._isOpen&&(!d.contains(b.menu[0],a.target)&&!d.contains(b.button[0],a.target)&&a.target!==b.button[0])&&b.close()});d(this.element[0].form).bind("reset.multiselect",function(){setTimeout(d.proxy(b.refresh,b),10)})},_setButtonWidth:function(){var a=this.element.outerWidth(),b=this.options;/\d/.test(b.minWidth)&&a<b.minWidth&&(a=b.minWidth);this.button.width(a)},_setMenuWidth:function(){var a=this.menu,b=this.button.outerWidth()-parseInt(a.css("padding-left"),10)-parseInt(a.css("padding-right"),10)-parseInt(a.css("border-right-width"),10)-parseInt(a.css("border-left-width"),10);a.width(b||this.button.outerWidth())},_traverse:function(a,b){var c=d(b),f=38===a||37===a,c=c.parent()[f?"prevAll":"nextAll"]("li:not(.ui-multiselect-disabled, .ui-multiselect-optgroup-label)")[f?"last":"first"]();c.length?c.find("label").trigger("mouseover"):(c=this.menu.find("ul").last(),this.menu.find("label")[f? "last":"first"]().trigger("mouseover"),c.scrollTop(f?c.height():0))},_toggleState:function(a,b){return function(){this.disabled||(this[a]=b);b?this.setAttribute("aria-selected",!0):this.removeAttribute("aria-selected")}},_toggleChecked:function(a,b){var c=b&&b.length?b:this.inputs,f=this;c.each(this._toggleState("checked",a));c.eq(0).focus();this.update();var h=c.map(function(){return this.value}).get();this.element.find("option").each(function(){!this.disabled&&-1<d.inArray(this.value,h)&&f._toggleState("selected",a).call(this)});c.length&&this.element.trigger("change")},_toggleDisabled:function(a){this.button.attr({disabled:a,"aria-disabled":a})[a?"addClass":"removeClass"]("ui-state-disabled");var b=this.menu.find("input"),b=a?b.filter(":enabled").data("ech-multiselect-disabled",!0):b.filter(function(){return!0===d.data(this,"ech-multiselect-disabled")}).removeData("ech-multiselect-disabled");b.attr({disabled:a,"arial-disabled":a}).parent()[a?"addClass":"removeClass"]("ui-state-disabled");this.element.attr({disabled:a,"aria-disabled":a})},open:function(){var a=this.button,b=this.menu,c=this.speed,f=this.options,h=[];if(!(!1===this._trigger("beforeopen")||a.hasClass("ui-state-disabled")||this._isOpen)){var g=b.find("ul").last(),e=f.show,i=a.offset();d.isArray(f.show)&&(e=f.show[0],c=f.show[1]||this.speed);e&&(h=[e,c]);g.scrollTop(0).height(f.height);d.ui.position&&!d.isEmptyObject(f.position)?(f.position.of=f.position.of||a,b.show().position(f.position).hide()):b.css({top:i.top+a.outerHeight(),left:i.left});d.fn.show.apply(b,h);this.labels.eq(0).trigger("mouseover").trigger("mouseenter").find("input").trigger("focus");a.addClass("ui-state-active");this._isOpen=!0;this._trigger("open")}},close:function(){if(!1!==this._trigger("beforeclose")){var a=this.options,b=a.hide,c=this.speed,f=[];d.isArray(a.hide)&&(b=a.hide[0],c=a.hide[1]||this.speed);b&&(f=[b,c]);d.fn.hide.apply(this.menu,f);this.button.removeClass("ui-state-active").trigger("blur").trigger("mouseleave");this._isOpen=!1;this._trigger("close")}},enable:function(){this._toggleDisabled(!1)},disable:function(){this._toggleDisabled(!0)},checkAll:function(){this._toggleChecked(!0);this._trigger("checkAll")},uncheckAll:function(){this._toggleChecked(!1);this._trigger("uncheckAll")},getChecked:function(){return this.menu.find("input").filter(":checked")},destroy:function(){d.Widget.prototype.destroy.call(this);this.button.remove();this.menu.remove();this.element.show();return this},isOpen:function(){return this._isOpen},widget:function(){return this.menu},getButton:function(){return this.button},_setOption:function(a,b){var c=this.menu;switch(a){case "header":c.find("div.ui-multiselect-header")[b?"show":"hide"]();break;case "checkAllText":c.find("a.ui-multiselect-all span").eq(-1).text(b);break;case "uncheckAllText":c.find("a.ui-multiselect-none span").eq(-1).text(b);break;case "height":c.find("ul").last().height(parseInt(b,10));break;case "minWidth":this.options[a]=parseInt(b,10);this._setButtonWidth();this._setMenuWidth();break;case "selectedText":case "selectedList":case "noneSelectedText":this.options[a]=b;this.update();break;case "classes":c.add(this.button).removeClass(this.options.classes).addClass(b);break;case "multiple":c.toggleClass("ui-multiselect-single",!b),this.options.multiple=b,this.element[0].multiple=b,this.refresh()}d.Widget.prototype._setOption.apply(this,arguments)}})})(jQuery);

/*! case insensitive :contains selector, see http://stackoverflow.com/questions/2196641/ */
jQuery.expr[":"].icontains = jQuery.expr.createPseudo(function(arg) {
	return function( elem ) {
		return jQuery(elem).text().toUpperCase().indexOf(arg.toUpperCase()) >= 0;
	};
});

var remove_doublettes = function(arr) {
	var uniques = [];
	for (var i = 0; i < arr.length; i++)
		if($.inArray(arr[i], uniques)===-1)
			uniques.push(arr[i]);
	return uniques;
}

var handle_filter_input = function(a1, a2) {
	$("tbody tr").hide(); // hide all rows first
	var value = $("input[name=filter]").val();
	// handle OR, |, ||
	var alternatives = value.split(/ or | \|\| | \| /);
	//console.log(alternatives);
	if (alternatives.length > 1) {
		for (var i = 0; i < alternatives.length; i++)
			process_filter(alternatives[i]);
	} else {
		process_filter(value);
	}
}

var process_filter = function(value) {
	// handle AND, &, &&, whitespace
	value = value.replace(/and|\&/g, " ");
	var conditions = value.split(/\W+/);
	//console.log(conditions);
	var selectorstr = "tbody tr td";
	switch($("#searchbox_option").find('option:selected').val()) {
		case "tagcloud":
			selectorstr += ".tagcloud"
			break;
	}
	for (var i = 0; i < conditions.length; i++)
		if (conditions[i].length > 0)
			selectorstr += ":icontains('" + conditions[i] + "')";
	//console.log(selectorstr);
	$(selectorstr).parent().show();
}

$(document).ready(function() {

	/* Setup Tag Search */

	var lookup_list = [];
	$("td.tagcloud").each(function(index) {
		$.merge(lookup_list, $(this).text().split(", "));
	});
	lookup_list = remove_doublettes(lookup_list);
	//console.log(lookup_list);
	$("input[name=filter]").autocomplete({
		onSelect: handle_filter_input,
		width: $("input[name=filter]").width(),
		maxHeight:400,
		lookup: lookup_list
	});
	$('input[name=filter]').keydown(function(event) {
		if (event.which == 13) {
			event.preventDefault();
			handle_filter_input();
		}
	});
	$('input[name=filter]').typing({
		stop: handle_filter_input,
		delay: 400
	});
	$("#searchbox_option").change(
		handle_filter_input);

	/* Setup Minified Question Base */

	$("#selected_category").change(function() {
		$("#change_category").click();
	});
	$("#target_category").change(function() {
		$("#move_question").click();
	});
	$("#change_category").hide();
	$("#move_question").hide();
    
    /* Selection of table-columns */
    $("#column_select")
   .multiselect({
      selectedList: 6,
      header: false,
      noneSelectedText: 'Tabelleninformationen auswählen',
      selectedText: '# Spalten ausgewählt',
      close: function(){
          $('#form_column_select').submit();
       },

   });
   $("#search_select").multiselect({
     selectedList: 2,
     noneSelectedText: 'in allen Tabellenspalten',
     checkAllText: 'alle',
     uncheckAllText: 'keine',
     selectedText: 'in # ausgewählten Spalten',
      header: true,
   });
   $( "input[type=submit]" ).button();
   $( document ).tooltip();
});
