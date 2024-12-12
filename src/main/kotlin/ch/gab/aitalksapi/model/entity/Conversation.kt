package ch.gab.aitalksapi.model.entity

import ch.gab.aitalksapi.statemachine.model.enums.State
import jakarta.persistence.*

@Entity
@Table(name = "conversation")
data class Conversation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "assistant_model", referencedColumnName = "id")
    val assistantModel: Model? = null,

    @ManyToOne
    @JoinColumn(name = "user_model", referencedColumnName = "id")
    val userModel: Model? = null,

    val systemInstructions: String? = null,

    val userInstructions: String? = null,

    @OneToMany
    @JoinTable(
        name = "ConversationMessage",
        inverseJoinColumns = [
            JoinColumn(
                name = "message_id",
                referencedColumnName = "id"
            )
        ],
        joinColumns = [
            JoinColumn(
                name = "conversation_id",
                referencedColumnName = "id"
            )
        ]
    )
    var messages: Set<Message> = emptySet(),

    var state: State? = State.INITIATING
)