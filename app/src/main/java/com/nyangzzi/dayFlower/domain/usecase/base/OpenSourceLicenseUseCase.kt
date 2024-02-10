package com.nyangzzi.dayFlower.domain.usecase.base

import com.nyangzzi.dayFlower.domain.repository.OpenSourceLicenseRepository
import javax.inject.Inject

class OpenSourceLicenseUseCase @Inject constructor(
    private val repository: OpenSourceLicenseRepository
) {
    operator fun invoke() = repository.getOpenSourceLicense()
}