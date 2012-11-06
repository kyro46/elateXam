<?php
class question_bank_autor_column extends question_bank_column_base {
    protected $metaname = 'autor';
    protected $metatitle = 'Autor';
    protected $tbl_shortcut = 'at';
	
    public function get_name() {
		return $this->metaname;
	}
	protected function get_title() {
		return $this->metatitle;
	}
    public function get_extra_joins() {
        return array($this->tbl_shortcut => "LEFT JOIN (".
                                 "SELECT ti.itemid, tg.rawname AS $this->metaname FROM {tag_instance} ti ".
                                 "LEFT JOIN {tag} tg ON ti.tagid = tg.id WHERE ti.itemtype = 'question' AND tg.name LIKE '%$this->metaname=%'".
                             ") $this->tbl_shortcut ON $this->tbl_shortcut.itemid = q.id");
    }

    public function get_required_fields() {
        return array($this->tbl_shortcut.".".$this->metaname);
    }
    public function is_sortable() {
        return $this->tbl_shortcut.".".$this->metaname;
    }
    protected function display_content($question, $rowclasses) {
        $name = $this->metaname;
        echo str_replace($this->metaname.'=','',$question->$name);
    }
}
class question_bank_schwierigkeit_column extends question_bank_column_base {
    protected $metaname = 'schwierigkeit';
    protected $metatitle = 'Schwierigkeit';
    protected $tbl_shortcut = 'sc';
	public function get_name() {
		return $this->metaname;
	}
	protected function get_title() {
		return $this->metatitle;
	}
    public function get_extra_joins() {
        return array($this->tbl_shortcut => "LEFT JOIN (".
                                 "SELECT ti.itemid, tg.rawname AS $this->metaname FROM {tag_instance} ti ".
                                 "LEFT JOIN {tag} tg ON ti.tagid = tg.id WHERE ti.itemtype = 'question' AND tg.name LIKE '%$this->metaname=%'".
                             ") $this->tbl_shortcut ON $this->tbl_shortcut.itemid = q.id");
    }

    public function get_required_fields() {
        return array($this->tbl_shortcut.".".$this->metaname);
    }
    public function is_sortable() {
        return $this->tbl_shortcut.".".$this->metaname;
    }
	protected function display_content($question, $rowclasses) {
        $name = $this->metaname;
        echo str_replace($this->metaname.'=','',$question->$name);
	}
}
class question_bank_tags_column extends question_bank_column_base {
	public function get_name() {
		return 'tags';
	}
	protected function get_title() {
		return "Schlagworte";
	}
    public function get_extra_joins() {
        return array('tc' => "LEFT JOIN (".
                                 "SELECT ti.itemid, GROUP_CONCAT(tg.rawname SEPARATOR ', ') AS tags FROM {tag_instance} ti ".
                                 "LEFT JOIN {tag} tg ON ti.tagid = tg.id WHERE ti.itemtype = 'question' AND tg.name NOT LIKE '%=%' GROUP BY ti.itemid".
                             ") tc ON tc.itemid = q.id");
    }

    public function get_required_fields() {
        return array('tc.tags');
    }
	protected function display_content($question, $rowclasses) {
        echo '<div class="tags_container">'.$question->tags.'</div>';
	}
}
?>