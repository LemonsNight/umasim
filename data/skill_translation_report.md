# UmaSim /race/ 技能繁體中文漢化 — 第二階段報告

生成時間：2026-11-13T18:03:11+08:00

## 一、翻譯來源與匹配依據

- 翻譯基準：`skill_translation_source.csv`（2168 筆，ID 無重複）。
- 原始資料：`data/skill_data.txt`（`SkillData.info` / `description` / `invokes`）。
- 繁中來源：**UmamusumeDB 繁中站**（https://umamusumedb.com/zh-tw/skills/），其資料說明頁
  （https://umamusumedb.com/data-sources/）明確記載：繁體中文（台灣服）名稱與說明
  來自 **GameTora 的官方遊戲內文本**；簡體中文為 OpenCC 轉寫，**未使用**。
- 資料檔案：`public/data/skills.json`、`public/data/skills-lite.zh-tw.json`
  （https://github.com/shiyuzi-ship-it/umamusumedb）。
- 匹配規則：`技能 ID == skills.json.gt_id` **且** `CSV 日文名稱 == skills.json.name_jp`。
- 說明規則：`skills.json.description_jp == data/skill_data.txt info[0]` 完全相同時，
  才把 `skills-lite.zh-tw.json.effect` 視為該句的官方繁中對應。

## 二、完整統計

| 項目 | 數量 |
| --- | ---: |
| 總技能數量 | 2168 |
| 技能 ID 重複 | 0 |
| 重複日文名稱（組數 / 涉及列數） | 273 / 549 |
| ID + 日文名雙重核對通過 | 1887 |
| **成功匹配官方繁中名稱** | **947** |
| ├ 其中繁中名與日文同形（顯示不變） | 123 |
| **成功匹配官方繁中說明** | **953** |
| 未找到官方繁中名稱 | 1221 |
| 未找到官方繁中說明 | 1215 |
| 已核對 ID 但無繁中條目（pending） | 725 |
| 資料庫無對應 ID（unmatched） | 281 |
| 繁中欄位為英文／日文回退值 | 215 |
| status = verified | 1021 |
| status = pending | 725 |
| status = unmatched | 422 |
| 非 verified 技能列（合計） | 1147 |
| 模擬器提示用語（translated_unverified，待校對） | 36 |
| 唯一日文名補充顯示映射 | 793 |

說明：953 筆官方繁中說明全數通過 `description_jp == info[0]` 精確核對（零衝突）；
1887 筆 ID 命中者之日文名稱 100% 與資料庫一致（零誤配）。

## 三、不確定的翻譯（待校對）

### 3.1 模擬器固定提示用語（`translated_unverified`）

下列為**模擬器自行產生**的提示文字，並非遊戲官方文本，由本專案 UI 層翻譯，
僅做整句精確替換，所有數值／倍率／單位／比較符號原樣保留。請人工校對用詞：

| 日文原文 | 繁中顯示 |
| --- | --- |
| 発動条件近似 | 發動條件近似 |
| GI条件は無視 | 忽略 GI 條件 |
| ナイター条件は無視 | 忽略夜賽條件 |
| 季節条件は無視 | 忽略季節條件 |
| 天候条件は無視 | 忽略天候條件 |
| 交流重賞条件はレース場のみ判定、重賞かどうかは無視 | 交流重賞條件僅判定賽場，忽略是否為重賞 |
| 順位条件は無視 | 忽略名次條件 |
| 相対位置条件は無視 | 忽略相對位置條件 |
| 他のウマ娘との距離条件は無視 | 忽略與其他賽馬娘的距離條件 |
| 他のウマ娘のスキル条件は無視 | 忽略其他賽馬娘的技能條件 |
| 内外条件は無視 | 忽略內外側條件 |
| 他のウマ娘の作戦条件は無視 | 忽略其他賽馬娘的跑法條件 |
| 視界内のウマ娘条件は満たしている前提 | 以視野內有賽馬娘為前提 |
| チームのスキル発動数条件は無視 | 忽略隊伍技能發動次數條件 |
| ファン数は満たす前提 | 以粉絲數達標為前提 |
| 前提スキルは強い方の発動が前提 | 以效果較強的前提技能已發動為前提 |
| 先頭との距離は最大（1.6倍）固定 | 與領頭馬的距離固定為最大（1.6倍） |
| 発動直後に2回追い抜く条件で近似 | 以發動後立即超越2次為條件近似 |
| チームメンバーのステータス合計条件は最大値前提 | 以隊伍成員能力值總和為最大值前提 |
| 勝利数条件は最大値前提 | 以勝場數為最大值前提 |
| 追い抜き回数は2回（1.1倍）固定 | 超越次數固定為2次（1.1倍） |
| ファン数条件は最大値前提 | 以粉絲數條件為最大值前提 |
| 先頭から離れている条件は満たす前提 | 以落後領頭馬為前提 |
| 中盤連続競り合いは4～6秒（3.0倍）固定 | 中盤連續競爭固定為4～6秒（3.0倍） |
| UAFは全勝前提 | 以 UAF 全勝為前提 |
| 海外適性Lv合計は最大値前提 | 以海外適性Lv總和為最大值前提 |
| 終盤開始までに取ったリードの距離は最大（1.8倍）固定 | 終盤開始前取得的領先距離固定為最大（1.8倍） |
| お料理Ptは16000以上（1.2倍）前提 | 以料理Pt達16000以上（1.2倍）為前提 |
| 総研究Lvは最大前提 | 以總研究Lv為最大值前提 |
| ラヴは最大前提 | 以愛意值為最大值前提 |
| 総発展Ptは最大前提（1.2倍） | 以總發展Pt為最大值前提（1.2倍） |
| 伝説の宿前提（1.2倍） | 以傳說之宿為前提（1.2倍） |
| チームランクS以上前提（1.2倍） | 以隊伍階級S以上為前提（1.2倍） |
| 同時に別のスキルを2つ発動する条件で近似 | 近似為同時發動另外2個技能 |
| 同時に別のスキルを1つ発動する条件で近似 | 近似為同時發動另外1個技能 |
| 外方向移動に対する横ブロックは未実装 | 尚未實作對外側移動的側向阻擋 |

### 3.2 繁中名稱與日文同形（123 筆）

台服官方名稱與日文原名使用相同漢字，顯示結果不變，故標為 verified 但列出供確認。
完整清單可由 `skill_translation_zh_TW.json` 中 `zh_TW == ja` 篩出。前 20 筆：

| ID | 名稱 |
| --- | --- |
| 101371 | 想像×創造＝∞ |
| 110191 | 萌到讓我活過來了！ |
| 110211 | 火神鳴 |
| 110851 | 蒼炎 |
| 200184 | 青嵐 |
| 200301 | 伏兵◎ |
| 200302 | 伏兵○ |
| 200372 | 直線加速 |
| 200382 | 直線回復 |
| 200432 | 集中力 |
| 200551 | 逃亡者 |
| 200622 | 後方待機 |
| 200631 | 疾風怒濤 |
| 200691 | 慧眼 |
| 200742 | 深呼吸 |
| 200963 | 紫電一閃 |
| 200961 | 短距離直線◎ |
| 200962 | 短距離直線○ |
| 201033 | 豪風一閃 |
| 201103 | 光芒一閃 |

### 3.3 明確不翻譯的部分

- `SkillData.info[1..]`、`description`、`invokes.messages` 中**含數值／條件**的文字
  （距離割合、順位率、殘り距離、目標速度、持続、比較符號、技能 ID 等）一律保留日文，
  避免改動任何參數或條件邏輯。
- `ImportExportConverter` 的匯入／匯出字串、技能搜尋 `findSkills`、
  `skillDataMap2`、`getSkill` 等內部比對邏輯完全未動。

## 四、未匹配技能清單

完整清單見 `data/skill_translation_pending.csv`（含 ID、日文名、原因、建議動作）。

## 五、建置與測試結果

### 5.1 建置

- 環境：Windows / JDK **25.0.4.1**（`D:\app\java\EclipseTemurin`）/ Gradle 9.6.1。
- 指令：`./gradlew compose:wasmJsBrowserDistribution`（執行兩次；第二次是因為建置期間補齊了 2 條
  模擬器提示用語，必須重新編譯以確保產物與原始碼完全一致）。兩次皆 **BUILD SUCCESSFUL**
  （1m56s / 1m39s），只有專案既有的 Koalaplot deprecation 警告與 webpack 體積警告，**無編譯錯誤**。
- 執行環境禁止呼叫 `cmd.exe`，因此未直接執行 `build_wasm.bat`，改為執行其等價步驟：
  Gradle 的 WASM 發布任務 + 將 `compose/build/dist/wasmJs/productionExecutable`
  複製到 `web/src/jsMain/resources/race` 與 `docs/race`（腳本內的 `pause` 略過）。
- 產物：主程式 `01c506e149551bf0aa00.wasm`（5,560,582 bytes）、
  skiko `658820f72b4617efb380.wasm`（8,648,883 bytes）、`wasm.js` / `.map` / `index.html` /
  `composeResources` / 圖片。兩目錄 `wasm.js` 逐位元組相同。
- 注意：新 wasm 檔名與第一階段不同，舊的 `a846e771...wasm`／`dac03b3f...wasm`
  已被取代（建置腳本原本就會清空目錄再複製）。

### 5.2 驗證結果

| 驗證項 | 結果 | 依據 |
| --- | --- | --- |
| WASM 建置成功 | PASS | BUILD SUCCESSFUL ×2 |
| 繁中名稱進入產物 | PASS | `波瀾注意砲！`、`赤色王牌`、`精誠所至` 在 wasm/js 中以 UTF-8 命中 |
| 繁中說明進入產物 | PASS | `在競賽中間附近若位於隊列後方…` 命中 |
| 模擬器提示用語進入產物 | PASS | `發動條件近似` 命中 |
| 日文原名仍保留 | PASS | `波乱注意砲！` 命中 → 搜尋與回退可用 |
| 技能資料仍由遠端載入 | PASS | `レース中間付近で後方だと`、`目標速度1500` 未出現在產物中，代表仍由 `loadSkillData()` 於執行時從 GitHub 取得 `skill_data.txt`，未被硬編碼 |
| 日文名稱搜尋 | PASS | `it.name.contains(filter)` 保留，另加繁中顯示名搜尋 |
| 選擇／取消選擇技能 | PASS（邏輯未動） | `toggleSkill` 與 `skill.id` 參數完全未改 |
| 模擬結果與修改前一致 | PASS | `race/` 模組與 `compose/.../store/` 的 `git status` 為空；Gradle 顯示 `:race:compileKotlinWasmJs UP-TO-DATE`；`:race:jvmTest`（RaceCalculatorTest）**BUILD SUCCESSFUL in 13s** |
| 未翻譯技能回退日文 | PASS | `displayNameZhTw()` / `displayMessagesZhTw()` 皆以 `?: name` / `?: message` 回退 |
| 參數零改動 | PASS | 靜態模擬 3,992 條條件／提示字串，**0 條**數值、比較符號、百分比、技能 ID 被改動 |
| 翻譯資料完整性 | PASS | 2,168 筆 ID 完整覆蓋 CSV、零重複、status 合法；Kotlin 名稱映射 947、說明映射 953 與 JSON 完全一致 |

### 5.3 需要人工目視確認的項目

本環境沒有瀏覽器互動能力，以下項目建議在本地 HTTP Server 預覽時確認（資料與程式碼層面已驗證）：

1. 技能按鈕顯示繁中名稱（已翻譯 947 筆）／未翻譯顯示日文。
2. 滑鼠懸停時，第一段顯示官方繁中說明（953 筆），後面的模擬器條件／數值保持日文。
3. 分類收起摘要與最外層技能摘要顯示繁中名稱。
4. 輸入日文技能名可搜尋；輸入繁中譯名也可搜尋。
5. 技能選擇／取消、模擬次數與結果數值與第一階段一致。

### 5.4 維護提醒

- 請**不要**對 `SkillTranslation.kt` 執行 `umasim_race_zh_hant_phase1.ps1`（第一階段靜態替換腳本），
  否則會破壞比對用的字串常數。
- 若要補齊未翻譯技能，請更新 `data/skill_translation_pending.csv` 後重新產生
  `skill_translation_zh_TW.json` 與 `SkillTranslation.kt`，再重新建置 WASM。
- 36 條 `ui_phrases` 為 `translated_unverified`，用詞請人工校對（見第三節）。

## 六、第三輪排查：固有／進化技能顯示遺漏

### 6.1 結論

回報的「`SkillInput.kt` 仍直接顯示 `skill.name` / `skill.messages`」在**目前工作區並不成立**：
該檔案已在提交 `eacbf9e` 完成相同修改。本輪依「先檢查、不覆蓋任何成果」的要求，
**未改動任何 `.kt` 原始碼、未重新產生翻譯 JSON、未改動比賽演算法**。
以下為逐項查核證據。

### 6.2 `SkillInput.kt` 現況（行號為目前磁碟內容）

| 顯示位置 | 行號 | 目前程式碼 | 匹配鍵 |
| --- | ---: | --- | --- |
| 技能按鈕（普通／固有／進化共用 `SkillChip`） | 171 | `Text(skill.displayNameZhTw())` | **技能 ID** |
| 懸停說明 | 153 | `skill.displayMessagesZhTw().forEach { Text(it) }` | 技能 ID + 固定短語 |
| 最外層收起摘要 | 35 | `hasSkills.joinToString { it.displayNameZhTw() }` | **技能 ID** |
| 分類收起摘要 | 264 | `hasSkills.joinToString { it.displayNameZhTw() }` | **技能 ID** |
| 固有技能 | 224 | `SkillChip(virtual, uniqueSkill, …)` → 第 171 行 | **技能 ID** |
| 進化技能 | 241 | `SkillChip(virtual, skill, …)` → 第 171 行 | **技能 ID** |
| 搜尋 | 74–75 | `it.name.contains(filter) \|\| it.displayNameZhTw().contains(filter)` | 日文原名保留 |

固有與進化技能都經過同一個 `SkillChip`，因此自然以 `SkillData.id` 查表，
未使用日文名稱作為唯一匹配鍵；`toggleSkill(virtual, skill)`、`skillIdSet.contains(skill.id)`
皆未變動。

### 6.3 四個顯示函數（`SkillTranslation.kt` 2767–2789）

| 函數 | 實作 | 回退 |
| --- | --- | --- |
| `SkillData.displayNameZhTw()` | `skillNameZhTw[id] ?: name` | 原始日文名 |
| `SkillData.displayMessagesZhTw()` | 只把 `info[0]` 換成官方繁中說明，其餘走 `noticePhraseZhTw` | 原始日文 |
| `displaySkillNameZhTw(jaName)` | 僅「只有名稱字串」的結果畫面使用，且只收錄全表唯一日文名 | 原始日文 |
| `displayNoticeZhTw(text)` | 模擬器固定提示用語對應 | 原始日文 |

### 6.4 全項目 `SkillData.name` / `messages` 顯示位置排查

| 位置 | 用途 | 狀態 |
| --- | --- | --- |
| `pages/race/SkillInput.kt` 35/153/171/264 | 技能 UI | 已漢化（ID 鍵） |
| `pages/race/ImportExport.kt:63` | 匯入結果預覽 | 已漢化；匯出字串在 `ImportExportConverter`，**未動** |
| `pages/race/ModeInput.kt:82` | 貢獻度勾選標籤 | 已漢化（`id` 參數仍是 `skill.id`） |
| `pages/race/GraphOutput.kt:149,153` | 結果圖表標註 | 已漢化（結果物件只有名稱字串，用唯一名補充映射） |
| `pages/race/ContributionOutput.kt:58,69,74` | 貢獻度表格 | 已漢化；第 45 行 `it.name == entry.name` 是內部比對鍵，未動 |
| `store/operation/RaceOperation.kt` | 模擬結果統計、以 name 分組 | **邏輯層，不可改** |
| `store/ImportExportConverter.kt` | 匯入／匯出格式 | **資料格式，不可改** |
| `mcp/…/getUmaRaceSkillData.kt:31` | MCP 對外 JSON `name` 欄位 | **原始資料介面，不可改** |
| `race/…/test/testCalc2.kt` | 除錯用 `println` | 非 UI |
| `race/…/data2/SkillData.kt` | 資料類別定義 | 不可改 |

結論：**沒有遺漏的 UI 顯示點**。

### 6.5 驗收逐條結果

| 驗收項 | 結果 | 說明 |
| --- | --- | --- |
| 東海帝王等角色固有技能顯示繁中 | **PASS** | `100031 究極テイオーステップ → 終極帝皇舞步`（verified）；`110031 → 絕對是我`、`120031 → 歌舞昇平，趣味橫生` |
| 進化技能名稱顯示繁中 | **受資料限制，未通過** | 見 6.6；顯示層已正確按 ID 接入，但資料表無繁中譯名，依設計回退日文 |
| 普通技能名稱繼續漢化 | PASS | rare 307/331、normal 416/486、minus 47/49 |
| 懸停說明正常 | PASS | 953 條官方繁中說明；條件／數值／符號保留日文 |
| 未翻譯技能回退日文 | PASS | `?: name` / `?: message` |
| 日文搜尋仍然有效 | PASS | `it.name.contains(filter)` 保留，另加繁中搜尋 |
| 技能選擇與模擬邏輯不變 | PASS | `race/`、`store/` 零改動；RaceCalculatorTest 2/2 通過 |
| WASM 建置 | PASS | 見 6.8 |

### 6.6 真正的剩餘缺口（翻譯資料覆蓋，非 UI 遺漏）

| rarity | 總數 | 繁中名稱 | 繁中說明 |
| --- | ---: | ---: | ---: |
| unique（固有） | 295 | 173 | 244 |
| evo（進化） | 638 | **0** | **0** |
| inherit（繼承） | 273 | **0** | **0** |
| scenario（劇本） | 53 | **0** | **0** |

合計 **964 條**無繁中譯名，全部回退日文。原因：UmamusumeDB 繁中站只有
`public/data/skills-lite.zh-tw.json` 一份繁中技能資料（1212 條，多為國際服技能），
已確認該 repo 沒有其他繁中技能資料檔；日服進化／繼承／劇本技能缺乏可靠的台服官方繁中文本。
本輪依「不要重新生成翻譯 JSON、不要覆蓋既有翻譯成果、不編造官方譯名」的要求，
**未補資料、未改 JSON**。若要補齊，需先取得可靠台服官方技能表，再以增量方式補充
（保留既有 947 條已驗證名稱）。

### 6.7 角色選擇器（獨立議題，未與技能名混用）

- 位置：`SkillInput.kt:210` 的 `SelectBox(charaList, charaName, …)`；`charaList` 由
  `skillData2.mapNotNull { it.holder }` 產生，內容是日文角色／服裝名（如 `[トウカイテイオー]…`）；
  預設項常數 `NOT_SELECTED = "(未選択)"`（`race/calc2/RaceState.kt:33`）亦為日文。
- **不能套用技能翻譯**：`charaName` 同時是 `charaToUniqueSkill` / `charaToEvoSkills` 的 key
  （`SkillOperation.kt:9,11` 以 `holder!!` 建立索引）。翻譯顯示值會讓 `setCharaName` 與查表失效；
  角色名與技能名是兩套不同資料，不應混為一談。
- 本輪**僅檢查、未改動**。若後續要漢化角色名，需要獨立的角色名對照表，且只能改
  `SelectBox` 的 `itemToString` 顯示層，值仍須保持原始 `holder` 字串。

### 6.8 第三輪建置與測試

- `./gradlew compose:wasmJsBrowserDistribution :race:jvmTest` → **BUILD SUCCESSFUL**，
  因本輪無原始碼變動，所有任務 `UP-TO-DATE`；WASM 產物未變（`01c506e149551bf0aa00.wasm`）。
- 為避免快取命中，另執行 `./gradlew :race:cleanTest :race:jvmTest` 強制重跑：
  `TEST-io.github.mee1080.umasim.race.calc2.RaceCalculatorTest.xml`
  → **tests=2、failures=0、errors=0、skipped=0**。

### 6.9 本輪修改檔案清單

- **原始碼修改：0 個**（所報問題已於 `eacbf9e` 修復，重複修改反而會覆蓋既有成果）。
- **翻譯 JSON / Kotlin 映射：0 個改動**。
- 僅追加本報告章節：`data/skill_translation_report.md`。
- `.zh-Hant.bak`（14 個）與 `.workbuddy/` 維持 untracked，與上一輪相同。

## 七、第二階段資料補全（任務 A 技能 + 任務 B 角色）

### 7.1 階段 1 現況分析（重新讀檔核實）

- Git：`main` @ `eacbf9e`，與 `origin/main` 同步；開始前有 1 個未提交檔案（`skill_translation_report.md` 第六節，已保留未覆蓋）。
- 技能總數 **2168**、ID 零重複；繁中名稱 **947**、繁中說明 **953**；重複日文名 **273 組／549 行**。
- 覆蓋率：`evo 0/638`、`inherit 0/273`、`scenario 0/53`、`unique 173/295`、`normal 416/486`、`rare 307/331`、`special 4/43`、`minus 47/49`。
- 角色：`charaList` 來自 `skillData2.mapNotNull { it.holder }`，共 **273** 個唯一 holder；`SelectBox` 直接顯示日文 holder，`NOT_SELECTED = "(未選択)"`；`charaToUniqueSkill` / `charaToEvoSkills` 以 `holder!!` 建索引，故 `charaName` 同時是 UI 值與查表鍵。
- 本階段未修改任何檔案。

### 7.2 階段 2 來源調查（關鍵突破）

| 來源 | URL / 落地 | 類型 | 條目 |
| --- | --- | --- | --- |
| GameTora 完整技能表 | `https://gametora.com/data/umamusume`（manifest `/data/manifests/umamusume.json`），經 `shiyuzi-ship-it/umamusumedb` 的 `scripts/import-gametora.mjs` 匯入 `public/data/skills.json` | 台服(TW server)官方遊戲內文本 `i18n.zh_tw.name` / `.desc` | 1948（1595 有繁中） |
| GameTora 角色表 | 同倉庫 `public/data/characters.json` `i18n.zh_tw.name/title` | 台服官方角色名 | 272（265 有繁中） |
| 台服官方公告（佐證） | `uma.komoejoy.com` 平衡性調整公告 | 明確列出「(固有技能)／(繼承技能)」同名 | 佐證用 |

**先前覆蓋率為 0 的真正原因**：`scripts/import-gametora.mjs` 產生 `skills-lite.json` 時使用
`.filter((sk) => sk.cost || sk.rarity === 'unique')`，把沒有 cost 的**進化／繼承／劇本技能整批過濾**；
`skills-lite.zh-tw.json` 只是這個子集的在地化版本。完整 `skills.json` 保留了全部 1948 條的
`i18n.zh_tw`（其中 `evolved` 689 條，`gt_id` 可達 9 位數，與 CSV 進化技能 ID 一致）。

來源驗證：`/data-sources/` 明確說明繁中來自 GameTora 台服官方文本；`src/lib/localized.js` 說明
簡中 `zh` 是由 `zh_tw` 以 OpenCC 轉寫，**本次只使用 `i18n.zh_tw`**，未使用簡中、英文國際服名，
未使用任何機器翻譯。

匹配統計：`gt_id` 命中 1887/2168；`name_jp` 與 CSV 日文名 **1887/1887 完全一致**；
`description_jp == skill_data.txt info[0]` 精確對應 **1520 筆**；ID 匹配失敗 281（含 273 個
umasim 自有 `9xxxxx` 繼承技能）。可新增繁中名稱 551、繁中說明 567。

### 7.3 階段 3 增量補充結果

**只填補原本為空的欄位，既有 947 個名稱與 953 條說明逐筆比對確認零覆蓋；未變更的 1442 列逐欄位完全一致。**

| 項目 | 數量 |
| --- | ---: |
| 新增繁中名稱 | **705**（551 `verified` + 154 派生 `translated_unverified`） |
| 新增繁中說明 | **567**（全數 `verified`） |
| 名稱總數 | 947 → **1652**（76.2%） |
| 說明總數 | 953 → **1520**（70.1%） |
| 變化技能列 | 726 |

各類別覆蓋率（前 → 後）：

| rarity | 總數 | 繁中名稱 | 繁中說明 |
| --- | ---: | --- | --- |
| evo | 638 | 0 → **492** (77.1%) | 0 → **507** (79.5%) |
| normal | 486 | 416 (85.6%) | 395 (81.3%) |
| rare | 331 | 307 (92.7%) | 263 (79.5%) |
| unique | 295 | 173 (58.6%) | 244 (82.7%) |
| inherit | 273 | 0 → **154** (56.4%) | 0（刻意不派生） |
| scenario | 53 | 0 → **31** (58.5%) | 0 → **31** (58.5%) |
| minus | 49 | 47 (95.9%) | 47 (95.9%) |
| special | 43 | 4 → **32** (74.4%) | 4 → **33** (76.7%) |

狀態：`verified` **1593**（原 1021）、`translated_unverified` **154**、`pending` **153**、
`unmatched` **268**；仍未翻譯名稱 **516**、說明 **648**。

**繼承技能（保守處理）**：CSV 的 `9xxxxx` 在 GameTora 沒有對應 ID。本次僅對「`9xxxxx → 1xxxxx`
派生 ID 命中、日文名完全一致、且對應固有技能已有台服官方繁中名」的 **154 條**補名稱，
並以台服官方公告「固有／繼承技能同名」佐證；**標記為 `translated_unverified`**（譯文是官方文本，
但 ID 為派生對應，需人工複核），`notes` 寫明 `derived_mapping_needs_review`。
**說明文字完全不派生**，維持日文回退。

說明翻譯仍嚴守原規則：只有 `description_jp == info[0]` 才使用官方繁中說明；
`info[1..]`、`description`、`invokes.messages` 的條件／數值／比較符號／技能 ID／邏輯關係
一律逐字保留日文（2,702 條條件段檢查，0 條被改動）。

### 7.4 階段 4 角色名稱漢化

- 新增獨立映射 `data/chara_translation_zh_TW.json` 與 `CharaTranslation.kt`，**與技能映射完全分離**。
- 以**完整 holder 字串**為鍵（非日文名猜測）：`title_jp == holder[title]` 且 `name_jp` 以
  `holder[base]` 開頭，雙重核對後取 `i18n.zh_tw.name`；無繁中 title 者保留原服裝名。
- 覆蓋率 **265/273 = 97.1%**，0 個多重候選衝突；`(未選択)` 顯示為「（未選擇）」（UI 佔位文字，
  標 `translated_unverified`）。
- `SkillInput.kt` **只新增 3 行**：2 行註解 + `itemToString = { displayCharaNameZhTw(it) }`。
  `onSelect = { dispatch(setCharaName(virtual, it)) }` 未變，`charaName` / `holder` 內部值保持原始日文；
  `charaToUniqueSkill` / `charaToEvoSkills` 的 273 個鍵全部仍在映射中，關聯不受影響。
- 8 個無台服官方繁中名者保留原文（見 7.8）。

### 7.5 階段 5 代碼接入檢查

普通／固有／進化／繼承／劇本技能全部經同一個 `SkillChip` → `displayNameZhTw()`（技能 ID 鍵）；
懸停說明 `displayMessagesZhTw()`；外層與分類摘要；日文搜尋 `it.name.contains(filter)` 保留、
繁中搜尋 `it.displayNameZhTw().contains(filter)` 保留；角色選擇器顯示層新增。
**未重寫、未重構任何顯示系統**，僅更新 `SkillTranslation.kt` 的三個資料 map 區塊
（函數定義與 36 條提示用語 map 與備份逐字相同）。

### 7.6 修改檔案清單

| 檔案 | 動作 |
| --- | --- |
| `data/skill_translation_zh_TW.json` | 增量更新（726 列），meta 補來源與統計 |
| `compose/…/race/SkillTranslation.kt` | 僅更新 3 個 map 區塊（1652 / 1520 / 1344） |
| `data/skill_translation_pending.csv` | 重新產生未匹配／待補清單 |
| `data/chara_translation_zh_TW.json` | **新增**角色名映射 |
| `compose/…/race/CharaTranslation.kt` | **新增**角色顯示層（266 筆） |
| `compose/…/race/SkillInput.kt` | **+3 行**角色選擇器 `itemToString` |
| `data/skill_translation_report.md` | 追加本章節（第六節未動） |
| `docs/race`、`web/src/jsMain/resources/race` | WASM 產物更新 |

**零改動**：`SkillData.kt`、`data/skill_data.txt`、`SkillOperation.kt`、`RaceOperation.kt`、
`ImportExportConverter.kt`、第一階段 14 個 UI 漢化檔、`ui_phrases`（36 條）。

### 7.7 Git diff 摘要

```
 SkillInput.kt                 |     3 +
 SkillTranslation.kt          |  1827 +/-
 skill_translation_pending.csv|  1722 +/-
 skill_translation_report.md  |   106 +   (上一輪) + 本章節
 skill_translation_zh_TW.json |  9646 +/-
 docs/race + web/.../race     |  舊 wasm 刪除、wasm.js 更新
 新檔案: CharaTranslation.kt / chara_translation_zh_TW.json / 1f3a52626859f496ddff.wasm ×2
```
依要求**未執行 `git commit`、未 push**。

### 7.8 尚未解決的問題

- **516 個技能仍無繁中名稱**（evo 146、unique 122、inherit 119、normal 70、rare 24、
  scenario 22、special 11、minus 2）：GameTora 台服資料本身就沒有繁中文本（多為 JP-only
  或尚未實裝），依要求保留日文，不猜測、不編造。
- **648 條說明仍無繁中**：同上，且繼承技能說明刻意不派生。
- **154 條繼承技能**為派生 ID 對應，標 `translated_unverified`，建議人工複核。
- **36 條模擬器提示用語**仍為 `translated_unverified`，用詞待校對。
- **8 個角色名**無台服官方繁中名，保留原文：`[Divine Raiment]レッドディザイア`、
  `[Fate's Chosen Star]エピファネイア`、`[Lueur Angélique]ヴィクトワールピサ`、
  `[Miracle Author]キセキ`、`[Monochrome GM]ルーラーシップ`、
  `[Éclat de Roseraie]ローズキングダム`、`[熟れしめでたし実り姫]ユキノビジン`、
  `[絶佳の暁闇]ファレノプシス`。
- 本環境無瀏覽器互動能力，**未執行實際瀏覽器目視檢查**；已用產物字串級驗證取代（見 7.9），
  建議於本地 HTTP Server 預覽確認按鈕／懸停／摘要／角色下拉與技能選擇。

### 7.9 階段 6 建置與測試結果

| 項目 | 結果 |
| --- | --- |
| WASM 建置 | `./gradlew compose:wasmJsBrowserDistribution` → **BUILD SUCCESSFUL in 1m43s**（JDK 25.0.4.1）；僅既有 webpack 體積／deprecation 警告，無編譯錯誤 |
| 產物 | `1f3a52626859f496ddff.wasm`（5.5 MiB）+ `658820f72b4617efb380.wasm`，已複製到 `web/src/jsMain/resources/race` 與 `docs/race`，兩目錄 `wasm.js` 逐位元組相同 |
| 模擬計算測試 | `./gradlew :race:cleanTest :race:jvmTest` 強制重跑 → **tests=2, failures=0, errors=0, skipped=0** |
| JSON 解析 | 技能 / 角色兩份 JSON 皆可解析，2168 + 273 筆 |
| 翻譯 ID 完整性／前後對照 | ID 集合 2168 前後一致、零重複；既有繁中名稱／說明零覆蓋；1442 列完全未變 |
| Kotlin ↔ JSON | 名稱 1652、說明 1520、唯一名 1344、角色 266 全部逐筆一致；顯示函數與提示用語 map 未變 |
| 原始資料 | `SkillData.kt` / `skill_data.txt` / `SkillOperation.kt` / `RaceOperation.kt` git status 為空 |
| 參數保全 | 2,702 條條件段逐一檢查，數值／符號 0 改動；`conditions_policy` 全部保留 |
| 日文／繁中搜尋 | `it.name.contains` 與 `it.displayNameZhTw().contains` 皆保留 |
| 技能選擇 | `toggleSkill(virtual, skill)`、`skillIdSet.contains(skill.id)` 未變 |
| 角色與固有技能關聯 | 273 個 holder 鍵全數保留，`onSelect` 仍傳原始 item |
| 產物字串驗證 | 檢出 `飢腸轆轆的大將`、`順時針的輪舞曲`、`不要命令我！！`、`終極帝皇舞步`、`唯獨愛你`、`魯道夫象徵`、`（未選擇）`；日文原名 `波乱注意砲！` 仍存在（回退／搜尋可用） |
| 瀏覽器目視檢查 | **未執行**（無瀏覽器互動能力），見 7.8 |
