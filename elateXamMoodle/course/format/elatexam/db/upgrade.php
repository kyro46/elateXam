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
 * Version details
 *
 * @package    format
 * @subpackage elatexam
 * @license    http://www.gnu.org/copyleft/gpl.html GNU GPL v3 or later
 */

defined('MOODLE_INTERNAL') || die();

function xmldb_format_elatexam_upgrade($oldversion = 0) {
    global $DB;
    $dbman = $DB->get_manager();
	
	if ($oldversion < 2013010401) {
		// Define table exam to be dropped
        $table = new xmldb_table('exam_category_question');

        // Conditionally launch drop table for exam
        if ($dbman->table_exists($table)) {
            $dbman->drop_table($table);
        }
		
		// Define table exam to be dropped
        $table = new xmldb_table('exam_categories');

        // Conditionally launch drop table for exam
        if ($dbman->table_exists($table)) {
            $dbman->drop_table($table);
        }
		
		// Define table exam to be dropped
        $table = new xmldb_table('exam');

        // Conditionally launch drop table for exam
        if ($dbman->table_exists($table)) {
            $dbman->drop_table($table);
        }
	}
	
    if ($oldversion < 2013010401) {
		// Define table exam to be created
        $table = new xmldb_table('exam');

        // Adding fields to table exam
        $table->add_field('id', XMLDB_TYPE_INTEGER, '10', null, XMLDB_NOTNULL, XMLDB_SEQUENCE, null);
        $table->add_field('title', XMLDB_TYPE_CHAR, '255', null, XMLDB_NOTNULL, null, null);
        $table->add_field('time', XMLDB_TYPE_INTEGER, '4', null, XMLDB_NOTNULL, null, null);
        $table->add_field('kindnessextensiontime', XMLDB_TYPE_INTEGER, '4', null, XMLDB_NOTNULL, null, null);
        $table->add_field('tasksperpage', XMLDB_TYPE_INTEGER, '4', null, XMLDB_NOTNULL, null, null);
        $table->add_field('tries', XMLDB_TYPE_INTEGER, '4', null, XMLDB_NOTNULL, null, null);
        $table->add_field('showhandlinghintsbeforestart', XMLDB_TYPE_INTEGER, '2', null, null, null, null);
        $table->add_field('numberofcorrectors', XMLDB_TYPE_INTEGER, '4', null, XMLDB_NOTNULL, null, null);
        $table->add_field('description', XMLDB_TYPE_TEXT, null, null, XMLDB_NOTNULL, null, null);
        $table->add_field('starttext', XMLDB_TYPE_TEXT, null, null, XMLDB_NOTNULL, null, null);
        $table->add_field('courseid', XMLDB_TYPE_INTEGER, '10', null, XMLDB_NOTNULL, null, null);

        // Adding keys to table exam
        $table->add_key('primary', XMLDB_KEY_PRIMARY, array('id'));
        $table->add_key('courseid', XMLDB_KEY_FOREIGN, array('courseid'), 'course', array('id'));

        // Conditionally launch create table for exam
        if (!$dbman->table_exists($table)) {
            $dbman->create_table($table);
        }
	}
	if ($oldversion < 2013010401) {	
		// Define table exam_exports to be created
        $table = new xmldb_table('exam_exports');

        // Adding fields to table exam_exports
        $table->add_field('id', XMLDB_TYPE_INTEGER, '10', null, XMLDB_NOTNULL, XMLDB_SEQUENCE, null);
        $table->add_field('export_time', XMLDB_TYPE_INTEGER, '10', null, XMLDB_NOTNULL, null, null);
        $table->add_field('examid', XMLDB_TYPE_INTEGER, '10', null, XMLDB_NOTNULL, null, null);
        $table->add_field('structure', XMLDB_TYPE_TEXT, null, null, XMLDB_NOTNULL, null, null);

        // Adding keys to table exam_exports
        $table->add_key('primary', XMLDB_KEY_PRIMARY, array('id'));
        $table->add_key('examid', XMLDB_KEY_FOREIGN, array('examid'), 'exam', array('id'));

        // Conditionally launch create table for exam_exports
        if (!$dbman->table_exists($table)) {
            $dbman->create_table($table);
        }

        // elatexam savepoint reached
	}
	upgrade_plugin_savepoint(true, 2013010401, 'format', 'elatexam');
    return true;
}