package com.dash.app.controller

import com.dash.app.controller.requests.twitterWidget.AddUserToFollowPayload
import com.dash.app.controller.response.Page
import com.dash.domain.model.twitterwidget.FollowedUser
import com.dash.domain.service.TwitterWidgetService
import com.dash.utils.PageMapper
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/twitterWidget", produces = [MediaType.APPLICATION_JSON_VALUE])
class TwitterWidgetController(
    private val twitterWidgetService: TwitterWidgetService
) {
    @GetMapping("/followed")
    fun getFollowedUsers(
        @RequestParam(value = "search", defaultValue = "") searchParam: String,
        @RequestParam(value = "pageNumber", defaultValue = "0") pageNumber: Int,
        @RequestParam(value = "pageSize", defaultValue = "10") pageSize: Int
    ): Page<FollowedUser> =
        PageMapper.mapPageToPageResponse(
            twitterWidgetService.getFollowedUsers(searchParam, pageNumber, pageSize)
        )

    @PostMapping("/addFollowedUser")
    fun addFollowedUser(
        @RequestBody addUserToFollowPayload: AddUserToFollowPayload
    ): FollowedUser = twitterWidgetService.addFollowedUser(addUserToFollowPayload.userHandle)

    @DeleteMapping("/deleteFollowedUser")
    fun deleteFollowedUser(
        @RequestParam("followedUserId") followedUserId: Int
    ) = twitterWidgetService.deleteFollowedUser(followedUserId)
}
