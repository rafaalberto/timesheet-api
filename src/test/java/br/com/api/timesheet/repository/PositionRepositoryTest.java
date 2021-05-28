package br.com.api.timesheet.repository;

import br.com.api.timesheet.entity.Position;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class PositionRepositoryTest {

    @Autowired
    private PositionRepository positionRepository;

    Position position;

    @Before
    public void setUp() {
        position = new Position();
        position.setId(1L);
        position.setTitle("Developer");
        position.setDangerousness(false);
        positionRepository.save(position);
    }

    @Test
    public void shouldFindByTitle() {
        position = positionRepository.findByTitle("DEVELOPER").orElse(null);
        if (position == null) throw new AssertionError();
        assertEquals("DEVELOPER", position.getTitle());
    }

    @After
    public void tearDown() {
        positionRepository.deleteAll();
    }

}
