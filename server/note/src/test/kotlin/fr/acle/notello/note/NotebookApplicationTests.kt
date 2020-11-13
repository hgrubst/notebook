package fr.acle.notello.note

import fr.acle.notello.common.config.SpringContextUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest
class NotebookApplicationTests(val ctx: ApplicationContext) {

    @Test
    fun contextLoads() {

        Assertions.assertNotNull(SpringContextUtil.applicationContext)

    }

}
