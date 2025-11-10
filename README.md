# Arkanoid Game - Object - Oriented Programming Project
## Author
Group 2 - Class INT2204 11

Đinh Thị Tuyết Anh - 24021360
Hà Hoàng Kim Chi - 24021393
Trần Minh Khuê - 24021537
Bùi Thị Bích Phượng - 24021601
Instructor: [Tên giảng viên]
Semester: [HK1/HK2 - Năm học]

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

_Có dùng hay không và dùng ở đâu_

### 1. Singleton Pattern
**Used in:** `GameManager`, `AudioManager`, `ResourceLoader`

**Purpose:** Ensure only one instance exists throughout the application.

---

## Multithreading Implementation
_Có dùng hay không và dùng như thế nào_

The game uses multiple threads to ensure smooth performance:

1. **Game Loop Thread**: Updates game logic at 60 FPS
2. **Rendering Thread**: Handles graphics rendering (EDT for JavaFX Application Thread)
3. **Audio Thread Pool**: Plays sound effects asynchronously
4. **I/O Thread**: Handles save/load operations without blocking UI

---

## Installation

1. Clone the project from the repository.
2. Open the project in the IDE.
3. Run the project.

## Usage

### Controls
| Key | Action |
|-----|--------|
| `←` | Move paddle left |
| `→` | Move paddle right |
| `SPACE` | Launch ball / Shoot laser |
| `P` or `ESC` | Pause game |
| `R` | Restart game |
| `Q` | Quit to menu |

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

### Screenshots

**Main Menu**  
[<img width="1623" height="1651" alt="image" src="https://github.com/user-attachments/assets/609e8cd5-d1d9-4d00-a1f1-e84b61239dbf" />](https://drive.google.com/file/d/1B6SImK5VKgddLgWHz-xwudOdHq16cVnz/view?usp=drive_link)

**Music Settings**
https://drive.google.com/open?id=1X1AoK-rtSsMe2otat1kEibC5PLAvSxM6&usp=drive_copy

**Gameplay**  
https://drive.google.com/open?id=1X1AoK-rtSsMe2otat1kEibC5PLAvSxM6&usp=drive_copy

**Game in progress**  
<img width="1598" height="1658" alt="image" src="https://github.com/user-attachments/assets/b9d69c60-d714-472f-8061-68f6961e0587" />

**Leaderboard**  
https://drive.google.com/file/d/1OC5rtuqmhx7wi4Qt1_6sM7YsaTI8R0H6/view?usp=drive_link

### Video Demo

*Full gameplay video is available in `docs/demo/gameplay.mp4`*

---

## Future Improvements

### Planned Features
1. **Additional game modes**
   - Time attack mode
   - Survival mode with endless levels
   - Co-op multiplayer mode

2. **Enhanced gameplay**
   - Boss battles at end of worlds
   - More power-up varieties (freeze time, shield wall, etc.)
   - Achievements system

3. **Technical improvements**
   - Migrate to LibGDX or JavaFX for better graphics
   - Add particle effects and advanced animations
   - Implement AI opponent mode
   - Add online leaderboard with database backend

---

## Technologies Used

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17+ | Core language |
| JavaFX | 19.0.2 | GUI framework |
| Maven | 3.9+ | Build tool |
| Jackson | 2.15.0 | JSON processing |


---

## Notes

- The game was developed as part of the Object-Oriented Programming with Java course curriculum.
- All code is written by group members with guidance from the instructor.
- Some assets (images, sounds) may be used for educational purposes under fair use.
- The project demonstrates practical application of OOP concepts and design patterns.

---

*Last updated: [10/11/2025]*
