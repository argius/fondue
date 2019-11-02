package app.service;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import app.dao.RemarkMapper;
import app.model.Remark;
import app.model.RemarkExample;
import app.model.RemarkExample.Criteria;

// This is not a generted file.
@Primary
@Component
public class RemarkServiceImpl extends RemarkCrudServiceImpl implements RemarkService {

    @Autowired
    private RemarkMapper mapper;

    @Override
    public List<Remark> getLatestUpdate(int count) {
        RemarkExample example = new RemarkExample();
        Criteria criteria = example.createCriteria();
        criteria.andDisabledEqualTo(NumberUtils.SHORT_ZERO);
        example.setOrderByClause("updated desc, id asc");
        RowBounds rowBounds = new RowBounds(0, count);
        return mapper.selectByExampleWithRowbounds(example, rowBounds);
    }

    @Override
    public List<Remark> getUpdatedAfter(long millis) {
        RemarkExample example = new RemarkExample();
        Criteria criteria = example.createCriteria();
        criteria.andDisabledEqualTo(NumberUtils.SHORT_ZERO);
        criteria.andUpdatedGreaterThanOrEqualTo(new Date(millis));
        example.setOrderByClause("updated asc");
        return mapper.selectByExample(example);
    }
}
