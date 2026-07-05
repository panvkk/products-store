# Products Store (Из Т-Академии)

Это приложение - классический пример онлайн магазина, с функционалом корзины, настройки уведомлений и просмотра деталей товаров. Для получения списка и деталей товаров используется API dummyjson.com.

Релизный APK можно скачать здесь: [click](https://github.com/panvkk/products-store/blob/hw7/finishing-touches/app/release_apk/app-release.apk)

# 📸 Screenshots

<p align="center">
  <img width="250" alt="Main List" src="https://github.com/user-attachments/assets/033abbdc-8c6f-41f7-a3e9-74c16491213d" />
  <img width="250" alt="Details" src="https://github.com/user-attachments/assets/e661a403-44f0-4ba4-8bc0-88e37344e045" />
  <img width="250" alt="Cart" src="https://github.com/user-attachments/assets/dc09ac74-80ed-4739-84e9-95dddcc76371" />
  <img width="250" alt="Cart hint" src="https://github.com/user-attachments/assets/1a1c092c-ed73-4c05-93f3-7838171f08ea" />
</p>

# 🛠 Tech Stack

**Language**: Kotlin + Coroutines & Flow (реактивные потоки данных).

**UI**: Jetpack Compose (декларативный интерфейс).

**Architecture**: Clean Architecture + The Elm Architecture for UI (В качестве библиотеки [KoTEA](https://github.com/tinkoff-mobile-tech/KoTEA) ).

**DI**: Hilt.

**Persistence**: Room, DataStore.

**Testing**: JUnit4(runner), JUnit5, Espresso, Kaspresso

# 🚀 Key Features

**Пагинация**: На экране со списком реализована ручная пагинация, в связке с шиммером для непрогруженных элементов даёт отличный UX.



**Тестирование**
- *Unit* -- Покрытие мапперов, репозитория, а также (благодаря KoTEA) presentation слой при помощи тестирования чистой фукнции update()
- *Integration* -- Репозиторий карзины
- *UI* -- Сценарии на экранах списка товаров и корзины

# 📄 License

Данный проект является PET-проектом и доступен для ознакомления.
