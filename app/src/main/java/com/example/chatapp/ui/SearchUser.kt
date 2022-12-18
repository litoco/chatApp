package com.example.chatapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.viewmodels.SearchUserViewModel

@Composable
fun RandomlySearchUser(
    viewModel: SearchUserViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Box(modifier = Modifier.fillMaxSize(1f)
    ) {
        val userNameList = viewModel.getUserNameList()
        var textBoxText = viewModel.getTextBoxText()

        if (userNameList.size > 0) {
            LazyColumn(modifier = Modifier
                .fillMaxWidth(1f)
                .padding(top = 16.dp)) {
                items(userNameList) { username ->
                    Text(
                        text = username,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(bottom = 16.dp)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onPrimary,
                                shape = RoundedCornerShape(corner = CornerSize(10.dp))
                            )
                            .padding(16.dp)
                    )
                }
            }
        } else {
            Text(text = textBoxText,
                modifier = Modifier.padding(16.dp).align(alignment = Alignment.Center),
                fontSize = 18.sp, textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary)
        }

        IconButton(
            onClick = { viewModel.populateList() },
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(corner = CornerSize(10.dp)))
                .align(alignment = Alignment.BottomEnd)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Icon(
                imageVector = Icons.Filled.Refresh, contentDescription = "Refresh Users",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(width = 32.dp, height = 32.dp)
            )
        }

    }
}