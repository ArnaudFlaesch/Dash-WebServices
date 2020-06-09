package com.dash.controller

import com.dash.entity.Tab
import com.dash.repository.TabRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}