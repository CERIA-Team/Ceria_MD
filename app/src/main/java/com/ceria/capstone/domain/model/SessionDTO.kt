package com.ceria.capstone.domain.model

import com.ceria.capstone.data.remote.response.ListeningSessionItem

data class SessionDTO(val id: String, val songCount: Int) {
    constructor(listeningSessionItem: ListeningSessionItem) : this(
        id = listeningSessionItem.listenId, songCount = listeningSessionItem.count.session
    )
}