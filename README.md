# Plugin Slimefun + Ekosistem Nusa

Repo ini berisi dua bagian utama yang terpisah:

## 1. SLIMEFUN (Inti — folder `src/`, `addon-sources/`, `gradle/`)
Repo utama Slimefun 4 (multi-version, Paper 1.21 – 26.x). Ini adalah plugin dasar yang tidak diubah oleh tim Nusa.

## 2. NUSA PLUGINS (Tambahan — folder `Nusa*`)
Plugin pendamping yang dibuat untuk melengkapi Slimefun:

| Plugin | Fungsi | Bedrock | Integrasi |
|--------|--------|---------|-----------|
| `NusaItems` | Custom item + resource pack otomatis (seperti Oraxen) | ✅ Otomatis | Semua Nusa |
| `NusaMobs` | Custom mob + stats/AI | ✅ Otomatis | Semua Nusa |
| `NusaSkills` | Skill/ability pemain | ✅ Otomatis | NusaRPG, NusaItems |
| `NusaEngine` | Engine stats/AI + model renderer | ✅ Otomatis | NusaMobs, NusaRPG |
| `NusaRPG` | RPG server (class/job/stats/level) | ✅ Otomatis | NusaSkills, NusaAuth |
| `NusaAuth` | Autentikasi (register/login pw + anti-bot) | ✅ Otomatis | NusaRPG |
| `NusaOptimize` | Anti-lag (GC, entity limit, chunk) | ✅ Otomatis | NusaEngine |

### Aturan Utama (untuk semua plugin Nusa)
- `api-version: '1.21'` (terbaru)
- `description`: "Java & Bedrock"
- `softdepend`: `ItemsAdder`, `Oraxen`, `MythicMobs`
- `Resource pack`: otomatis dari `config.yml` (tanpa `.mcpack` manual)
- `Integrasi`: semua plugin Nusa saling deteksi otomatis (`softdepend`, tidak wajib semua aktif)

### Cara Kerja Singkat
1. **Config server**: edit `src/main/resources/config.yml` di setiap plugin
2. **Build**: `./gradlew jar` (atau manual jika Java 21 tidak tersedia)
3. **Deploy**: deploy key SSH (`~/.ssh/nusa_deploy_key`) ke GitHub
4. **Bedrock**: pemain Bedrock langsung melihat model tanpa config manual

### Struktur Folder
```
repo/
  src/                    # Slimefun utama (tidak diubah)
  addon-sources/          # Addon Slimefun
  gradle/                 # Build Slimefun
  .gitignore              # Izinkan release/*.jar
  README.md               # File ini
  NusaItems/              # Plugin custom item
  NusaMobs/               # Plugin custom mob
  NusaSkills/             # Skill system
  NusaEngine/             # Engine + model renderer
  NusaRPG/                # RPG server
  NusaAuth/               # Auth + anti-bot
  NusaOptimize/           # Anti-lag
  release/                # .jar semua plugin Nusa
    NusaItems-1.1.0.jar
    NusaMobs-1.0.0.jar
    NusaSkills-1.0.0.jar
    ...
```

### GitHub
Repo: `https://github.com/cahyunkk/plugin-slimefun.git`
Branch: `main`
Deploy: `SSH deploy key` (private key aman, tidak dikirim melalui chat)

## Download & Search

- [Download semua plugin Nusa](downloads/README.md)
- [Search plugin (HTML)](downloads/search.html)

### Catatan Keamanan
- Private deploy key (`~/.ssh/nusa_deploy_key`) disimpan lokal dengan permission `600`
- Tidak pernah dikirim melalui chat atau log publik
