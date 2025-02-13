# Game of Life - Toroidal Version

## Description
This project is an implementation of Conway's Game of Life in Java. The distinctive feature of this version is its toroidal grid, meaning that the grid's edges are connected to each other, creating a continuous space in all directions (like the surface of a torus).

## Features

### Grid Initialization
Users can choose between three initialization modes:

1. **Random Mode**:
   - Custom grid width and height
   - Customizable cell density (probability of live cells)

2. **Preset Mode**:
   - Empty grid (50x50)
   - Simple glider (coming soon)
   - Gun (coming soon)

3. **Import Mode** (coming soon):
   - Select a file previously imported into a specific folder

### Game Commands
During execution, the following commands are available:
- `ENTER`: Evolve the grid to the next generation
- `A`: Add a live cell (coordinates required)
- `N`: Display neighbor counts for each cell
- `Q`: Quit the game

### Game Rules
The classic Game of Life rules are implemented:
1. Any live cell with fewer than 2 live neighbors dies (underpopulation)
2. Any live cell with 2 or 3 live neighbors survives
3. Any live cell with more than 3 live neighbors dies (overpopulation)
4. Any dead cell with exactly 3 live neighbors becomes alive (reproduction)

### Technical Features
- Toroidal grid: cells that reach one edge are connected to the opposite edge
- Console display with borders for better readability
- Live cells are represented by 'o'
- Debug mode to visualize the number of neighbors for each cell

## Requirements
- Java Runtime Environment (JRE)
- Console supporting ASCII characters

## Usage
1. Compile and run the program
2. Choose your initialization mode (1 for preset, 2 for random, and 3 to select an imported file)
3. If random mode:
   - Enter desired width and height
   - Set initial population density (between 0 and 1)
4. If select imported file mode:
   - coming soon
5. Use in-game commands to interact with the simulation

## Future Development
- Addition of missing preset configurations (glider, gun)
- Display of live cell count
- Generation counter
- Ability to import configurations from files

## Technical Details
The program is structured around several key methods:
- `evolve()`: Computes the next generation based on the current state
- `countNeighbors()`: Counts live neighbors for a cell, implementing toroidal wrapping
- `randomSeed()`: Generates initial grid with specified density
- `displayGrid()`: Renders the current state with ASCII characters
- `displayNeighborCounts()`: Debug feature showing neighbor counts

### Grid Structure
The grid is implemented as a 2D boolean array where:
- `true` represents a live cell
- `false` represents a dead cell
- Border cells are used to simplify neighbor counting
