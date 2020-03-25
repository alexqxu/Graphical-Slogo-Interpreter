# API Changes

## Changes that I decided to do:
#### Command API:

* Removed getValue()
    * This was no longer needed as the command objects were redesigned to execute upon the model.
* Added execute()
    * Uses the Command design pattern to execute actions on the Model (Turtles and Variables)
#### Model Object
* Activate() and Deactivate()
    * Activates and deactivates certain turtles based on ID. This allows for the handling of multiple turtles as defined
    in the complete project implementation specification.

## Changes that others asked from me:

#### Logical Controller API

* setLanguage()
    This was added in to the Logical Controller API to allow for the View to send inputs that change the parsing language
    for user inputs into SLogo.