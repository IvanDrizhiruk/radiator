package ua.dp.ardas.radiator.jobs.kanbanflow;



import com.google.common.collect.Lists;

import java.util.List;

public class Column {

    public String columnName;
    public List <Row> rows = Lists.newArrayList();

    public Column(String columnName) {
        this.columnName = columnName;
    }
}