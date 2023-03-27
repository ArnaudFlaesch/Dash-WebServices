package com.dash.app.controller

import com.common.app.security.SecurityConditions
import com.dash.app.controller.requests.tab.CreateTabPayload
import com.dash.app.controller.requests.tab.UpdateTabPayload
import com.dash.domain.model.TabDomain
import com.dash.domain.service.TabService
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tab")
@CrossOrigin(origins = ["*"])
class TabController(private val tabService: TabService) {

    @GetMapping("/")
    @PostFilter(SecurityConditions.doesTabBelongToAuthenticatedUser)
    fun getTabs(): List<TabDomain> = tabService.getTabs()

    @PostMapping("/addTab")
    fun addTab(@RequestBody createTabPayload: CreateTabPayload): TabDomain = tabService.addTab(createTabPayload.label)

    @PostMapping("/updateTab")
    fun updateTab(@RequestBody updatePayload: UpdateTabPayload): TabDomain = tabService.updateTab(updatePayload.id, updatePayload.label, updatePayload.tabOrder)

    @PostMapping("/updateTabs")
    fun updateTabs(@RequestBody tabList: List<TabDomain>): List<TabDomain> = tabService.saveTabs(tabList)

    @DeleteMapping("/deleteTab")
    @PreAuthorize(SecurityConditions.isUserAdmin)
    fun deleteTab(@RequestParam(value = "id") id: Int) = tabService.deleteTab(id)
}
