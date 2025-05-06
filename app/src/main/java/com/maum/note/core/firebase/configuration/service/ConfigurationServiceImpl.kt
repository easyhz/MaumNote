package com.maum.note.core.firebase.configuration.service

import com.google.firebase.firestore.FirebaseFirestore
import com.maum.note.core.firebase.constant.Collection.CONFIGURATION
import com.maum.note.core.firebase.constant.Field
import com.maum.note.core.firebase.configuration.model.response.ConfigurationResponse
import com.maum.note.core.firebase.util.documentHandler
import javax.inject.Inject

class ConfigurationServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ConfigurationService {
    override suspend fun fetchConfiguration(): Result<ConfigurationResponse> = documentHandler {
        firestore.collection(CONFIGURATION).document(Field.CONFIGURATION).get()
    }
}