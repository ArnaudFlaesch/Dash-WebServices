package com.dash.app.controller

import com.dash.app.controller.requests.CreateTabPayload
import com.dash.domain.service.TabService
import com.dash.infra.entity.Tab
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tab")
@CrossOrigin(origins = ["*"])
class TabController {

    @Autowired
    private lateinit var tabService: TabService

    @GetMapping("/")
    fun getTabs(): List<Tab> = (tabService.getTabs())

    @PostMapping("/addTab")
    fun addTab(@RequestBody createTabPayload: CreateTabPayload): Tab = tabService.addTab(createTabPayload.label)

    @PostMapping("/updateTab")
    fun updateTab(@RequestBody tab: Tab): Tab = tabService.updateTab(tab)

    @PostMapping("/updateTabs")
    fun updateTabs(@RequestBody tabs: List<Tab>): List<Tab> = tabService.saveTabs(tabs)

    @DeleteMapping("/deleteTab")
    fun deleteTab(@RequestParam(value = "id") id: Int) = tabService.deleteTab(id)
}
