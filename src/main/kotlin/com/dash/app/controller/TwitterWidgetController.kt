package com.dash.app.controller

import com.dash.app.controller.requests.twitterWidget.AddUserToFollowPayload
import com.dash.app.controller.response.Page
import com.dash.app.controller.response.toPageResponse
import com.dash.domain.model.twitterwidget.FollowedUser
import com.dash.domain.service.TwitterWidgetService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/twitterWidget", produces = [MediaType.APPLICATION_JSON_VALUE])
class TwitterWidgetController(
    private val twitterWidgetService: TwitterWidgetService
) {
    @GetMapping("/followed")
    fun getFollowedUsers(
        @RequestParam(value = "search", defaultValue = "") searchParam: String,
        @RequestParam(defaultValue = "0") pageNumber: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ): Page<FollowedUser> = twitterWidgetService.getFollowedUsers(searchParam, pageNumber, pageSize).toPageResponse()

    @PostMapping("/addFollowedUser")
    fun addFollowedUser(
        @RequestBody addUserToFollowPayload: AddUserToFollowPayload
    ): FollowedUser = twitterWidgetService.addFollowedUser(addUserToFollowPayload.userHandle)

    @DeleteMapping("/deleteFollowedUser")
    fun deleteFollowedUser(
        @RequestParam followedUserId: Int
    ) = twitterWidgetService.deleteFollowedUser(followedUserId)
}
