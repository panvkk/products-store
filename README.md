# Products Store (Из Т-Академии)

Это приложение - классический пример онлайн магазина, с функционалом корзины, настройки уведомлений и просмотра деталей товаров. Для получения списка и деталей товаров используется API dummyjson.com.

[Релизный APK можно скачать здесь](https://github.com/panvkk/products-store/blob/hw7/finishing-touches/app/release_apk/app-release.apk)

**[Видео работы приложения](https://github.com/user-attachments/assets/18952b45-abcd-4cd2-8d05-85f315b6fb3e)**

# 📸 Screenshots

<p align="center">
  <img width="250" alt="Main List" src="https://github.com/user-attachments/assets/033abbdc-8c6f-41f7-a3e9-74c16491213d" />
  <img width="250" alt="Details" src="https://github.com/user-attachments/assets/e661a403-44f0-4ba4-8bc0-88e37344e045" />
  <img width="250" alt="Cart" src="https://github.com/user-attachments/assets/dc09ac74-80ed-4739-84e9-95dddcc76371" />
</p>

# 🛠 Tech Stack

**UI**: Jetpack Compose (декларативный интерфейс).

**Architecture**: Clean Architecture(Data, Domain, Presentation) + The Elm Architecture for UI (В качестве библиотеки [KoTEA](https://github.com/tinkoff-mobile-tech/KoTEA) ).

**Asynchrony**: Kotlin Coroutines & Flow.

**DI**: Hilt.

**Database**: Room, DataStore.

**Network**: Retrofit + Serialization.

**Image Loading**: Glide.

**Testing**: JUnit4(runner), JUnit5, Espresso, Kaspresso

# 🚀 Key Features

**Основной экран**: Каталог товаров с реализованной пагинацией, в связке с шиммером для непрогруженных элементов даёт отличный UX. С этого экрана товары добавляются в корзину

**Детали**: Экран деталей для продуктов

**Корзина**: Корзина покупок, в ней можно установить уведомления для любого товара -- пользователь будет каждый час получать напоминание для совершения покупки

**Обработка отсутствия сети**: При отсутстсвии соединения, в деталях будут отображаться закэшированные данные о товарах. Также пользователь будет видеть снизу бар, говорящий, что соединение отсутствует, и кнопку для попытки обновления данных.

**Разовые события**: Для разовых событий реализован собственный Toast, который имеет кастомную анимацию и помогает в достижении лучшего UX

# Тестирование

**Unit** -- Покрытие мапперов, репозитория, а также (благодаря KoTEA) presentation слой при помощи тестирования чистой фукнции update().

**Integration** -- Репозиторий корзины.

**UI** -- Сценарии на экранах списка товаров и корзины.

# 📄 License

Данный проект является PET-проектом и доступен для ознакомления.
