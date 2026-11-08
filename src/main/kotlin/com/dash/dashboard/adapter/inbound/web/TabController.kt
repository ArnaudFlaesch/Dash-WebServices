package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.adapter.inbound.web.request.tab.CreateTabRequest
import com.dash.dashboard.adapter.inbound.web.request.tab.UpdateTabRequest
import com.dash.dashboard.application.port.inbound.TabUseCase
import com.dash.dashboard.domain.model.tab.Tab
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tabs")
class TabController(
    private val tabUseCase: TabUseCase
) {
    @GetMapping
    fun getTabs(): List<Tab> = tabUseCase.getUserTabs()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addTab(
        @RequestBody createTabRequest: CreateTabRequest
    ): Tab = tabUseCase.addTab(createTabRequest.label)

    @PutMapping("/{tabId}")
    fun updateTab(
        @PathVariable tabId: Int,
        @RequestBody updateRequest: UpdateTabRequest
    ): Tab = tabUseCase.updateTab(tabId, updateRequest.label, updateRequest.tabOrder)

    @PatchMapping
    fun updateTabs(
        @RequestBody tabList: List<Tab>
    ): List<Tab> = tabUseCase.saveTabs(tabList)

    @DeleteMapping("/{tabId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTab(
        @PathVariable tabId: Int
    ) {
        tabUseCase.deleteTab(tabId)
    }
}
