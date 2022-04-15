package com.cashmanager.controller

import com.cashmanager.controller.requests.InsertLabelPayload
import com.cashmanager.entity.Label
import com.cashmanager.service.LabelService
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
    fun addLabel(@RequestBody insertLabelPayload: InsertLabelPayload): Label = labelService.addLabel(insertLabelPayload.newLabel)

    @PatchMapping("/updateLabel")
    fun updateLabel(@RequestBody label: Label): Label = labelService.updateLabel(label)

    @DeleteMapping("/deleteLabel")
    fun deleteLabel(@RequestParam(value = "labelId") id: Int) = labelService.deleteLabel(id)
}
