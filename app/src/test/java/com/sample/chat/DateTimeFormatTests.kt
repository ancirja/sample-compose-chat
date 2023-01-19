package com.sample.chat

import com.google.common.truth.Truth.assertThat
import com.sample.chat.utils.toDateString
import org.junit.Test
import java.util.Calendar

class DateTimeFormatTests {
    @Test
    fun `date format`() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, 2023)
        calendar.set(Calendar.MONTH, 0)
        calendar.set(Calendar.DAY_OF_MONTH, 19)
        calendar.set(Calendar.HOUR, 12)
        calendar.set(Calendar.MINUTE, 12)

        assertThat(calendar.time.toDateString()).isEqualTo("Thursday, 12:12")
    }

    @Test
    fun `timestamp format`() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, 2023)
        calendar.set(Calendar.MONTH, 0)
        calendar.set(Calendar.DAY_OF_MONTH, 19)
        calendar.set(Calendar.HOUR, 12)
        calendar.set(Calendar.MINUTE, 12)

        assertThat(calendar.timeInMillis.toDateString()).isEqualTo("Thursday, 12:12")
    }
}
