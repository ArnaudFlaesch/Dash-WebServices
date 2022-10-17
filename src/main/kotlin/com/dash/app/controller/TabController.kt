package com.dash.app.controller

import com.dash.app.controller.requests.CreateTabPayload
import com.dash.domain.model.TabDomain
import com.dash.domain.service.TabService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tab")
@CrossOrigin(origins = ["*"])
class TabController {

    @Autowired
    private lateinit var tabService: TabService

    @GetMapping("/")
    fun getTabs(): List<TabDomain> = (tabService.getTabs())

    @PostMapping("/addTab")
    fun addTab(@RequestBody createTabPayload: CreateTabPayload): TabDomain = tabService.addTab(createTabPayload.label)

    @PostMapping("/updateTab")
    fun updateTab(@RequestBody tab: TabDomain): TabDomain = tabService.saveTab(tab)

    @PostMapping("/updateTabs")
    fun updateTabs(@RequestBody tabList: List<TabDomain>): List<TabDomain> = tabService.saveTabs(tabList)

    @DeleteMapping("/deleteTab")
    fun deleteTab(@RequestParam(value = "id") id: Int) = tabService.deleteTab(id)
}
