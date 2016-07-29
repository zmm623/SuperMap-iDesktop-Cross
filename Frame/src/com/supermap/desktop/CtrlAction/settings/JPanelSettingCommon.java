package com.supermap.desktop.CtrlAction.settings;

import com.supermap.desktop.GlobalParameters;
import com.supermap.desktop.frame.FrameProperties;
import com.supermap.desktop.ui.controls.GridBagConstraintsHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * 常用选项
 *
 * @author XiaJT
 */
public class JPanelSettingCommon extends BaseSettingPanel {

	/**
	 * 自动新建窗口浏览数据集数据
	 */
	private JCheckBox checkBoxShowDataInNowWindow;
	/**
	 * 关闭窗口提示保存
	 */
	private JCheckBox checkBoxIsShowFormClosingInfo;
	/**
	 * 工作空间关闭提示保存
	 */
	private JCheckBox checkBoxWorkspaceCloseNotify;
	/**
	 * 关闭工作空间提示内存数据源
	 */
	private JCheckBox checkBoxCloseMemoryDatasourceNotify;
	/**
	 * 自动关闭没有图层的地图窗口
	 */
	private JCheckBox checkBoxIsAutoCloseEmptyMap;

	private ItemListener itemListener;

	@Override
	protected void initComponents() {
		checkBoxShowDataInNowWindow = new JCheckBox();
		checkBoxIsShowFormClosingInfo = new JCheckBox();
		checkBoxWorkspaceCloseNotify = new JCheckBox();
		checkBoxCloseMemoryDatasourceNotify = new JCheckBox();
		checkBoxIsAutoCloseEmptyMap = new JCheckBox();
		this.setBorder(BorderFactory.createTitledBorder(FrameProperties.getString("String_CaptionOperate")));
		itemListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Object source = e.getSource();
				if (source != null && source instanceof Component) {
					if (changedValues.containsKey(source)) {
						changedValues.remove(source);
					} else {
						changedValues.put(((Component) source), null);
					}
				}
			}
		};
	}

	@Override
	protected void initLayout() {
		this.setLayout(new GridBagLayout());
		this.add(checkBoxShowDataInNowWindow, new GridBagConstraintsHelper(0, 0, 1, 1).setWeight(1, 0).setAnchor(GridBagConstraints.WEST).setFill(GridBagConstraints.NONE));
		this.add(checkBoxIsShowFormClosingInfo, new GridBagConstraintsHelper(0, 1, 1, 1).setWeight(1, 0).setInsets(5, 0, 0, 0).setAnchor(GridBagConstraints.WEST).setFill(GridBagConstraints.NONE));
		this.add(checkBoxWorkspaceCloseNotify, new GridBagConstraintsHelper(0, 2, 1, 1).setWeight(1, 0).setInsets(5, 0, 0, 0).setAnchor(GridBagConstraints.WEST).setFill(GridBagConstraints.NONE));
		this.add(checkBoxCloseMemoryDatasourceNotify, new GridBagConstraintsHelper(0, 3, 1, 1).setWeight(1, 0).setInsets(5, 0, 0, 0).setAnchor(GridBagConstraints.WEST).setFill(GridBagConstraints.NONE));
		this.add(checkBoxIsAutoCloseEmptyMap, new GridBagConstraintsHelper(0, 4, 1, 1).setWeight(1, 0).setInsets(5, 0, 0, 0).setAnchor(GridBagConstraints.WEST).setFill(GridBagConstraints.NONE));
		this.add(new JPanel(), new GridBagConstraintsHelper(0, 5, 1, 1).setWeight(1, 1).setInsets(5, 0, 0, 0).setAnchor(GridBagConstraints.WEST).setFill(GridBagConstraints.NONE));
	}

	@Override
	protected void initListeners() {
		checkBoxShowDataInNowWindow.addItemListener(itemListener);
		checkBoxIsShowFormClosingInfo.addItemListener(itemListener);
		checkBoxWorkspaceCloseNotify.addItemListener(itemListener);
		checkBoxCloseMemoryDatasourceNotify.addItemListener(itemListener);
		checkBoxIsAutoCloseEmptyMap.addItemListener(itemListener);
	}

	@Override
	protected void initResources() {
		checkBoxShowDataInNowWindow.setText(FrameProperties.getString("String_ShowDataInNowWindow"));
		checkBoxIsShowFormClosingInfo.setText(FrameProperties.getString("String_ShowCloseInfoForm"));
		checkBoxWorkspaceCloseNotify.setText(FrameProperties.getString("String_CloseWorkspaceNotyfy"));
		checkBoxCloseMemoryDatasourceNotify.setText(FrameProperties.getString("String_ShowWorkspaceMemorytTip"));
		checkBoxIsAutoCloseEmptyMap.setText(FrameProperties.getString("String_CloseEmptyWindow"));
	}

	@Override
	protected void initComponentStates() {
		checkBoxShowDataInNowWindow.setSelected(GlobalParameters.isShowDataInNewWindow());
		checkBoxIsShowFormClosingInfo.setSelected(GlobalParameters.isShowFormClosingInfo());
		checkBoxWorkspaceCloseNotify.setSelected(GlobalParameters.isWorkspaceCloseNotify());
		checkBoxCloseMemoryDatasourceNotify.setSelected(GlobalParameters.isCloseMemoryDatasourceNotify());
		checkBoxIsAutoCloseEmptyMap.setSelected(GlobalParameters.isAutoCloseEmptyWindow());
	}

	@Override
	public void apply() {
		for (Component component : changedValues.keySet()) {
			if (component == checkBoxShowDataInNowWindow) {
				GlobalParameters.setIsShowDataInNewWindow(checkBoxShowDataInNowWindow.isSelected());
			}
			if (component == checkBoxIsShowFormClosingInfo) {
				GlobalParameters.setIsShowFormClosingInfo(checkBoxIsShowFormClosingInfo.isSelected());
			}
			if (component == checkBoxWorkspaceCloseNotify) {
				GlobalParameters.setIsWorkspaceCloseNotify(checkBoxWorkspaceCloseNotify.isSelected());
			}
			if (component == checkBoxCloseMemoryDatasourceNotify) {
				GlobalParameters.setIsCloseMemoryDatasourceNotify(checkBoxCloseMemoryDatasourceNotify.isSelected());
			}
			if (component == checkBoxIsAutoCloseEmptyMap) {
				GlobalParameters.setIsAutoCloseEmptyWindow(checkBoxIsAutoCloseEmptyMap.isSelected());
			}
		}
	}

	@Override
	public void dispose() {

	}
}
