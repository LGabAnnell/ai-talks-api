package ch.gab.aitalksapi.service

import ch.gab.aitalksapi.im.model.request.enums.IMModel
import ch.gab.aitalksapi.model.entity.Model
import ch.gab.aitalksapi.model.repository.ModelRepository
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service

@Service
class ModelService(
    private val modelrepository: ModelRepository
) {
    @PostConstruct
    fun createModelEntries(): MutableList<Model> = modelrepository.saveAll(
        IMModel.entries.map { Model(name = it.name) }
    )

    fun findByName(name: String): Model = requireNotNull(modelrepository.findByName(IMModel.fromString(name).name)) {
        "The model $name does not exist."
    }

    fun save(model: Model) = modelrepository.save(model)
}