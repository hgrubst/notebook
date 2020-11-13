package fr.acle.notello.note.config

import fr.acle.notello.common.model.Auditor
import fr.acle.notello.note.repository.NotebookEntityCallback
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import java.util.*


@Configuration
@EnableMongoAuditing
class MongoConfig() {

    @Bean
    fun transactionManager(dbFactory: MongoDatabaseFactory): MongoTransactionManager {
        return MongoTransactionManager(dbFactory)
    }

//    @Bean
//    fun auditorProvider(): AuditorAware<Auditor> {
//        return SpringSecurityAuditorAware()
//    }

    //custom converter maybe

}

internal class SpringSecurityAuditorAware : AuditorAware<Auditor> {
    override fun getCurrentAuditor(): Optional<Auditor> {
        if (SecurityContextHolder.getContext().authentication != null && SecurityContextHolder.getContext().authentication.isAuthenticated()) {
            val claims = (SecurityContextHolder.getContext().authentication as JwtAuthenticationToken).tokenAttributes;
            return Optional.of(Auditor(claims.get("user_name") as String, null))
        } else {
            return Optional.ofNullable(null);
        }
    }
}
