<?php
$xml_output = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<response>\n";
require_once('../../../config.php');
require_login();
$task   = addslashes(optional_param('task', '', PARAM_ALPHA));

//get questions
if ($task == 'categories') {
    $categories   = addslashes(optional_param('categories', '', PARAM_RAW));
    $searchp   = optional_param('search', '', PARAM_RAW);
    $tests = array();
    $joins = array();
    $joinrules = array();
    $search_parts = array();
    $search_all = array();
    if ($searchp != '') {
        $columns = array("questionname", "questiontext", "creatorname", "modifiername","tags");
        $search_select = explode(",",optional_param('searchblock', '', PARAM_RAW));
        if ($off_tags= $DB->get_records_sql("SELECT name, rawname FROM {tag} WHERE tagtype = :seltag OR tagtype = :textag ORDER BY name",array('seltag'=>'official_select','textag'=>'official_text'))) {
            if(count($off_tags)){
                foreach($off_tags as $tag) {
                    $columns[$tag->rawname] = $tag->name;
                    $zeichen = 'abcdefghijklmnopqrstABCDEFGHIJKLMNOTQRST';   
                    mt_srand( (double) microtime() * 1000000);  
                    $tbl_shortcut = $zeichen[mt_rand(0,(strlen($zeichen)-1))].$zeichen[mt_rand(0,(strlen($zeichen)-1))].$zeichen[mt_rand(0,(strlen($zeichen)-1))];
                    $joinrules[$tag->rawname] = "LEFT JOIN (
                                                 SELECT ti.itemid, GROUP_CONCAT(tg.rawname SEPARATOR ', ') AS `".$tag->rawname."` FROM {tag_instance} ti 
                                                 LEFT JOIN {tag} tg ON ti.tagid = tg.id WHERE ti.itemtype = 'question' AND tg.name LIKE '%".$tag->name."=%' GROUP BY ti.itemid
                                             ) ".$tbl_shortcut." ON ".$tbl_shortcut.".itemid = q.id";
                }
            }
        }
        if (strpos(",".$searchp,"\"") > 0) {
            $searcharray = preg_split('"\\"([^\\"]*)\\""', "\"".$searchp."\"" , -1, PREG_SPLIT_NO_EMPTY);
        } else {
            $searcharray = array();
        }
        $searcharray2 = preg_split('"\\"([^\\"]*)\\""', $searchp, -1, PREG_SPLIT_NO_EMPTY);
        foreach ($searcharray2 as $sa2) {
            $sa2 = preg_replace('/  /i',' ',$sa2);
            $sa2 = preg_replace('/( )*\\|+( )*|( )+(oder|or)( )+/i','|',$sa2);
            $searcharray = array_merge($searcharray,preg_split('/(( )+(and|und)( )+|&| )/i', $sa2, -1, PREG_SPLIT_NO_EMPTY));
        }
        $searcharray = array_unique($searcharray);
        //var_dump($search_select);
        if (strlen(optional_param('searchblock', '', PARAM_RAW))>1) { 
            $all = false;
            $search_select = array_combine($search_select,$search_select);
        } else {$all = true;}
        foreach ($searcharray as $s_element) {
            $s_element = addslashes($s_element);
            $search_all = array();
            foreach ($columns as $rawcolumn => $column) {
                if ($all || isset($search_select[$column])) {
                    $search = array();
                    foreach (explode("|",$s_element) as $search_phrase) {
                        switch ($column) {
                            case 'questionname':
                                $search[] = 'q.name LIKE "%'.$search_phrase.'%"';                  
                            break;
                            case 'creatorname':
                                $search[] = '(u.firstname LIKE "%'.$search_phrase.'%" OR u.lastname LIKE "%'.$search_phrase.'%")';
                                $joins["user"] = "LEFT JOIN {user} u ON u.id = q.createdby"; 
                            break;
                            case 'modifiername':
                                $search[] = '(u.firstname LIKE "%'.$search_phrase.'%" OR u.lastname LIKE "%'.$search_phrase.'%")';
                                $joins["user"] = "LEFT JOIN {user} u ON u.id = q.createdby";   
                            break;
                            case 'questiontext':// questiontext
                                $search[] = 'q.questiontext LIKE "%'.$search_phrase.'%"';
                            break;
                            case 'tags':
                                $search[] = 'tc.tags LIKE "%'.$search_phrase.'%"';
                                $joins["tags"] = "LEFT JOIN (".
                                                     "SELECT ti.itemid, GROUP_CONCAT(tg.rawname SEPARATOR ', ') AS tags FROM {tag_instance} ti ".
                                                     "LEFT JOIN {tag} tg ON ti.tagid = tg.id WHERE ti.itemtype = 'question' AND tg.name NOT LIKE '%=%' GROUP BY ti.itemid".
                                                 ") tc ON tc.itemid = q.id"; 
                            break;
                            default:
                                $search[] = '`'.$rawcolumn.'` LIKE "%'.$search_phrase.'%"';
                                $joins[] = $joinrules[$rawcolumn];  
                            break;
                        }
                    }
                    if (count($search)) {
                        $search_all[] = '('.implode(' OR ',$search).')';
                    }               
                }
            }
            if (count($search_all)) {
                $search_parts[] = '('.implode(' OR ',$search_all).')';
            }          
        }
        if (count($search_parts)) {
            $tests[] = '('.implode(" AND ",$search_parts).')';
        }
    }
    $where = '';
    if (count($tests)) {
        $where = " AND ".implode(' AND ', $tests);
    }
    $query = "SELECT q.id, q.name, q.qtype, q.defaultmark AS points FROM {question} AS q ".implode(" ",$joins)." WHERE q.category IN ($categories) ".$where;
    //echo "<br /><br /><br />query:<br />".$query."<br /><br />";
    $questions = null;
    $questions = $DB->get_records_sql($query,array());
    if (count($questions)) {
        foreach ($questions as $question) {
            $xml_output .= "\t<question id=\"$question->id\" points=\"$question->points\" qtype=\"$question->qtype\" qtypelocal=\"".get_string('pluginname', 'qtype_'.$question->qtype)."\">$question->name</question>\n";
        }
    }
    
// get question data
} elseif ($task == 'getquestion') {
    $qid   = optional_param('qid', '', PARAM_INT);
    if ($DB->record_exists('question', array('id' => $qid))) {
        $query = "SELECT id, name, qtype, timemodified, defaultmark AS points FROM {question} WHERE id = ".$qid;
        $question = $DB->get_record_sql($query,array());
        $xml_output .= "\t<question id=\"$question->id\" timemodified=\"$question->timemodified\" points=\"$question->points\" qtype=\"$question->qtype\" qtypelocal=\"".get_string('pluginname', 'qtype_'.$question->qtype)."\">$question->name</question>\n";
    }
    
//remove exports
} elseif ($task == 'remexport') {
    $exportid   = optional_param('exportid', '', PARAM_INT);
    if ($DB->record_exists('exam_exports', array('id' => $exportid))) {
        $query = "SELECT id, export_time, examid FROM {exam_exports} WHERE id = ?";
        $export = $DB->get_record_sql($query,array($exportid));
        $DB->delete_records('exam_exports', array('id' => $exportid));
        if (file_exists($CFG->dirroot.'/course/format/elatexam/xams/exam'.$export->examid.'_'.$export->export_time.'.xml')) {
            unlink($CFG->dirroot.'/course/format/elatexam/xams/exam'.$export->examid.'_'.$export->export_time.'.xml');
        }
        if (file_exists($CFG->dirroot.'/course/format/elatexam/xams/exam'.$export->examid.'_'.$export->export_time.'.html')) {
            unlink($CFG->dirroot.'/course/format/elatexam/xams/exam'.$export->examid.'_'.$export->export_time.'.html');
        }
    }
    
//remove exams
} elseif ($task == 'remexam') {
    $examid   = optional_param('examid', '', PARAM_INT);
    if ($DB->record_exists('exam', array('id' => $examid))) {
        $query = "SELECT id, export_time, examid FROM {exam_exports} WHERE examid = ?";
        $exports = $DB->get_records_sql($query,array($examid));
        foreach ($exports as $export) {
            if (file_exists($CFG->dirroot.'/course/format/elatexam/xams/exam'.$export->examid.'_'.$export->export_time.'.xml')) {
                unlink($CFG->dirroot.'/course/format/elatexam/xams/exam'.$export->examid.'_'.$export->export_time.'.xml');
            }
            if (file_exists($CFG->dirroot.'/course/format/elatexam/xams/exam'.$export->examid.'_'.$export->export_time.'.html')) {
                unlink($CFG->dirroot.'/course/format/elatexam/xams/exam'.$export->examid.'_'.$export->export_time.'.html');
            }
        }
        $DB->delete_records('exam', array('id' => $examid));
        $DB->delete_records('exam_exports', array('examid' => $examid));
    }
}

header("Content-type: text/xml");
echo $xml_output."</response>";
?>
