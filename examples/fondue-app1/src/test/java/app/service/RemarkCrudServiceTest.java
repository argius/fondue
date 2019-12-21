package app.service;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public final class RemarkCrudServiceTest {

    @Autowired
    private RemarkCrudService service;

    @Test
    public void testGetRemarks() {
        assertEquals(false, service.getRemarks().isEmpty());
    }

}
