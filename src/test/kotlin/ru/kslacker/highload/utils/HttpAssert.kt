package ru.kslacker.highload.utils


import org.skyscreamer.jsonassert.JSONCompareMode
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

import java.nio.charset.StandardCharsets
import jakarta.servlet.http.Cookie
import org.apache.commons.io.IOUtils
import org.skyscreamer.jsonassert.JSONAssert
import java.util.Objects

class HttpAssert(private val mockMvc: () -> MockMvc) {
    fun assertApiCall(
        request: MockHttpServletRequestBuilder,
        status: ResultMatcher,
        requestFile: String? = null,
        responseFile: String? = null,
    ): MvcResult {
        requestFile?.let {
            request.contentType(MediaType.APPLICATION_JSON)
            request.content(getFileContent(it))
        }

        val resultActions =
            mockMvc().perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status)
                .andReturn()

        responseFile?.let {
            JSONAssert.assertEquals(
                getFileContent(it),
                resultActions.response.getContentAsString(StandardCharsets.UTF_8),
                JSONCompareMode.STRICT
            )
        }

        return resultActions
    }

    private fun getFileContent(fileName: String): String {
        return IOUtils.toString(ClassLoader.getSystemResourceAsStream(fileName), StandardCharsets.UTF_8).trim()
    }
}
