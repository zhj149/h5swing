package org.webswing.server.common.model.meta;

import org.webswing.server.common.model.meta.ConfigFieldEditorType.EditorType;

public class MetaField {

	private String name;
	private ConfigGroup tab;
	private String label;
	private String description;
	private boolean discriminator;
	private boolean variables;
	private String[] presets;
	private EditorType type;
	private Object value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ConfigGroup getTab() {
		return tab;
	}

	public void setTab(ConfigGroup tab) {
		this.tab = tab;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDiscriminator() {
		return discriminator;
	}

	public void setDiscriminator(boolean discriminator) {
		this.discriminator = discriminator;
	}

	public boolean isVariables() {
		return variables;
	}

	public void setVariables(boolean variables) {
		this.variables = variables;
	}

	public String[] getPresets() {
		return presets;
	}

	public void setPresets(String[] presets) {
		this.presets = presets;
	}

	public EditorType getType() {
		return type;
	}

	public void setType(EditorType type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Name:" + name + " type:" + type.name() + " value:" + value;
	}
}