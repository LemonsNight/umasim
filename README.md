# umasim（繁体中文汉化版）

> **本仓库是 [mee1080/umasim](https://github.com/mee1080/umasim) 的非官方繁体中文（zh-Hant）汉化分支，由社区维护，并非原作者官方仓库。**

**在线预览（繁体中文界面）**：<https://lemonsnight.github.io/umasim/race/>

原名：ウマ娘育成シミュレータ (umasim)（赛马娘养育模拟器）

> 说明：本文件使用简体中文书写，但项目内的游戏内容已汉化为繁体中文。

## 汉化内容

在保留原模拟器全部功能的基础上，本分支主要完成了以下汉化：

- **界面**：`/race/`（赛事模拟）页面的 UI 已全面翻译为繁体中文。
- **技能数据**：技能名称与说明已对照台服官方文本翻译。根据 [翻译报告](data/skill_translation_report.md)，截至 2026-11-13 已匹配约 947 条官方繁中技能名称、953 条技能说明。
- **角色名称**：角色名称已补充繁体中文数据。

翻译来源为 [UmamusumeDB 繁中站](https://umamusumedb.com/zh-tw/skills/)，其数据底层来自 GameTora 的台服官方游戏内文本，**未使用简体中文机械转换**。

### 已知限制

部分技能尚未找到官方繁中文本，会回退显示日文或英文；详细统计与待校对条目见 [翻译报告](data/skill_translation_report.md)。

## 构建与运行

沿用原项目的 Gradle wrapper，例如：

```bash
./gradlew :compose:run                         # 运行新版 Compose 桌面端
./gradlew :compose:wasmJsBrowserDevelopmentRun  # 运行 Wasm 网页版
```

## 原作者与授权

- 原作者：mee1080，Copyright (c) 2021
- 授权协议：AGPL v3
- 汉化文本基于台服官方游戏文本，相关权利归原权利人所有。

## 其他

`/race/` 模块的部分代码移植自 Romulus Urakagi Tsai (@urakagi) 的 uma-clock-emu：
https://github.com/urakagi/uma-clock-emu
