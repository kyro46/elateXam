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
 * Strings for component 'format_elatexam', language 'en', branch 'MOODLE_20_STABLE'
 *
 * @package   format_elatexam
 * @license   http://www.gnu.org/copyleft/gpl.html GNU GPL v3 or later
 */

$string['pluginname'] = 'ElateXam Format';
////////// Strings for modifed questionbank //////////////////////
$string['copyto'] = 'Kopieren nach >>';
$string['empty_search'] = 'Kein Ergebnis für die aktuelle Suche.';
$string['customize_table'] = 'Tabelleninformationen anpassen: ';
$string['search'] = 'suchen';
$string['reset'] = 'zurücksetzen';
$string['reset_search'] = 'Suche zurücksetzen';
$string['search_text'] = 'Suchtext (jedes Wort muss für eine Frage in einer der ausgewählten Tabellenspalten vorkommen)';
$string['search_every_cat'] = ' in allen Kategorien suchen ';
$string['tags'] = 'Schlagworte';
$string['category'] = 'Kategorie';

////////// Strings for examexport //////////////////////
$string['random_sort'] = 'zufällige Reihenfolge';
$string['num_shown'] = 'Aufgabenanzahl';
$string['exam_saved'] = "Klausur gespeichert... <br />";
$string['exam_xml_exported'] = "XML-Export erfolgt... ";
$string['download_export'] = "Klausurexport herunterladen";
$string['exam_xhtml_exported'] = "HTML-Export erfolgt... ";
$string['show_export'] = "(Druck-)Vorschau anzeigen";
$string['continue_to_exams'] = "Weiter zur Klausurübersicht...";
$string['no_export'] = "Kein Export erfolgt (keine Kategorien und Fragen zum Exportieren erstellt).";

$string['exams'] = "Klausuren";
$string['exam_groups'] = "Klausurgruppen";
$string['open_question_bank'] = "Fragensammlung öffnen";
$string['create_exam_group'] = "neue Klausur(gruppe) erstellen";
$string['edit_and_export'] = "bearbeiten &amp; exportieren";
$string['confirm_remove_exam_group'] = "Wollen Sie diese Klausurgruppe und ihre Exporte löschen?";
$string['remove_exam_group'] = "Klausurgruppe löschen";
$string['exported_on'] = "exportiert am";
$string['remove_export'] = "Export löschen";
$string['confirm_remove_export'] = "Wollen Sie diesen Klausurexport löschen?";
$string['tasks'] = "Aufgaben";
$string['random'] = 'zufällig';
$string['deny_task_by_type_point'] = "Es können nur Aufgaben mit der gleichen Punktzahl und dem gleichen Aufgabentyp zu einer Auswahlkategorie hinzugefügt werden.";
$string['move_to_another_category'] = "in andere Kategorie verschieben";
$string['choice_category_desc'] = "Bei einer Auswahlkategorie wird in der Prüfung aus mehreren hinterlegten Fragen die angegebene Anzahl zufällig ausgewählt. In einer Auswahlkategorie sind nur Fragen mit gleicher Punktzahl und dem selben Fragentyp möglich.";
$string['add_questions'] = "Aufgaben zu dieser Kategorie hinzufügen";
$string['del_category'] = "Kategorie löschen";
$string['confirm_del_category'] = "Wollen Sie diese Kategorie mit allen Unterkategorien und Aufgaben wirklich löschen?";
$string['create_category'] = "Neue Kategorie erstellen";
$string['points'] = "Punkte";
$string['all_points'] = "Gesamtpunktzahl";
$string['question_changed'] = "Die Frage wurde seit dem geladenen Klausur-Export verändert.";
$string['del_question'] = "Aufgabe entfernen";
$string['confirm_del_question'] = "Wollen Sie diese Aufgabe wirklich löschen?";
$string['move_question'] = "in andere Kategorie verschieben";
$string['question_doesnt_exist'] = "Die Frage existiert nicht mehr ";
$string['edit_exam'] = "Klausur bearbeiten";
$string['back_exam_groups'] = "zurück zu den Klausurgruppen";
$string['load_export'] = "Exportierte Klausur laden";
$string['category_validate'] = "Die Bezeichnung für eine Kategorie muss mindestens 1 Zeichen lang sein.";
$string['catname'] = "Bezeichnung";
$string['default_category'] = "Standardkategorie";
$string['choice_category'] = "Auswahlkategorie";
$string['add_selected_questions'] = "ausgewählte Fragen hinzufügen";
$string['done'] = "Fertig";
$string['catname_not_include'] = "Der Kategoriename darf weder ; noch | enthalten.";
$string['cancel'] = "Abbrechen";
$string['new_category'] = "neue Kategorie";
$string['specify_search'] = "Suche eingrenzen";
$string['all'] = "alle";
$string['none'] = "keine";
$string['selected'] = "ausgewählt";
$string['select_question_categories'] = "Kategorien von Fragen auswählen";
$string['compose_exam'] = "Klausur zusammenstellen";
$string['save_and_export_exam'] = "Klausur speichern und neuen Export erstellen";

////////// Strings for Questionlib //////////////////////
$string['generalfeedback'] = 'Hinweistext';
$string['generalfeedback_help'] = 'Lösungshinweis, der während der Prüfung angezeigt wird (optional).';
$string['correctorfeedback'] = 'Musterlösung';
$string['penaltyforeachincorrecttry'] = "Negative Punkte für falsche Antworten";
$string['penaltyforeachincorrecttry_help'] = "Genauer: Anzahl an Punkten, die bei einer falschen Antwort nicht vergeben werden.";
$string['right'] = "richtig";
$string['wrong'] = "falsch";

////////// Modified or added Strings for MC //////////////////////
$string['num_shown'] = 'Anzahl anzuzeigender Antworten insgesamt';
$string['num_shown_help'] = 'Der Standardwert (0) bedeutet, dass alle Antwortmöglichkeiten angezeigt werden.';
$string['num_right_min'] = 'Minimale Anzahl anzuzeigender richtiger Antworten';
$string['num_right_max'] = 'Maximale Anzahl anzuzeigender richtiger Antworten';
$string['penalty_empty'] = 'Negative Punkte für nicht gewählte richtige Antworten';
$string['penalty_wrong'] = 'Negative Punkte für gewählte Falschantwort';
$string['penaltyheader'] = 'Einstellungen für Punkteabzug';
$string['assessment_reg'] = 'Reguläre Bewertung';
$string['assessment_dif'] = 'Unterschiedliche Bewertung';

////////// Modified or added Strings for Essay/FreeText //////////////////////
$string['responsefieldlines'] = 'Höhe des Textfeldes';
$string['responsefieldwidth'] = 'Breite des Textfeldes';
$string['initialtextfieldvalue'] = 'Textvorbelegung';

////////// Modified or added Strings for MultiAnswer/CLOZE //////////////////////
$string['casesensitivity'] = 'Berücksichtigung von Groß- und Kleinschreibung';

