package com.supermap.desktop.process.meta.metaProcessImplements;

import com.supermap.data.CursorType;
import com.supermap.data.DatasetType;
import com.supermap.data.DatasetVector;
import com.supermap.data.Datasource;
import com.supermap.data.QueryParameter;
import com.supermap.data.Recordset;
import com.supermap.desktop.Application;
import com.supermap.desktop.process.ProcessProperties;
import com.supermap.desktop.process.meta.MetaProcess;
import com.supermap.desktop.process.parameter.implement.DefaultParameters;
import com.supermap.desktop.process.parameter.implement.ParameterSaveDataset;
import com.supermap.desktop.process.parameter.implement.ParameterSingleDataset;
import com.supermap.desktop.process.parameter.implement.ParameterTextArea;
import com.supermap.desktop.process.parameter.interfaces.IParameters;
import com.supermap.desktop.process.parameter.interfaces.ProcessData;
import com.supermap.desktop.properties.CommonProperties;
import com.supermap.desktop.utilities.StringUtilities;

import javax.swing.*;
import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created by xie on 2017/2/21.
 * sql查询简单实现
 */
public class MetaProcessSqlQuery extends MetaProcess {
	private IParameters parameters = new DefaultParameters();
	private ParameterSingleDataset dataset;
    private ParameterTextArea parameterAttributeFilter;
    private ParameterTextArea parameterResultFields;
    private ParameterSaveDataset parameterSaveDataset;
    private DatasetType[] datasetTypes = new DatasetType[]{
            DatasetType.POINT, DatasetType.LINE, DatasetType.REGION,
            DatasetType.POINT3D, DatasetType.LINE3D, DatasetType.REGION3D,
            DatasetType.TEXT, DatasetType.TABULAR, DatasetType.CAD
    };

    public MetaProcessSqlQuery() {
        initMetaInfo();
    }

    private void initMetaInfo() {
        this.dataset = new ParameterSingleDataset(datasetTypes);
        if (null != Application.getActiveApplication().getActiveDatasets() && Application.getActiveApplication().getActiveDatasets().length > 0) {
            this.dataset.setSelectedItem(Application.getActiveApplication().getActiveDatasets()[0]);
        }
        parameterResultFields = new ParameterTextArea(CommonProperties.getString("String_QueryField"));
        parameterAttributeFilter = new ParameterTextArea(CommonProperties.getString("String_QueryCondition"));
        parameterSaveDataset = new ParameterSaveDataset();
        parameterSaveDataset.setDatasetName("QueryResult");
        parameters.setParameters(this.dataset, this.parameterResultFields,
                this.parameterAttributeFilter, this.parameterSaveDataset);
    }

    @Override
    public String getTitle() {
        return ProcessProperties.getString("String_SqlQuery");
    }

    @Override
    public JComponent getComponent() {
        return parameters.getPanel();
    }

    @Override
    public void run() {
        if (null != dataset.getSelectedItem() && dataset.getSelectedItem() instanceof DatasetVector) {
            // 构建查询语句
            DatasetVector currentDatasetVector = ((DatasetVector) dataset.getSelectedItem());
            QueryParameter queryParameter = new QueryParameter();
            queryParameter.setCursorType(CursorType.DYNAMIC);
            queryParameter.setHasGeometry(true);

            // 查询字段
            String queryFields = (String) parameterResultFields.getSelectedItem();
            String[] queryFieldNames = getQueryFieldNames(queryFields);
            queryParameter.setResultFields(queryFieldNames);
            // 查询条件
            queryParameter.setAttributeFilter((String) parameterAttributeFilter.getSelectedItem());
            preProcessSQLQuery(queryParameter);
            queryParameter.setSpatialQueryObject(currentDatasetVector);
            Recordset resultRecord = currentDatasetVector.query(queryParameter);
            if (resultRecord != null && resultRecord.getRecordCount() > 0) {
                if (StringUtilities.isNullOrEmpty(queryFields)) {
                    resultRecord.dispose();
                    resultRecord = null;
                }
	            ProcessData processData = new ProcessData();
	            processData.setData(resultRecord);
	            outPuts.set(0, processData);

                // 保存查询结果
                saveQueryResult(resultRecord);
            }
        }

    }

    private void saveQueryResult(Recordset resultRecord) {
        Datasource resultDatasource = parameterSaveDataset.getResultDatasource();
        String datasetName = parameterSaveDataset.getDatasetName();
        if (resultDatasource != null && !StringUtilities.isNullOrEmpty(datasetName)) {
            DatasetVector resultDataset = null;
            try {
                resultDataset = resultDatasource.recordsetToDataset(resultRecord, datasetName);
            } catch (Exception e) {
                resultDataset = null;
            }
            resultRecord.moveFirst();
            if (resultDataset == null) {
                Application.getActiveApplication().getOutput().output(CommonProperties.getString("String_SQLQuerySaveAsResultFaield"));
            } else {
                Application.getActiveApplication().getOutput()
                        .output(MessageFormat.format(CommonProperties.getString("String_SQLQuerySavaAsResultSucces"), resultDataset.getName()));
            }
        }

    }

    private void preProcessSQLQuery(QueryParameter queryParameter) {
        try {
            for (String field : queryParameter.getResultFields()) {
                String strText = field.toUpperCase();
                if (strText.contains("SUM(") || strText.contains("MAX(") || strText.contains("MIN(") || strText.contains("AVG(") || strText.contains("COUNT(")
                        || strText.contains("STDEV(") || strText.contains("STDEVP(") || strText.contains("VAR(") || strText.contains("VARP(")) {
                    queryParameter.setCursorType(CursorType.STATIC);
                    break;
                }
            }

            if (queryParameter.getGroupBy().length > 0) {
                queryParameter.setCursorType(CursorType.STATIC);
            }

        } catch (Exception ex) {
            Application.getActiveApplication().getOutput().output(ex);
        }
    }

    private String[] getQueryFieldNames(String queryFields) {
        int bracketsCount = 0;
        java.util.List<String> fieldNames = new ArrayList<>();
        char[] fieldNamesChars = queryFields.toCharArray();
        StringBuilder builderFieldName = new StringBuilder();
        for (char fieldNamesChar : fieldNamesChars) {
            if (fieldNamesChar == ',' && bracketsCount == 0 && builderFieldName.length() > 0) {
                fieldNames.add(builderFieldName.toString());
                builderFieldName.setLength(0);
            } else {
                builderFieldName.append(fieldNamesChar);
                if (fieldNamesChar == '(') {
                    bracketsCount++;
                } else if (fieldNamesChar == ')' && bracketsCount > 0) {
                    bracketsCount--;
                }
            }
        }
        if (builderFieldName.length() > 0) {
            fieldNames.add(builderFieldName.toString());
            builderFieldName.setLength(0);
        }
        return fieldNames.toArray(new String[fieldNames.size()]);
    }

}