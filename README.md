# RETIREMENT 986 — widget Android

Trzy srebrne zegary inspirowane stylistyką deski rozdzielczej Porsche Boxster 986 (2001). Projekt autorski, bez oficjalnych znaków towarowych i grafik Porsche.

## Co pokazuje
- Lewy zegar: pozostałe lata i miesiące.
- Środkowy zegar: **dni do emerytury**, data docelowa **07.11.2028**.
- Prawy zegar: liczba dni pozostałych w ostatnim roku odliczania (maks. 365).
- Wskazówki są czerwone, tarcze srebrne. Środkowa skala wskazówki obejmuje 10 lat, prawa 365 dni.
- Po nadejściu emerytury wyświetla zero, bez ujemnych wartości.

## Uruchomienie
1. Zainstaluj Android Studio (JDK 17, Android SDK 35).
2. Otwórz katalog `Retirement986` jako projekt (File → Open).
3. Android Studio powinno zaproponować synchronizację Gradle. Projekt używa Android Gradle Plugin 8.7.3. Jeśli IDE wymaga wrappera, użyj lokalnej instalacji Gradle 8.9 lub wygeneruj wrapper poleceniem `gradle wrapper --gradle-version 8.9`.
4. Podłącz telefon z Androidem 8.0+ lub uruchom emulator i kliknij Run.
5. Przytrzymaj pusty obszar ekranu głównego → Widgety → Retirement 986 → dodaj widget (najlepiej 4×2 lub większy).

## Budowa APK
Po zsynchronizowaniu: Build → Build APK(s). Wynik: `app/build/outputs/apk/debug/app-debug.apk`.

## Zmiana daty
`app/src/main/java/com/retirement986/widget/RetirementWidget.java` → `RETIREMENT = LocalDate.of(2028,11,7)`.

## Aktualizacje
Android prosi o aktualizację co 30 minut (system może opóźniać wywołania); dodatkowo widget reaguje na zmianę daty, czasu i strefy czasowej. Nie wymaga internetu ani uprawnień.

## Uwagi
Projekt zawiera pełne źródła i zasoby, ale **nie został skompilowany ani uruchomiony w emulatorze** w tym środowisku (brak Android SDK/Gradle). Do wygenerowania APK potrzebne jest Android Studio.
