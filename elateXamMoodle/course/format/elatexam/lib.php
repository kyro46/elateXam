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
 * This file contains general functions for the course format ElateXam
 *
 * @since 2.0
 * @package moodlecore
 * @license   http://www.gnu.org/copyleft/gpl.html GNU GPL v3 or later
 */

/**
 * The string that is used to describe a section of the course
 * e.g. Topic, Week...
 *
 * @return string
 */
function callback_elatexam_definition() {
	return get_string('elatexam');
}

/**
 * Toggle whether this format uses sections.
 *
 * @return bool
 */
function callback_elatexam_uses_sections() {
	return false;
}

/**
 * Toogle display of course contents (sections, activities)
 *
 * @return bool
 */
function callback_elatexam_display_content() {
	return false;
}