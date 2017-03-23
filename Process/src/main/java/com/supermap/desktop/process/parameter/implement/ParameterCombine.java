package com.supermap.desktop.process.parameter.implement;

import com.supermap.desktop.process.enums.ParameterType;
import com.supermap.desktop.process.parameter.events.ParameterCombineBuildPanelListener;
import com.supermap.desktop.process.parameter.interfaces.IParameter;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 聚合IParameter的面板,聚合JComponent面板的请用ParameterUserDefine或自定义类型
 * To aggregate IParameter panels。
 * use the ParameterUserDefine or custom type for the JComponent panel
 *
 * @author XiaJT
 */
public class ParameterCombine extends AbstractParameter {
	// Q: 添加的关联事件如何保存？
	public static final String HORIZONTAL = "PARAMETER_COMBINE_TYPE_HORIZONTAL";
	public static final String VERTICAL = "PARAMETER_COMBINE_TYPE_VERTICAL";
	private ArrayList<IParameter> parameterList = new ArrayList<>();
	private ArrayList<ParameterCombineBuildPanelListener> parameterCombineBuildPanelListeners = new ArrayList<>();
	private String combineType = VERTICAL;
	private int weightIndex = -1;

	public ParameterCombine() {

	}

	public ParameterCombine(String combineType) {
		this.combineType = combineType;
	}


	public ParameterCombine addParameters(IParameter... parameters) {
		for (IParameter parameter : parameters) {
			if (this.parameterList.indexOf(parameter) == -1) {
				this.parameterList.add(parameter);
			}
		}
		return this;
	}

	public void addParameters(int weightIndex, IParameter... parameters) {
		this.weightIndex = weightIndex;
		Collections.addAll(this.parameterList, parameters);
		firePanelRebuildListener();
	}

	public void setWeightIndex(int weightIndex) {
		this.weightIndex = weightIndex;
	}

	@Override
	public String getType() {
		return ParameterType.COMBINE;
	}

	public void reBuildPanel() {
		buildPanel();
	}

	private void buildPanel() {
		firePanelRebuildListener();
	}

	private void firePanelRebuildListener() {
		for (ParameterCombineBuildPanelListener parameterCombineBuildPanelListener : parameterCombineBuildPanelListeners) {
			parameterCombineBuildPanelListener.rebuild();
		}
	}

	public int removeParameter(IParameter parameter) {
		int index;
		if ((index = parameterList.indexOf(parameter)) != -1) {
			parameterList.remove(parameter);
			firePanelRebuildListener();
		}
		return index;
	}

	public ArrayList<IParameter> getParameterList() {
		return parameterList;
	}

	public String getCombineType() {
		return combineType;
	}

	public int getWeightIndex() {
		return weightIndex;
	}

	public void addParameterCombineBuildPanelListeners(ParameterCombineBuildPanelListener parameterCombineBuildPanelListener) {
		if (!parameterCombineBuildPanelListeners.contains(parameterCombineBuildPanelListener)) {
			parameterCombineBuildPanelListeners.add(parameterCombineBuildPanelListener);
		}
	}
}
