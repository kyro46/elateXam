<style type="text/css">
.ui-state-highlight { height: 1.5em; line-height: 1.2em; }
</style>
<script type="text/javascript">
/*jQuery MultiSelect UI Widget 1.13 | Copyright (c) 2012 Eric Hynds | http://www.erichynds.com/jquery/jquery-ui-multiselect-widget/ | Dual licensed under the MIT and GPL licenses: | http://www.opensource.org/licenses/mit-license.php | http://www.gnu.org/licenses/gpl.html */
(function(d){var k=0;d.widget("ech.multiselect",{options:{header:!0,height:175,minWidth:225,classes:"",checkAllText:"Check all",uncheckAllText:"Uncheck all",noneSelectedText:"Select options",selectedText:"# selected",selectedList:0,show:null,hide:null,autoOpen:!1,multiple:!0,position:{}},_create:function(){var a=this.element.hide(),b=this.options;this.speed=d.fx.speeds._default;this._isOpen=!1;a=(this.button=d('<button type="button"><span class="ui-icon ui-icon-triangle-2-n-s"></span></button>')).addClass("ui-multiselect ui-widget ui-state-default ui-corner-all").addClass(b.classes).attr({title:a.attr("title"),"aria-haspopup":!0,tabIndex:a.attr("tabIndex")}).insertAfter(a);(this.buttonlabel=d("<span />")).html(b.noneSelectedText).appendTo(a);var a=(this.menu=d("<div />")).addClass("ui-multiselect-menu ui-widget ui-widget-content ui-corner-all").addClass(b.classes).appendTo(document.body),c=(this.header=d("<div />")).addClass("ui-widget-header ui-corner-all ui-multiselect-header ui-helper-clearfix").appendTo(a);(this.headerLinkContainer=d("<ul />")).addClass("ui-helper-reset").html(function(){return!0===b.header?'<li><a class="ui-multiselect-all" href="#"><span class="ui-icon ui-icon-check"></span><span>'+b.checkAllText+'</span></a></li><li><a class="ui-multiselect-none" href="#"><span class="ui-icon ui-icon-closethick"></span><span>'+b.uncheckAllText+"</span></a></li>":"string"===typeof b.header?"<li>"+b.header+"</li>":""}).append('<li class="ui-multiselect-close"><a href="#" class="ui-multiselect-close"><span class="ui-icon ui-icon-circle-close"></span></a></li>').appendTo(c);(this.checkboxContainer=d("<ul />")).addClass("ui-multiselect-checkboxes ui-helper-reset").appendTo(a);this._bindEvents();this.refresh(!0);b.multiple||a.addClass("ui-multiselect-single")},_init:function(){!1===this.options.header&&this.header.hide();this.options.multiple||this.headerLinkContainer.find(".ui-multiselect-all, .ui-multiselect-none").hide();this.options.autoOpen&&this.open();this.element.is(":disabled")&&this.disable()},refresh:function(a){var b=this.element,c=this.options,f=this.menu,h=this.checkboxContainer,g=[],e="",i=b.attr("id")||k++;b.find("option").each(function(b){d(this);var a=this.parentNode,f=this.innerHTML,h=this.title,k=this.value,b="ui-multiselect-"+(this.id||i+"-option-"+b),l=this.disabled,n=this.selected,m=["ui-corner-all"],o=(l?"ui-multiselect-disabled ":" ")+this.className,j;"OPTGROUP"===a.tagName&&(j=a.getAttribute("label"),-1===d.inArray(j,g)&&(e+='<li class="ui-multiselect-optgroup-label '+a.className+'"><a href="#">'+j+"</a></li>",g.push(j)));l&&m.push("ui-state-disabled");n&&!c.multiple&&m.push("ui-state-active");e+='<li class="'+o+'">';e+='<label for="'+b+'" title="'+h+'" class="'+m.join(" ")+'">';e+='<input id="'+b+'" name="multiselect_'+i+'" type="'+(c.multiple?"checkbox":"radio")+'" value="'+k+'" title="'+f+'"';n&&(e+=' checked="checked"',e+=' aria-selected="true"');l&&(e+=' disabled="disabled"',e+=' aria-disabled="true"');e+=" /><span>"+f+"</span></label></li>"});h.html(e);this.labels=f.find("label");this.inputs=this.labels.children("input");this._setButtonWidth();this._setMenuWidth();this.button[0].defaultValue=this.update();a||this._trigger("refresh")},update:function(){var a=this.options,b=this.inputs,c=b.filter(":checked"),f=c.length,a=0===f?a.noneSelectedText:d.isFunction(a.selectedText)?a.selectedText.call(this,f,b.length,c.get()):/\d/.test(a.selectedList)&&0<a.selectedList&&f<=a.selectedList?c.map(function(){return d(this).next().html()}).get().join(", "):a.selectedText.replace("#",f).replace("#",b.length);this.buttonlabel.html(a);return a},_bindEvents:function(){function a(){b[b._isOpen? "close":"open"]();return!1}var b=this,c=this.button;c.find("span").bind("click.multiselect",a);c.bind({click:a,keypress:function(a){switch(a.which){case 27:case 38:case 37:b.close();break;case 39:case 40:b.open()}},mouseenter:function(){c.hasClass("ui-state-disabled")||d(this).addClass("ui-state-hover")},mouseleave:function(){d(this).removeClass("ui-state-hover")},focus:function(){c.hasClass("ui-state-disabled")||d(this).addClass("ui-state-focus")},blur:function(){d(this).removeClass("ui-state-focus")}});this.header.delegate("a","click.multiselect",function(a){if(d(this).hasClass("ui-multiselect-close"))b.close();else b[d(this).hasClass("ui-multiselect-all")?"checkAll":"uncheckAll"]();a.preventDefault()});this.menu.delegate("li.ui-multiselect-optgroup-label a","click.multiselect",function(a){a.preventDefault();var c=d(this),g=c.parent().nextUntil("li.ui-multiselect-optgroup-label").find("input:visible:not(:disabled)"),e=g.get(),c=c.parent().text();!1!==b._trigger("beforeoptgrouptoggle",a,{inputs:e,label:c})&&(b._toggleChecked(g.filter(":checked").length!==g.length,g),b._trigger("optgrouptoggle",a,{inputs:e,label:c,checked:e[0].checked}))}).delegate("label","mouseenter.multiselect",function(){d(this).hasClass("ui-state-disabled")||(b.labels.removeClass("ui-state-hover"),d(this).addClass("ui-state-hover").find("input").focus())}).delegate("label","keydown.multiselect",function(a){a.preventDefault();switch(a.which){case 9:case 27:b.close();break;case 38:case 40:case 37:case 39:b._traverse(a.which,this);break;case 13:d(this).find("input")[0].click()}}).delegate('input[type="checkbox"], input[type="radio"]',"click.multiselect",function(a){var c=d(this),g=this.value,e=this.checked,i=b.element.find("option");this.disabled||!1===b._trigger("click",a,{value:g,text:this.title,checked:e})?a.preventDefault():(c.focus(),c.attr("aria-selected",e),i.each(function(){this.value===g?this.selected=e:b.options.multiple||(this.selected=!1)}),b.options.multiple||(b.labels.removeClass("ui-state-active"),c.closest("label").toggleClass("ui-state-active",e),b.close()),b.element.trigger("change"),setTimeout(d.proxy(b.update,b),10))});d(document).bind("mousedown.multiselect",function(a){b._isOpen&&(!d.contains(b.menu[0],a.target)&&!d.contains(b.button[0],a.target)&&a.target!==b.button[0])&&b.close()});d(this.element[0].form).bind("reset.multiselect",function(){setTimeout(d.proxy(b.refresh,b),10)})},_setButtonWidth:function(){var a=this.element.outerWidth(),b=this.options;/\d/.test(b.minWidth)&&a<b.minWidth&&(a=b.minWidth);this.button.width(a)},_setMenuWidth:function(){var a=this.menu,b=this.button.outerWidth()-parseInt(a.css("padding-left"),10)-parseInt(a.css("padding-right"),10)-parseInt(a.css("border-right-width"),10)-parseInt(a.css("border-left-width"),10);a.width(b||this.button.outerWidth())},_traverse:function(a,b){var c=d(b),f=38===a||37===a,c=c.parent()[f?"prevAll":"nextAll"]("li:not(.ui-multiselect-disabled, .ui-multiselect-optgroup-label)")[f?"last":"first"]();c.length?c.find("label").trigger("mouseover"):(c=this.menu.find("ul").last(),this.menu.find("label")[f? "last":"first"]().trigger("mouseover"),c.scrollTop(f?c.height():0))},_toggleState:function(a,b){return function(){this.disabled||(this[a]=b);b?this.setAttribute("aria-selected",!0):this.removeAttribute("aria-selected")}},_toggleChecked:function(a,b){var c=b&&b.length?b:this.inputs,f=this;c.each(this._toggleState("checked",a));c.eq(0).focus();this.update();var h=c.map(function(){return this.value}).get();this.element.find("option").each(function(){!this.disabled&&-1<d.inArray(this.value,h)&&f._toggleState("selected",a).call(this)});c.length&&this.element.trigger("change")},_toggleDisabled:function(a){this.button.attr({disabled:a,"aria-disabled":a})[a?"addClass":"removeClass"]("ui-state-disabled");var b=this.menu.find("input"),b=a?b.filter(":enabled").data("ech-multiselect-disabled",!0):b.filter(function(){return!0===d.data(this,"ech-multiselect-disabled")}).removeData("ech-multiselect-disabled");b.attr({disabled:a,"arial-disabled":a}).parent()[a?"addClass":"removeClass"]("ui-state-disabled");this.element.attr({disabled:a,"aria-disabled":a})},open:function(){var a=this.button,b=this.menu,c=this.speed,f=this.options,h=[];if(!(!1===this._trigger("beforeopen")||a.hasClass("ui-state-disabled")||this._isOpen)){var g=b.find("ul").last(),e=f.show,i=a.offset();d.isArray(f.show)&&(e=f.show[0],c=f.show[1]||this.speed);e&&(h=[e,c]);g.scrollTop(0).height(f.height);d.ui.position&&!d.isEmptyObject(f.position)?(f.position.of=f.position.of||a,b.show().position(f.position).hide()):b.css({top:i.top+a.outerHeight(),left:i.left});d.fn.show.apply(b,h);this.labels.eq(0).trigger("mouseover").trigger("mouseenter").find("input").trigger("focus");a.addClass("ui-state-active");this._isOpen=!0;this._trigger("open")}},close:function(){if(!1!==this._trigger("beforeclose")){var a=this.options,b=a.hide,c=this.speed,f=[];d.isArray(a.hide)&&(b=a.hide[0],c=a.hide[1]||this.speed);b&&(f=[b,c]);d.fn.hide.apply(this.menu,f);this.button.removeClass("ui-state-active").trigger("blur").trigger("mouseleave");this._isOpen=!1;this._trigger("close")}},enable:function(){this._toggleDisabled(!1)},disable:function(){this._toggleDisabled(!0)},checkAll:function(){this._toggleChecked(!0);this._trigger("checkAll")},uncheckAll:function(){this._toggleChecked(!1);this._trigger("uncheckAll")},getChecked:function(){return this.menu.find("input").filter(":checked")},destroy:function(){d.Widget.prototype.destroy.call(this);this.button.remove();this.menu.remove();this.element.show();return this},isOpen:function(){return this._isOpen},widget:function(){return this.menu},getButton:function(){return this.button},_setOption:function(a,b){var c=this.menu;switch(a){case "header":c.find("div.ui-multiselect-header")[b?"show":"hide"]();break;case "checkAllText":c.find("a.ui-multiselect-all span").eq(-1).text(b);break;case "uncheckAllText":c.find("a.ui-multiselect-none span").eq(-1).text(b);break;case "height":c.find("ul").last().height(parseInt(b,10));break;case "minWidth":this.options[a]=parseInt(b,10);this._setButtonWidth();this._setMenuWidth();break;case "selectedText":case "selectedList":case "noneSelectedText":this.options[a]=b;this.update();break;case "classes":c.add(this.button).removeClass(this.options.classes).addClass(b);break;case "multiple":c.toggleClass("ui-multiselect-single",!b),this.options.multiple=b,this.element[0].multiple=b,this.refresh()}d.Widget.prototype._setOption.apply(this,arguments)}})})(jQuery);
// id counter for the created Categories
var catid_counter = 0;
// poisition of mouse cursor in some situations (scrolling)
var mousepos;
// currently shown questions
var currQuestions   = {};
//document ready actions
$(function(){
    //hold session  
    setInterval(function(){
       $.get('<?php echo $_SERVER['PHP_SELF'] ?>?id=<?php echo $course->id ?>&alive=1');
    }, 840000);//14min
    //create first category
    $('.examcontainer').addClass('addCat');
    createCategory('Klausur',1, false);
    
    $( "#cattype" ).buttonset();
    $( ".question_button" ).button();
    $('#catname').keypress(function(event) {//prevent submit on
        if (event.keyCode == 13) {
            event.preventDefault();
        }
    })
});

function createCategoryButton(newcat_trigger) {
    $(newcat_trigger).parent().parent().addClass('addCat');
    $('#category-dialog-form').dialog('open');
}
function createCategory(name, type, drag, catid, num_shown, shuffle) {
    if (catid == null) {
        catid = catid_counter++;
    }
    if (num_shown == null) {
        num_shown = 3;
    }
    if (shuffle) {
        shuffle = 'checked="true"';
    }
    var new_cat = $('<div class="exam_cat" id="cat'+ catid +'"></div>');
    new_cat.data('points',0);
    new_cat.data('type',type);
    var catPoints = 'Punkte: <span id="cat'+ catid +'_point_sum">0.0</span>';
    if (type == 2) {
        new_cat.addClass('catchoice');
        catPoints = '<i>Aufgaben: <input id="cat'+ catid +'_quest_count" class="jquiSpinner" value="'+num_shown+'" /></i> ' + catPoints;
        new_cat.data('qtype', '');
        new_cat.data('num_shown', num_shown);
    }
    new_cat.mouseover(
        function(e) {
            e.stopPropagation();
            $(this).css('background-color', '#EEEEEE');
        }).mouseout(
        function() {
            $(this).css('background-color', 'white');
        });
    new_cat.css('background-color', 'white');
    if (drag) {
        catPoints = '<label><i title="zufällige Reihenfolge der Aufgaben">zufällig: <input type="checkbox" id="cat'+ catid +'_random" '+shuffle+'/></i></label>'+catPoints;
        /*new_cat.draggable({
            handle: "p",
            revert: "invalid",
            iframeFix: true,
            cursor: "move"
            /*,stack: "div" //necessary but creates bugs if you use dialog */ 
        //});*/
        new_cat.droppable({
            greedy: true,
            tolerance: "pointer",
            activeClass: "ui-state-default",
            hoverClass: "ui-state-hover",
            drop: function( event, ui ) {
                var deny = false;
                var category = $(this);
                if (category.data('type') == 2) {
                    if (category.data('qtype') != '' && (category.data('qtype') != ui.draggable.data('qtype') || category.data('points') != ui.draggable.data('points') ) ) {
                        deny = true;
                        alert('Es können nur Aufgaben mit der gleichen Punktzahl und dem gleichen Aufgabentyp zu einer Aufgabenkategorie hinzugefügt werden.');
                    }
                }
                if (!deny) {
                    var tplPoints = ui.draggable.data('points');
                    if (ui.draggable.parent().attr('id') != category.attr('id')) {
                        category.append(ui.draggable);
                        calculate();
                    }
                }
                ui.draggable.animate({left: 0, top: 0});
                ui.draggable.css({backgroundColor: ''});
            }
        });
    }
    
    var cathandle='', catdel = '', addquestion = '', createCategoryButton = '';
    if (drag) {
        //cathandle = '<p class="drag_handle" title="in andere Kategorie verschieben"><span class="ui-icon ui-icon-arrow-4"></span></p>'+'<span class="exam_points">'+catPoints+'</span>';
        cathandle = '<span class="exam_points">'+catPoints+'</span>'; 
        if (type == 2) {
            catdel = '<span class="ui-icon ui-icon-info" title="Bei einer Auswahlkategorie wird in der Prüfung aus mehreren hinterlegten Fragen die angegebene Anzahl zufällig ausgewählt. In einer Auswahlkategorie sind nur Fragen mit gleicher Punktzahl und dem selben Fragentyp möglich."></span> ';
            new_cat.droppable( "option", "accept", ".exam_quest" );
        }
        addquestion = '<span onclick="addQuestions(this)" title="Aufgaben zu dieser Kategorie hinzufügen"><span class="ui-icon ui-icon-document"></span></span>';
        catdel += '<span class="ui-icon ui-icon-trash" title="Kategorie löschen" onclick="if(confirm(\'Wollen Sie diese Kategorie mit allen Unterkategorien und Aufgaben wirklich löschen?\')){$(this).closest(\'div\').slideUp(\'normal\', function() { removeElement($(this)); } );}"></span>';           
    } else {
        createCategoryButton = '<span onclick="createCategoryButton(this)" title="neue Kategorie erstellen"><span class="ui-icon ui-icon-folder-open"></span></span>';
        new_cat.css('position','relative');
        new_cat.mousedown(function(e){
    		mousepos = (e.pageY - $(this).parent().offset().top-2);
    	});
        cathandle = '<span class="exam_points"><strong>Gesamtpunktanzahl: <span id="cat'+ catid +'_point_sum">0.0</span></strong></span>';
    }
    
    new_cat.html( '<span class="cat_name">'+name+
                  '</span><span class="cat_control">'+catdel+
                  createCategoryButton+
                  addquestion+
                  '</span>'+cathandle+
                  '<hr style="clear:both" />');
    if (type == 2) {
        $('#cat'+ catid +'_quest_count').spinner({
            min: 1,
            numberFormat: "n0",
            stop: function() {
                new_cat.data('num_shown',$(this).val());
                calculate();
            }
        });
    }
    new_cat.tooltip({
        track: true
    });
    $('.addCat').append(new_cat);
    $('.addCat').removeClass('addCat');
    $( '#'+new_cat.attr('id')).sortable({
            items: "div",
            placeholder: "ui-state-highlight",
            forcePlaceholderSize: true,
            start: function( event, ui ) {//iframefix
                var iframeParent = $('#id_description_tbl').parent();
                iframeParent.css('position','relative');
                iframeParent.prepend(
                    '<div id="id_description_ifr_overlay" style="position:absolute; top:0; left:0; z-index: 1000;width:'+iframeParent.width()+'px; height:'+(iframeParent.height()+50)+'px;"></div>');
                iframeParent = $('#id_starttext_tbl').parent();
                iframeParent.css('position','relative');
                iframeParent.prepend(
                    '<div id="id_starttext_ifr_overlay" style="position:absolute; top:0; left:0; z-index: 1000;width:'+iframeParent.width()+'px; height:'+(iframeParent.height()+50)+'px;"></div>');
            },
            stop: function( event, ui ) {//iframefix
                $('#id_description_ifr_overlay').remove();
                $('#id_starttext_ifr_overlay').remove();
            } 
        });
}
function createQuestion(curQuest, existing, addid) {
    //console.log(curQuest);
    var new_quest = $('<div class="exam_quest"></div>');
    new_quest.data('points', curQuest['points']);
    new_quest.data('qtype', curQuest['qtype']);
    new_quest.data('qid', curQuest['qid']);
    if (existing != null) {
        if (existing == 0) {
            curQuest['name'] = '<span class="ui-state-error msgblock" title="'+curQuest['name']+'"><span class="ui-icon ui-icon-alert msgblock"></span>'+curQuest['name']+'</span>';
        }
        if (existing == 2) {
            curQuest['name'] = '<span class="ui-state-highlight msgblock" title="Die Frage wurde seit dem geladenen Klausur-Export verändert."><span class="ui-icon ui-icon-info msgblock"></span>'+curQuest['name']+'</span>';
        }
    }
    new_quest.html('<img src="<?php echo $CFG->wwwroot ?>/theme/image.php?theme=standard&component=qtype_'+curQuest['qtype']+'&image=icon" title="'+curQuest['qtypelocal']+'" />'+
                curQuest['name']+
                '<span class="quest_control"><span class="ui-icon ui-icon-trash" title="Aufgabe entfernen"  onclick="if(confirm(\'Wollen Sie diese Aufgabe wirklich löschen?\')){$(this).closest(\'div\').slideUp(\'normal\', function() { removeElement($(this)); } );}"></span>'+
                '<p class="drag_handle" title="in andere Kategorie verschieben"><span class="ui-icon ui-icon-arrow-4"></span></p></span>'+
                '<span class="exam_points">'+Number(curQuest['points']).toFixed(1)+'</span>');
    new_quest.draggable({
            handle: "p",
            revert: "invalid",
            iframeFix: true,
            cursor: "move"
            /*,stack: "div" //necessary but creates bugs if you use dialog */
        });
    var category = $('.addHere');
    if (addid != null) {
        category = $('#cat'+addid);
    }
    category.append(new_quest);
    
    if (category.data('type') == 2) {
        if (category.data('qtype') == '' && existing != 0) {
            category.data('qtype', curQuest['qtype']);
            category.data('points', curQuest['points']);
        }
    }
}
function removeElement(element) {
    element.remove();
    calculate();
}
function closeQDialog(){
    $('#questions_dialog').hide('normal');
    $('.addHere').removeClass('addHere');
}
function addQuestions(questButton){
    if ($(questButton).closest('div').hasClass('addHere')) {
        closeQDialog();
    } else {
        $(".ui-selected").removeClass('ui-selected');
        $('.addHere').removeClass('addHere');
        $('#questions_dialog').css('top', mousepos - $('#questions_dialog').height()/2);
        $('#questions_dialog').show('normal');
        $(questButton).closest('div').addClass('addHere');
        $('html, body').animate({
            scrollTop: $("#questions_dialog").offset().top
        }, 600);
    }    
}
function addQuestion(){
    var catDif = 0;
    $(".ui-selected").each( function () {
        var question = $(this);
        var questionID = parseInt(question.attr('id').substr(5));
        var category = $('.addHere');
        var deny = false;
        if (category.data('type') == 2) {
            if (category.data('qtype') != '' && (category.data('qtype') != currQuestions[questionID]['qtype'] || category.data('points') != currQuestions[questionID]['points'] ) ) {
                deny = true;
                alert('Es können nur Aufgaben mit der gleichen Punktzahl und dem gleichen Aufgabentyp zu einer Aufgabenkategorie hinzugefügt werden.');
            }
        }
        if (!deny) {
            createQuestion(currQuestions[questionID]);
        }
    });
    calculate();
}

function calculate(pCat){
    if (pCat == null) {
        pCat = $('#cat0');
    }
    var sum = 0;
    if (pCat.data('type') == 2) {
        pCat.children('.exam_quest').each( function(){ sum += parseFloat($(this).data('points')); });
        if (sum > parseInt(pCat.data('num_shown')) * parseFloat(pCat.data('points'))) {
            sum = parseInt(pCat.data('num_shown')) * parseFloat(pCat.data('points'));
        }
    } else {
        pCat.children('.exam_quest').each( function(){ sum += parseFloat($(this).data('points')); });
        pCat.children('.exam_cat').each( function(){sum += calculate($(this)); });
        
    }
    $('#'+pCat.attr('id')+'_point_sum').html(sum.toFixed(1));
    return sum;
}
function resetStructure(){
    catid_counter = 0;
    $('.examcontainer').empty();
    closeQDialog();
}
function setStructure(option){
    resetStructure();
    var exportTime = parseInt($(option).val());
    var importStructure = exports[exportTime];
    var elements = importStructure.split(';');
    var questcount = 0;
    $.each(elements, function() {
        var element = this.split('|');
        if (element[0] == 'c') {
            if (catid_counter < element[1]) {
                catid_counter = element[1];
            }            
            //createCategory();
            $('#cat'+element[2]).addClass('addCat');
            var catType = 1;
            if (element[4] == 'choice') {
                catType = 2;
            }
            var drag = true;
            if (element[1] == 0) {
                drag = false;
                $('.examcontainer').addClass('addCat');
            }
            var shuffle = true;
            if (element[6] == 1) {
                shuffle = false;
            }
            createCategory(element[3], catType, drag, element[1], element[5], shuffle);
            catid_counter++;            
        }
        if (element[0] == 'q' && element[1] >= 0) {
            questcount++;
            $.ajax({
                url: '<?php echo $CFG->wwwroot ?>/course/format/elatexam/exam_ajax.php',			
                data: {task: 'getquestion', qid: element[1]},
                dataType: 'xml',
                cache: false,
                success: function (xmlResp) {
                    var xmlNode = $(xmlResp).find('question');
                    if (xmlNode) {
                        var exists = 1;
                        if (exportTime < xmlNode.attr('timemodified')) {
                            exists = 2;
                        }
                        createQuestion({qid: element[1],
                                        name: xmlNode.text(),
                                        points: xmlNode.attr('points'),
                                        qtype: xmlNode.attr('qtype'),
                                        qtypelocal: xmlNode.attr('qtypelocal')
                                        }, exists, element[2]);
                    } else {
                        createQuestion({qid: element[1],
                                        name: 'Die Frage mit der ID '+element[1]+' existiert nicht mehr.',
                                        points: 0.0,
                                        qtype: '',
                                        qtypelocal: ''
                                        }, 0, element[2]);
                    }
                    if (--questcount == 0) {
                        calculate();
                    }
                }
            });
        }
    });
}
<?php if (count($xam_exports)) { ?>
    <?php $tplDate = new DateTime(); ?>
    var exports = {
    <?php foreach ($xam_exports as $export) { ?>
        <?php echo $export->export_time ?> : "<?php echo $export->structure ?>",
    <?php } ?>
    }
    $(function(){setStructure($('#structselect option:first'));});
<?php } ?>
</script>

<div class="questionbankwindow boxwidthwide boxaligncenter">
    <h2 class="xams_top">Klausur bearbeiten</h2>
    <div>
        <a href="<?php echo $_SERVER['PHP_SELF'] ?>?id=<?php echo $course->id ?>"><button>zurück zu den Klausurgruppen</button></a>
    </div>
    <?php if (count($xam_exports)) { ?>
    <div style="float: right;">
        Exportierte Klausur laden:
            <select id="structselect" onchange="setStructure(this)">
            <?php foreach ($xam_exports as $export) { ?>
                <option value="<?php echo $export->export_time ?>">exportiert am: <?php echo date_format($tplDate->setTimestamp($export->export_time), 'Y-m-d H:i') ?></option>
            <?php } ?>
            </select>
    </div>
    <?php } ?>
    <div style="clear: both;"></div>
        <?php $mform->display(); ?>
</div>

<div id="category-dialog-form" title="Neue Kategorie erstellen">
    <p class="validateTips">Eine Bezeichnung für die Kategorie mit mindestens einem Buchstaben wird benötigt.</p>
 
    <form onsubmit="return false">
    <fieldset>
        <label for="catname">Bezeichnung</label>
        <input type="text" name="catname" id="catname" class="text ui-widget-content ui-corner-all" onkeydown="" />
        <div id="cattype" class="ui-buttonset">
            <input id="cattype1" class="ui-helper-hidden-accessible" type="radio" checked="checked" name="cattype" value="1" />
            <label class="ui-state-active ui-button ui-widget ui-state-default ui-button-text-only" for="cattype1" role="button" aria-disabled="false">
                Standardkategorie
            </label>
            <input id="cattype2" class="ui-helper-hidden-accessible" type="radio" name="cattype" value="2" />
            <label class="ui-button ui-widget ui-state-default ui-button-text-only ui-corner-left" for="cattype2" role="button" aria-disabled="false"
                title="Bei einer Auswahlkategorie wird in der Prüfung aus mehreren hinterlegten Fragen die angegebene Anzahl zufällig ausgewählt. In einer Auswahlkategorie sind nur Fragen mit gleicher Punktzahl und dem selben Fragentyp möglich.">
                Auswahlkategorie
            </label>
        </div>        
    </fieldset>
    </form>
</div>

<div id="questions_dialog">
    <div class="question_category_block">
        <?php echo html_writer::select($selectable_categories, 'category_select[]', null, false, array('multiple' => 'true', 'id' => 'category_select')); ?>
        <span style="float: right; display: none;" id="questionspinner"><img src="<?php echo $CFG->wwwroot ?>/pix/i/loading.gif" width="30" /></span>
    </div>
    <div class="question_search_block">
        <input id="quest_search" />
        <?php echo html_writer::select($selectable_cols, 'search_select[]', null/*$SESSION->search_columns*/, false, array('multiple' => 'true', 'id' => 'search_select')); ?>
        <button type="button" class="table-search-link ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only" role="button" aria-disabled="false" title="Suchen" onclick="changeCategories()">
            <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
        </button>
    </div>
    <div class="selectable_questions">
        <ol id="quest_list">
        </ol>
    </div>
    <div id="add_questions">
        <span class="question_button" onclick="addQuestion(); return false;">ausgewählte Fragen hinzufügen</span>
        <span class="question_button" onclick="closeQDialog(); return false;">Fertig</span>
    </div>
</div>
<script type="text/javascript">
var _selectRange = false, _deselectQueue = [];//used by selectable without ctrl
/**
 * category dialog-box
 */
$(function() {
    var catname = $( "#catname" ),
        //cattype = $( "#cattype" ),
        allFields = $( [] ).add( catname ),
        tips = $( ".validateTips" );

    function updateTips( t ) {
        tips
            .text( t )
            .addClass( "ui-state-highlight" );
        setTimeout(function() {
            tips.removeClass( "ui-state-highlight", 1500 );
        }, 500 );
    }

    function checkLength( o, min ) {
        if ( o.val().length < min ) {
            o.addClass( "ui-state-error" );
            updateTips( "Die Bezeichnung für eine Kategorie muss mindestens 1 Zeichen lang sein." );
            return false;
        } else {
            return true;
        }
    }

    function checkRegexp( o, regexp, n ) {
        if ( ( regexp.test( o.val() ) ) ) {
            o.addClass( "ui-state-error" );
            updateTips( n );
            return false;
        } else {
            return true;
        }
    }

    $( "#category-dialog-form" ).dialog({
        autoOpen: false,
        draggable: false,
        height: 240,
        width: 360,
        modal: true,
        buttons: {
            "Kategorie erstellen": function() {
                var bValid = true;
                allFields.removeClass( "ui-state-error" );

                bValid = bValid && checkLength( catname, 1) && checkRegexp(catname, /(\||;)/, 'Der Kategoriename darf weder ; noch | enthalten.');
                if ( bValid ) {
                    createCategory(catname.val(),$( "input[name=cattype]:checked" ).val(),true,null,null,$( "input[name=cattype]:checked" ).val() == 2);                        
                    $( this ).dialog( "close" );
                }
            },
            "Abbrechen": function() {
                $( this ).dialog( "close" );
            }
        },
        open: function() {
            $("#category-dialog-form").keypress(function(e) {
                if (e.keyCode == $.ui.keyCode.ENTER) {
                    $(this).parent().find("button:eq(0)").trigger("click");
                }
            });
        },
        close: function() {
            allFields.val( "" ).removeClass( "ui-state-error" );
        }
    });
    //Multiselect --> custom search
    $("#search_select").multiselect({
     selectedList: 2,
     noneSelectedText: 'Suche eingrenzen',
     checkAllText: 'alle',
     uncheckAllText: 'keine',
     selectedText: '# ausgewählt',
     header: true,
   });
   //Multiselect --> question categories
   $("#category_select").multiselect({
     selectedList: 0,
     noneSelectedText: 'Kategorien von Fragen auswählen',
     checkAllText: 'alle',
     uncheckAllText: 'keine',
     selectedText: '# Kategorien ausgewählt',
     header: true,
     close: changeCategories
   });
    var availableTags = [
            ""
        ];
    $( "#quest_search" ).autocomplete({
        source: availableTags
    });
    $( "#category-dialog-form" ).tooltip();
    //question as selectable
    $( "#quest_list" ).selectable({//change of the main functions to get the multiple selection without ctrl
        selecting: function (event, ui) {
            if (event.detail == 0) {
                _selectRange = true;
                return true;
            }
            if ($(ui.selecting).hasClass('ui-selected')) {
                _deselectQueue.push(ui.selecting);
            }
        },
        unselecting: function (event, ui) {
            $(ui.unselecting).addClass('ui-selected');
        },
        stop: function () {
            if (!_selectRange) {
                $.each(_deselectQueue, function (ix, de) {
                    $(de)
                        .removeClass('ui-selecting')
                        .removeClass('ui-selected');
                });
            }
            _selectRange = false;
            _deselectQueue = [];
        }
    });
    $( "#questions_dialog").appendTo($(".exam_side").parent());
    $('#questions_dialog').hide('normal');
    /*$( "#pointsSpinner" ).spinner({
        min: 0,
        max: 5,
        numberFormat: "n0"
    });*/
    $('#mform1').on('submit',function (){
        //DB-Structure
        //serialise the structure
        if ($('#cat0').children('div').length) {
            $('input[name=examdata]').val((getCatAndChildren($('#cat0'),0)));
            //console.log($('input[name=examdata]').val());
        }
        //return false;
    })
});
/**
  * function to get the structure-string from category
  */
function getCatAndChildren(parentCat,parentId) {
    var tplCatid = parentCat.attr('id').replace(/cat/g, "");
    var tplStructure = ';c'
                        +'|'+tplCatid
                        +'|'+parentId
                        +'|'+parentCat.children('.cat_name').html(); 
    var cattype = 'default', qcount = '-1', qrandom = '0' ;         
    if ($('#cat'+ tplCatid +'_quest_count').length) {
        cattype = 'choice';
        qcount = $('#cat'+ tplCatid +'_quest_count').val();
    }
    if ($('#cat'+ tplCatid +'_random').is(':checked')) {
        qrandom ='1';
    }
    var cchildren = '';
    parentCat.children('div').each(function (){
        if ($(this).hasClass('exam_cat')) {
            cchildren += getCatAndChildren($(this),tplCatid);
        } else {
            cchildren += ';q|'+$(this).data('qid')+'|'+tplCatid;
            if (cattype == 'choice') {
                cattype = $(this).data('qtype');
            }
        }
    });
    
    return tplStructure+'|'+cattype+'|'+ qcount+'|'+qrandom+cchildren;
}

// last selected questioncategories (only new request if changes)
var currentCategories = '';
// last search phrase
var currentSearch = '';
// last search customization
var currSearchBlock = '';
/**
  * load questions from database
  */
function changeCategories() {
    var categories = $('select#category_select').val();
    var searchPhrase = $('#quest_search').val();
    var searchBlock = $("#search_select").val();
    if (categories) {
        categories = categories.join(',');
    }
    if (searchBlock) {
        searchBlock = searchBlock.join(',');
    }
    if (categories != currentCategories || searchPhrase != currentSearch || searchBlock != currSearchBlock )  {
        currentCategories = categories;
        currentSearch = searchPhrase;
        currSearchBlock = searchBlock;
        $('#questionspinner').css('display','');
        $.ajax({
            url: '<?php echo $CFG->wwwroot ?>/course/format/elatexam/exam_ajax.php',			
            data: {task: 'categories', categories: categories, search: searchPhrase, searchblock: searchBlock},
            dataType: 'xml',
            cache: false,
            success: function (xmlResp) {
                $('#quest_list').empty();
                currQuestions = {};
                $(xmlResp).find('question').each(function() {
                    var xmlNode = $(this);
                    currQuestions[xmlNode.attr("id")] = {
                        'qid'     : xmlNode.attr("id"),
                        'points'  : xmlNode.attr("points"),
                        'name'    : xmlNode.text(),
                        'qtype'   : xmlNode.attr("qtype"),
                        'qtypelocal'   : xmlNode.attr("qtypelocal")
                    };
                    $('#quest_list') 
                        .append(
                            $('<li id="quest'+xmlNode.attr("id")+'">')
                                .append($('<span class="questtype" title="'+xmlNode.attr("qtypelocal")+'">')
                                    .append('<img src="<?php echo $CFG->wwwroot ?>/theme/image.php?theme=standard&component=qtype_'+xmlNode.attr("qtype")+'&image=icon" />'))
                                .append(xmlNode.text())
                                .append($('<span class="questionpoints">')
                                    .append(Number(xmlNode.attr("points")).toFixed(1))));
                });
                $('#questionspinner').css('display','none');
            }
        });
    }
}
</script>