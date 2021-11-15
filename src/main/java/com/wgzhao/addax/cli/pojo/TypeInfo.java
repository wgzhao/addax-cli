package com.wgzhao.addax.cli.pojo;

public class TypeInfo
{
    private Integer dtype;

    private String colType;

    private String sqlType;

    private String sqlTypeCode;

    public Integer getDtype() {
        return dtype;
    }

    public void setDtype(Integer dtype) {
        this.dtype = dtype;
    }

    public String getColType() {
        return colType;
    }

    public void setColType(String colType) {
        this.colType = colType == null ? null : colType.trim();
    }

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType == null ? null : sqlType.trim();
    }

    public String getSqlTypeCode() {
        return sqlTypeCode;
    }

    public void setSqlTypeCode(String sqlTypeCode) {
        this.sqlTypeCode = sqlTypeCode == null ? null : sqlTypeCode.trim();
    }
}