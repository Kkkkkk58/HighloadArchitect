package ru.kslacker.highload.controller

import com.github.springtestdbunit.annotation.DatabaseSetup
import com.github.springtestdbunit.annotation.ExpectedDatabase
import com.github.springtestdbunit.assertion.DatabaseAssertionMode
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.kslacker.highload.SocialNetworkIntegrationTest

class UserControllerTest : SocialNetworkIntegrationTest() {
    @Test
    @ExpectedDatabase(
        value = "/controller/user/register/happy_path/after.xml",
        assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED
    )
    fun `register - happy path - ok`() {
        httpAssert.assertApiCall(
            request = MockMvcRequestBuilders.post("/user/register"),
            requestFile = "controller/user/register/happy_path/request.json",
            status = status().isOk,
        )
    }

    @Test
    fun `register - bad request - error`() {
        httpAssert.assertApiCall(
            request = MockMvcRequestBuilders.post("/user/register"),
            requestFile = "controller/user/register/bad_request/request.json",
            status = status().isBadRequest
        )
    }

    @Test
    @DatabaseSetup("/controller/user/get_by_id/happy_path/immutable.xml")
    fun `get user by id - happy path - ok`() {
        httpAssert.assertApiCall(
            request = MockMvcRequestBuilders.get("/user/kslacker"),
            responseFile = "controller/user/get_by_id/happy_path/response.json",
            status = status().isOk
        )
    }

    @Test
    fun `get user by id - user not found - error`() {
        httpAssert.assertApiCall(
            request = MockMvcRequestBuilders.get("/user/unknown-user"),
            status = status().isNotFound
        )
    }
}
