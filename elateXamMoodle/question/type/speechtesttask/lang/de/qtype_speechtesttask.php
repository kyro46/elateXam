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

$string['pluginname'] = 'Hörverstehen';
$string['pluginname_help'] = 'TODO:SummaryGerName';
$string['pluginname_link'] = 'question/type/speechtesttask';
$string['pluginnameadding'] = 'Hinzufügen einer Hörverstehen-Frage';
$string['pluginnameediting'] = 'Bearbeiten einer Hörverstehen-Frage';
$string['pluginnamesummary'] = 'Spielt eine MP3-Datei ab und zeigt anschließend MC-Fragen an';

////////// Strings for the Question Selection Form //////////////////////

$string['qheader'] = 'Frage';
$string['correct'] = ' ist korrekt';
$string['playCount'] = 'Anzahl der Wiedergaben';
$string['playCount_help'] = 'Gibt an, wie oft die MP3-Datei insgesamt abgespielt werden kann.';
$string['filePathMP3'] = 'Link zur MP3-Datei';
$string['filePathMP3_help'] = 'Ein relativer Link auf eine Datei auf dem Prüfungsserver oder ein Direktlink zu einer MP3-Datei an einem anderen Ort im Netz (der Prüfungsserver muss darauf zugreifen können).';
$string['delayForAudioInSeconds'] = 'Zeit bis zur MP3-Wiedergabe (s)';
$string['delayForAudioInSeconds_help'] = 'Zeit bis zur MP3-Wiedergabe nach dem Aufruf der Aufgabe in Sekunden. Vorgabewert: 20 Sekunden.';
$string['maximumTimeForTask'] = 'Maximale Bearbeitungszeit (mm:ss)';
$string['maximumTimeForTask_help'] = 'Zeitfenster bis zur automatischen Weiterleitung zum nächsten Aufgabenkomplex. Angabe in Minuten und Sekunden. (Beispiel:"6:30" oder "03:00" oder "5". Werden keine Sekunden angegeben wird die Zahl als Minutenangabe interpretiert.)';
$string['timetostartreplay'] = 'Zeitfenster bis zur Antworteinblendung nach MP3-Wiedergabeende (s)';
$string['timetostartreplay_help'] = 'Zeitfenster, nach dem die Antwortalternativen angezeigt werden und das somit für die erneute MP3-Wiedergabe nach Wiedergabeende genutzt werden kann. Vorgabewert: 3 Sekunden.';
$string['isRestricted'] = 'Restriktive Ablaufumgebung (Prüfungsserver) aktivieren';
$string['isRestricted_help'] = 'Prüfungsserver: Freie Navigation deaktivieren und Zeitlimit pro Fragenkomplex setzen. Ist dies aktiv, so müssen alle anderen Fragen der Klausur diese Option ebenfalls nutzen.';
$string['addquestionbtn'] = 'Weiteren Fragenblock hinzufügen';
$string['addanswerbtn'] = 'Weitere Antwortmöglichkeit hinzufügen';