package com.dash.cashmanager.adapter.inbound.web

import com.dash.cashmanager.adapter.inbound.web.request.InsertLabelRequest
import com.dash.cashmanager.application.port.inbound.LabelUseCase
import com.dash.cashmanager.domain.model.Label
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/label")
class LabelController(
    private val labelUseCase: LabelUseCase
) {
    @GetMapping("/")
    fun getLabels(): List<Label> = labelUseCase.getUserLabels()

    @PostMapping("/addLabel")
    fun addLabel(
        @RequestBody insertLabelRequest: InsertLabelRequest
    ): Label = labelUseCase.addLabel(insertLabelRequest.newLabel)

    @PatchMapping("/updateLabel")
    fun updateLabel(
        @RequestBody label: Label
    ): Label = labelUseCase.updateLabel(label)

    @DeleteMapping("/deleteLabel")
    fun deleteLabel(
        @RequestParam(value = "labelId") id: Int
    ) {
        labelUseCase.deleteLabel(id)
    }
}
