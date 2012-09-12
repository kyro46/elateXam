/**
 * Programm zur Konvertierung von aus Moodle exportierten Übungsfragen (Moodle-XML)
 * in Elate ComplexTaskDef-XML.
 *
 * @author Christoph Jobst
 * @version 1.0
 */

package de.christophjobst.main;

import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.AddonTaskBlock;
import de.thorstenberger.taskmodel.complex.complextaskdef.Config;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.ClozeTaskBlock;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.MappingTaskBlock;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.McTaskBlock;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.TextTaskBlock;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.ClozeTaskBlock.ClozeConfig;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.MappingTaskBlock.MappingConfig;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.McTaskBlock.McConfig;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.McTaskBlock.McConfig.Different;

public class CategoryManager {

	Category category = new Category();
	boolean hasTextTaskBlock = false;
	boolean hasMcTaskBlock = false;
	boolean hasClozeTaskBlock = false;
	boolean hasMappingTaskBlock = false;
	boolean hasAddonTaskBlock = false;

	String title;
	MappingTaskBlock mappingTaskBlock = new MappingTaskBlock();
	McTaskBlock mcTaskBlock = new McTaskBlock();
	TextTaskBlock textTaskBlock = new TextTaskBlock();
	ClozeTaskBlock clozeTaskBlock = new ClozeTaskBlock();
	AddonTaskBlock addonTaskBlock = new AddonTaskBlock();

	public CategoryManager(Category category) {
		this.title = category.getTitle().toString();
		this.category = category;
		// Einheitliche Config für alle TaskBlock-Instanzen
		Config generalTaskBlockConfig = new Config();
		generalTaskBlockConfig.setNoOfSelectedTasks(100);
		// TODO Punkte = Anzahl der Lücken/Matchings - inkonsistent, da in
		// Frageinstanzen nicht einheitlich viele Lücken/Matchings
		generalTaskBlockConfig.setPointsPerTask(5);
		generalTaskBlockConfig.setPreserveOrder(false);

		// Vorbereitung ClozeTaskBlock
		clozeTaskBlock.setConfig(generalTaskBlockConfig);
		ClozeConfig clozeConfig = new ClozeConfig();
		clozeConfig.setIgnoreCase(true);
		clozeConfig.setNegativePoints(0);
		clozeTaskBlock.setClozeConfig(clozeConfig);

		// Vorbereitung TextTaskBlock
		textTaskBlock.setConfig(generalTaskBlockConfig);

		// Vorbereitung MappingTaskBlock
		mappingTaskBlock.setConfig(generalTaskBlockConfig);
		MappingConfig mappingConfig = new MappingConfig();
		mappingConfig.setNegativePoints(0);
		mappingTaskBlock.setMappingConfig(mappingConfig);

		// Vorbereitung McTaskBlock
		mcTaskBlock.setConfig(generalTaskBlockConfig);
		McConfig mcConfig = new McConfig();
		Different different = new Different();
		different.setCorrectAnswerNegativePoints(0);
		different.setIncorrectAnswerNegativePoints(0);
		mcConfig.setDifferent(different);
		mcTaskBlock.setMcConfig(mcConfig);

		// Vorbereitung AddonTaskBlock
		addonTaskBlock.setConfig(generalTaskBlockConfig);

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public MappingTaskBlock getMappingTaskBlock() {
		return mappingTaskBlock;
	}

	public void setMappingTaskBlock(MappingTaskBlock mappingTaskBlock) {
		this.mappingTaskBlock = mappingTaskBlock;
	}

	public McTaskBlock getMcTaskBlock() {
		return mcTaskBlock;
	}

	public void setMcTaskBlock(McTaskBlock mcTaskBlock) {
		this.mcTaskBlock = mcTaskBlock;
	}

	public TextTaskBlock getTextTaskBlock() {
		return textTaskBlock;
	}

	public void setTextTaskBlock(TextTaskBlock textTaskBlock) {
		this.textTaskBlock = textTaskBlock;
	}

	public ClozeTaskBlock getClozeTaskBlock() {
		return clozeTaskBlock;
	}

	public void setClozeTaskBlock(ClozeTaskBlock clozeTaskBlock) {
		this.clozeTaskBlock = clozeTaskBlock;
	}

	public boolean isHasAddonTaskBlock() {
		return hasAddonTaskBlock;
	}

	public void setHasAddonTaskBlock(boolean hasAddonTaskBlock) {
		this.hasAddonTaskBlock = hasAddonTaskBlock;
	}

	public AddonTaskBlock getAddonTaskBlock() {
		return addonTaskBlock;
	}

	public Category generateCategory() {
		if (hasClozeTaskBlock) {
			category.getMcTaskBlockOrClozeTaskBlockOrTextTaskBlock().add(
					clozeTaskBlock);
		}
		if (hasTextTaskBlock) {
			category.getMcTaskBlockOrClozeTaskBlockOrTextTaskBlock().add(
					textTaskBlock);
		}
		if (hasMappingTaskBlock) {
			category.getMcTaskBlockOrClozeTaskBlockOrTextTaskBlock().add(
					mappingTaskBlock);
		}
		if (hasMcTaskBlock) {
			category.getMcTaskBlockOrClozeTaskBlockOrTextTaskBlock().add(
					mcTaskBlock);
		}
		if (hasAddonTaskBlock) {
			category.getMcTaskBlockOrClozeTaskBlockOrTextTaskBlock().add(
					addonTaskBlock);
		}
		return category;
	}

}