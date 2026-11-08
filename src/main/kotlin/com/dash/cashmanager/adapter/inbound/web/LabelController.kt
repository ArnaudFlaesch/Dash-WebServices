package com.dash.cashmanager.adapter.inbound.web

import com.dash.cashmanager.adapter.inbound.web.request.InsertLabelRequest
import com.dash.cashmanager.application.port.inbound.LabelUseCase
import com.dash.cashmanager.domain.model.Label
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/labels")
class LabelController(
    private val labelUseCase: LabelUseCase
) {
    @GetMapping
    fun getLabels(): List<Label> = labelUseCase.getUserLabels()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addLabel(
        @RequestBody insertLabelRequest: InsertLabelRequest
    ): Label = labelUseCase.addLabel(insertLabelRequest.newLabel)

    @PutMapping("/{labelId}")
    fun updateLabel(
        @PathVariable labelId: Int,
        @RequestBody label: Label
    ): Label = labelUseCase.updateLabel(label.copy(id = labelId))

    @DeleteMapping("/{labelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteLabel(
        @PathVariable labelId: Int
    ) {
        labelUseCase.deleteLabel(labelId)
    }
}
