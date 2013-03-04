/**
 * Programm zur Konvertierung von aus Moodle exportierten Übungsfragen (Moodle-XML)
 * in Elate ComplexTaskDef-XML.
 *
 * @author Christoph Jobst
 * @version 1.0
 */

package de.christophjobst.main;

import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.AddonTaskBlock;
import de.thorstenberger.taskmodel.complex.complextaskdef.AddonSubTaskDef;
import de.thorstenberger.taskmodel.complex.complextaskdef.ClozeSubTaskDef;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.McTaskBlock.McConfig.Regular;
import de.thorstenberger.taskmodel.complex.complextaskdef.Config;
import de.thorstenberger.taskmodel.complex.complextaskdef.McSubTaskDef;
import de.thorstenberger.taskmodel.complex.complextaskdef.TextSubTaskDef;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.ClozeTaskBlock;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.MappingTaskBlock;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.McTaskBlock;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.TextTaskBlock;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.ClozeTaskBlock.ClozeConfig;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.MappingTaskBlock.MappingConfig;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.McTaskBlock.McConfig;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.McTaskBlock.McConfig.Different;
import de.thorstenberger.taskmodel.complex.complextaskdef.MappingSubTaskDef;

public class CategoryManager {

	Category category = new Category();
	boolean hasTextTaskBlock = false;
	boolean hasMcTaskBlock = false;
	boolean hasClozeTaskBlock = false;
	boolean hasMappingTaskBlock = false;
	boolean hasAddonTaskBlock = false;

	public int defaultgrade = 0;

	String title;
	MappingTaskBlock mappingTaskBlock = new MappingTaskBlock();
	McTaskBlock mcTaskBlock = new McTaskBlock();
	TextTaskBlock textTaskBlock = new TextTaskBlock();
	ClozeTaskBlock clozeTaskBlock = new ClozeTaskBlock();
	AddonTaskBlock addonTaskBlock = new AddonTaskBlock();
	String num_shown = "-1";
	String tasktype = "default";

	// Für Auswahlkategorien (-> Klausurplugin) zusätzlich Anzahl und Typ
	// übernehmen
	// num_shown: wie viele Fragen in der Kategorie sollen angezeigt werden?
	// tasktype: Welchen Typs sind diese Fragen -> Nur 1 Taskblock benutzen
	public CategoryManager(Category category, String num_shown, String tasktype) {
		this.title = category.getTitle().toString();
		this.category = category;
		this.num_shown = num_shown;
		this.tasktype = tasktype;
	}

	public boolean isHasTextTaskBlock() {
		return hasTextTaskBlock;
	}

	public void setHasTextTaskBlock(boolean hasTextTaskBlock) {
		this.hasTextTaskBlock = hasTextTaskBlock;
	}

	public boolean isHasMcTaskBlock() {
		return hasMcTaskBlock;
	}

	public void setHasMcTaskBlock(boolean hasMcTaskBlock) {
		this.hasMcTaskBlock = hasMcTaskBlock;
	}

	public boolean isHasClozeTaskBlock() {
		return hasClozeTaskBlock;
	}

	public void setHasClozeTaskBlock(boolean hasClozeTaskBlock) {
		this.hasClozeTaskBlock = hasClozeTaskBlock;
	}

	public boolean isHasMappingTaskBlock() {
		return hasMappingTaskBlock;
	}

	public void setHasMappingTaskBlock(boolean hasMappingTaskBlock) {
		this.hasMappingTaskBlock = hasMappingTaskBlock;
	}

	public boolean isHasAddonTaskBlock() {
		return hasAddonTaskBlock;
	}

	public void setHasAddonTaskBlock(boolean hasAddonTaskBlock) {
		this.hasAddonTaskBlock = hasAddonTaskBlock;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setMappingTaskBlock(MappingSubTaskDef mappingSubTaskDef,
			String defaultgrade) {

		// Einfach alles nehmen und mit versch. Bepunktung etc. eintragen.
		if (num_shown.equals("-1")) {
			this.mappingTaskBlock = new MappingTaskBlock();
			Config generalTaskBlockConfig = new Config();
			generalTaskBlockConfig.setNoOfSelectedTasks(1);
			generalTaskBlockConfig.setPointsPerTask(Float
					.parseFloat(defaultgrade));
			generalTaskBlockConfig.setPreserveOrder(false);
			this.mappingTaskBlock.setConfig(generalTaskBlockConfig);
			MappingConfig mappingConfig = new MappingConfig();
			// TODO NegativePoints aus Moodle beziehen
			mappingConfig.setNegativePoints(1);
			this.mappingTaskBlock.setMappingConfig(mappingConfig);

			this.mappingTaskBlock.getMappingSubTaskDefOrChoice().add(
					mappingSubTaskDef);

			category.getMcTaskBlockOrClozeTaskBlockOrTextTaskBlock().add(
					mappingTaskBlock);
		} else {
			// Nur einen einzgen Taskblock anlegen! Einheitliche Punkte, Anzahl
			// pro Prüfling festlegen.
			if (!hasMappingTaskBlock) {
				Config generalTaskBlockConfig = new Config();
				generalTaskBlockConfig.setNoOfSelectedTasks(Integer
						.parseInt(num_shown));
				generalTaskBlockConfig.setPointsPerTask(Float
						.parseFloat(defaultgrade));
				generalTaskBlockConfig.setPreserveOrder(false);
				this.mappingTaskBlock.setConfig(generalTaskBlockConfig);
				MappingConfig mappingConfig = new MappingConfig();
				// TODO NegativePoints aus Moodle beziehen
				mappingConfig.setNegativePoints(1);
				this.mappingTaskBlock.setMappingConfig(mappingConfig);

				this.mappingTaskBlock.getMappingSubTaskDefOrChoice().add(
						mappingSubTaskDef);
			} else {
				this.mappingTaskBlock.getMappingSubTaskDefOrChoice().add(
						mappingSubTaskDef);
			}
		}

	}

	public void setMcTaskBlock(McSubTaskDef mcSubTaskDef, String defaultgrade,
			Boolean assessmentmode, float penalty, float penaltyEmpty,
			float penaltyWrong) {

		// Einfach alles nehmen und mit versch. Bepunktung etc. eintragen.
		if (num_shown.equals("-1")) {
			mcTaskBlock = new McTaskBlock();
			Config generalTaskBlockConfig = new Config();
			generalTaskBlockConfig.setNoOfSelectedTasks(1);
			generalTaskBlockConfig.setPointsPerTask(Float
					.parseFloat(defaultgrade));
			generalTaskBlockConfig.setPreserveOrder(false);

			mcTaskBlock.setConfig(generalTaskBlockConfig);
			McConfig mcConfig = new McConfig();

			if (assessmentmode) {
				Different different = new Different();
				different.setCorrectAnswerNegativePoints(penaltyEmpty);
				different.setIncorrectAnswerNegativePoints(penaltyWrong);
				mcConfig.setDifferent(different);
			} else {
				Regular regular = new Regular();
				regular.setNegativePoints(penalty);
				mcConfig.setRegular(regular);
			}

			mcTaskBlock.setMcConfig(mcConfig);

			mcTaskBlock.getMcSubTaskDefOrChoice().add(mcSubTaskDef);

			category.getMcTaskBlockOrClozeTaskBlockOrTextTaskBlock().add(
					mcTaskBlock);
		} else {
			// Nur einen einzgen Taskblock anlegen! Einheitliche Punkte, Anzahl
			// pro Prüfling festlegen.
			if (!hasMcTaskBlock) {
				Config generalTaskBlockConfig = new Config();
				generalTaskBlockConfig.setNoOfSelectedTasks(Integer
						.parseInt(num_shown));
				generalTaskBlockConfig.setPointsPerTask(Float
						.parseFloat(defaultgrade));
				generalTaskBlockConfig.setPreserveOrder(false);

				mcTaskBlock.setConfig(generalTaskBlockConfig);
				McConfig mcConfig = new McConfig();
				if (assessmentmode) {
					Different different = new Different();
					different.setCorrectAnswerNegativePoints(penaltyEmpty);
					different.setIncorrectAnswerNegativePoints(penaltyWrong);
					mcConfig.setDifferent(different);
				} else {
					Regular regular = new Regular();
					regular.setNegativePoints(penalty);
					mcConfig.setRegular(regular);
				}
				mcTaskBlock.setMcConfig(mcConfig);

				mcTaskBlock.getMcSubTaskDefOrChoice().add(mcSubTaskDef);

			} else {
				mcTaskBlock.getMcSubTaskDefOrChoice().add(mcSubTaskDef);
			}
		}
	}

	public void setTextTaskBlock(TextSubTaskDef textSubTaskDef,
			String defaultgrade) {

		// Einfach alles nehmen und mit versch. Bepunktung etc. eintragen.
		if (num_shown.equals("-1")) {
			textTaskBlock = new TextTaskBlock();
			Config generalTaskBlockConfig = new Config();
			generalTaskBlockConfig.setNoOfSelectedTasks(1);
			generalTaskBlockConfig.setPointsPerTask(Float
					.parseFloat(defaultgrade));
			generalTaskBlockConfig.setPreserveOrder(false);

			textTaskBlock.setConfig(generalTaskBlockConfig);

			textTaskBlock.getTextSubTaskDefOrChoice().add(textSubTaskDef);

			category.getMcTaskBlockOrClozeTaskBlockOrTextTaskBlock().add(
					textTaskBlock);
		} else {
			// Nur einen einzgen Taskblock anlegen! Einheitliche Punkte, Anzahl
			// pro Prüfling festlegen.
			if (!hasTextTaskBlock) {
				Config generalTaskBlockConfig = new Config();
				generalTaskBlockConfig.setNoOfSelectedTasks(Integer
						.parseInt(num_shown));
				generalTaskBlockConfig.setPointsPerTask(Float
						.parseFloat(defaultgrade));
				generalTaskBlockConfig.setPreserveOrder(false);

				textTaskBlock.setConfig(generalTaskBlockConfig);

				textTaskBlock.getTextSubTaskDefOrChoice().add(textSubTaskDef);
			} else {
				textTaskBlock.getTextSubTaskDefOrChoice().add(textSubTaskDef);
			}
		}
	}

	public void setClozeTaskBlock(ClozeSubTaskDef clozeSubTaskDef,
			float defaultgrade, Boolean casesensitivity, float penalty) {

		float negativePoints = penalty;
		System.out.println(negativePoints);
		// if (negativePoints == 0) {
		// negativePoints = 1.0f;
		// }

		// Einfach alles nehmen und mit versch. Bepunktung etc. eintragen.
		if (num_shown.equals("-1")) {
			clozeTaskBlock = new ClozeTaskBlock();
			Config generalTaskBlockConfig = new Config();
			generalTaskBlockConfig.setNoOfSelectedTasks(1);
			generalTaskBlockConfig.setPointsPerTask(defaultgrade);
			generalTaskBlockConfig.setPreserveOrder(false);

			// Vorbereitung ClozeTaskBlock
			clozeTaskBlock.setConfig(generalTaskBlockConfig);
			ClozeConfig clozeConfig = new ClozeConfig();
			clozeConfig.setIgnoreCase(casesensitivity);
			clozeConfig.setNegativePoints(negativePoints);
			clozeTaskBlock.setClozeConfig(clozeConfig);

			clozeTaskBlock.getClozeSubTaskDefOrChoice().add(clozeSubTaskDef);

			category.getMcTaskBlockOrClozeTaskBlockOrTextTaskBlock().add(
					clozeTaskBlock);
		} else {
			// Nur einen einzgen Taskblock anlegen! Einheitliche Punkte, Anzahl
			// pro Prüfling festlegen.
			if (!hasClozeTaskBlock) {
				Config generalTaskBlockConfig = new Config();
				generalTaskBlockConfig.setNoOfSelectedTasks(Integer
						.parseInt(num_shown));
				generalTaskBlockConfig.setPointsPerTask(defaultgrade);
				generalTaskBlockConfig.setPreserveOrder(false);

				// Vorbereitung ClozeTaskBlock
				clozeTaskBlock.setConfig(generalTaskBlockConfig);
				ClozeConfig clozeConfig = new ClozeConfig();
				clozeConfig.setIgnoreCase(true);
				clozeConfig.setNegativePoints(negativePoints);
				clozeTaskBlock.setClozeConfig(clozeConfig);

				this.clozeTaskBlock.getClozeSubTaskDefOrChoice().add(
						clozeSubTaskDef);
			} else {
				this.clozeTaskBlock.getClozeSubTaskDefOrChoice().add(
						clozeSubTaskDef);
			}
		}
	}

	public void setAddonTaskBlock(AddonSubTaskDef addonSubTaskDef,
			String defaultgrade) {

		// Einfach alles nehmen und mit versch. Bepunktung etc. eintragen.
		if (num_shown.equals("-1")) {
			addonTaskBlock = new AddonTaskBlock();
			Config generalTaskBlockConfig = new Config();
			generalTaskBlockConfig.setNoOfSelectedTasks(1);
			generalTaskBlockConfig.setPointsPerTask(Float
					.parseFloat(defaultgrade));
			generalTaskBlockConfig.setPreserveOrder(false);

			addonTaskBlock.setConfig(generalTaskBlockConfig);

			addonTaskBlock.getAddonSubTaskDefOrChoice().add(addonSubTaskDef);

			category.getMcTaskBlockOrClozeTaskBlockOrTextTaskBlock().add(
					addonTaskBlock);
		} else {
			// Nur einen einzgen Taskblock anlegen! Einheitliche Punkte, Anzahl
			// pro Prüfling festlegen.
			if (!hasAddonTaskBlock) {
				Config generalTaskBlockConfig = new Config();
				generalTaskBlockConfig.setNoOfSelectedTasks(Integer
						.parseInt(num_shown));
				generalTaskBlockConfig.setPointsPerTask(Float
						.parseFloat(defaultgrade));
				generalTaskBlockConfig.setPreserveOrder(false);

				addonTaskBlock.setConfig(generalTaskBlockConfig);

				addonTaskBlock.getAddonSubTaskDefOrChoice()
						.add(addonSubTaskDef);
			} else {
				addonTaskBlock.getAddonSubTaskDefOrChoice()
						.add(addonSubTaskDef);
			}
		}

	}

	public int getCategoryLength() {
		return category.getMcTaskBlockOrClozeTaskBlockOrTextTaskBlock()
				.toArray().length;
	}

	public Category getCategory() {

		if (!num_shown.equals("-1")) {

			if (hasMappingTaskBlock) {
				category.getMcTaskBlockOrClozeTaskBlockOrTextTaskBlock().add(
						mappingTaskBlock);

			}
			if (hasMcTaskBlock) {
				category.getMcTaskBlockOrClozeTaskBlockOrTextTaskBlock().add(
						mcTaskBlock);

			}
			if (hasTextTaskBlock) {
				category.getMcTaskBlockOrClozeTaskBlockOrTextTaskBlock().add(
						textTaskBlock);

			}
			if (hasClozeTaskBlock) {
				category.getMcTaskBlockOrClozeTaskBlockOrTextTaskBlock().add(
						clozeTaskBlock);

			}
			if (hasAddonTaskBlock) {
				category.getMcTaskBlockOrClozeTaskBlockOrTextTaskBlock().add(
						addonTaskBlock);
			}
		}

		return category;

	}

}