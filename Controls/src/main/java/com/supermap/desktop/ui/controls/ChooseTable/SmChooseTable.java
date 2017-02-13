package com.supermap.desktop.ui.controls.ChooseTable;

import com.supermap.data.DatasetVector;
import com.supermap.desktop.properties.CommonProperties;
import com.supermap.desktop.ui.CheckTableModle;
import com.supermap.desktop.ui.controls.CheckHeaderCellRenderer;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by lixiaoyao on 2017/2/10.
 */
public class SmChooseTable extends JTable {
    CheckTableModle checkTableModle = null;
    private final Object[] tableTitle = {"", CommonProperties.getString("String_Field_Caption")};
    private final static int checkColumnIndex=0;
    private final static int checkColumnIndexMaxSize=40;
    private final static int rowHeight=23;
    private final static int enableColumn=0;
    private static final int TABLE_COLUMN_CHECKABLE = 0;
    private static final int TABLE_COLUMN_CAPTION = 1;
    private DatasetVector datasetVector=null;

    private void init(){
        this.setRowHeight(rowHeight);
        this.getTableHeader().setDefaultRenderer(new CheckHeaderCellRenderer(this,""));
    }

    public SmChooseTable(DatasetVector datasetVector) {
        this.datasetVector=datasetVector;
        this.checkTableModle = new CheckTableModle(getData(datasetVector), tableTitle, enableColumn);
        this.setModel(this.checkTableModle);
        this.getColumn(this.getModel().getColumnName(checkColumnIndex)).setMaxWidth(checkColumnIndexMaxSize);
        init();
    }

    private Object[][] getData(DatasetVector datasetVector){
        int count = 0;
        for (int i = 0; i < datasetVector.getFieldInfos().getCount(); i++) {
            if (!datasetVector.getFieldInfos().get(i).isSystemField()) {
                count++;
            }
        }
        Object data[][]=new Object[count][2];
        int length = 0;
        for (int i = 0; i < datasetVector.getFieldInfos().getCount(); i++) {
            if (!datasetVector.getFieldInfos().get(i).isSystemField()) {
                data[length][TABLE_COLUMN_CHECKABLE]=false;
                data[length][TABLE_COLUMN_CAPTION]= datasetVector.getFieldInfos().get(i).getCaption();
                length++;
            }
        }
        return data;
    }

    public ArrayList getSelectedFieldsName(){
        ArrayList<String> selectedFieldsName = new ArrayList<String>();
        for (int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < this.datasetVector.getFieldInfos().getCount(); j++) {
                if ((Boolean) this.getValueAt(i, TABLE_COLUMN_CHECKABLE) && this.datasetVector.getFieldInfos().get(j).getCaption().equals(this.getValueAt(i, TABLE_COLUMN_CAPTION))) {
                    selectedFieldsName.add(this.datasetVector.getFieldInfos().get(j).getName());
                }
            }
        }
        return selectedFieldsName;
    }
}
