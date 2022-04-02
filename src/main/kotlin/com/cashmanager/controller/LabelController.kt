package com.cashmanager.controller

import com.cashmanager.entity.Label
import com.cashmanager.service.LabelService
import com.dash.entity.Tab
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/label")
@CrossOrigin(origins = ["*"])
class LabelController {

    @Autowired
    private lateinit var labelService: LabelService

    @GetMapping("/")
    fun getLabels(): List<Label> = (labelService.getLabels())

    @PostMapping("/addLabel")
    fun addLabel(@RequestBody label: Label): Label = labelService.addLabel(label)

    @DeleteMapping("/deleteLabel")
    fun deleteLabel(@RequestParam(value = "id") id: Int) = labelService.deleteLabel(id)
}
