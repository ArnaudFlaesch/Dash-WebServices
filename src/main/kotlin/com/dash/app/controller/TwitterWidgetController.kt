package com.dash.app.controller

import com.dash.app.controller.requests.twitterWidget.AddUserToFollowPayload
import com.dash.domain.model.twitterwidget.FollowedUser
import com.dash.domain.service.TwitterWidgetService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/twitterWidget", produces = [MediaType.APPLICATION_JSON_VALUE])
class TwitterWidgetController(
    private val twitterWidgetService: TwitterWidgetService
) {

    @GetMapping("/followed")
    fun getFollowedUsers(@RequestParam(value = "search", defaultValue = "") searchParam: String): List<FollowedUser> =
        twitterWidgetService.getFollowedUsers(searchParam)

    @PostMapping("/addFollowedUser")
    fun addFollowedUser(@RequestBody addUserToFollowPayload: AddUserToFollowPayload): FollowedUser =
        twitterWidgetService.addFollowedUser(addUserToFollowPayload.userHandle)

    @DeleteMapping("/deleteFollowedUser")
    fun deleteFollowedUser(@RequestParam("followedUserId") followedUserId: Int) =
        twitterWidgetService.deleteFollowedUser(followedUserId)
}
