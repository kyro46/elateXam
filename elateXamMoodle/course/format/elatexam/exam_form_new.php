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
 * Defines the editing form for an Exam Instance
 * 
 * @author C.Wilhelm, KLW
 * @license	http://www.gnu.org/copyleft/gpl.html GNU GPL v3 or later
 */

defined('MOODLE_INTERNAL') || die();
require_once($CFG->dirroot . '/lib/formslib.php');
//require_once($CFG->dirroot . '/course/format/elatexam/folder_view.php');

class exam_form extends moodleform {
    
    /**
     * elements of form
     */
	protected function definition() {
		$mform = &$this->_form;
		//$categoryview = new category_view($this);

		///// Add fields from legacy "meta" question type  //////////

		$mform->addElement('header', 'generalheader', 'Klausurbedingungen');//get_string("general", 'form'));
        
        $mform->addElement('hidden', 'examid', $this->_customdata->id);
        $mform->addElement('hidden', 'examdata', '');
        $mform->addElement('hidden', 'courseid', $this->_customdata->courseid);
        
		$mform->addElement('text', 'title', get_string('title', 'qtype_meta'));
        $mform->setDefault('title', $this->_customdata->title);
		//$mform->addHelpButton('title', 'title', 'qtype_meta');

		$mform->addElement('text', 'time', get_string('time', 'qtype_meta'), ' style="width:35px;"');
		$mform->setType('time', PARAM_INT);
        $mform->setDefault('time', $this->_customdata->time);
		//$mform->addHelpButton('time', 'time', 'qtype_meta');

		$mform->addElement('text', 'kindnessextensiontime', get_string('kindnessextensiontime', 'qtype_meta'), ' style="width:35px;"');
		$mform->setType('kindnessextensiontime', PARAM_INT);
		$mform->setDefault('kindnessextensiontime', $this->_customdata->kindnessextensiontime);
		//$mform->addHelpButton('kindnessextensiontime', 'kindnessextensiontime', 'qtype_meta');

		$mform->addElement('text', 'tasksperpage', get_string('tasksperpage', 'qtype_meta'), ' style="width:35px;"');
		$mform->setType('tasksperpage', PARAM_INT);
		$mform->setDefault('tasksperpage', $this->_customdata->tasksperpage);
		//$mform->addHelpButton('tasksperpage', 'tasksperpage', 'qtype_meta');

		$mform->addElement('text', 'tries', get_string('tries', 'qtype_meta'), ' style="width:35px;"');
		$mform->setType('tries', PARAM_INT);
		$mform->setDefault('tries', $this->_customdata->tries);
		//$mform->addHelpButton('tries', 'tries', 'qtype_meta');

		$mform->addElement('editor', "description", get_string('description', 'qtype_meta'), array('rows' => 8));
        $mform->setDefault('description', array('text'=>$this->_customdata->description, 'format'=>FORMAT_HTML));
		//$mform->addHelpButton('description', 'description', 'qtype_meta');

		$mform->addElement('advcheckbox', 'showhandlinghintsbeforestart', get_string('showhandlinghintsbeforestart', 'qtype_meta'), "");
		$mform->setDefault('showhandlinghintsbeforestart', $this->_customdata->showhandlinghintsbeforestart);

		$mform->addElement('editor', "starttext", get_string('starttext', 'qtype_meta'), array('rows' => 6));
        $mform->setDefault('starttext', array('text'=>$this->_customdata->starttext, 'format'=>FORMAT_HTML));
		//$mform->addHelpButton('starttext', 'starttext', 'qtype_meta');

		$mform->addElement('text', 'numberofcorrectors', get_string('numberofcorrectors', 'qtype_meta'), ' style="width:35px;"');
		$mform->setType('numberofcorrectors', PARAM_INT);
		$mform->setDefault('numberofcorrectors', $this->_customdata->numberofcorrectors);
		//$mform->addHelpButton('numberofcorrectors', 'numberofcorrectors', 'qtype_meta');

		////// Set Fields which are required to fill out //////////
		//$mform->addRule('description', null, 'required', null, 'client');
		$mform->addRule('time', null, 'required', null, 'client');

		////// Add Question Selector  //////////
		
		$mform->addElement('header', 'qheader', 'Klausur zusammenstellen');//get_string('qheader', 'qtype_meta'));
        $mform->addElement('html', $this->get_category_tree());
		//$mform->addElement('html', $categoryview->get_list_html());
		/*if(category_view::not_empty())
			$mform->addElement('html', $this->get_question_bank()->get_html());
		else $mform->addElement('html', $this->get_question_bank()->get_hidden_html());*/
        //$mform->addElement('html', html_writer::input_hidden_params($this->_customdata));
		////// Add Submit / Cancel Buttons  //////////
		$buttonarray=array();
		$buttonarray[] =& $mform->createElement('submit', 'submitbutton', 'Klausur speichern und neuen Export erstellen');//get_string('savechanges'));
		$buttonarray[] =& $mform->createElement('cancel');
		$mform->addGroup($buttonarray, 'buttonar', '', array(' '), false);
		$mform->closeHeaderBefore('buttonar');
	}

	/**
	 * overridden to prevent submission when clicked on certain buttons
	 * @see moodleform::no_submit_button_pressed()
	 */
	public function no_submit_button_pressed() {
		if(count(preg_grep('/^(move_|delanswerbtn_|add|remove|use_|change_).*/', array_keys($_POST))) > 0)
			return true;
		return parent::no_submit_button_pressed();
	}
    
    private function get_category_tree(){
        return 
            '<div class="exam_side">
                <div class="examcontainer">
                </div>
                '//<label for="pointsSpinner">Nachkommastellen für Punkte:</label>
                //<input id="pointsSpinner" name="pointsSpinner" value="1" />
            .'</div>';
    }
	/*public function get_question_bank() {
		return $this->_customdata; // stored as such
	}*/
}