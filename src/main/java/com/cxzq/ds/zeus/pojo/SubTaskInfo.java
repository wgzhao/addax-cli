package com.cxzq.ds.zeus.pojo;

import java.util.Date;

public class SubTaskInfo
{
    private String id;

    private String sourceId;

    private String sourceDb;

    private String sourceTbl;

    private String targetId;

    private String targetDb;

    private String targetTbl;

    private Integer isAddTargetTbl;

    private Integer addTargetTblStatus;

    private String addTargetTblReason;

    private Integer addFieldStatus;

    private String addFieldReason;

    private Integer addJsonStatus;

    private String addJsonReason;

    private Integer targetMappingStatus;

    private String targetMappingReason;

    private String taskId;

    private Date ctime;

    private Date mtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId == null ? null : sourceId.trim();
    }

    public String getSourceDb() {
        return sourceDb;
    }

    public void setSourceDb(String sourceDb) {
        this.sourceDb = sourceDb == null ? null : sourceDb.trim();
    }

    public String getSourceTbl() {
        return sourceTbl;
    }

    public void setSourceTbl(String sourceTbl) {
        this.sourceTbl = sourceTbl == null ? null : sourceTbl.trim();
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId == null ? null : targetId.trim();
    }

    public String getTargetDb() {
        return targetDb;
    }

    public void setTargetDb(String targetDb) {
        this.targetDb = targetDb == null ? null : targetDb.trim();
    }

    public String getTargetTbl() {
        return targetTbl;
    }

    public void setTargetTbl(String targetTbl) {
        this.targetTbl = targetTbl == null ? null : targetTbl.trim();
    }

    public Integer getIsAddTargetTbl() {
        return isAddTargetTbl;
    }

    public void setIsAddTargetTbl(Integer isAddTargetTbl) {
        this.isAddTargetTbl = isAddTargetTbl;
    }

    public Integer getAddTargetTblStatus() {
        return addTargetTblStatus;
    }

    public void setAddTargetTblStatus(Integer addTargetTblStatus) {
        this.addTargetTblStatus = addTargetTblStatus;
    }

    public String getAddTargetTblReason() {
        return addTargetTblReason;
    }

    public void setAddTargetTblReason(String addTargetTblReason) {
        this.addTargetTblReason = addTargetTblReason == null ? null : addTargetTblReason.trim();
    }

    public Integer getAddFieldStatus() {
        return addFieldStatus;
    }

    public void setAddFieldStatus(Integer addFieldStatus) {
        this.addFieldStatus = addFieldStatus;
    }

    public String getAddFieldReason() {
        return addFieldReason;
    }

    public void setAddFieldReason(String addFieldReason) {
        this.addFieldReason = addFieldReason == null ? null : addFieldReason.trim();
    }

    public Integer getAddJsonStatus() {
        return addJsonStatus;
    }

    public void setAddJsonStatus(Integer addJsonStatus) {
        this.addJsonStatus = addJsonStatus;
    }

    public String getAddJsonReason() {
        return addJsonReason;
    }

    public void setAddJsonReason(String addJsonReason) {
        this.addJsonReason = addJsonReason == null ? null : addJsonReason.trim();
    }

    public Integer getTargetMappingStatus() {
        return targetMappingStatus;
    }

    public void setTargetMappingStatus(Integer targetMappingStatus) {
        this.targetMappingStatus = targetMappingStatus;
    }

    public String getTargetMappingReason() {
        return targetMappingReason;
    }

    public void setTargetMappingReason(String targetMappingReason) {
        this.targetMappingReason = targetMappingReason == null ? null : targetMappingReason.trim();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }
}