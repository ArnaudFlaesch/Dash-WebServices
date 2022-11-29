package com.dash.infra.repository

import com.common.infra.repository.UserRepository
import com.common.utils.AbstractIT
import com.dash.infra.entity.twitterwidget.FollowedUserEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
class TwitterRepositoryTests : AbstractIT() {

    @Autowired
    private lateinit var twitterWidgetRepository: TwitterWidgetRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun testInsertFollowedUsers() {
        val userId = 1
        val followedUser1 = FollowedUserEntity(id = 0, userHandle = "Nono", user = userRepository.getReferenceById(userId))
        val followedUser2 = FollowedUserEntity(id = 0, userHandle = "Nono2", user = userRepository.getReferenceById(userId))
        twitterWidgetRepository.save(followedUser1)
        twitterWidgetRepository.save(followedUser2)

        val followedUsersList = twitterWidgetRepository.searchFollowedUsers("", userId)
        assertThat(followedUsersList).hasSize(2)
        assertThat(followedUsersList[0].userHandle).isEqualTo("Nono")
        assertThat(followedUsersList[1].userHandle).isEqualTo("Nono2")
    }
}
