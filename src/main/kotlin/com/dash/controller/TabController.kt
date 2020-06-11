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
    fun addWidget(@RequestBody tab: Tab): Tab {
        return tabRepository.save(tab)
    }

    @PostMapping("/updateTab")
    fun updateWidget(@RequestBody tab: Tab): Tab {
        return tabRepository.save(tab)
    }

    @PostMapping("/deleteTab")
    fun deleteWidget(@RequestBody id: Int) {
        val tab = tabRepository.getOne(id)
        return tabRepository.delete(tab)
    }
}
