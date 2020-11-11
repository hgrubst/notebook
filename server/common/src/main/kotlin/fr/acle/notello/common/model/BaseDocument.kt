package fr.acle.notello.common.model

import org.springframework.data.annotation.*
import org.springframework.data.mongodb.core.mapping.Field
import java.time.Instant

abstract class BaseAuditableDocument {
    @CreatedBy
    @Field("created_by")
    var createdBy: Auditor? = null

    @CreatedDate
    @Field("created_date")
    var createdDate: Instant = Instant.now()

    @Field("last_modified_by")
    @LastModifiedBy
    var lastModifiedBy: Auditor? = null

    @LastModifiedDate
    @Field("last_modified_date")
    var lastModifiedDate: Instant = Instant.now()
}

abstract class BaseVersionableDocument {
    @Version()
    @Field("version")
    var version: Int = 0
}

abstract class BaseAuditableVersionableDocument : BaseAuditableDocument() {
    @Version()
    @Field("version")
    var version: Int = 0
}