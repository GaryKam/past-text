package io.github.garykam.pasttext.data.repository

import io.github.garykam.pasttext.data.local.PastTextDao
import io.github.garykam.pasttext.data.model.PastText

class PastTextRepository(private val pastTextDao: PastTextDao) {
    val pastTexts = pastTextDao.getAll()

    suspend fun addPastText(pastText: PastText) = pastTextDao.insert(pastText)
}
