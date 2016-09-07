The purpose of this document is just to list the functionality of each of the classes as we think of them just to keep us all on the same page. Please add to this as you think of classes and items that need to be included in them.

## GameInstance
* Run the game loop
* Handle assets (spritesheet, textures)

##Window Renderer
* Set render settings
* Display all objects on screen (GLFW and OpenGL)

## InputHandler
* Handle keyboard and mouse input (KeyEventListener and MouseListener)

## Spritesheet
* Create spritesheets

## Level
* Basic class for the levels
* May include algorithm or randomization for levels

## TileManager
* A class to manage collisions of lasers, shapes, and walls.

## Entity
* Base class for an entity (walls, shapes, lasers)

## Wall
* Class unmovable walls/boundries

### Source extends Wall
* Class for the laser source

### Finish (or something along these lines) extends Wall
* Class for where the laser has to reach

## Shape
* Class for movable/rotatable/placeable shapes

## Laser
* Class for the actual laser object

Later on...

## Achievement
* Class for achievements & displaying them