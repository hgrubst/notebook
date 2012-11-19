package repositories;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:conf/application-context.xml","file:conf/application-context-test.xml"})
public class BaseRepositoryTest {

	@Autowired
	MongoTemplate mongoTemplate;

	
	
}
