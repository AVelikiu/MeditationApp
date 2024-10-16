package com.example.meditationapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import kotlinx.coroutines.delay
import com.example.meditationapp.ui.theme.MeditationAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeditationAppTheme {
                val navController = rememberNavController()
                AppNavHost(navController)
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("onboarding") { OnboardingScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("main") { MainScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
    }
}

@Composable
fun SplashScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Image(
            painter = painterResource(id = R.drawable.logo1),
            contentDescription = "Logo1",
            modifier = Modifier
                .padding(top = 50.dp)
                .size(500.dp)


        )
    }

    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("onboarding")
    }
}

@Composable
fun OnboardingScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Logo image
        Image(
            painter = painterResource(id = R.drawable.logo1),
            contentDescription = "logo1",
            modifier = Modifier
                .padding(top = 50.dp)
                .align(Alignment.TopCenter)
                .size(300.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "ПРИВЕТ",
                style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold),
                color = Color.White,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = "Наслаждайся отборочными.\nБудь внимателен.\nДелай хорошо.",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center),
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = { navController.navigate("login") },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C9A92))
            ) {
                Text(
                    text = "Войти в аккаунт",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                )
            }

            ClickableText(
                text = AnnotatedString("Еще нет аккаунта? Зарегистрируйтесь"),
                onClick = { navController.navigate("login") },
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}




@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF253334))
    ) {





        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo1),
                contentDescription = "logo1",
                modifier = Modifier
                    .size(140.dp)
            )

            Text(
                text = "Sign in",
                color = Color.White,
                fontSize = 32.sp,
                modifier = Modifier
                    .padding(top = 20.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(top = 160.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = false
                },
                label = { Text("Email", color = if (emailError) Color.Red else Color(0xFF7C9A92)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                isError = emailError,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = if (emailError) Color.Red else Color(0xFF7C9A92),
                    unfocusedIndicatorColor = if (emailError) Color.Red else Color(0xFF7C9A92)
                )
            )
            if (emailError) {
                Text(
                    text = "Поле Email не может быть пустым",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            TextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = false
                },
                label = { Text("Пароль", color = if (passwordError) Color.Red else Color(0xFF7C9A92)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                isError = passwordError,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = if (passwordError) Color.Red else Color(0xFF7C9A92),
                    unfocusedIndicatorColor = if (passwordError) Color.Red else Color(0xFF7C9A92)
                )
            )
            if (passwordError) {
                Text(
                    text = "Поле Пароль не может быть пустым",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(50.dp))


            Button(
                onClick = {
                    emailError = email.isEmpty()
                    passwordError = password.isEmpty()

                    if (!emailError && !passwordError) {
                        navController.navigate("main")
                    }
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(61.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C9A92))
            ) {
                Text(
                    text = "Sign in",
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Normal,
                )
            }


            ClickableText(
                text = AnnotatedString("Register"),
                onClick = { navController.navigate("registration") },
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Right
                ),
                modifier = Modifier.padding(top = 20.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = { navController.navigate("main") },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(61.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C9A92))
            ) {
                Text(
                    text = "Профиль",
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Normal,
                )
            }
        }
    }
}



@Composable
fun MainScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .background(color = Color(0xFF253334))
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hamburger),
                    contentDescription = "гамбургер",
                    modifier = Modifier
                        .size(35.dp)
                        .padding(start = 15.dp)
                        .align(Alignment.CenterVertically),
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(id = R.drawable.logo1),
                    contentDescription = "логотип",
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterVertically),
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "профиль",
                    modifier = Modifier
                        .size(45.dp)
                        .align(Alignment.CenterVertically),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "С возвращением, Alex!",
                color = Color.White,
                fontSize = 30.sp
            )

            Text(
                text = "Каким ты себя ощущаешь сегодня?",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = painterResource(id = R.drawable.first),
                    contentDescription = "первая картинка",
                    modifier = Modifier.size(70.dp) // Сделаем размер адаптивным
                )
                Image(
                    painter = painterResource(id = R.drawable.relaxmood),
                    contentDescription = "настроение расслабленное",
                    modifier = Modifier.size(70.dp) // Адаптивный размер
                )
                Image(
                    painter = painterResource(id = R.drawable.focusmood),
                    contentDescription = "настроение сконцентрированное",
                    modifier = Modifier.size(70.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.anxious),
                    contentDescription = "тревожное настроение",
                    modifier = Modifier.size(70.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.rectangle),
                    contentDescription = "прямоугольник",
                    modifier = Modifier.fillMaxSize()
                )

                Text(
                    text = "Заголовок блока",
                    color = Color.Black,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 25.dp, top = 20.dp)
                )

                Text(
                    text = "Кратенькое описание\nблока с двумя строчками",
                    color = Color.Black,
                    fontSize = 17.sp,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 25.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.picture),
                    contentDescription = "картинка",
                    modifier = Modifier
                        .size(210.dp)
                        .align(Alignment.CenterEnd)
                        .padding(start = 20.dp)
                )

                Button(
                    onClick = {  },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 25.dp, bottom = 10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF253334))
                ) {
                    Text(text = "подробнее")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.rectangle),
                    contentDescription = "прямоугольник",
                    modifier = Modifier.fillMaxSize()
                )

                Text(
                    text = "Заголовок блока",
                    color = Color.Black,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 25.dp, top = 20.dp)
                )

                Text(
                    text = "Кратенькое описание\nблока с двумя строчками",
                    color = Color.Black,
                    fontSize = 17.sp,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 25.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.picture2),
                    contentDescription = "вторая картинка",
                    modifier = Modifier
                        .size(210.dp)
                        .align(Alignment.CenterEnd)
                        .padding(start = 20.dp)
                )

                Button(
                    onClick = {  },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 25.dp, bottom = 10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF253334))
                ) {
                    Text(text = "подробнее")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo1),
                    contentDescription = "логотип",
                    modifier = Modifier.size(80.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.sound),
                    contentDescription = "звук",
                    modifier = Modifier.size(27.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.profile1),
                    contentDescription = "профиль",
                    modifier = Modifier
                        .size(27.dp)
                        .clickable {
                            navController.navigate("profile")
                        }
                )
            }
        }
    }
}



@Composable
fun ProfileScreen(navController: NavHostController) {
    val images = remember { mutableStateListOf(R.drawable.firstp, R.drawable.secondp, R.drawable.thirdp, R.drawable.fourthp) }
    var selectedImage by remember { mutableStateOf<Int?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF253334))
    ) {

        Column(
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hamburger),
                    contentDescription = "hamburger",
                    modifier = Modifier
                        .size(35.dp)
                        .padding(start = 15.dp)
                        .align(Alignment.CenterVertically),
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(id = R.drawable.logo1),
                    contentDescription = "logo1",
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterVertically),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "exit",
                    color = Color.White,
                    fontSize = 17.sp,
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .align(Alignment.CenterVertically)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))


            Image(
                painter = painterResource(id = R.drawable.profilephoto),
                contentDescription = "profilephoto",
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(20.dp))


            Text(
                text = "Alex",
                color = Color.White,
                fontSize = 34.sp,
                modifier = Modifier.padding(end = 15.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))


            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxHeight(0.8f)
            ) {
                items(images) { image ->
                    ImageItem(imageResId = image) {
                        selectedImage = image
                    }
                }
                item {
                    AddNewItem {
                        navController.navigate("camera")
                    }
                }
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter) //
                .height(90.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.main),
                contentDescription = "main",
                modifier = Modifier
                    .size(70.dp)
                    .clickable {
                        navController.navigate("main")
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.sound),
                contentDescription = "sound",
                modifier = Modifier.size(27.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.user1),
                contentDescription = "user1",
                modifier = Modifier.size(27.dp)
            )
        }


        selectedImage?.let { image ->
            FullScreenImageDialog(image) { shouldDelete ->
                if (shouldDelete) {
                    images.remove(image)
                }
                selectedImage = null
            }
        }
    }
}

@Composable
fun ImageItem(imageResId: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun FullScreenImageDialog(imageId: Int, onDismiss: (Boolean) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = imageId),
            contentDescription = "Fullscreen Image",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        )


        Column(
            modifier = Modifier.align(Alignment.BottomCenter)

                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { onDismiss(false) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C9A92))
            ) {
                Text("Закрыть")
            }
            Button(onClick = { onDismiss(true) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C9A92))) {
                Text("Удалить")
            }
        }
    }
}

@Composable
fun AddNewItem(
    context: () -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data

        }
    }
    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF6AAE72))
            .clickable {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                launcher.launch(intent)
            }
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add New",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .size(48.dp)
        )
    }
}
