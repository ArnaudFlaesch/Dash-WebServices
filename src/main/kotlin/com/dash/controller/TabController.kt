package com.dash.controller

import com.dash.entity.Tab
import com.dash.repository.TabRepository
import com.dash.service.TabService
import com.dash.service.WidgetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tab")
@CrossOrigin(origins = ["*"])
class TabController {

    @Autowired
    private lateinit var tabRepository: TabRepository

    @Autowired
    private lateinit var tabService: TabService

    @Autowired
    private lateinit var widgetService: WidgetService

    @GetMapping("/")
    fun getTabs(): List<Tab> {
        return (tabRepository.findByOrderByTabOrderAsc())
    }

    @PostMapping("/addTab")
    fun addTab(@RequestBody tab: Tab): Tab {
        tab.tabOrder = tabRepository.getNumberOfTabs() + 1
        return tabRepository.save(tab)
    }

    @PostMapping("/updateTab")
    fun updateTab(@RequestBody tab: Tab): Tab {
        return tabRepository.save(tab)
    }

    @PostMapping("/updateTabs")
    fun updateTabs(@RequestBody tabs: List<Tab>): List<Tab> {
        return tabService.saveTabs(tabs)
    }

    @DeleteMapping("/deleteTab")
    fun deleteTab(@RequestParam(value = "id") id: Int) {
        widgetService.deleteWidgetsByTabId(id)
        val tab = tabRepository.getOne(id)
        return tabRepository.delete(tab)
    }
}
