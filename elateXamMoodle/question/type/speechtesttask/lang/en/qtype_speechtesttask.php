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
 * Strings for component 'qtype_speechtesttask', language 'en', branch 'MOODLE_20_STABLE'
 *
 * @package    qtype
 * @subpackage speechtesttask
 * @license    http://www.gnu.org/copyleft/gpl.html GNU GPL v3 or later
 */

$string['pluginname'] = 'Speechtest';
$string['pluginname_help'] = 'TODO:SummaryEng';
$string['pluginname_link'] = 'question/type/speechtesttask';
$string['pluginnameadding'] = 'Adding a speechtest question';
$string['pluginnameediting'] = 'Editing a speechtest question';
$string['pluginnamesummary'] = 'Plays a MP3 and schows MC-Questions afterwards';

////////// Strings for the Question Selection Form //////////////////////

$string['qheader'] = 'Question';
$string['correct'] = ' is correct';
$string['playCount'] = 'Playcount';
$string['playCount_help'] = 'Defines, how often the MP3 will/can be played.';
$string['filePathMP3'] = 'Link to MP3-File';
$string['filePathMP3_help'] = 'Link to MP3-File. Either relative or absolute.';
$string['delayForAudioInSeconds'] = 'Delay till MP3-play (s)';
$string['delayForAudioInSeconds_help'] = 'Delay till MP3-play in seconds. Default: 20 seconds.';
$string['maximumTimeForTask'] = 'Maximum time for this task (mm:ss)';
$string['maximumTimeForTask_help'] = 'Maximum time for this task (mm:ss), e.g. "05:30" or "10:00" or "5". When no seconds are givven, we assume minutes.';
$string['timetostartreplay'] = 'Time to click replay after audio is over (s)';
$string['timetostartreplay_help'] = 'Time to click replay after audio is over in seconds.';
$string['addquestionbtn'] = 'Add another Question Block';
//$string['addanswerbtn'] = 'Add another possible Answer';