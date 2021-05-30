package com.dash.controller

import com.dash.entity.Tab
import com.dash.service.TabService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tab")
@CrossOrigin(origins = ["*"])
class TabController {

    @Autowired
    private lateinit var tabService: TabService

    @GetMapping("/")
    fun getTabs(): List<Tab> {
        return (tabService.getTabs())
    }

    @PostMapping("/addTab")
    fun addTab(@RequestBody tab: Tab): Tab {
        return tabService.addTab(tab)
    }

    @PostMapping("/updateTab")
    fun updateTab(@RequestBody tab: Tab): Tab {
        return tabService.updateTab(tab)
    }

    @PostMapping("/updateTabs")
    fun updateTabs(@RequestBody tabs: List<Tab>): List<Tab> {
        return tabService.saveTabs(tabs)
    }

    @DeleteMapping("/deleteTab")
    fun deleteTab(@RequestParam(value = "id") id: Int) {
        return tabService.deleteTab(id)
    }
}
