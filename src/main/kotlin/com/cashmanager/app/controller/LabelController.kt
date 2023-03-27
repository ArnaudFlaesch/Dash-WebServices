package com.cashmanager.app.controller

import com.cashmanager.app.controller.requests.InsertLabelPayload
import com.cashmanager.domain.model.LabelDomain
import com.cashmanager.domain.service.LabelService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/label")
@CrossOrigin(origins = ["*"])
class LabelController(private val labelService: LabelService) {

    @GetMapping("/")
    fun getLabels(): List<LabelDomain> = labelService.getUserLabels()

    @PostMapping("/addLabel")
    fun addLabel(@RequestBody insertLabelPayload: InsertLabelPayload): LabelDomain =
        labelService.addLabel(insertLabelPayload.newLabel)

    @PatchMapping("/updateLabel")
    fun updateLabel(@RequestBody label: LabelDomain): LabelDomain = labelService.updateLabel(label)

    @DeleteMapping("/deleteLabel")
    fun deleteLabel(@RequestParam(value = "labelId") id: Int) = labelService.deleteLabel(id)
}
