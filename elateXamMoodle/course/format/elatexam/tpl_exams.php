<script type="text/javascript">
/**
 * function to show more informations of an exported exam
 */
function showDetails(cont, points, questions_count, questions_exist, questions_changed) {
    $(cont).html('Gesamtpunktzahl: ' + points +
                 '<br />Anzahl an Fragen: ' + questions_count +
                 '<br />davon noch vorhanden: ' + questions_exist +
                 '<br />davon inzwischen verändert: ' + questions_changed );
}
function removeGroup(groupId){
    $('#xam_group'+groupId).slideUp('800',function(){$(this).remove();});
    $.ajax({
        url: '<?php echo $CFG->wwwroot ?>/course/format/elatexam/exam_ajax.php',			
        data: {task: 'remexam', examid: groupId},
        dataType: 'xml',
        cache: false,
        success: function (xmlResp) {
        }
    });
}
function removeExport(exportId){
    $('#export'+exportId).slideUp('400',function(){$(this).remove();});
    $.ajax({
        url: '<?php echo $CFG->wwwroot ?>/course/format/elatexam/exam_ajax.php',			
        data: {task: 'remexport', exportid: exportId},
        dataType: 'xml',
        cache: false,
        success: function (xmlResp) {
        }
    });
}

</script>
<?php
$tplDate = new DateTime();
?>
<div class="xams_page">
    <h2 class="xams_top">Klausuren</h2>
    <div class="create_xam">
        <big>Klausurgruppen</big><a href="<?php echo $_SERVER['PHP_SELF'] ?>?id=<?php echo $course->id ?>&task=edit"><button>neue Klausur(gruppe) erstellen</button></a>
    </div>
    <div style="clear: both;"></div>
    <?php foreach ($xam_list as $xam) { ?>
        <div class="xam_group" id="xam_group<?php echo $xam->id ?>">
            <div class="xam_title"><?php echo $xam->title ?> - 
                <a href="<?php echo $_SERVER['PHP_SELF'] ?>?id=<?php echo $course->id ?>&task=edit&examid=<?php echo $xam->id ?>">bearbeiten &amp; exportieren</a>
                <a href="#" onclick="if(confirm('Wollen Sie diese Klausurgruppe und ihre Exporte löschen?')){removeGroup(<?php echo $xam->id ?>);}" title="Klausurgruppe löschen"><span class="xam_control ui-button-icon-primary ui-icon ui-icon-trash"></span></a>
            </div>
            <ul>
            <?php if(count($xam->exports) > 0) {foreach ($xam->exports as $xam_export) { //$xam->points =0;$xam->questions_count = 3; $xam->questions_exist = 2; $xam->questions_changed = 1; ?>
                <li id="export<?php echo $xam_export->id?>">exportiert am: <?php echo date_format($tplDate->setTimestamp($xam_export->export_time), 'Y-m-d H:i') ?>
                    <span class="xam_control">
                        <div class="fg-buttonset fg-buttonset-multi">
                        	<a href="format/elatexam/xams/<?php echo "exam".$xam->id."_".$xam_export->export_time.".xml" ?>">
                                <button class="fg-button ui-state-default ui-corner-left" title="Klausurexport herunterladen (XML)">
                                    <span class="ui-button-icon-primary ui-icon ui-icon-disk"></span>
                                </button>
                            </a>
                            <a href="format/elatexam/xams/<?php echo "exam".$xam->id."_".$xam_export->export_time.".html" ?>">
                            	<button class="fg-button ui-state-default" title="(Druck-)Vorschau (HTML)">
                                    <span class="ui-button-icon-primary ui-icon ui-icon-print"></span>
                                </button>
                            </a>
                        	<button class="fg-button ui-state-default ui-corner-right" title="Export löschen" onclick="if(confirm('Wollen Sie diesen Klausurexport löschen?')){removeExport(<?php echo $xam_export->id?>);}">
                                <span class="ui-button-icon-primary ui-icon ui-icon-trash"></span>
                            </button>
                        </div>
                        <?php /*<a href="format/elatexam/xams/<?php echo "exam".$xam->id."_".$xam_export->export_time.".xml" ?>"><button class="table-save-link ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only" role="button" aria-disabled="false" title="Export (XML)">
                            <span class="ui-button-icon-primary ui-icon ui-icon-disk"></span>
                            <span class="ui-button-text">Export (XML)</span>
                        </button></a>
                        <a href="format/elatexam/xams/<?php echo $xam_export->prevfile ?>" target="_blank" ><button class="table-print-link ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only" role="link" aria-disabled="false" title="Vorschau (HTML)">
                            <span class="ui-button-icon-primary ui-icon ui-icon-print"></span>
                            <span class="ui-button-text">Vorschau (HTML)</span>
                        </button></a>
                        <button class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only" role="button" aria-disabled="false" title="Löschen" onclick="if(confirm('Wollen Sie diese Klausurgruppe und ihre Exporte löschen?')){removeGroup(<?php echo $xam->id ?>);}">
                            <span class="ui-button-icon-primary ui-icon ui-icon-trash"></span>
                            <span class="ui-button-text">Löschen</span>
                        </button>*/ ?>
                    </span>
                    <?php /*<br /><span onclick="showDetails(this, <?php echo intval($xam_export->points) ?>, <?php echo intval($xam_export->questions_count) ?>, <?php echo intval($xam_export->questions_exist) ?>, <?php echo intval($xam_export->questions_changed) ?>)"><a href="#" onclick="return false;">Details anzeigen &gt;&gt;</a></span>*/ ?>
                </li>
            <?php }} ?>
            </ul>
        </div>
    <?php } ?>
</div>
<script type="text/javascript">
$(function(){
    $('.fg-button').hover(
    	function(){ 
    		$(this).addClass("ui-state-hover"); 
    	},
    	function(){ 
    		$(this).removeClass("ui-state-hover"); 
    	}
    );
});


</script>