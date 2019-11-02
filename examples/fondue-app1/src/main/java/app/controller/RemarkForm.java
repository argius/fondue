package app.controller;

import fondue.fw.FormMethods;
import app.model.Remark;

public final class RemarkForm implements FormMethods {

    private String id;
    private String created;
    private String updated;
    private String disabled;
    private String userId;
    private String userName;
    private String remark;

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getCreated() {
        return this.created;
    }

    public void setCreated(String value) {
        this.created = value;
    }

    public String getUpdated() {
        return this.updated;
    }

    public void setUpdated(String value) {
        this.updated = value;
    }

    public String getDisabled() {
        return this.disabled;
    }

    public void setDisabled(String value) {
        this.disabled = value;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String value) {
        this.userId = value;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String value) {
        this.userName = value;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String value) {
        this.remark = value;
    }

    public void applyModel(Remark model) {
        id = convertFrom(java.lang.Long.class, model.getId());
        created = convertFrom(java.sql.Timestamp.class, model.getCreated());
        updated = convertFrom(java.sql.Timestamp.class, model.getUpdated());
        disabled = convertFrom(java.lang.Short.class, model.getDisabled());
        userId = convertFrom(java.lang.String.class, model.getUserId());
        userName = convertFrom(java.lang.String.class, model.getUserName());
        remark = convertFrom(java.lang.String.class, model.getRemark());
    }

    public Remark toModel() {
        Remark o = new Remark();
        o.setId(convertTo(java.lang.Long.class, this.id));
        o.setCreated(convertTo(java.sql.Timestamp.class, this.created));
        o.setUpdated(convertTo(java.sql.Timestamp.class, this.updated));
        o.setDisabled(convertTo(java.lang.Short.class, this.disabled));
        o.setUserId(convertTo(java.lang.String.class, this.userId));
        o.setUserName(convertTo(java.lang.String.class, this.userName));
        o.setRemark(convertTo(java.lang.String.class, this.remark));
        return o;
    }

    @Override
    public String toString() {
        return toString(this);
    }
}
