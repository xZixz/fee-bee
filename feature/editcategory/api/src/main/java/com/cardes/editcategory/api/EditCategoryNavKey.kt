package com.cardes.editcategory.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class EditCategoryNavKey(val categoryId: Long) : NavKey
