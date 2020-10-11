package com.dash.service

import com.dash.entity.Tab
import com.dash.repository.TabRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TabService {

    @Autowired
    private lateinit var tabRepository: TabRepository

    fun saveTabs(tabs: List<Tab>): List<Tab> {
        return tabs.map { tab -> tabRepository.save(tab) }
    }
}