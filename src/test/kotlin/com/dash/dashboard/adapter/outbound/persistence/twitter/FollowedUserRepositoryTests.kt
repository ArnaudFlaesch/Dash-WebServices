package com.dash.dashboard.adapter.outbound.persistence.twitter

import com.dash.shared.adapter.outbound.persistence.user.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Pageable

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FollowedUserRepositoryTests {
    @Autowired
    private lateinit var followedUserRepository: FollowedUserRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @BeforeAll
    @AfterAll
    fun tearDown() {
        followedUserRepository.deleteAll()
    }

    @Test
    fun testInsertFollowedUsers() {
        val userId = 1
        val followedUser1 =
            FollowedUserEntity(
                id = 0,
                userHandle = "Nono",
                user = userRepository.getReferenceById(userId)
            )
        val followedUser2 =
            FollowedUserEntity(
                id = 0,
                userHandle = "Nono2",
                user = userRepository.getReferenceById(userId)
            )
        followedUserRepository.save(followedUser1)
        followedUserRepository.save(followedUser2)

        val followedUsersList =
            followedUserRepository.searchFollowedUsers(
                "",
                userId,
                Pageable.ofSize(10)
            )
        assertThat(followedUsersList.content).hasSize(2)
        assertThat(followedUsersList.content[0].id).isNotNull
        assertThat(followedUsersList.content[0].userHandle).isEqualTo("Nono")
        assertThat(followedUsersList.content[0].user.id).isEqualTo(userId)
        assertThat(followedUsersList.content[1].id).isNotNull
        assertThat(followedUsersList.content[1].userHandle).isEqualTo("Nono2")
        assertThat(followedUsersList.content[1].user.id).isEqualTo(userId)
    }
}
