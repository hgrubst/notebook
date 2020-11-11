package fr.acle.notello.common.config

import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class SpringContextUtil(ctx: ApplicationContext) {

    @PostConstruct()
    fun init() {
        SpringContextUtil.applicationContext
    }

    companion object {
        var applicationContext: ApplicationContext? = null
    }

}