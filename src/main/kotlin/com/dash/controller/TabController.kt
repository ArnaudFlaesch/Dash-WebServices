package com.dash.controller

import com.dash.entity.Tab
import com.dash.entity.Widget
import com.dash.repository.TabRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tab")
@CrossOrigin( origins = ["*"])
class TabController {

    @Autowired
    private lateinit var tabRepository: TabRepository

    @GetMapping("/")
    fun getTabs() : List<Tab> {
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

    @DeleteMapping("/deleteTab")
    fun deleteTab(@RequestParam(value = "id") id: Int) {
        val tab = tabRepository.getOne(id)
        return tabRepository.delete(tab)
    }
}
