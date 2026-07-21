# NusaSkills — Companion Plugin

Plugin pendamping (teman) untuk **NusaItems** dan **NusaMobs**. Dibuat tanpa merubah repo asli (`plugin-slimefun`).

## Struktur
- `NusaSkills/src/main/java/com/github/cahyunkk/nusaskills/`
- Integrasi: mendeteksi NusaItems / NusaMobs saat startup
- Skill framework dasar dengan `SkillManager`

## Perintah
- `/nusaskills list` — daftar skill pemain
- `/nusaskills info` — info plugin
- `/nusaskills reload` — reload config

## Build
```bash
cd NusaSkills
./gradlew jar  # atau: gradlew jar
```

Hasil: `build/libs/NusaSkills-1.0.0.jar`

## Deploy Key GitHub — PENTING
Saya **tidak bisa** mengirimkan private deploy key melalui chat karena alasan keamanan (risiko kebocoran). Berikut cara aman:

1. Di server/repo GitHub kamu, buat **Deploy Key** (Settings → Deploy keys → Add deploy key → Allow write access jika perlu push).
2. Salin **public key** ke GitHub.
3. Simpan **private key** (`~/.ssh/id_ed25519_github` atau file `.pem`) di server atau lokal kamu — **jangan** kirimkan melalui chat.
4. Konfigurasi `git remote` menggunakan URL deploy:
   ```bash
   git remote set-url origin git@github.com:cahyunkk/plugin-slimefun.git
   ```
5. Push:
   ```bash
   git push origin main
   ```

Jika kamu ingin saya bantu setup otomatis, berikan akses ke environment server (bukan key mentah via chat), atau berikan instruksi deploy manual.

## Catatan
- Plugin ini **tidak mengubah** isi repo `plugin-slimefun`.
- Dibuat sebagai folder terpisah (`NusaSkills/`) agar mudah dipindah atau diupload sebagai repo baru jika diperlukan.
