package com.cashmanager.repository

import com.cashmanager.entity.Label
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LabelRepository : JpaRepository<Label, Int> {

}
