package io.github.mee1080.umasim.compose.pages.race

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.mee1080.umasim.compose.common.atoms.MyButton
import io.github.mee1080.umasim.compose.common.atoms.TextWithLink
import io.github.mee1080.umasim.race.calc2.UmaStatus
import io.github.mee1080.umasim.store.AppState
import io.github.mee1080.umasim.store.ImportExportConverter
import io.github.mee1080.umasim.store.framework.OperationDispatcher
import io.github.mee1080.umasim.store.operation.importChara

@Composable
fun ImportExport(virtual: Boolean, state: AppState, dispatch: OperationDispatcher<AppState>) {
    val chara by derivedStateOf { state.chara(virtual) }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ImportDialog(virtual, dispatch)
        ExportDialog(chara)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ImportDialog(virtual: Boolean, dispatch: OperationDispatcher<AppState>) {
    var open by remember { mutableStateOf(false) }
    MyButton({ open = true }) {
        Text("匯入")
    }
    if (open) {
        var input by remember { mutableStateOf("") }
        var result by remember { mutableStateOf<UmaStatus?>(null) }
        AlertDialog(
            onDismissRequest = { open = false },
            text = {
                Column {
                    Text("※請在欄位中使用 Ctrl+V 貼上，再按「匯入」（Android 可能不支援）")
                    Text("※格式較為寬鬆，也可以使用 ChatGPT 等工具進行 OCR 後匯入")
                    TextWithLink("　例： https://x.com/mee10801/status/1796811671304028542")
                    OutlinedTextField(
                        value = input,
                        onValueChange = { input = it },
                        modifier = Modifier.heightIn(max = 200.dp),
                    )
                    MyButton(
                        onClick = { result = ImportExportConverter.importChara(input) },
                        modifier = Modifier.padding(vertical = 8.dp),
                    ) {
                        Text("匯入")
                    }
                    result?.let {
                        Text("角色：${it.charaName}")
                        Text("能力值：${it.speed}/${it.stamina}/${it.power}/${it.guts}/${it.wisdom}")
                        Text("適性：バ場${it.surfaceFit}/距離${it.distanceFit}/脚質${it.styleFit}")
                        if (it.hasSkills.isNotEmpty()) {
                            Text("技能：")
                            FlowRow(Modifier.padding(start = 16.dp)) {
                                it.hasSkills.forEach { skill ->
                                    Text("${skill.displayNameZhTw()}, ")
                                }
                            }
                        }
                        Text("※僅會匯入能力值、適性與技能")
                    }
                }
            },
            confirmButton = {
                MyButton(
                    onClick = {
                        result?.let {
                            dispatch(importChara(virtual, it))
                        }
                        open = false
                    },
                    enabled = result != null,
                ) {
                    Text("套用")
                }
            },
            dismissButton = {
                MyButton({ open = false }) {
                    Text("取消")
                }
            }
        )
    }
}

@Composable
private fun ExportDialog(chara: UmaStatus) {
    var open by remember { mutableStateOf(false) }
    MyButton({ open = true }) {
        Text("匯出")
    }
    if (open) {
        var value by remember { mutableStateOf("") }
        LaunchedEffect(Unit) {
            value = ImportExportConverter.exportChara(chara)
        }
        AlertDialog(
            onDismissRequest = { open = false },
            text = {
                Column {
                    OutlinedTextField(
                        value = value,
                        onValueChange = {},
                        readOnly = true,
                    )
                    Text("※請在欄位中使用 Ctrl+C 複製（Android 不支援）")
                    Text("※僅會匯出能力值、適性與技能")
                }
            },
            confirmButton = {
                MyButton({ open = false }) {
                    Text("關閉")
                }
            },
        )
    }
}
