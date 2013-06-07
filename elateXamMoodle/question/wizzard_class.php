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
 * A class for representing question categories.
 *
 * @package    moodlecore
 * @subpackage questionbank
 * @copyright  1999 onwards Martin Dougiamas {@link http://moodle.com}
 * @license    http://www.gnu.org/copyleft/gpl.html GNU GPL v3 or later
 */


defined('MOODLE_INTERNAL') || die();

// number of categories to display on page
define('QUESTION_PAGE_LENGTH', 25);

require_once($CFG->libdir . '/listlib.php');
require_once($CFG->dirroot . '/question/wizzard_form.php');
require_once($CFG->dirroot . '/question/move_form.php');

/**
 * Class representing a list of question categories
 *
 * @copyright  1999 onwards Martin Dougiamas {@link http://moodle.com}
 * @license    http://www.gnu.org/copyleft/gpl.html GNU GPL v3 or later
 */
class question_category_list extends moodle_list {
    public $table = "question_categories";
    public $listitemclassname = 'question_category_list_item';
    /**
     * @var reference to list displayed below this one.
     */
    public $nextlist = null;
    /**
     * @var reference to list displayed above this one.
     */
    public $lastlist = null;

    public $context = null;
    public $sortby = 'parent, sortorder, name';

    public function __construct($type='ul', $attributes='', $editable = false, $pageurl=null, $page = 0, $pageparamname = 'page', $itemsperpage = 20, $context = null){
        parent::__construct('ul', '', $editable, $pageurl, $page, 'cpage', $itemsperpage);
        $this->context = $context;
    }

    public function get_records() {
        $this->records = get_categories_for_contexts($this->context->id, $this->sortby);
    }
}


/**
 * An item in a list of question categories.
 *
 * @copyright  1999 onwards Martin Dougiamas {@link http://moodle.com}
 * @license    http://www.gnu.org/copyleft/gpl.html GNU GPL v3 or later
 */
class question_category_list_item extends list_item {
    public function set_icon_html($first, $last, $lastitem){
        global $CFG;
        $category = $this->item;
        $url = new moodle_url('/question/category.php', ($this->parentlist->pageurl->params() + array('edit'=>$category->id)));
        $this->icons['edit']= $this->image_icon(get_string('editthiscategory', 'question'), $url, 'edit');
        parent::set_icon_html($first, $last, $lastitem);
        $toplevel = ($this->parentlist->parentitem === null);//this is a top level item
        if (($this->parentlist->nextlist !== null) && $last && $toplevel && (count($this->parentlist->items)>1)){
            $url = new moodle_url($this->parentlist->pageurl, array('movedowncontext'=>$this->id, 'tocontext'=>$this->parentlist->nextlist->context->id, 'sesskey'=>sesskey()));
            $this->icons['down'] = $this->image_icon(
                get_string('shareincontext', 'question', print_context_name($this->parentlist->nextlist->context)), $url, 'down');
        }
        if (($this->parentlist->lastlist !== null) && $first && $toplevel && (count($this->parentlist->items)>1)){
            $url = new moodle_url($this->parentlist->pageurl, array('moveupcontext'=>$this->id, 'tocontext'=>$this->parentlist->lastlist->context->id, 'sesskey'=>sesskey()));
            $this->icons['up'] = $this->image_icon(
                get_string('shareincontext', 'question', print_context_name($this->parentlist->lastlist->context)), $url, 'up');
        }
    }

    public function item_html($extraargs = array()){
        global $CFG, $OUTPUT;
        $str = $extraargs['str'];
        $category = $this->item;

        $editqestions = get_string('editquestions', 'question');

        /// Each section adds html to be displayed as part of this list item
        $questionbankurl = new moodle_url("/question/edit.php", ($this->parentlist->pageurl->params() + array('category'=>"$category->id,$category->contextid")));
        $catediturl = $this->parentlist->pageurl->out(true, array('edit' => $this->id));
        $item = "<b><a title=\"{$str->edit}\" href=\"$catediturl\">".$category->name ."</a></b> <a title=\"$editqestions\" href=\"$questionbankurl\">".'('.$category->questioncount.')</a>';

        $item .= '&nbsp;'. $category->info;

        // don't allow delete if this is the last category in this context.
        if (count($this->parentlist->records) != 1) {
            $item .=  '<a title="' . $str->delete . '" href="'.$this->parentlist->pageurl->out(true, array('delete'=>$this->id, 'sesskey'=>sesskey())).'">
                    <img src="' . $OUTPUT->pix_url('t/delete') . '" class="iconsmall" alt="' .$str->delete. '" /></a>';
        }

        return $item;
    }
}


/**
 * Class representing q question category
 *
 * @copyright  1999 onwards Martin Dougiamas {@link http://moodle.com}
 * @license    http://www.gnu.org/copyleft/gpl.html GNU GPL v3 or later
 */
class question_category_object {

    var $str;
    /**
     * Nested lists to display categories.
     *
     * @var array
     */
    var $editlists = array();
    var $newtable;
    var $tab;
    var $tabsize = 3;

    /**
     * @var moodle_url Object representing url for this page
     */
    var $pageurl;
    /**
     * @var question_category_edit_form Object representing form for adding / editing categories.
     */
    var $catform;
    /**
     * Constructor
     *
     * Gets necessary strings and sets relevant path information
     */
    public function question_category_object($page, $pageurl, $contexts, $currentcat, $defaultcategory, $todelete, $addcontexts) {
        global $CFG, $COURSE, $OUTPUT;

        $this->tab = str_repeat('&nbsp;', $this->tabsize);

        $this->str = new stdClass();
        $this->str->course         = get_string('course');
        $this->str->category       = get_string('category', 'question');
        $this->str->categoryinfo   = get_string('categoryinfo', 'question');
        $this->str->questions      = get_string('questions', 'question');
        $this->str->add            = get_string('add');
        $this->str->delete         = get_string('delete');
        $this->str->moveup         = get_string('moveup');
        $this->str->movedown       = get_string('movedown');
        $this->str->edit           = get_string('editthiscategory', 'question');
        $this->str->hide           = get_string('hide');
        $this->str->order          = get_string('order');
        $this->str->parent         = get_string('parent', 'question');
        $this->str->add            = get_string('add');
        $this->str->action         = get_string('action');
        $this->str->top            = get_string('top');
        $this->str->addcategory    = get_string('addcategory', 'question');
        $this->str->editcategory   = get_string('editcategory', 'question');
        $this->str->cancel         = get_string('cancel');
        $this->str->editcategories = get_string('editcategories', 'question');
        $this->str->page           = get_string('page');

        $this->pageurl = $pageurl;

        $this->initialize($page, $contexts, $currentcat, $defaultcategory, $todelete, $addcontexts);
    }

    /**
     * Initializes this classes general category-related variables
     */
    public function initialize($page, $contexts, $currentcat, $defaultcategory, $todelete, $addcontexts) {
        $lastlist = null;
        foreach ($contexts as $context){
            $this->editlists[$context->id] = new question_category_list('ul', '', true, $this->pageurl, $page, 'cpage', QUESTION_PAGE_LENGTH, $context);
            $this->editlists[$context->id]->lastlist =& $lastlist;
            if ($lastlist!== null){
                $lastlist->nextlist =& $this->editlists[$context->id];
            }
            $lastlist =& $this->editlists[$context->id];
        }

        $count = 1;
        $paged = false;
        foreach ($this->editlists as $key => $list){
            list($paged, $count) = $this->editlists[$key]->list_from_records($paged, $count);
        }
        $this->catform = new question_category_edit_form($this->pageurl, compact('contexts', 'currentcat'));
        if (!$currentcat){
            $this->catform->set_data(array('parent'=>$defaultcategory));
        }

    }

    /**
     * Displays the user interface
     *
     */
    public function display_user_interface($courseid, $categoryid) {

        /// Interface for editing existing categories
        //$this->output_edit_lists();
        echo '<br />';		
		echo '
			<script>
			$(function() {
			$( "#accordion" ).accordion({
			heightStyle: "content", collapsible : true,'; 
		//Uncomment to collape all options onload
		//echo	'active : \'none\'';
		echo	'})});
			</script>
			<div id="accordion">
			<h3>Assistent zur Bedienung der Fragenpoolverwaltung</h3>
			<div>
			<p>
			Lernen Sie die Verwendung des Fragenpools in 5 einfachen Schritten kennen.
					</div>
			<h3>1. Kategorie zur Frageeinordnung anlegen</h3>
			<div>
			<p> Fragen werden in Ordnern/Kategorien gesammelt und aufbewahrt. Diese Kategorien sollten sich an den Klausuren orientieren, zu denen die Fragen jeweils geh&ouml;ren. <br>Legen Sie hier eine neue Kategorie an:';
	
        /// Interface for adding a new category:
        $this->output_new_table();
        echo '<br />';
		echo '</p>
			</div>
			<h3>2. Single-Choice-Frage anlegen (je nach Bedarf mehrfach durchlaufen)</h3>
			<div>
			<p>Was im Editor zu tun ist:
			<ol>
			  <li>W&auml;hlen Sie f&uumlr die Frage eine Kategorie und einen aussagekr&auml;ftigen Namen</li>
			  <li>Die Fragestellung und die Antwortalternativen eintippen, davon eine als korrekt anhaken</li>
			  <li>Optionale Eingabefelder k&ouml;nnen Sie ignorieren, alles ist bereits nach den &uuml;blichen Vorgaben konfiguriert</li>
			  <li>Speichern Sie ihre Eingaben - Sie gelangen danach automatisch hierher zur&uuml;ck</li>
			</ol>
			Legen Sie einige Fragen an: <br><br>
			<form><input type="button" value=" Zum Editor " 
			onClick="window.location.href=\'question.php?returnurl=%2Fquestion%2Fwizzard.php%3Fcourseid%3D' . $courseid . '&courseid=' . $courseid . '&category=' . $categoryid . '&qtype=multichoice\'"></form>';
			
		echo	'</p>
			</div>
			<h3>3. Benachrichtigen Sie die zust&auml;ndige Qualit&auml;tspr&uuml;fung (optional)</h3>
			<div>
			<p>
				Sie k&ouml;nnen einen Nachrichtendienst innerhalb der Fragenverwaltung verwenden, um Ihre Gutachter &uuml;ber pr&uuml;fbereite Fragen zu informieren.
				Diese erhalten dann per Mail, sp&auml;testens aber bei der n&auml;chsten Anmeldung am Fragenpool, Ihre Nachricht.
				Tippen Sie im Suchfeld in der Mitte der Seite den Namen der gew&uuml;nschten Kontaktperson ein und w&auml;hlen diese danach aus der Ergebnisliste.
				<br>
				<br>
				Sollen Ihre Fragen von einer Gutachtergruppe gepr&uuml;ft werden, geben Sie nun bescheid,  welche Kategorie(n) gepr&uuml;ft werden sollen.
				<br>
				Falls Sie keine(n) Gutachter haben, k&ouml;nnen Sie probeweise auch dem Nutzer "admin" eine Nachricht schicken.
				<form><input type="button" value=" Nachrichten versenden " 
					onClick="window.open(\'../message/index.php?viewing=search\'); return false;"></form>	<br>	
				Den Nachrichtendienst k&ouml;nnen Sie auch mittels der Navigation auf der linken Seite erreichen (Mein Profil->Mitteilungen).
			</div>
			<h3>4. Klausur zusammenstellen (optional)</h3>
			<div>
			<p>	Sind Sie selbst f&uuml;r die Zusammenstellung der Klausur verantwortlich, so k&ouml;nnen Sie nun eine Klausur anlegen. Folgendes ist zu tun:
			<br>
			<ol>
			  <li>Legen Sie zuerst die allgemeinen Rahmenbedingungen der Klausur fest. Unter diesen Eingabefeldern finden Sie das Klausurformular.</li>
			  <li><table border="0"><tr>
				<td>Klicken Sie im Klausurformular auf das Ordnersymbol, um eine Kategorie f&uuml;r Fragen in der Klausur anzulegen: </th>
 				<td><span class="ui-icon ui-icon-folder-open"></span</th>
			  </tr>
			  <tr> 
			  	<td>Klicken Sie im Klausurformular danach auf das "Leere-Seite"-Symbol, um diese Kategorie mit Fragen zu f&uuml;llen:</th>
			   <td><span align="middle" class="ui-icon ui-icon-document"></span></th>
			  </tr>			  <tr> 
			  	<td>Mit klick auf das Stiftsymbol gelangen Sie in die Editoroberfl&auml;che der Frage:</th>
			   <td><span class="ui-icon ui-icon-pencil" style="display: inline-block;"></span></th>
			  </tr>
			</table>
			</li>
			<li>Die Liste "Kategorien von Fragen ausw&auml;hlen" beinhaltet alle von Ihnen angelegten Kategorien. Haken Sie die anzuzeigenden an.</li>
			<li>Klicken Sie auf eine freie Fl&auml;che und danach auf die einzelnen Fragen f&uuml;r die Klausur.</li>
			<li>Klicken Sie nun auf "ausgew&auml;hlte Fragen hinzuf&uuml;gen" und "Fertig".</li>
			<li>Wenn Sie nun speichern k&ouml;nnen Sie einen Export herunterladen, der auf dem ElateXam-Pr&uuml;fungssystem genutzt werden kann.</li>
			</ol>
			<br>
			Legen Sie eine Klausur an:
			<br><br>
					<form><input type="button" value=" Klausur erstellen " 
					onClick="window.open(\'../course/view.php?id=' . $courseid . '&task=edit\'); return false;"></form>	<br>
			
			
			</p>
			</div>
			<h3>5. Fortgeschrittene Bedienung</h3>
			<div>
			<p>
			Andere Fragetypen anlegen, tabellarische Auflistung aller Fragen, mit Suchfunktion, 
			Editierfunktion, Verschiebeoption zwischen Kategorien und L&ouml;schm&ouml;glichkeit: <br>
					<form><input type="button" value="' . get_string('open_question_bank', 'format_elatexam') . '" 
					onClick="window.open(\'edit.php?courseid=' . $courseid . '\'); return false;"></form>
					<br>
			Kategorien auflisten, neu anordnen, editieren, l&ouml;schen oder mit Beschreibungstexten versehen: <br><br>
					<form><input type="button" value="Kategorien verwalten" 
					onClick="window.open(\'category.php?courseid=' . $courseid . '\'); return false;"></form>
					<br>
					Fragen aus dem Moodle (1.9) f&uuml;r den Lehrbetrieb importieren:
					Die Fragen m&uuml;ssen zuvor von dort exporiert worden sein und auf Ihrem PC vorliegen. W&auml;hlen Sie daf&uuml;r das Moodle-XML-Format.<br><br>
					<form><input type="button" value="Fragen importieren" 
					onClick="window.open(\'import.php?courseid=' . $courseid . '\'); return false;"></form>
					<br>					
					Den Fragenpool f&uuml;r eigene Sicherungen exportieren:
					W&auml;hlen Sie daf&uuml;r das Moodle-XML-Format.<br><br>
					<form><input type="button" value="Fragen exportieren" 
					onClick="window.open(\'export.php?courseid=' . $courseid . '\'); return false;"></form>
					<br>
					</div>
			</div>';			
    }

    /**
     * Outputs a table to allow entry of a new category
     */
    public function output_new_table() {
        $this->catform->display();
    }

    /**
     * Outputs a list to allow editing/rearranging of existing categories
     *
     * $this->initialize() must have already been called
     *
     */
    public function output_edit_lists() {
        global $OUTPUT;

        echo $OUTPUT->heading_with_help(get_string('editcategories', 'question'), 'editcategories', 'question');

        foreach ($this->editlists as $context => $list){
            $listhtml = $list->to_html(0, array('str'=>$this->str));
            if ($listhtml){
                echo $OUTPUT->box_start('boxwidthwide boxaligncenter generalbox questioncategories contextlevel' . $list->context->contextlevel);
                echo $OUTPUT->heading(get_string('questioncatsfor', 'question', print_context_name(get_context_instance_by_id($context))), 3);
                echo $listhtml;
                echo $OUTPUT->box_end();
            }
        }
        echo $list->display_page_numbers();
     }

    /**
     * gets all the courseids for the given categories
     *
     * @param array categories contains category objects in  a tree representation
     * @return array courseids flat array in form categoryid=>courseid
     */
    public function get_course_ids($categories) {
        $courseids = array();
        foreach ($categories as $key=>$cat) {
            $courseids[$key] = $cat->course;
            if (!empty($cat->children)) {
                $courseids = array_merge($courseids, $this->get_course_ids($cat->children));
            }
        }
        return $courseids;
    }

    public function edit_single_category($categoryid) {
    /// Interface for adding a new category
        global $COURSE, $DB;
        /// Interface for editing existing categories
        if ($category = $DB->get_record("question_categories", array("id" => $categoryid))) {

            $category->parent = "$category->parent,$category->contextid";
            $category->submitbutton = get_string('savechanges');
            $category->categoryheader = $this->str->edit;
            $this->catform->set_data($category);
            $this->catform->display();
        } else {
            print_error('invalidcategory', '', '', $categoryid);
        }
    }

    /**
     * Sets the viable parents
     *
     *  Viable parents are any except for the category itself, or any of it's descendants
     *  The parentstrings parameter is passed by reference and changed by this function.
     *
     * @param    array parentstrings a list of parentstrings
     * @param   object category
     */
    public function set_viable_parents(&$parentstrings, $category) {

        unset($parentstrings[$category->id]);
        if (isset($category->children)) {
            foreach ($category->children as $child) {
                $this->set_viable_parents($parentstrings, $child);
            }
        }
    }

    /**
     * Gets question categories
     *
     * @param    int parent - if given, restrict records to those with this parent id.
     * @param    string sort - [[sortfield [,sortfield]] {ASC|DESC}]
     * @return   array categories
     */
    public function get_question_categories($parent=null, $sort="sortorder ASC") {
        global $COURSE, $DB;
        if (is_null($parent)) {
            $categories = $DB->get_records('question_categories', array('course' => $COURSE->id), $sort);
        } else {
            $select = "parent = ? AND course = ?";
            $categories = $DB->get_records_select('question_categories', $select, array($parent, $COURSE->id), $sort);
        }
        return $categories;
    }

    /**
     * Deletes an existing question category
     *
     * @param int deletecat id of category to delete
     */
    public function delete_category($categoryid) {
        global $CFG, $DB;
        question_can_delete_cat($categoryid);
        if (!$category = $DB->get_record("question_categories", array("id" => $categoryid))) {  // security
            print_error('unknowcategory');
        }
        /// Send the children categories to live with their grandparent
        $DB->set_field("question_categories", "parent", $category->parent, array("parent" => $category->id));

        /// Finally delete the category itself
        $DB->delete_records("question_categories", array("id" => $category->id));
    }

    public function move_questions_and_delete_category($oldcat, $newcat){
        question_can_delete_cat($oldcat);
        $this->move_questions($oldcat, $newcat);
        $this->delete_category($oldcat);
    }

    public function display_move_form($questionsincategory, $category){
        global $OUTPUT;
        $vars = new stdClass();
        $vars->name = $category->name;
        $vars->count = $questionsincategory;
        echo $OUTPUT->box(get_string('categorymove', 'question', $vars), 'generalbox boxaligncenter');
        $this->moveform->display();
    }

    public function move_questions($oldcat, $newcat){
        global $DB;
        $questionids = $DB->get_records_select_menu('question',
                'category = ? AND (parent = 0 OR parent = id)', array($oldcat), '', 'id,1');
        question_move_questions_to_category(array_keys($questionids), $newcat);
    }

    /**
     * Creates a new category with given params
     */
    public function add_category($newparent, $newcategory, $newinfo, $return = false) {
        global $DB;
        if (empty($newcategory)) {
            print_error('categorynamecantbeblank', 'question');
        }
        list($parentid, $contextid) = explode(',', $newparent);
        //moodle_form makes sure select element output is legal no need for further cleaning
        require_capability('moodle/question:managecategory', get_context_instance_by_id($contextid));

        if ($parentid) {
            if(!($DB->get_field('question_categories', 'contextid', array('id' => $parentid)) == $contextid)) {
                print_error('cannotinsertquestioncatecontext', 'question', '', array('cat'=>$newcategory, 'ctx'=>$contextid));
            }
        }

        $cat = new stdClass();
        $cat->parent = $parentid;
        $cat->contextid = $contextid;
        $cat->name = $newcategory;
        $cat->info = $newinfo;
        $cat->sortorder = 999;
        $cat->stamp = make_unique_id_code();
        $categoryid = $DB->insert_record("question_categories", $cat);
        if ($return) {
            return $categoryid;
        } else {
            redirect($this->pageurl);//always redirect after successful action
        }
    }

    /**
     * Updates an existing category with given params
     */
    public function update_category($updateid, $newparent, $newname, $newinfo) {
        global $CFG, $DB;
        if (empty($newname)) {
            print_error('categorynamecantbeblank', 'question');
        }

        // Get the record we are updating.
        $oldcat = $DB->get_record('question_categories', array('id' => $updateid));
        $lastcategoryinthiscontext = question_is_only_toplevel_category_in_context($updateid);

        if (!empty($newparent) && !$lastcategoryinthiscontext) {
            list($parentid, $tocontextid) = explode(',', $newparent);
        } else {
            $parentid = $oldcat->parent;
            $tocontextid = $oldcat->contextid;
        }

        // Check permissions.
        $fromcontext = get_context_instance_by_id($oldcat->contextid);
        require_capability('moodle/question:managecategory', $fromcontext);

        // If moving to another context, check permissions some more.
        if ($oldcat->contextid != $tocontextid) {
            $tocontext = get_context_instance_by_id($tocontextid);
            require_capability('moodle/question:managecategory', $tocontext);
        }

        // Update the category record.
        $cat = new stdClass();
        $cat->id = $updateid;
        $cat->name = $newname;
        $cat->info = $newinfo;
        $cat->parent = $parentid;
        $cat->contextid = $tocontextid;
        $DB->update_record('question_categories', $cat);

        // If the category name has changed, rename any random questions in that category.
        if ($oldcat->name != $cat->name) {
            $where = "qtype = 'random' AND category = ? AND " . $DB->sql_compare_text('questiontext') . " = ?";

            $randomqtype = question_bank::get_qtype('random');
            $randomqname = $randomqtype->question_name($cat, false);
            $DB->set_field_select('question', 'name', $randomqname, $where, array($cat->id, '0'));

            $randomqname = $randomqtype->question_name($cat, true);
            $DB->set_field_select('question', 'name', $randomqname, $where, array($cat->id, '1'));
        }

        if ($oldcat->contextid != $tocontextid) {
            // Moving to a new context. Must move files belonging to questions.
            question_move_category_to_context($cat->id, $oldcat->contextid, $tocontextid);
        }

        redirect($this->pageurl);
    }
}
