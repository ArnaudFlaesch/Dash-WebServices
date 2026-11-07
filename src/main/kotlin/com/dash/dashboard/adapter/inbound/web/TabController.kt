package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.adapter.inbound.web.request.tab.CreateTabRequest
import com.dash.dashboard.adapter.inbound.web.request.tab.UpdateTabRequest
import com.dash.dashboard.application.port.inbound.TabUseCase
import com.dash.dashboard.domain.model.tab.Tab
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tab")
class TabController(
    private val tabUseCase: TabUseCase
) {
    @GetMapping("/")
    fun getTabs(): List<Tab> = tabUseCase.getUserTabs()

    @PostMapping("/addTab")
    fun addTab(
        @RequestBody createTabRequest: CreateTabRequest
    ): Tab = tabUseCase.addTab(createTabRequest.label)

    @PostMapping("/updateTab")
    fun updateTab(
        @RequestBody updateRequest: UpdateTabRequest
    ): Tab = tabUseCase.updateTab(updateRequest.id, updateRequest.label, updateRequest.tabOrder)

    @PostMapping("/updateTabs")
    fun updateTabs(
        @RequestBody tabList: List<Tab>
    ): List<Tab> = tabUseCase.saveTabs(tabList)

    @DeleteMapping("/deleteTab")
    fun deleteTab(
        @RequestParam id: Int
    ) {
        tabUseCase.deleteTab(id)
    }
}
