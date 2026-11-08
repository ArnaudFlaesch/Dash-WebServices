package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.adapter.inbound.web.request.twitter.AddUserToFollowRequest
import com.dash.dashboard.application.port.inbound.TwitterWidgetUseCase
import com.dash.dashboard.domain.model.twitter.FollowedUser
import com.dash.shared.domain.model.Page
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/twitter/followed-users", produces = [MediaType.APPLICATION_JSON_VALUE])
class TwitterWidgetController(
    private val twitterWidgetUseCase: TwitterWidgetUseCase
) {
    @GetMapping
    fun getFollowedUsers(
        @RequestParam(value = "search", defaultValue = "") searchParam: String,
        @RequestParam(defaultValue = "0") pageNumber: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ): Page<FollowedUser> = twitterWidgetUseCase.getFollowedUsers(searchParam, pageNumber, pageSize)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addFollowedUser(
        @RequestBody addUserToFollowRequest: AddUserToFollowRequest
    ): FollowedUser = twitterWidgetUseCase.addFollowedUser(addUserToFollowRequest.userHandle)

    @DeleteMapping("/{followedUserId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteFollowedUser(
        @PathVariable followedUserId: Int
    ) {
        twitterWidgetUseCase.deleteFollowedUser(followedUserId)
    }
}
