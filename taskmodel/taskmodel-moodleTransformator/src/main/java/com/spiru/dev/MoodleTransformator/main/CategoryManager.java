/**
 * Programm zur Konvertierung von aus Moodle exportierten �bungsfragen (Moodle-XML)
 * in Elate ComplexTaskDef-XML.
 *
 * @author Christoph Jobst
 * @version 1.0
 */

package com.spiru.dev.MoodleTransformator.main;

import de.thorstenberger.taskmodel.complex.jaxb.Config;
import de.thorstenberger.taskmodel.complex.jaxb.ComplexTaskDef.Category;
import de.thorstenberger.taskmodel.complex.jaxb.ComplexTaskDef.Category.ClozeTaskBlock;
import de.thorstenberger.taskmodel.complex.jaxb.ComplexTaskDef.Category.MappingTaskBlock;
import de.thorstenberger.taskmodel.complex.jaxb.ComplexTaskDef.Category.McTaskBlock;
import de.thorstenberger.taskmodel.complex.jaxb.ComplexTaskDef.Category.TextTaskBlock;
import de.thorstenberger.taskmodel.complex.jaxb.ComplexTaskDef.Category.ClozeTaskBlock.ClozeConfig;
import de.thorstenberger.taskmodel.complex.jaxb.ComplexTaskDef.Category.MappingTaskBlock.MappingConfig;
import de.thorstenberger.taskmodel.complex.jaxb.ComplexTaskDef.Category.McTaskBlock.McConfig;
import de.thorstenberger.taskmodel.complex.jaxb.ComplexTaskDef.Category.McTaskBlock.McConfig.Different;

public class CategoryManager {

	Category category = new Category();
	boolean hasTextTaskBlock = false;
	boolean hasMcTaskBlock = false;
	boolean hasClozeTaskBlock = false;
	boolean hasMappingTaskBlock = false;
	String title;
	MappingTaskBlock mappingTaskBlock = new MappingTaskBlock();
	McTaskBlock mcTaskBlock = new McTaskBlock();
	TextTaskBlock textTaskBlock = new TextTaskBlock();
	ClozeTaskBlock clozeTaskBlock = new ClozeTaskBlock();

	public CategoryManager(Category category) {
		this.title = category.getTitle().toString();
		this.category = category;
		// Einheitliche Config f�r alle TaskBlock-Instanzen
		Config generalTaskBlockConfig = new Config();
		generalTaskBlockConfig.setNoOfSelectedTasks(100);
		// TODO Punkte = Anzahl der L�cken/Matchings - inkonsistent, da in
		// Frageinstanzen nicht einheitlich viele L�cken/Matchings
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
		return category;
	}

}
