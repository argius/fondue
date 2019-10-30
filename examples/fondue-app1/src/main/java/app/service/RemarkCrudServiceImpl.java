package app.service;

import java.util.List;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import fondue.fw.PP;
import app.dao.RemarkMapper;
import app.model.Remark;
import app.model.RemarkExample;

@Component
public class RemarkCrudServiceImpl implements RemarkCrudService {

    @Autowired
    private RemarkMapper mapper;

    @Override
    public List<Remark> getRemarks(PP page) {
        RemarkExample example = new RemarkExample();
        page.setTotalCount(mapper.countByExample(example));
        RowBounds rowBounds = new RowBounds(page.offset(), page.limit());
        return mapper.selectByExampleWithRowbounds(example, rowBounds);
    }

    @Override
    public Remark getRemark(long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public Remark create(Remark entity) {
        mapper.insertSelective(entity);
        return entity;
    }

    @Override
    public Remark update(Remark entity) {
        mapper.updateByPrimaryKey(entity);
        return entity;
    }

    @Override
    public void delete(long id) {
        mapper.deleteByPrimaryKey(id);
    }
}
