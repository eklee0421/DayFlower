package com.nyangzzi.dayFlower.presentation.base.dialog

import androidx.compose.runtime.Composable

@Composable
fun RemoveUserDialog(isShow: Boolean, onConfirm: () -> Unit, onDismiss: () -> Unit) {

    if (isShow) {
        MessageDialog(
            title = "앱 회원을 탈퇴하시겠습니까?",
            content = "탈퇴 시 보관함을 비롯한 모든 개인정보가 삭제되며, 복구 불가능합니다.",
            confirmText = "회원탈퇴", onConfirm = onConfirm, onDismiss = onDismiss
        )
    }
}