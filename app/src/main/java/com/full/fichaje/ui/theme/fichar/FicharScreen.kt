package com.full.crm.ui.theme.agenda

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.full.fichaje.OptionsBar
import java.util.Calendar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Fichajes(modifier: Modifier = Modifier, ficharViewModel: FicharViewModel) {

    Scaffold(bottomBar = { OptionsBar(modifier = Modifier.padding(top = 5.dp),selectedIcon = 1) }) {
        Column {
            Row {

            }
            Row(modifier = Modifier.padding(bottom = 75.dp)) {
                LazyColumn(
                    content =
                    {
                        items(100) {
                            Text(text = "Item $it")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}