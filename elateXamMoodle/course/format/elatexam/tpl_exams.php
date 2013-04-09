<script type="text/javascript">
/**
 * remove exam and all exports of this exam
 */
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
/**
 * remove export of an exam
 */
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
    <h2 class="xams_top"><?php echo get_string('exams', 'format_elatexam') ?></h2>
    
    <div class="create_xam">
        <a href="http://localhost/moodlexam2/question/edit.php?courseid=<?php echo $course->id ?>"><button><?php echo get_string('open_question_bank', 'format_elatexam') ?></button></a>
        <big><?php echo get_string('exam_groups', 'format_elatexam') ?></big><a href="<?php echo $_SERVER['PHP_SELF'] ?>?id=<?php echo $course->id ?>&task=edit"><button><?php echo get_string('create_exam_group', 'format_elatexam') ?></button></a>
    </div>
    <div style="clear: both;"></div>
    <?php foreach ($xam_list as $xam) { ?>
        <div class="xam_group" id="xam_group<?php echo $xam->id ?>">
            <div class="xam_title"><?php echo $xam->title ?> - 
                <a href="<?php echo $_SERVER['PHP_SELF'] ?>?id=<?php echo $course->id ?>&task=edit&examid=<?php echo $xam->id ?>"><?php echo get_string('edit_and_export', 'format_elatexam') ?></a>
                <a href="#" onclick="if(confirm('<?php echo get_string('confirm_remove_exam_group', 'format_elatexam') ?>')){removeGroup(<?php echo $xam->id ?>);}" title="<?php echo get_string('remove_exam_group', 'format_elatexam') ?>"><span class="xam_control ui-button-icon-primary ui-icon ui-icon-trash"></span></a>
            </div>
            <ul>
            <?php if(count($xam->exports) > 0) {foreach ($xam->exports as $xam_export) { //$xam->points =0;$xam->questions_count = 3; $xam->questions_exist = 2; $xam->questions_changed = 1; ?>
                <li id="export<?php echo $xam_export->id?>"><?php echo get_string('exported_on', 'format_elatexam') ?>: <?php echo date_format($tplDate->setTimestamp($xam_export->export_time), 'Y-m-d H:i') ?>
                    <span class="xam_control">
                        <div class="fg-buttonset fg-buttonset-multi">
                        	<a href="format/elatexam/xams/<?php echo "exam".$xam->id."_".$xam_export->export_time.".xml" ?>">
                                <button class="fg-button ui-state-default ui-corner-left" title="<?php echo get_string('download_export', 'format_elatexam') ?> (XML)">
                                    <span class="ui-button-icon-primary ui-icon ui-icon-disk"></span>
                                </button>
                            </a>
                            <a href="format/elatexam/xams/<?php echo "exam".$xam->id."_".$xam_export->export_time.".html" ?>">
                            	<button class="fg-button ui-state-default" title="<?php echo get_string('show_export', 'format_elatexam') ?> (HTML)">
                                    <span class="ui-button-icon-primary ui-icon ui-icon-print"></span>
                                </button>
                            </a>
                        	<button class="fg-button ui-state-default ui-corner-right" title="<?php echo get_string('remove_export', 'format_elatexam') ?>" onclick="if(confirm('<?php echo get_string('confirm_remove_export', 'format_elatexam') ?>')){removeExport(<?php echo $xam_export->id?>);}">
                                <span class="ui-button-icon-primary ui-icon ui-icon-trash"></span>
                            </button>
                        </div>
                    </span>
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