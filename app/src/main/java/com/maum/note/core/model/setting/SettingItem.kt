package com.maum.note.core.model.setting

import androidx.annotation.StringRes
import com.maum.note.BuildConfig
import com.maum.note.R

interface SettingItem {
    val stringResId: Int
    val enabledClick: Boolean
        get() = true
    fun getValue(): String? = null
}

enum class MajorSettingItem(
    @StringRes val stringResId: Int,
    val items: List<SettingItem>
) {
    NOTE(
        stringResId = R.string.setting_major_note,
        items = NoteSettingItem.entries
    ), ETC(
        stringResId = R.string.setting_major_etc,
        items = EtcSettingItem.entries
    )
}

enum class NoteSettingItem: SettingItem {
    AGE {
        override val stringResId: Int
            get() = R.string.setting_note_age
    }, TONE {
        override val stringResId: Int
            get() = R.string.setting_note_tone
    }, TRASH {
        override val stringResId: Int
            get() = R.string.setting_note_trash
    };
}

enum class EtcSettingItem: SettingItem {
    ABOUT {
        override val stringResId: Int
            get() = R.string.setting_etc_about

        override fun getValue(): String? {
            return null
        }
    }, VERSION {
        override val stringResId: Int
            get() = R.string.setting_etc_version

        override val enabledClick: Boolean
            get() = false

        override fun getValue(): String {
            return "v${BuildConfig.VERSION_NAME}"
        }
    };
}