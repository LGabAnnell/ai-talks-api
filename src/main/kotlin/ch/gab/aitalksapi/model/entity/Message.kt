package ch.gab.aitalksapi.model.entity

import ch.gab.aitalksapi.im.model.request.enums.IMRole
import jakarta.persistence.*

@Entity
data class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val conversationId: Long? = null,

    @Column(name = "content", length = 5000)
    val content: String? = null,
    var role: String? = null,
    val index: Int = 0,
)
