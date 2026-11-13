package io.github.mee1080.umasim.compose.pages.race

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.mee1080.umasim.compose.common.atoms.LabeledCheckbox
import io.github.mee1080.umasim.compose.common.parts.LinedTable
import io.github.mee1080.umasim.compose.common.parts.NumberInput
import io.github.mee1080.umasim.compose.common.parts.Table
import io.github.mee1080.umasim.race.calc2.RaceSetting
import io.github.mee1080.umasim.store.*
import io.github.mee1080.umasim.store.framework.OperationDispatcher
import io.github.mee1080.umasim.store.operation.deleteHistoryEntry
import io.github.mee1080.umasim.store.operation.selectHistoryEntry
import io.github.mee1080.utility.roundToString
import io.github.mee1080.utility.secondToTimeString
import io.github.mee1080.utility.toPercentString
import kotlin.math.roundToInt

@Composable
fun SummaryOutput(state: AppState, dispatch: OperationDispatcher<AppState>) {
    if (state.simulationHistory.isEmpty()) return
    val selectedEntry =
        state.simulationHistory.firstOrNull { it.id == state.selectedSimulationId } ?: state.simulationHistory.last()
    val summary = selectedEntry.summary
    Column {
        Text("結果", style = MaterialTheme.typography.headlineSmall)
        SelectionContainer {
            SummaryTable(state, dispatch)
        }
        if (summary.setting.trackDetail.runUp > 0) {
            Text("※各項時間包含起跑助走區間")
        }
        SelectionContainer {
            SkillTable(summary)
        }
    }
}

private val rowLabels = listOf(
    "最大衝刺率",
    "完賽率",
    "平均時間",
    "最快時間",
    "最慢時間",
    "衝刺平均剩餘耐力",
    "衝刺最大剩餘耐力",
    "衝刺最小剩餘耐力",
    "平均剩餘體力",
    "最大剩餘體力",
    "最小剩餘體力",
    "位置調整次數",
    "耐力保留發生率",
    "耐力保留平均距離",
    "競り追完賽率",
    "競り追平均時間",
)

@Composable
private fun SummaryTable(state: AppState, dispatch: OperationDispatcher<AppState>) {
    Column {
        val scrollState = rememberScrollState()
        val rowCount = 17
        val columnCount = state.simulationHistory.size + 1
        LinedTable(
            rowCount = rowCount, columnCount = columnCount,
            modifier = Modifier.fillMaxWidth().horizontalScroll(scrollState),
            cellBackground = MaterialTheme.colorScheme.surface,
            cellPadding = 4.dp,
        ) { row, column ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = when {
                    row == 0 -> Alignment.CenterHorizontally
                    column == 0 -> Alignment.Start
                    else -> Alignment.End
                },
                verticalArrangement = Arrangement.Center,
            ) {
                if (column == 0) {
                    if (row == 0) {
                        Box(Modifier.height(32.dp))
                    } else {
                        Text(
                            text = rowLabels[row - 1],
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                } else {
                    val entry = state.simulationHistory[column - 1]
                    val isSelected = entry.id == state.selectedSimulationId
                    if (row == 0) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.height(32.dp).padding(horizontal = 4.dp)
                        ) {
                            Text(
                                text = "#${entry.id}",
                                style = if (isSelected) MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold) else MaterialTheme.typography.bodyMedium,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .clickable { dispatch(selectHistoryEntry(entry.id)) }
                                    .padding(4.dp)
                            )
                            Text(
                                text = "✕",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier
                                    .clickable { dispatch(deleteHistoryEntry(entry.id)) }
                                    .padding(4.dp)
                            )
                        }
                    } else {
                        val valueStr = toComparisonTableData(entry)[row - 1]
                        Text(
                            text = valueStr,
                            style = if (isSelected) MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold) else MaterialTheme.typography.bodyMedium,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 4.dp),
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
        HorizontalScrollbar(rememberScrollbarAdapter(scrollState), Modifier.fillMaxWidth())
    }
}

private fun toComparisonTableData(entry: SimulationHistoryEntry): List<String> {
    val summary = entry.summary
    val allSummary = summary.allSummary
    return if (allSummary.count == 0) {
        listOf(
            summary.spurtRate.toPercentString(2),
            summary.finishRate.toPercentString(2),
        ) + List(14) { "-" }
    } else {
        listOf(
            summary.spurtRate.toPercentString(2),
            summary.finishRate.toPercentString(2),
            allSummary.averageTime.secondToTimeString(),
            allSummary.bestTime.secondToTimeString(),
            allSummary.worstTime.secondToTimeString(),
            allSummary.averageSp.roundToString(1),
            allSummary.bestSp.roundToString(1),
            allSummary.worstSp.roundToString(1),
            allSummary.averageGoalSp.roundToString(1),
            allSummary.bestGoalSp.roundToString(1),
            allSummary.worstGoalSp.roundToString(1),
            allSummary.positionCompetitionCount.roundToString(2),
            allSummary.staminaKeepRate.toPercentString(1),
            allSummary.staminaKeepDistance.roundToString(1),
            allSummary.competeFightFinishRate.toPercentString(1),
            allSummary.competeFightTime.roundToString(1),
        )
    }
}

@Composable
private fun SkillTable(summary: SimulationSummary) {
    Column {
        val summaries = summary.skillSummaries
        if (summaries.isEmpty()) return
        val tableData = buildList {
            add(
                listOf(
                    "發動次數",
                    "發動率",
                    "平均發動位置 1",
                    "二次發動率",
                    "平均發動位置 2",
                    "序盤發動率",
                    "中盤銜接率",
                    "平均中盤銜接時間",
                    "中盤發動率",
                    "終盤銜接率",
                    "平均終盤銜接時間",
                    "終盤發動率",
                    "平均終盤延遲",
                    "速度提升無效比例",
                )
            )
            summaries.forEach { add(toTableData(summary.setting, it.second)) }
        }
        var calcSp by remember { mutableStateOf(true) }
        var kire by remember { mutableStateOf(false) }
        val rareHintLvList = remember(summaries) {
            SnapshotStateList(summaries.size) { 2 }
        }
        val normalHintLvList = remember(summaries) {
            SnapshotStateList(summaries.size) { 4 }
        }
        val kireFactor = if (kire) 0.1 else 0.0
        val skillPtList = summaries.mapIndexed { index, (_, skill) ->
            val rareFactor = skillLvToFactor[rareHintLvList[index]] - kireFactor
            val normalFactor = skillLvToFactor[normalHintLvList[index]] - kireFactor
            (skill.rareSkillPt * rareFactor).toInt() + skill.normalSkillPt.sumOf { (it * normalFactor).toInt() }
        }
        Row(
            modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
        ) {
            Text("技能情報")
            LabeledCheckbox(calcSp, { calcSp = it }) {
                Text("SP計算")
            }
        }
        Row {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text("", Modifier.padding(4.dp))
                summaries.forEach {
                    Text(it.first, Modifier.padding(4.dp))
                }
            }
            Table(
                tableData.size, 14, scrollable = true,
                modifier = Modifier.weight(1f),
            ) { row, col ->
                Text(
                    tableData[row][col], Modifier.padding(4.dp).align(
                        when {
                            row == 0 -> Alignment.Center
                            col == 2 || col == 4 -> Alignment.CenterStart
                            else -> Alignment.CenterEnd
                        }
                    )
                )
            }
            if (calcSp) {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text("SP / ヒントLv / 下位", Modifier.padding(4.dp))
                    summaries.forEachIndexed { index, (_, skill) ->
                        Row(
                            modifier = Modifier.padding(4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = skillPtList[index].toString(),
                                modifier = Modifier.width(40.dp),
                                textAlign = TextAlign.End,
                            )
                            if (skill.rareSkillPt > 0) {
                                Text("/")
                                NumberInput(
                                    value = rareHintLvList[index], onValueChange = { rareHintLvList[index] = it },
                                    min = 0, max = 5,
                                )
                            }
                            if (skill.normalSkillPt.isNotEmpty()) {
                                Text("/")
                                NumberInput(
                                    value = normalHintLvList[index], onValueChange = { normalHintLvList[index] = it },
                                    min = 0, max = 5,
                                )
                            }
                        }
                    }
                }
            }
        }
        if (calcSp) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("技能點數合計： ${skillPtList.sum()}")
                LabeledCheckbox(kire, { kire = it }) { Text("切れ者") }
            }
        }
    }
}

private fun toTableData(setting: RaceSetting, entry: SimulationSkillSummary): List<String> {
    return if (entry.count == 0) {
        listOf("0") + List(13) { "-" }
    } else {
        listOf(
            entry.count.toString(),
            entry.triggerRate.toPercentString(1),
            (entry.averageStartFrame1 / 15.0).roundToString(2, "s") + "/" +
                    entry.averageStartPosition1.roundToString(2, "m") +
                    toPositionString(setting, entry.averageStartPosition1),
            entry.doubleTriggerRate.toPercentString(1),
            (entry.averageStartFrame2 / 15.0).roundToString(2, "s") + "/" +
                    entry.averageStartPosition2.roundToString(2, "m") +
                    toPositionString(setting, entry.averageStartPosition2),
            entry.phase0TriggeredRate.toPercentString(1),
            entry.phase1ConnectionRate.toPercentString(1),
            (entry.averagePhase1ConnectionFrame / 15.0).roundToString(2, "s"),
            entry.phase1TriggeredRate.toPercentString(1),
            entry.phase2ConnectionRate.toPercentString(1),
            (entry.averagePhase2ConnectionFrame / 15.0).roundToString(2, "s"),
            entry.phase2TriggeredRate.toPercentString(1),
            (entry.averagePhase2DelayFrame / 15.0).roundToString(2, "s"),
            entry.invalidRate.toPercentString(1),
        )
    }
}

private fun toPositionString(setting: RaceSetting, position: Double): String {
    return when {
        position.isNaN() || position.isInfinite() -> ""
        position < setting.phase0Half -> ""
        position < setting.phase1Start -> "(中盤入り前${(setting.phase1Start - position).roundToInt()}m)"
        position < setting.phase1Half -> "(中盤開始${(position - setting.phase1Start).roundToInt()}m)"
        position < setting.phase2Start -> "(終盤入り前${(setting.phase2Start - position).roundToInt()}m)"
        position < setting.phase2Half -> "(終盤開始${(position - setting.phase2Start).roundToInt()}m)"
        else -> "(ゴール前${(setting.courseLength - position).roundToInt()}m)"
    }
}
