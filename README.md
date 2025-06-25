# FeeBee

A simple Android expense tracking app using Jetpack Compose and modular architecture.

## ✨ Features

* [x] Modularized architecture 
* [x] Jetpack Compose UI
* [x] Navigation component support
* [x] Hilt for dependency injection
* [x] Room for local storage

## 🚀 Getting Started

### Prerequisites

* Android Studio Giraffe or newer
* Kotlin 1.9+
* AGP 8.1.0+
* JDK 17+


### Run the app

Open the project in Android Studio and press **Run** ▶️.

---

## 🧱 Project Structure

```
app/               # Entry point of the app
build-logic/       # Custom Gradle conventions/plugins
core/
  - common/
  - data/
  - domain/
  - ui/
  - designsystem/ # Reusable design components
features/
  - spendings/
  - analytics/
  - categories/
```

---

## 🛠️ Built With

* Kotlin
* Jetpack Compose
* Hilt
* Navigation Component
* Room

---

## 🙋‍♂️ Contributing

1. Fork the repo
2. Create a feature branch (`feat/your-feature`)
3. Commit your changes (`git commit -m 'feat: your message'`)
4. Push and create a Pull Request

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

