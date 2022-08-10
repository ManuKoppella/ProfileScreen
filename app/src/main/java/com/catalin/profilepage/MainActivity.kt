package com.catalin.profilepage

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.catalin.profilepage.ui.theme.ProfilePageTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfilePageTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ProfileScreen()
                }
            }
        }
    }
}

@Composable
fun ProfileScreen() {
     val image = painterResource(R.drawable.img_4081)
    // Create a box to overlap image and texts
    Box {
                Image(
                    painter = image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )

        val notification = rememberSaveable { mutableStateOf("") }
        if (notification.value.isNotEmpty()) {
            Toast.makeText(LocalContext.current, notification.value, Toast.LENGTH_LONG).show()
            notification.value = ""
        }

        var username by rememberSaveable { mutableStateOf("default Username") }
        var event by rememberSaveable { mutableStateOf("Add Event") }
        var journal by rememberSaveable {
            mutableStateOf(
                "Add to journal+ "
            )
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Cancel",
                    modifier = Modifier.clickable { notification.value = "Cancelled" })
                Text(text = "Save",
                    modifier = Modifier.clickable { notification.value = "Profile updated" })
            }

            ProfileImage()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Username: ", modifier = Modifier.width(100.dp))
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        textColor = Color.Black
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(text = "Upcoming Events:", modifier = Modifier.width(100.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {

                    Text(
                        text = "4/27: LQBTQ Rally", modifier = Modifier
                            .width(250.dp)
                            .padding(start = 15.dp)
                    )
                    Text(
                        text = "5/15: NAACP Protest", modifier = Modifier
                            .width(250.dp)
                            .padding(start = 15.dp)
                    )
                    Text(
                        text = "5/29: Association on American...", modifier = Modifier
                            .width(250.dp)
                            .padding(start = 15.dp)
                    )
                    TextField(
                        value = event,
                        onValueChange = { event = it },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            textColor = Color.Black

                        ),
                        singleLine = false,
                        modifier = Modifier.height(50.dp)

                    )

                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "Journal:", modifier = Modifier
                        .width(100.dp)
                        .padding(top = 8.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Events 1", modifier = Modifier
                            .width(100.dp)
                            .padding(start = 15.dp)
                    )
                    Text(
                        text = "Events 2", modifier = Modifier
                            .width(100.dp)
                            .padding(start = 15.dp)
                    )
                    Text(
                        text = "Events 3", modifier = Modifier
                            .width(100.dp)
                            .padding(start = 15.dp)
                    )

                    TextField(
                        value = journal,
                        onValueChange = { journal = it },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            textColor = Color.Black
                        ),
                        singleLine = false,
                        modifier = Modifier.height(50.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileImage() {
    val imageUri = rememberSaveable { mutableStateOf("") }
    val painter = rememberImagePainter(
        if (imageUri.value.isEmpty())
            R.drawable.ic_user
        else
            imageUri.value
    )
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri.value = it.toString() }
    }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Card(
            shape = CircleShape,
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .wrapContentSize()
                    .clickable { launcher.launch("image/*") },
                contentScale = ContentScale.Crop
            )
        }
        Text(text = "Change profile picture")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProfilePageTheme {
        ProfileScreen()
    }
}