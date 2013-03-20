<?php
// This file is part of Moodle - http://moodle.org/
//
// Moodle is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Moodle is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with Moodle.  If not, see <http://www.gnu.org/licenses/>.

/**
 * Renderer for outputting the elatexam course format.
 *
 * @package format_elatexam
 * @copyright 2012 KLW
 * @license http://www.gnu.org/copyleft/gpl.html GNU GPL v3 or later
 * @since Moodle 2.3
 */


defined('MOODLE_INTERNAL') || die();
require_once($CFG->dirroot.'/course/format/renderer.php');

/**
 * Basic renderer for elatexam format.
 *
 * @copyright 2012 Dan Poltawski
 * @license http://www.gnu.org/copyleft/gpl.html GNU GPL v3 or later
 */
class format_elatexam_renderer extends format_section_renderer_base {

    /**
     * Generate the starting container html for a list of sections
     * @return string HTML to output.
     */
    protected function start_section_list() {
        return html_writer::start_tag('ul', array('class' => 'elatexam'));
    }

    /**
     * Generate the closing container html for a list of sections
     * @return string HTML to output.
     */
    protected function end_section_list() {
        return html_writer::end_tag('ul');
    }

    /**
     * Generate the title for this section page
     * @return string the page title
     */
    protected function page_title() {
        return get_string('elatexam_title');
    }

    /**
     * Generate the edit controls of a section
     *
     * @param stdClass $course The course entry from DB
     * @param stdClass $section The course_section entry from DB
     * @param bool $onsectionpage true if being printed on a section page
     * @return array of links with edit controls
     */
    protected function section_edit_controls($course, $section, $onsectionpage = false) {
        global $PAGE;

        if (!$PAGE->user_is_editing()) {
            return array();
        }

        $coursecontext = context_course::instance($course->id);

        if ($onsectionpage) {
            $url = course_get_url($course, $section->section);
        } else {
            $url = course_get_url($course);
        }
        $url->param('sesskey', sesskey());

        $controls = array();
        if (has_capability('moodle/course:setcurrentsection', $coursecontext)) {
            if ($course->marker == $section->section) {  // Show the "light globe" on/off.
                $url->param('marker', 0);
                $controls[] = html_writer::link($url,
                                    html_writer::empty_tag('img', array('src' => $this->output->pix_url('i/marked'),
                                        'class' => 'icon ', 'alt' => get_string('markedthisxam'))),
                                    array('title' => get_string('markedthisxam'), 'class' => 'editing_highlight'));
            } else {
                $url->param('marker', $section->section);
                $controls[] = html_writer::link($url,
                                html_writer::empty_tag('img', array('src' => $this->output->pix_url('i/marker'),
                                    'class' => 'icon', 'alt' => get_string('markthisxam'))),
                                array('title' => get_string('markthisxam'), 'class' => 'editing_highlight'));
            }
        }

        return array_merge($controls, parent::section_edit_controls($course, $section, $onsectionpage));
    }
    /**
     * Output the html for a multiple section page (no difference between one and multiple sections)
     *
     * @param stdClass $course The course entry from DB
     * @param array $sections The course_sections entries from the DB
     * @param array $mods used for print_section()
     * @param array $modnames used for print_section()
     * @param array $modnamesused used for print_section()
     */
    public function print_multiple_section_page($course, $sections, $mods, $modnames, $modnamesused) {
        global $PAGE, $CFG, $DB;   
        $list = 1;
        if (optional_param('task', '', PARAM_ALPHA) == 'edit') {
            require_once($CFG->dirroot . '/course/format/elatexam/exam_form_new.php');
            if ( ($examid = optional_param('examid', 0, PARAM_INT)) > 0) {
                if ($DB->record_exists('exam', array('id'=>$examid))) {
                    $exam = $DB->get_record('exam', array('id'=>$examid));
                }
            }
            if (!isset($exam) ){
                $exam = $this->createemptyexam($course->id);
            }
            //required
            
            //create form
            $mform = new exam_form('view.php?task=edit&id='.$course->id, $exam, 'post');
            if ($mform->is_cancelled()){
                
            } else if ($fromform=$mform->get_data()){
                $list = -1;
                
                $this->save_exam($fromform);
                
            } else {
                require_once($CFG->libdir . '/questionlib.php');
                $thiscontext = get_context_instance(CONTEXT_COURSE, $course->id);
                $contextswithacap = array();
                foreach (array_values($thiscontext->get_parent_contexts(true)) as $context) {
                    $cap='moodle/question:editall';//'moodle/question:add';//managecategory';
                    if (has_capability($cap, $context)) {
    					//var_dump($cap);var_dump($context);
                        $contextswithacap[] = $context;
                        break; //done with caps loop
                    }
                }

                $selCatContext = question_category_options($contextswithacap, false, 0, false);
                $selectable_categories = array();
                foreach ($selCatContext as $courses) {
                    foreach ($courses as $catKey => $catText) {
                        $selectable_categories[intval(substr($catKey,0,strpos($catKey,",")+1))] = $catText;
                    }
                }
                $list = -2;
                $xam_exports = $this->get_exports($examid);
                $mform->set_data($_POST);
                $selectable_cols = array('questiontext' => get_string('questiontext', 'question'),
                                        'questionname' => get_string('questionname', 'question'),
                                        'creatorname' => get_string('createdby', 'question'),
                                        'modifiername' => get_string('lastmodifiedby', 'question'),
                                        'tags' => get_string('tags', 'tag'));
                if ($off_tags= $DB->get_records_sql("SELECT name, rawname FROM {tag} WHERE tagtype = :seltag OR tagtype = :textag ORDER BY name",array('seltag'=>'official_select','textag'=>'official_text'))) {
                    if(count($off_tags)){
                        foreach($off_tags as $tag) {
                            $selectable_cols[$tag->name] = $tag->rawname;
                        }
                    }
                }
                require_once('tpl_exam.php');
            }
        }
        if ($list > 0) {
            //get exams
            //TODO - escape ',"
            $xam_list = $this->get_xams($course->id);
            
            //load template to show all exams
            require_once('tpl_exams.php');
        }
    }
    
    protected function save_exam(&$fromform) {
        global $DB;
        $exam                               = new stdClass();
        $exam->id                           = intval($fromform->examid);
        $exam->courseid                     = $fromform->courseid;
        $exam->title                        = $fromform->title;
        $exam->time                         = $fromform->time;
        $exam->kindnessextensiontime        = $fromform->kindnessextensiontime;
        $exam->tries                        = $fromform->tries;
        $exam->tasksperpage                 = $fromform->tasksperpage;
        $exam->description                  = $fromform->description["text"];
        $exam->showhandlinghintsbeforestart = $fromform->showhandlinghintsbeforestart;
        $exam->starttext                    = $fromform->starttext["text"];
        $exam->numberofcorrectors           = $fromform->numberofcorrectors;
        if ($exam->id > 0 && $DB->record_exists('exam', array('id' => $exam->id))) {
            $DB->update_record('exam', $exam);
        } else {
            $exam->id = $DB->insert_record('exam', $exam);
        }
        echo "Klausur gespeichert... <br />";
        $this->export_exam($fromform->examdata, $exam);
    }
    
    protected function export_exam($structure, $exam) {
        global $DB, $CFG;
        if (strlen($structure) > 4) {
            //echo "<br />$structure";
            require_once($CFG->dirroot.'/question/format/xml/format.php');
            require_once($CFG->dirroot.'/question/format/xhtml/format.php');
            $questionformatXML = new qformat_xml();
            $questionformatXHTML = new qformat_xhtml();
            $context = context_course::instance($exam->courseid);
            $export     = new stdClass();
            $export->export_time    = time();
            $export->structure      =  $structure;
            $export->examid      =  $exam->id;
            $export->id = $DB->insert_record('exam_exports', $export);
            $exam_components = explode(";",$export->structure);
            $xml = new DOMDocument('1.0', 'UTF-8');
            $xhtml = new DOMDocument('1.0', 'UTF-8');
            $xml->formatOutput = true;
            $xml->preserveWhiteSpace = false;             
            $xml_content = $xml->createElement("quiz");
            $componentXML = $xml->createDocumentFragment();
            foreach ($exam_components as $ecomp) {
                $ecomp = explode("|",$ecomp);
                if ($ecomp[0] == 'c') {
                    $componentXML->appendXML(
                         "<!-- question: 0  -->\n"
                        ."  <question type=\"category\">\n"
                        ."    <category id=\"$ecomp[1]\" parent=\"$ecomp[2]\" type=\"$ecomp[4]\" num_shown=\"$ecomp[5]\" shuffle=\"$ecomp[6]\" >\n"
                        ."         <text>$ecomp[3]</text>\n"
                        ."    </category>\n"
                        ."  </question>\n"
                    );
                    if ($ecomp[1] == $ecomp[2]) {
                        $componentXML->appendXML(
                             "\n<!-- question: -1  -->\n"
                            ."  <question type=\"meta\">\n"
                            ."    <name>\n"
                            ."         <text>$exam->title</text>\n"
                            ."    </name>\n"
                            ."    <questiontext format=\"html\">\n"
                            ."         <text><![CDATA[$exam->description]]></text>\n"
                            ."    </questiontext>\n"
                            ."    <generalfeedback format=\"html\">\n"
                            ."         <text><![CDATA[$exam->starttext]]></text>\n"
                            ."    </generalfeedback>\n"
                            ."    <time>$exam->time</time>\n"
                            ."    <kindnessextensiontime>$exam->kindnessextensiontime</kindnessextensiontime>\n"
                            ."    <tasksperpage>$exam->tasksperpage</tasksperpage>\n"
                            ."    <tries>$exam->tries</tries>\n"
                            ."    <showhandlinghintsbeforestart>$exam->showhandlinghintsbeforestart</showhandlinghintsbeforestart>\n"
                            ."    <numberofcorrectors>$exam->numberofcorrectors</numberofcorrectors>\n"
                            ."  </question>\n"
                        );
                        $xamhtml = 
                             "<h1>".get_string('title', 'qtype_meta').": $exam->title</h1>\n"
                            ."<div><strong>".get_string('description', 'qtype_meta').":</strong>$exam->description</div>\n"
                            ."<div><strong>".get_string('starttext', 'qtype_meta').":</strong>$exam->starttext</div>\n"
                            ."<div><strong>".get_string('time', 'qtype_meta').":</strong>$exam->time</div>\n"
                            ."<div><strong>".get_string('kindnessextensiontime', 'qtype_meta').":</strong>$exam->kindnessextensiontime</div>\n"
                            ."<div><strong>".get_string('tasksperpage', 'qtype_meta').":</strong>$exam->tasksperpage</div>\n"
                            ."<div><strong>".get_string('tries', 'qtype_meta').":</strong>$exam->tries</div>\n"
                            ."<div><strong>".get_string('showhandlinghintsbeforestart', 'qtype_meta').":</strong>".($exam->showhandlinghintsbeforestart ? get_string('yes') : get_string('no')) ."</div>\n"
                            ."<div><strong>".get_string('numberofcorrectors', 'qtype_meta').":</strong>$exam->numberofcorrectors</div>\n";
                    } else {
                        $xamhtml .= "<h2>$ecomp[3] ".($ecomp[6] == 1 ? '('.get_string('random_sort', 'format_elatexam').')' : '')." ".($ecomp[5] > 0 ? '('.get_string('num_shown', 'format_elatexam').': '.$ecomp[5].')' : '')."</h2>\n";
                    }
                } elseif ($ecomp[0] == 'q') {
                    $question = $DB->get_record('question', array('id'=>intval($ecomp[1])));
                    $question->export_process = true;
                    $qtype = question_bank::get_qtype($question->qtype, false);
                    $qtype->get_question_options($question);
                    $question->contextid = $context->id;
                    $componentXML->appendXML(str_replace('<question type=','<question category="'.$ecomp[2].'" id="'.$ecomp[1].'" type=',$questionformatXML->writequestion($question)));
                    //$xamhtml .= $questionformatXHTML->writequestion($question);
                }
            }
            
            $xml_content->appendChild($componentXML);
            $xml->appendChild( $xml_content );
            if (!is_dir($CFG->dirroot.'/course/format/elatexam/xams')) {
                mkdir($CFG->dirroot.'/course/format/elatexam/xams');
            }
            $xml->save($CFG->dirroot.'/course/format/elatexam/xams/exam'.$exam->id.'_'.$export->export_time.'.xml');
            echo 'XML-Export erfolgt... <a href="format/elatexam/xams/exam'.$exam->id.'_'.$export->export_time.'.xml">herunterladen</a><br />';
            $xhtml->loadHTML($xamhtml);
            $xhtml->saveHTMLFile($CFG->dirroot.'/course/format/elatexam/xams/exam'.$exam->id.'_'.$export->export_time.'.html');     
            echo 'HTML-Export erfolgt... <a href="format/elatexam/xams/exam'.$exam->id.'_'.$export->export_time.'.html">anzeigen</a><br /><br />';   
            echo 'Weiter zur <a href="view.php?id='.$exam->courseid.'">Klausurübersicht</a>...';    
            //echo '<div>XML-Export erfolgt: <a href="'.$CFG->dirroot.'/course/format/elatexam/xams/exam'.$exam->id.'_'.$export->export_time.'.xml'.'">Export herunterladen</a><br /><br />Weiter zur <a href="view.php?id='.$exam->courseid.'">Klausurübersicht</a>...</div>';
        } else {
            echo '<div>Kein Export erfolgt (keine Kategorien und Fragen zum Exportieren erstellt).<br />Weiter zur <a href="view.php?id='.$exam->courseid.'">Klausurübersicht</a>...</div>'; 
        }
    }
    
    private function get_xams($courseid) {
        global $DB;
        $xams = $DB->get_records_sql('SELECT * FROM {exam} WHERE courseid = ?', array($courseid));
        foreach ($xams as $xam) {
            $xam->exports = $DB->get_records_sql('SELECT * FROM {exam_exports} WHERE examid = ? ORDER BY export_time DESC', array($xam->id));
        }
        return $xams;
    }
    private function get_exports($examid) {
        global $DB;
        if ($examid > 0) {
            return $DB->get_records_sql('SELECT * FROM {exam_exports} WHERE examid = ? ORDER BY export_time DESC', array($examid));
        } else {
            return array();
        }
        
    }
    private function createemptyexam($courseid) {
        $exam                               = new stdClass;
        $exam->id                           = '';
        $exam->courseid                     = $courseid;
        $exam->title                        = '';
        $exam->time                         = '';
        $exam->kindnessextensiontime        = 0;
        $exam->tasksperpage                 = 2;
        $exam->tries                        = 1;
        $exam->numberofcorrectors           = 1;
        $exam->description                  = '';
        $exam->showhandlinghintsbeforestart = true;
        $exam->starttext                    = '';
        
        return $exam;
    }
}
