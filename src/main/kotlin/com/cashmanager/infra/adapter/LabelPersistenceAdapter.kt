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
    fun getLabels(authenticatedUserId: Int): List<LabelDomain> = labelRepository.findByUserIdOrderByLabel(authenticatedUserId).map(LabelEntity::toDomain)

    fun addLabel(
        labelToAdd: String,
        authenticatedUserId: Int
    ): LabelDomain {
        val userEntity = userRepository.getReferenceById(authenticatedUserId)
        val newLabel = LabelEntity(0, labelToAdd, userEntity)
        return labelRepository.save(newLabel).toDomain()
    }

    fun updateLabel(labelToUpdate: LabelDomain): LabelDomain {
        val oldLabel = labelRepository.getReferenceById(labelToUpdate.id)
        return labelRepository.save(oldLabel.copy(label = labelToUpdate.label)).toDomain()
    }

    fun deleteLabel(labelId: Int) {
        val label = labelRepository.getReferenceById(labelId)
        return labelRepository.delete(label)
    }
}
