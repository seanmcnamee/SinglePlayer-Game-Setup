# SinglePlayer-Game-Setup

A frame for a single-player games. Uses Java Swing and JFrame. 

Uses mouse and keyboard event listening. Allows for customs buttons with tints. For a example game from this, look at Battle-Arena. 

Structure is done in the following way: The App class merely loops to run the logic (tick()) and display (render()) of the current page. Each page has to define the functions for logic and display.
To make adding things to the field, all the background logic for positions and collisions has been taken care of in terms of a TOP-DOWN game mindset.

The following classes are used to make game entities: Drawable, Touchable, Movable. All Movable extend Touchables, and all Touchables extend Drawables. 
