package app.model;

import java.util.Date;

public class Remark {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column APP1.REMARKS.ID
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column APP1.REMARKS.CREATED
     *
     * @mbg.generated
     */
    private Date created;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column APP1.REMARKS.UPDATED
     *
     * @mbg.generated
     */
    private Date updated;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column APP1.REMARKS.DISABLED
     *
     * @mbg.generated
     */
    private Short disabled;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column APP1.REMARKS.USER_ID
     *
     * @mbg.generated
     */
    private String userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column APP1.REMARKS.USER_NAME
     *
     * @mbg.generated
     */
    private String userName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column APP1.REMARKS.REMARK
     *
     * @mbg.generated
     */
    private String remark;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column APP1.REMARKS.ID
     *
     * @return the value of APP1.REMARKS.ID
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column APP1.REMARKS.ID
     *
     * @param id the value for APP1.REMARKS.ID
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column APP1.REMARKS.CREATED
     *
     * @return the value of APP1.REMARKS.CREATED
     *
     * @mbg.generated
     */
    public Date getCreated() {
        return created;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column APP1.REMARKS.CREATED
     *
     * @param created the value for APP1.REMARKS.CREATED
     *
     * @mbg.generated
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column APP1.REMARKS.UPDATED
     *
     * @return the value of APP1.REMARKS.UPDATED
     *
     * @mbg.generated
     */
    public Date getUpdated() {
        return updated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column APP1.REMARKS.UPDATED
     *
     * @param updated the value for APP1.REMARKS.UPDATED
     *
     * @mbg.generated
     */
    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column APP1.REMARKS.DISABLED
     *
     * @return the value of APP1.REMARKS.DISABLED
     *
     * @mbg.generated
     */
    public Short getDisabled() {
        return disabled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column APP1.REMARKS.DISABLED
     *
     * @param disabled the value for APP1.REMARKS.DISABLED
     *
     * @mbg.generated
     */
    public void setDisabled(Short disabled) {
        this.disabled = disabled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column APP1.REMARKS.USER_ID
     *
     * @return the value of APP1.REMARKS.USER_ID
     *
     * @mbg.generated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column APP1.REMARKS.USER_ID
     *
     * @param userId the value for APP1.REMARKS.USER_ID
     *
     * @mbg.generated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column APP1.REMARKS.USER_NAME
     *
     * @return the value of APP1.REMARKS.USER_NAME
     *
     * @mbg.generated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column APP1.REMARKS.USER_NAME
     *
     * @param userName the value for APP1.REMARKS.USER_NAME
     *
     * @mbg.generated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column APP1.REMARKS.REMARK
     *
     * @return the value of APP1.REMARKS.REMARK
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column APP1.REMARKS.REMARK
     *
     * @param remark the value for APP1.REMARKS.REMARK
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table APP1.REMARKS
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", created=").append(created);
        sb.append(", updated=").append(updated);
        sb.append(", disabled=").append(disabled);
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }
}