package com.cashmanager.infra.adapter

import com.cashmanager.domain.model.LabelDomain
import com.cashmanager.infra.entity.LabelEntity
import com.cashmanager.infra.repository.LabelRepository
import com.common.infra.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class LabelPersistenceAdapter(
    private val labelRepository: LabelRepository,
    private val userRepository: UserRepository
) {
    fun getLabels(authenticatedUserId: Int): List<LabelDomain> =
        labelRepository.findByUserIdOrderByLabel(authenticatedUserId).map(LabelEntity::toDomain)

    fun addLabel(
        labelToAdd: String,
        authenticatedUserId: Int
    ): LabelDomain =
        LabelEntity(0, labelToAdd, userRepository.getReferenceById(authenticatedUserId))
            .let(labelRepository::save)
            .let(LabelEntity::toDomain)

    fun updateLabel(labelToUpdate: LabelDomain): LabelDomain =
        labelRepository
            .getReferenceById(labelToUpdate.id)
            .copy(label = labelToUpdate.label)
            .let(labelRepository::save)
            .let(LabelEntity::toDomain)

    fun deleteLabel(labelId: Int) =
        labelRepository
            .getReferenceById(labelId)
            .let(labelRepository::delete)
}
