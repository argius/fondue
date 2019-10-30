package app.service;

import java.util.List;
import org.springframework.stereotype.Service;
import app.model.Remark;

// This is not a generted file.
@Service
public interface RemarkService extends RemarkCrudService {

    List<Remark> getLatestUpdate(int count);

    List<Remark> getUpdatedAfter(long millis);
}
