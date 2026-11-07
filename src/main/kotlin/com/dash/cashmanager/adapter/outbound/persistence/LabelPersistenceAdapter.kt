package com.dash.cashmanager.adapter.outbound.persistence

import com.dash.cashmanager.application.port.outbound.LabelPersistencePort
import com.dash.cashmanager.domain.model.Label
import com.dash.shared.adapter.outbound.persistence.user.UserRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class LabelPersistenceAdapter(
    private val labelRepository: LabelRepository,
    private val userRepository: UserRepository
) : LabelPersistencePort {
    override fun getLabels(userId: Int): List<Label> =
        labelRepository.findByUserIdOrderByLabel(userId).map(LabelEntity::toDomain)

    override fun addLabel(
        labelToAdd: String,
        userId: Int
    ): Label =
        LabelEntity(0, labelToAdd, userRepository.getReferenceById(userId))
            .let(labelRepository::save)
            .let(LabelEntity::toDomain)

    @Transactional
    override fun updateLabel(labelToUpdate: Label): Label =
        labelRepository
            .getReferenceById(labelToUpdate.id)
            .apply { label = labelToUpdate.label }
            .toDomain()

    override fun deleteLabel(labelId: Int) {
        labelRepository.deleteById(labelId)
    }
}
