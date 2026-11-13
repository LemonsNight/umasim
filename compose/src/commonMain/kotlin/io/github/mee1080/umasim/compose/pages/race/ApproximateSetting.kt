package io.github.mee1080.umasim.compose.pages.race

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.mee1080.umasim.compose.common.atoms.SelectBox
import io.github.mee1080.umasim.race.data.PositionKeepMode
import io.github.mee1080.umasim.race.data.defaultCompeteFightRate
import io.github.mee1080.umasim.race.data.defaultPositionCompetitionRate
import io.github.mee1080.umasim.race.data.defaultSecureLeadRate
import io.github.mee1080.umasim.race.data2.ApproximateMultiCondition
import io.github.mee1080.umasim.race.data2.approximateConditions
import io.github.mee1080.umasim.store.AppState
import io.github.mee1080.umasim.store.framework.OperationDispatcher
import io.github.mee1080.umasim.store.operation.*
import io.github.mee1080.utility.toPercentString

@Composable
fun ApproximateSetting(state: AppState, dispatch: OperationDispatcher<AppState>) {
    val positionKeepMode by derivedStateOf { state.setting.positionKeepMode }
    val positionKeepRate by derivedStateOf { state.setting.positionKeepRate }
    HorizontalDivider()

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        val systemSetting = state.systemSetting
        Column {
            Text("近似條件", style = MaterialTheme.typography.headlineSmall)
            Text("以下項目因難以完整模擬，目前採用近似計算")
            Text("（未來預計開放調整）", style = MaterialTheme.typography.bodySmall)
        }

        Column {
            Text("全力衝刺", style = MaterialTheme.typography.titleLarge)
            Text("由於最高速度尚未知確，因此速度超過 2000 時視為不設上限")
        }

        Column {
            Text("位置保持", style = MaterialTheme.typography.titleLarge)

            SelectBox(
                PositionKeepMode.entries, positionKeepMode,
                onSelect = { dispatch(setPositionKeepMode(it)) },
                modifier = Modifier.width(512.dp),
                label = { Text("模式") },
                itemToString = { it.label },
            )
            when (positionKeepMode) {
                PositionKeepMode.APPROXIMATE -> {
                    Text("在以下區段進入降速模式")
                    Text("即使處於掛心狀態也會發動（為了固定位置）")
                    Text("尚未實作領頭跑法的各種模式及加速模式")
                    Text(
                        "先行：${
                            systemSetting.positionKeepSectionSen.mapIndexed { index, value -> index to value }
                                .filter { it.second }
                                .joinToString { it.first.toString() }
                        }"
                    )
                    Text(
                        "差行：${
                            systemSetting.positionKeepSectionSasi.mapIndexed { index, value -> index to value }
                                .filter { it.second }
                                .joinToString { (it.first + 1).toString() }
                        }"
                    )
                    Text(
                        "追込：${
                            systemSetting.positionKeepSectionOi.mapIndexed { index, value -> index to value }
                                .filter { it.second }
                                .joinToString { (it.first + 1).toString() }
                        }"
                    )
                }

                PositionKeepMode.VIRTUAL -> {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Column {
                            Text("依照與以下角色的距離進行判定")
                            Text("但尚未實作領頭馬之間的競爭（虛擬配速馬會以固定機率進入加速模式）")
                        }
                        Text("虛擬配速馬進入加速模式的機率：$positionKeepRate %")
                        Slider(
                            value = positionKeepRate.toFloat(),
                            onValueChange = { dispatch(setPositionKeepRate(it.toInt())) },
                            valueRange = 0f..100f,
                            steps = 100,
                            modifier = Modifier.fillMaxWidth(),
                        )
                        ImportExport(true, state, dispatch)
                        CharaInput(true, state, dispatch)
                        SkillInput(true, state, dispatch)
                    }
                }

                PositionKeepMode.SPEED_UP -> {
                    Text("以一定機率進入加速模式（實際遊戲中還會受到智力判定影響）")
                    Text("機率：$positionKeepRate %")
                    Slider(
                        value = positionKeepRate.toFloat(),
                        onValueChange = { dispatch(setPositionKeepRate(it.toInt())) },
                        valueRange = 0f..100f,
                        steps = 100,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                PositionKeepMode.NONE -> {
                    Text("不進行位置保持判定")
                }
            }
        }

        Column {
            Text("行進跑道", style = MaterialTheme.typography.titleLarge)
            Text("超越模式判定及側向阻擋造成的停止移動採用近似計算（請參閱技能發動設定）")
            Text("進入超越模式時，會沿著距離內側欄杆一個馬身的位置行進")
            Text("發動目標速度或目前速度技能時，會依照速度技能發動時的跑道變換機率向外移動一個馬身")
            Text("外側繞行損失目前一律以所有彎道皆為 90 度計算（未來希望能精確計算）")
        }

        Column {
            Text("位置競爭", style = MaterialTheme.typography.titleLarge)
            Text("領頭跑法時於${systemSetting.leadCompetitionPosition}公尺處固定發動")
        }

        Column {
            Text("追い比べ", style = MaterialTheme.typography.titleLarge)
            Text("在最後直線每秒以 ${systemSetting.competeFightRate.toPercentString()} 的機率發動")
            Row(verticalAlignment = Alignment.CenterVertically) {
                Slider(
                    value = (systemSetting.competeFightRate * 100).toFloat(),
                    onValueChange = { dispatch(setCompeteFightRate(it.toDouble() / 100.0)) },
                    valueRange = 0f..100f,
                    steps = 99,
                    modifier = Modifier.weight(1f),
                )
                Button(
                    onClick = { dispatch(setCompeteFightRate(defaultCompeteFightRate)) },
                ) { Text("重設") }
            }
        }

        Column {
            Text("腳色充足", style = MaterialTheme.typography.titleLarge)
            Text("持續時間固定為 3 秒 × 距離係數（0.45／1.0／0.875／0.8）")
            Text("尚未實作蓄力機制（目前理解為該機制尚未完成解析）")
        }

        Column {
            Text("耐力保留", style = MaterialTheme.typography.titleLarge)
            Text("若體力不足，將以 ${systemSetting.staminaKeepRate.toPercentString()} 的機率發動")
        }

        Column {
            Text("位置調整", style = MaterialTheme.typography.titleLarge)
            Text("與領頭馬的距離判定，以及附近是否有賽馬娘的判定，一律視為成功")
            Text("若未觸發耐力保留，則以 ${systemSetting.positionCompetitionRate.toPercentString()} 的機率發動")
            Text("實際發動次數接近期望值的機率約為 40%～60%（若同時判定耐力是否足夠則為 100%）")
            Row(verticalAlignment = Alignment.CenterVertically) {
                Slider(
                    value = (systemSetting.positionCompetitionRate * 100).toFloat(),
                    onValueChange = { dispatch(setPositionCompetitionRate(it.toDouble() / 100.0)) },
                    valueRange = 0f..100f,
                    steps = 99,
                    modifier = Modifier.weight(1f),
                )
                Button(
                    onClick = { dispatch(setPositionCompetitionRate(defaultPositionCompetitionRate)) },
                ) { Text("重設") }
            }
        }

        Column {
            Text("確保領先", style = MaterialTheme.typography.titleLarge)
            Text("除追込以外的跑法，以 ${systemSetting.secureLeadRate.toPercentString()} 的機率發動")
            Text("當自身採用領頭跑法、位置保持模式為虛擬配速馬，且對手跑法與自身不同時，速度提升量會乘上倍率")
            Row(verticalAlignment = Alignment.CenterVertically) {
                Slider(
                    value = (systemSetting.secureLeadRate * 100).toFloat(),
                    onValueChange = { dispatch(setSecureLeadRate(it.toDouble() / 100.0)) },
                    valueRange = 0f..100f,
                    steps = 99,
                    modifier = Modifier.weight(1f),
                )
                Button(
                    onClick = { dispatch(setSecureLeadRate(defaultSecureLeadRate)) },
                ) { Text("重設") }
            }
        }

        Column {
            Text("耐力比拚", style = MaterialTheme.typography.titleLarge)
            Text("遊戲中似乎會隨機套用 0.95～1.02 倍率，目前固定為 1.0 倍")
        }

        Column {
            Text("技能發動", style = MaterialTheme.typography.titleLarge)
            Text("涉及其他賽馬娘的技能發動條件，每秒依照以下規則判定")
            Text(
                "（目前設定較為粗略，歡迎回報與實際情況不符之處）",
                style = MaterialTheme.typography.bodySmall
            )
            approximateConditions.forEach { (key, condition) ->
                Text(condition.displayName, modifier = Modifier.padding(top = 8.dp))
                if (condition.valueOnStart > 0) {
                    Text("起跑時啟用判定", modifier = Modifier.padding(start = 8.dp))
                }
                if (condition is ApproximateMultiCondition) {
                    condition.conditions.forEach {
                        Text(
                            "${it.first.displayName} : ${it.first.description}",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                } else {
                    Text(condition.description, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
    }
}
