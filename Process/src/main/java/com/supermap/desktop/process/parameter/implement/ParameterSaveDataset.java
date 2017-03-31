package com.supermap.desktop.process.parameter.implement;

import com.supermap.data.Datasource;
import com.supermap.desktop.process.constraint.annotation.ParameterField;
import com.supermap.desktop.process.enums.ParameterType;
import com.supermap.desktop.process.parameter.interfaces.ISelectionParameter;
import com.supermap.desktop.properties.CommonProperties;

/**
 * @author XiaJT
 */
public class ParameterSaveDataset extends AbstractParameter implements ISelectionParameter {

	public static final String DATASOURCE_FIELD_NAME = "datasource";
	public static final String DETASET_FIELD_NAME = "detasetName";

	@ParameterField(name = DATASOURCE_FIELD_NAME)
	private Datasource resultDatasource;
	@ParameterField(name = DETASET_FIELD_NAME)
	private String datasetName;
	private String datasourceDescribe;
	private String datasetDescribe;

	@Override
	public String getType() {
		return ParameterType.SAVE_DATASET;
	}


	@Override
	public void setSelectedItem(Object value) {
		this.datasetName = (String) value;
	}

	@Override
	public Object getSelectedItem() {
		return datasetName;
	}

	@Override
	public void dispose() {

	}

    @Override
    public String getDescribe() {
        return CommonProperties.getString("String_TargetDataset");
    }

    public Datasource getResultDatasource() {
        return resultDatasource;
    }

	public void setResultDatasource(Datasource resultDatasource) {
		this.resultDatasource = resultDatasource;

	}

	public String getDatasetName() {
		return datasetName;
	}

	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}

	public void setDatasourceDescribe(String datasourceDescribe) {
		this.datasourceDescribe = datasourceDescribe;
	}

	public String getDatasourceDescribe() {
		return datasourceDescribe;
	}

	public String getDatasetDescribe() {
		return datasetDescribe;
	}

	public void setDatasetDescribe(String datasetDescribe) {
		this.datasetDescribe = datasetDescribe;
	}
}
