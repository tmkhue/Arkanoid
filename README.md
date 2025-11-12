# Arkanoid Game - Object - Oriented Programming Project
## Author
Group 2 - Class INT2204 11

### Thành viên nhóm
| Họ và tên | MSV |
|-----|--------| 
| Đinh Thị Tuyết Anh | 24021360 | 
| Hà Hoàng Kim Chi | 24021393 | 
| Trần Minh Khuê | 24021537 | 
| Bùi Thị Bích Phượng | 24021601 |

Instructor: Trần Hoàng Việt, Kiều Văn Tuyên

Semester: HK1 năm học 2025-2026

---

## Description
This is a classic Arkanoid game developed in Java as a final project for Object-Oriented Programming course. The project demonstrates the implementation of OOP principles and design patterns.

**Key features:**
1. The game is developed using Java 17+ with JavaFX/Swing for GUI.
2. Implements core OOP principles: Encapsulation, Inheritance, Polymorphism, and Abstraction.
3. Applies multiple design patterns: Singleton, Factory Method, Strategy, Observer, and State.
4. Features multithreading for smooth gameplay and responsive UI.
5. Includes sound effects, animations, and power-up systems.
6. Supports save/load game functionality and leaderboard system.

**Game mechanics:**
- Control a paddle to bounce a ball and destroy bricks
- Collect power-ups for special abilities
- Progress through multiple levels with increasing difficulty
- Score points and compete on the leaderboard

---

## UML Diagram

### Class Diagram
![Class Diagram](![alt text](image.png))

*Complete UML diagrams are available in the `docs/uml/` folder*

---

## Design Patterns Implementation

### 1. Singleton Pattern
**Used in:** `ArkanoidGame`, `MusicManager`

**Purpose:** Đảm bảo chỉ tồn tại 1 instance trong game.
### 2. Factory Method
**Used in:** `PowerUp`, `PowerUpFactory`, `BrickFactory`

**Purpose:** Tạo lớp Power Up để những Power Up cụ thể có thể kế thừa và thay đổi các chức năng chính của Power Up.
### 3. Template method
**Used in:** `Brick`

**Purpose:** Tạo những chức năng chính (số lần va chạm, xử lí va chạm,...) ở lớp cha nhưng để những lớp con viết đè lên 1 số bước cụ thể mà không thay đổi cấu trúc.
### 4. Observer
**Used in:** `ArkanoidGame`, `PowerUpFactory`, `MusicManager`, `Levels`

**Purpose:** Tạo 1 hệ thống để truyền thông tin cho nhiều lớp về 1 sự kiện đang xảy ra.

---

## Installation

1. Clone the project from the repository.
2. Open the project in the IDE.
3. Run the project.

## Usage

### Controls
| Key | Action |
|-----|--------|
| `←` | Di chuyển paddle sang trái |
| `→` | Di chuyển paddle sang phải |
| `↑` | Bắn bóng từ paddle |
| `SPACE` | Pause |
| `ESC` | Give up và về màn hình Game Over |

### How to Play
1. **Start the game**: ấn "START" để di chuyển paddle
2. **Control the paddle**: Sử dụng `←`và `→` hoặc di chuyển chuột để thay đổi vị trí paddle.
3. **Launch the ball**: Press SPACE to launch the ball from the paddle.
4. **Destroy bricks**: Bắn bóng vào gạch để phá gạch
5. **Collect power-ups**: Bắt những power up rơi xuống để kích hoạt power up.
6. **Avoid losing the ball**: Dùng paddle để đỡ bóng
7. **Complete the level**: Phá hủy tất cả những viên gạch có thể phá được để qua bàn tiếp theo.

### Power-ups
| Icon | Name | Effect |
|------|------|--------|
| 🟦 | Widen Paddle | Kéo dài Paddle trong 15 giây |
| ⚡ | Fast Ball | Increases ball speed by 50% |
| 🎯 | Triple Ball | Thêm 2 quả bóng |
| 🔫 | Arrow | Paddle có khả năng bắn mũi tên để xóa gạch |
| 🔥 | Strong Ball | Bóng đi phá gạch và đi xuyên qua không bị bật lại |
| 💗 | Extra Life | Thêm 1 mạng |

### Scoring System
Đối với mỗi khoảng thời gian giữa 2 lần bóng va chạm với paddle liên tiếp, điểm sẽ tăng theo điểm += 10*n với n là số lần bóng va chạm với 1 viên gạch trên màn hình.

---

## Demo

https://drive.google.com/drive/folders/11J0_xOVS681hop5AZCwmSis5ihGAFeHf?usp=drive_link
---

## Future Improvements

### Planned Features
1. **Thêm nhiều Power up hơn**
   - Gạch dính
   - Bóng di chuyển hình vòng cung

2. **Thêm cơ chế lưu màn hình khi đóng game**
   - Vị trí bóng, điểm và mạng còn lại sẽ được lưu cho đến khi người chơi tiếp tục.

3. **Cải thiện lại phần nhìn**
   - Thêm những hiệu ứng animation

---

## Technologies Used

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17+ | Core language |
| JavaFX | 21.0.6 | GUI framework |
| Maven | 3.9.9 | Build tool |


*Last updated: [10/11/2025]*
