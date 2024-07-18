package com.youtuza.YouTuZa_Backend.domain.comment.domain

import com.youtuza.YouTuZa_Backend.domain.BaseUUIDEntity
import java.util.UUID
import javax.persistence.Entity

@Entity
class Comment(
    id: UUID?,
    youtubeName: String,
    title: String,
    content: String,
    writer: String
): BaseUUIDEntity(id) {
    var youtubeName: String = youtubeName
        protected set

    var title: String = title
        protected set

    var content: String = content
        protected set

    var writer: String = writer
        protected set
}