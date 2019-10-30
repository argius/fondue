package app.service;

import java.util.List;
import org.springframework.stereotype.Service;
import fondue.fw.PP;
import app.model.Remark;

@Service
public interface RemarkCrudService {

    default List<Remark> getRemarks() {
        return getRemarks(new PP());
    }

    List<Remark> getRemarks(PP page);

    Remark getRemark(long id);

    Remark create(Remark entity);

    Remark update(Remark entity);

    void delete(long id);
}
