package com.example.chatapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.chatapp.R
import com.example.chatapp.utils.forwardingPainter

@Composable
fun UserProfile() {
    Column(modifier = Modifier
        .fillMaxSize(1f)
        .background(MaterialTheme.colorScheme.background)) {
        // userpic
        UserPhoto(profilePicUrl = "", modifier = Modifier
            .align(alignment = Alignment.CenterHorizontally)
            .padding(top = 48.dp)
            .height(150.dp)
            .width(150.dp))

        UserNameBlock("Username", modifier = Modifier
            .align(alignment = Alignment.CenterHorizontally)
            .padding(16.dp))

        InterestedIn(onClick = {
            // open a dialog box to select from male and female
        })
        Spacer(modifier = Modifier.weight(1f)) // fills the remaining space so that the items below this composable occupy the bottom of the screen
        // save button
        CancelSaveButtons(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp), cancelButtonClick = {}, saveButtonClick = {})
    }
}

@Composable
fun CancelSaveButtons(modifier: Modifier, cancelButtonClick: () -> Unit, saveButtonClick: () -> Unit) {
    Row (modifier = modifier) {
       Button(onClick = { cancelButtonClick() }, modifier = Modifier
           .weight(1f)
           .padding(start = 8.dp, end = 8.dp)) {
           Text(text = "Cancel")
       }
       Button(onClick = { saveButtonClick() }, modifier = Modifier
           .weight(1f)
           .padding(start = 8.dp, end = 8.dp)) {
           Text(text = "Save")
       }
    }
}

@Composable
fun UserNameBlock(username: String, modifier: Modifier) {
    Text(text = username, color = MaterialTheme.colorScheme.onPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = modifier)
}

@Composable
fun InterestedIn(onClick: () -> Unit) {
    Row (modifier = Modifier
        .clickable(onClick = onClick)
        .padding(16.dp)) {
       Text(text = "Interested In", color = MaterialTheme.colorScheme.onPrimary, fontSize = 18.sp)
    }
}

@Composable
fun UserPhoto(profilePicUrl: String, modifier: Modifier) {

    AsyncImage(
        model = profilePicUrl,
        contentDescription = "",
        error = forwardingPainter(painter = painterResource(id = R.drawable.ic_baseline_person), colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)),
        fallback = forwardingPainter(painter = painterResource(id = R.drawable.ic_baseline_person), colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)),
        modifier = modifier
            .clip(CircleShape)
            .border(width = 4.dp, shape = CircleShape, color = MaterialTheme.colorScheme.onPrimary)
    )
}

