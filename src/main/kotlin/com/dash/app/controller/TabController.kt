package com.dash.app.controller

import com.dash.app.controller.requests.tab.CreateTabPayload
import com.dash.app.controller.requests.tab.UpdateTabPayload
import com.dash.domain.model.TabDomain
import com.dash.domain.service.TabService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tab")
class TabController(
    private val tabService: TabService
) {
    @GetMapping("/")
    fun getTabs(): List<TabDomain> = tabService.getUserTabs()

    @PostMapping("/addTab")
    fun addTab(
        @RequestBody createTabPayload: CreateTabPayload
    ): TabDomain = tabService.addTab(createTabPayload.label)

    @PostMapping("/updateTab")
    fun updateTab(
        @RequestBody updatePayload: UpdateTabPayload
    ): TabDomain = tabService.updateTab(updatePayload.id, updatePayload.label, updatePayload.tabOrder)

    @PostMapping("/updateTabs")
    fun updateTabs(
        @RequestBody tabList: List<TabDomain>
    ): List<TabDomain> = tabService.saveTabs(tabList)

    @DeleteMapping("/deleteTab")
    fun deleteTab(
        @RequestParam(value = "id") id: Int
    ) = tabService.deleteTab(id)
}
