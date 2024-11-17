# Age of Empires - Turn-Based Strategy Game

This project is a simplified version of a **turn-based Age of Empires** strategy game. It was developed to practice object-oriented programming principles, class design, and game mechanics implementation in Java.

---

## Features
- A **50x100 game map** where players build structures, manage resources, and train units.
- Up to **4 players** can compete to survive and conquer.
- Units include **swordsmen, archers, spearmen, cavalry, catapults, and workers.**
- Buildings include **towers, universities, and main bases.**
- Players gather resources, construct buildings, and attack opponents.

---

## Game Mechanics

### Game Map
The game map is represented by a 50x100 grid. Each cell can contain multiple items, including buildings, soldiers, and workers.

#### Map Class Features:
- **Initialization**: Creates an empty map with each cell initialized as an empty `ArrayList<Item>`.
- **Add Items**: Adds an item to a specific cell on the map.
- **Remove Items**: Removes an item from a specific cell.
- **Map Status**: Displays the current state of the map, showing symbols for buildings, soldiers, and workers.

#### Example Map Display:
- `_` represents an empty cell.
- `W` represents a worker.
- `T` represents a tower.
- `U` represents a university.

T _ _ _ _ _ W _ _ _ _ _ _ _ U


---

### Players and Resources
Players are key components of the game, responsible for controlling units, constructing buildings, and managing resources.

#### Player Class Features:
- **Main Building (M)**: The player's starting building and critical for survival.
- **Resources**: Each player starts with:
  - 100 Wood
  - 100 Gold
  - 100 Stone
- **Units**: Players can train workers and soldiers, including:
  - Swordman (S)
  - Archer (A)
  - Spearman (P)
  - Cavalry (C)
  - Catapult (K)
- **Actions**:
  - Pass turns, gather resources, and attack enemy units or buildings.
  - Construct buildings such as towers and universities.

#### Key Resource Management:
Players earn:
- +10 Wood per turn
- +2 Gold per turn
- +5 Stone per turn

---

### Units and Buildings
Units and buildings are the core components of the game. Each type has unique attributes and roles.

#### Units:
- **Worker (W)**: Builds buildings and attacks weak enemies.
- **Swordman (S)**: A melee soldier with balanced attack and defense.
- **Archer (A)**: A ranged soldier with a high attack range but low defense.
- **Spearman (P)**: Effective against cavalry.
- **Cavalry (C)**: Fast-moving units with high attack power.
- **Catapult (K)**: Long-range siege units with high damage.

#### Buildings:
- **Main Building (M)**: Essential for survival; losing it results in defeat.
- **University (U)**: Enhances unit capabilities.
- **Tower (T)**: Defensive structure with a 7-unit attack range.

---

## Key Classes and Their Roles

- **Map**: Manages the game's 50x100 grid and handles item placement and removal.
- **Player**: Represents individual players, managing their units, buildings, and resources.
- **Worker**: Implements worker actions such as building and attacking.
- **Tower**: Implements defensive building functionalities with an attack range of 7 units.

---

## How to Run
1. Clone this repository:
   ```bash
   git clone https://github.com/your-username/age-of-empires.git

2. Compile the Java files:
    ```bash
    javac src/*.java

3. Run the game:
    ```bash
    java src.Game


