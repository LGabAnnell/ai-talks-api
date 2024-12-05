package ch.gab.aitalksapi.model.entity

import jakarta.persistence.*

@Entity
data class Model(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true, nullable = false)
    val name: String = ""
)