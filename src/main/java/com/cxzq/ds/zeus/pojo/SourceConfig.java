package com.cxzq.ds.zeus.pojo;

import java.util.Date;

public class SourceConfig {
    private String id;

    private String name;

    private String user;

    private String pass;

    private String dsn;

    private Integer dtype;

    private Integer dstatus;

    private Date ctime;

    private Date mtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user == null ? null : user.trim();
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass == null ? null : pass.trim();
    }

    public String getDsn() {
        return dsn;
    }

    public void setDsn(String dsn) {
        this.dsn = dsn == null ? null : dsn.trim();
    }

    public Integer getDtype() {
        return dtype;
    }

    public void setDtype(Integer dtype) {
        this.dtype = dtype;
    }

    public Integer getDstatus() {
        return dstatus;
    }

    public void setDstatus(Integer dstatus) {
        this.dstatus = dstatus;
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