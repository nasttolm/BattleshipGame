# Battleship Game

## About the Project

This is a Battleship game developed as part of a course at London South Bank University (LSBU). The project explores object-oriented programming (OOP) principles and modular design. As part of the coursework, an extension of the game was proposed, introducing new gameplay mechanics and enhancing strategic complexity.

## Project Structure

The Battleship application is organized into several key components:

### 1. Core Components

- **Game Logic:**
  - `Ship` (Base unit representation)
  - `Board` (Game field management)
  - `Square` (Individual cell handling)
  - `Fleet` (Collection of ships for a player)
  - `BoardFactory` (Creates different board configurations)
- **Ship Types:**
  - `Aeroplane`, `AircraftCarrier`, `Battleship`, `Destroyer`, `Submarine`, `SimpleShip`, `TemplateShip`

### 2. Player Management

- `AbstractGame` (Manages game flow and player turns)
- `AbstractPlayer` (Base class for human and AI players)
- `ComputerPlayerStrategy` (Defines AI behavior for computer players)

### 3. User Interfaces

- **CLI (****`cli/`**** package):**
  - `CLIGame`, `CLIPlayer`, `CLIHumanPlayer`, `CLIComputerPlayer`
  - `CLIGameLauncher`
- **GUI (****`gui/`**** package):**
  - `GUIGame`, `GUIPlayer`, `GUIHumanPlayer`, `GUIComputerPlayer`
  - `BoardPanel`, `BoardButton`, `GamePanel`
  - `GUIGameLauncher`

### 4. AI Strategies (`strategy/` package)

- `BasicStrategy`
- `BetterStrategy`
- `BetterStrategyWithDoubleRun`
- `Adjacent`
- `RandomStrategy`

### 5. Additional Components

- `GameType` (Defines different game modes)
- `Outcome` (Represents results of moves)
- `ShipDemo` (For demonstration purposes)
- `FailedToPlaceShipException` (Handles ship placement errors)

## Coursework-Based Enhancements

As part of the coursework, modifications and extensions were proposed to improve gameplay depth:

- **New Terrain Features:** The game expands beyond sea-based battles to include land terrain, requiring adjustments to unit placement logic.
- **Additional Unit Types:** Introduction of land-based units such as `Tanks`, `Bases`, and `AmmoDump`, alongside amphibious units (`Aeroplane`, `AircraftCarrier`).
- **Updated Placement Rules:** Different unit types now have specific placement constraints based on terrain type.
- **GUI Enhancements:** Improved visual representation to distinguish land and sea regions.
- **Technical Improvements:**
  - `Ship` class modified for terrain-based placement rules.
  - `Board` class updated to support mixed terrain.
  - `Square` class now includes terrain type attributes.
  - `Fleet` class extended to manage new unit types.
  - `BoardFactory` updated to generate diverse board configurations.
  - `BoardButton` modified to visualize terrain differences.

These changes adhere to the **Open-Closed Principle**, extending functionality while maintaining backward compatibility.

## How to Run

1. Clone the repository:
   ```sh
   git clone git@github.com:nasttolm/BattleshipGame.git
   ```
2. Navigate to the project folder:
   ```sh
   cd BattleshipGame
   ```
3. Run the CLI version:
   ```sh
   python cli/CLIGameLauncher.py
   ```
4. Run the GUI version:
   ```sh
   python gui/GUIGameLauncher.py
   ```

## Future Improvements

- Improve AI strategies by refining decision-making algorithms.
- Implement multiplayer mode.
- Introduce additional unit types and enhance terrain mechanics.
- Optimize performance, including AI move calculations and rendering improvements.

## Coursework Document

For a detailed analysis of the project’s expansion, refer to the coursework document: [Coursework Link](https://docs.google.com/document/d/1mbn_lRAX9MORVj2itAml0qvdyIe_PH90K7IysVIHm6o/edit?usp=sharing)

## License

This project is for educational purposes and is not licensed for commercial use.

