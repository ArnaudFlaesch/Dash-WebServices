package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.adapter.inbound.web.request.twitter.AddUserToFollowRequest
import com.dash.dashboard.application.port.inbound.TwitterWidgetUseCase
import com.dash.dashboard.domain.model.twitter.FollowedUser
import com.dash.shared.domain.model.Page
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/twitterWidget", produces = [MediaType.APPLICATION_JSON_VALUE])
class TwitterWidgetController(
    private val twitterWidgetUseCase: TwitterWidgetUseCase
) {
    @GetMapping("/followed")
    fun getFollowedUsers(
        @RequestParam(value = "search", defaultValue = "") searchParam: String,
        @RequestParam(defaultValue = "0") pageNumber: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ): Page<FollowedUser> = twitterWidgetUseCase.getFollowedUsers(searchParam, pageNumber, pageSize)

    @PostMapping("/addFollowedUser")
    fun addFollowedUser(
        @RequestBody addUserToFollowRequest: AddUserToFollowRequest
    ): FollowedUser = twitterWidgetUseCase.addFollowedUser(addUserToFollowRequest.userHandle)

    @DeleteMapping("/deleteFollowedUser")
    fun deleteFollowedUser(
        @RequestParam followedUserId: Int
    ) {
        twitterWidgetUseCase.deleteFollowedUser(followedUserId)
    }
}
