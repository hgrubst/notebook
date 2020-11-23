package fr.acle.notello.common.config

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class SpringContextUtil : ApplicationContextAware {

    companion object {
        lateinit var applicationContext: ApplicationContext
    }

    override fun setApplicationContext(ctx: ApplicationContext) {
        SpringContextUtil.applicationContext = ctx
    }

}