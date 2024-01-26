package com.nyangzzi.dayFlower.presentation.base.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.nyangzzi.dayFlower.R
import com.nyangzzi.dayFlower.presentation.base.util.noRippleClickable
import com.nyangzzi.dayFlower.ui.theme.Gray1
import com.nyangzzi.dayFlower.ui.theme.Gray5
import com.nyangzzi.dayFlower.ui.theme.Gray6
import com.nyangzzi.dayFlower.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    onValueChange: (String) -> Unit = {},
    onSearch: () -> Unit = {},
    placeholder: String = "",
    isSearchIcon: Boolean = true,
    badge: String? = null
) {

    val focusRequester = remember {
        FocusRequester()
    }

    Row(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(6.dp))
            .background(color = Gray1)
            .noRippleClickable {
                focusRequester.requestFocus()
            }
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (isSearchIcon) {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                modifier = Modifier.padding(start = 12.dp)
            )
        }

        badge?.let {
            Text(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .background(color = White, shape = RoundedCornerShape(16.dp))
                    .border(width = 1.dp, color = Gray5, shape = RoundedCornerShape(16.dp))
                    .padding(vertical = 4.dp, horizontal = 12.dp),
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = Gray6
            )
        }

        TextField(
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester),
            value = text, onValueChange = {
                onValueChange(it)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearch() }
            ),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium,
            placeholder = {
                Text(
                    placeholder,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                placeholderColor = Gray6,
                textColor = Gray6
            )
        )

        if (text.isNotEmpty()) {
            IconButton(onClick = { onValueChange("") }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_cancel),
                    contentDescription = null,
                )
            }
        }
    }
}