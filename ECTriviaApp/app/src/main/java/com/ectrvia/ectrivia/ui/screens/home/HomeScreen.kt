package com.ectrvia.ectrivia.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ectrvia.ectrivia.ui.components.ECTriviaButton
import com.ectrvia.ectrivia.ui.theme.ECTriviaBackground
import com.ectrvia.ectrivia.ui.theme.ECTriviaPrimary
import com.ectrvia.ectrivia.ui.theme.TextPrimary
import com.ectrvia.ectrivia.ui.theme.TextSecondary

@Suppress("UNUSED_PARAMETER")
@Composable
fun HomeScreen(
    onCreateRoom: () -> Unit,
    onJoinRoom: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ECTriviaBackground)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, ECTriviaPrimary.copy(alpha = 0.15f), RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "EC Trivia",
                    style = MaterialTheme.typography.headlineLarge,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(24.dp))

                ECTriviaButton(
                    text = "Create Room",
                    onClick = onCreateRoom,
                    isPrimary = true
                )
            }
        }
    }
}
