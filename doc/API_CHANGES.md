# API CHANGES

### Max Smith
### March 24th, 2020

#### Overall Flow
The overall flow of the program has been mostly preserved, with one minor change. The Model has now become a storage
of information and Object functionality, but information goes from the `LogicalController` straight to the `VisualController`,
bypassing the `Model` that was originally brainstormed. We believe that this is a minor change which improves the overall 
design of the program because it further simplifies the logical complexity within the model.

#### Personal APIs
Regarding APIs that I personally worked on, I attempted to refactor all `public` methods into interfaces for appropriate 
classes (e.g. `UserInput` has a `UserInputInterface`) to define their public API calls. It may been a better design
idea to refactor these further by whether they are internal or external to this stage in the design.

#### Original APIs
For the most part our original API decisions from the DESIGN_PLAN.md document were preserved. One major change was in the 
form of the `Command` object public API. These objects now all return a List of Strings to adhere to our code replacement
implementation. However, the locality of all APIs (internal vs. external) were all preserved.