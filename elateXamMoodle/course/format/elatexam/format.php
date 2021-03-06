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
 * ElateXam course format.  Display the whole course as "elatexam" made of modules.
 *
 * @package format_elatexam
 * @license http://www.gnu.org/copyleft/gpl.html GNU GPL v3 or later
 */

defined('MOODLE_INTERNAL') || die();

$context = context_course::instance($course->id);
if (optional_param('alive', 0, PARAM_INT) != 1) {//keeping_session_alive
    if (($marker >=0) && has_capability('moodle/course:setcurrentsection', $context) && confirm_sesskey()) {
        $course->marker = $marker;
        course_set_marker($course->id, $marker);
    }
    
    $renderer = $PAGE->get_renderer('format_elatexam');
    
    $renderer->print_multiple_section_page($course, $sections, $mods, $modnames, $modnamesused);
}