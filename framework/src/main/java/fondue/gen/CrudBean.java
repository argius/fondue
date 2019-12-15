package fondue.gen;

import java.util.List;
import fondue.gen.Config.Dao;
import fondue.gen.Config.Validation;

public final class CrudBean extends BaseBean {

    private String rootPkg;
    private String entityId;
    private String entitiesId;
    private String resourceId;
    private String resourcesId;
    private String entityFqcn;
    private int pagePerCount;
    private List<MyBatisMapperXmlBean.Result> items;
    private List<Validation> validations;
    private Dao dao;

    public String getRootPkg() {
        return rootPkg;
    }

    public void setRootPkg(String rootPkg) {
        this.rootPkg = rootPkg;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityName) {
        this.entityId = entityName;
    }

    public String getEntitiesId() {
        return entitiesId;
    }

    public void setEntitiesId(String entitiesName) {
        this.entitiesId = entitiesName;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceName) {
        this.resourceId = resourceName;
    }

    public String getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(String resourcesName) {
        this.resourcesId = resourcesName;
    }

    public int getPagePerCount() {
        return pagePerCount;
    }

    public void setPagePerCount(int pagePerCount) {
        this.pagePerCount = pagePerCount;
    }

    public List<MyBatisMapperXmlBean.Result> getItems() {
        return items;
    }

    public void setItems(List<MyBatisMapperXmlBean.Result> items) {
        this.items = items;
    }

    public String getEntityFqcn() {
        return entityFqcn;
    }

    public void setEntityFqcn(String entityFqcn) {
        this.entityFqcn = entityFqcn;
    }

    public List<Validation> getValidations() {
        return validations;
    }

    public void setValidations(List<Validation> validations) {
        this.validations = validations;
    }

    public Dao getDao() {
        return dao;
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }
}
